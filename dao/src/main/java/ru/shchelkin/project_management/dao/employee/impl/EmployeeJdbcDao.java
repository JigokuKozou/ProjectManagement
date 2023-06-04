package ru.shchelkin.project_management.dao.employee.impl;

import lombok.NonNull;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.dao.employee.EmployeeDao;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;
import ru.shchelkin.project_management.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static ru.shchelkin.project_management.dao.employee.connector.DatabaseConnector.getConnection;

public class EmployeeJdbcDao implements EmployeeDao {
    public static final String TABLE_NAME = "employee";

    @Override
    public void create(@NonNull Employee employee) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + TABLE_NAME +
                            "(surname, name, patronymic, job_title, email, status, login, password)" +
                            " VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, employee.getSurname());
            statement.setString(2, employee.getName());
            statement.setString(3, employee.getPatronymic());
            statement.setString(4, employee.getJobTitle());
            statement.setString(5, employee.getEmail());
            statement.setInt(6, employee.getStatus().ordinal());
            statement.setString(7, employee.getLogin());
            statement.setString(8, employee.getPassword());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0)
                throw new RuntimeException("Employee was not saved");

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (!generatedKeys.next())
                throw new RuntimeException("Generated key was not return");

            employee.setId(generatedKeys.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException("Creation error", e);
        }
    }

    @Override
    public void update(@NonNull Employee updated) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE " + TABLE_NAME +
                            " SET surname=?, " +
                            "name=?, " +
                            "patronymic=?, " +
                            "job_title=?, " +
                            "email=?, " +
                            "status=?, " +
                            "login=?, " +
                            "password=? " +
                            "WHERE id=?");

            statement.setString(1, updated.getSurname());
            statement.setString(2, updated.getName());
            statement.setString(3, updated.getPatronymic());
            statement.setString(4, updated.getJobTitle());
            statement.setString(5, updated.getEmail());
            statement.setInt(6, updated.getStatus().ordinal());
            statement.setString(7, updated.getLogin());
            statement.setString(8, updated.getPassword());
            statement.setLong(9, updated.getId());

            if (statement.executeUpdate() == 0)
                throw new NoSuchElementException("Employee with id:'" + updated.getId() + "' not found");
        } catch (SQLException e) {
            throw new RuntimeException("Updating error", e);
        }
    }

    @Override
    public Optional<Employee> getById(Long id) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, surname, name, patronymic, job_title, email, status, login, password " +
                            "FROM " + TABLE_NAME +
                            " WHERE id=?");

            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();
            if (!result.next())
                return Optional.empty();

            Employee employee = getEmployeeFromResultSet(result);

            return Optional.of(employee);
        } catch (SQLException e) {
            throw new RuntimeException("Getting error", e);
        }
    }

    @Override
    public List<Employee> getAll() {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, surname, name, patronymic, job_title, email, status, login, password " +
                            "FROM " + TABLE_NAME);

            ResultSet resultSet = statement.executeQuery();

            List<Employee> result = new ArrayList<>();
            while (resultSet.next())
                result.add(getEmployeeFromResultSet(resultSet));

            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Getting error", e);
        }
    }

    @Override
    public List<Employee> getAll(FilterEmployeeByTeamRoleDto filterDao) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT e.id, e.surname, e.name, e.patronymic, e.job_title, " +
                            "e.email, e.status, e.login, e.password " +
                            "FROM " + TABLE_NAME +" e JOIN team_member tm ON e.id = tm.employee_id " +
                            "JOIN project_team pt ON tm.team_id = pt.id " +
                            "JOIN project p on pt.project_id = p.id " +
                            "WHERE p.code_name = '" + filterDao.getProjectCodeName() + "' " +
                            "AND tm.role=" + filterDao.getTeamRole().ordinal());

            ResultSet resultSet = statement.executeQuery();

            List<Employee> result = new ArrayList<>();
            while (resultSet.next())
                result.add(getEmployeeFromResultSet(resultSet));

            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Getting error", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM " + TABLE_NAME +
                            " WHERE id=?");

            statement.setLong(1, id);

            if (statement.execute())
                throw new NoSuchElementException("Employee with id:'" + id + "' not found");
        } catch (SQLException e) {
            throw new RuntimeException("Deleting error", e);
        }
    }

    public static Employee getEmployeeFromResultSet(ResultSet result) throws SQLException {
        return Employee.builder()
                .id(result.getLong(1))
                .surname(result.getString(2))
                .name(result.getString(3))
                .patronymic(result.getString(4))
                .jobTitle(result.getString(5))
                .email(result.getString(6))
                .status(EmployeeStatus.valueOf(result.getInt(7)))
                .login(result.getString(8))
                .password(result.getString(9))
                .build();
    }
}
