package ru.shchelkin.project_management.app;

import ru.shchelkin.project_management.business.service.employee.EmployeeService;
import ru.shchelkin.project_management.business.service.employee.impl.EmployeeServiceDao;
import ru.shchelkin.project_management.commons.role.TeamRole;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.controller.employee.EmployeeController;
import ru.shchelkin.project_management.dao.employee.impl.EmployeeDao;
import ru.shchelkin.project_management.dao.employee.impl.EmployeeJdbcDao;
import ru.shchelkin.project_management.dto.request.employee.CreateEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.DeleteEmployeeDto;
import ru.shchelkin.project_management.dto.request.employee.GetEmployeeByIdDto;
import ru.shchelkin.project_management.dto.request.employee.UpdateEmployeeDto;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;

public class Main {
    public static void main(String[] args) {
        EmployeeDao dao = new EmployeeJdbcDao();
        EmployeeService service = new EmployeeServiceDao(dao);
        EmployeeController controller = new EmployeeController(service);

        // ---------------- Create ---------------------------------------
        final CreateEmployeeDto createEmployeeDto = new CreateEmployeeDto();
        createEmployeeDto.setSurname("Иванов");
        createEmployeeDto.setName("Иван");
        createEmployeeDto.setPatronymic("Иванович");
        createEmployeeDto.setJobTitle("Разработчик");
        createEmployeeDto.setEmail("ivanovDev@mail.com");
        createEmployeeDto.setLogin("ivanov");
        createEmployeeDto.setPassword("12345678");

        var createdEmployee = controller.create(createEmployeeDto);
        System.out.println("Созданный сотрудник \n" + createdEmployee);

        // ---------------- Get by id ------------------------------------
        final GetEmployeeByIdDto getEmployeeByIdDto = new GetEmployeeByIdDto();
        getEmployeeByIdDto.setId(createdEmployee.getId());

        var getByIdEmployee = controller.getById(getEmployeeByIdDto);
        System.out.println("Получение по id \n" + getByIdEmployee);

        // ---------------- Update ---------------------------------------
        final UpdateEmployeeDto updateEmployeeDto = new UpdateEmployeeDto();
        updateEmployeeDto.setId(createdEmployee.getId());
        updateEmployeeDto.setSurname("Иванов");
        updateEmployeeDto.setName("Иван");
        updateEmployeeDto.setPatronymic("Иванович");
        updateEmployeeDto.setJobTitle("Менеджер проекта");
        updateEmployeeDto.setEmail("ivanovPm@mail.com");
        updateEmployeeDto.setLogin("ivanov");
        updateEmployeeDto.setPassword("12345678");
        updateEmployeeDto.setStatus(EmployeeStatus.ACTIVE);

        var updatedEmployee = controller.update(updateEmployeeDto);
        System.out.println("Обновлен созданный\n" + updatedEmployee);

        // ------------------ Delete -------------------------------------
        final DeleteEmployeeDto deleteEmployeeDto = new DeleteEmployeeDto(createdEmployee.getId());
        controller.delete(deleteEmployeeDto);
        System.out.println("Удален");
        controller.getAll().forEach(System.out::println);

        // ------------------ Filter DAO ---------------------------------
        final var filterDao = new FilterEmployeeByTeamRoleDto();
        filterDao.setProjectCodeName("projectA");
        filterDao.setTeamRole(TeamRole.DEVELOPER);

        System.out.println("\nПоиск по проекту: '" + filterDao.getProjectCodeName() + "' " +
                "и роли в команде: " + filterDao.getTeamRole());
        controller.getAll(filterDao).forEach(System.out::println);
        // ---------------------------------------------------------------
    }
}
