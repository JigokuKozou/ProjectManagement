package ru.shchelkin.project_management.dao.employee.impl;

import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.dao.Dao;
import ru.shchelkin.project_management.model.Employee;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class EmployeeDataStorage implements Dao<Employee> {
    private final static Path FILE_PATH_DEFAULT = Path.of("employeeDatabase.txt");

    private final File databaseFile;

    private final List<Employee> employees;

    private Long nextFreeId = 0L;

    public EmployeeDataStorage() {
        this(FILE_PATH_DEFAULT);
    }

    public EmployeeDataStorage(Path filePath) {
        employees = new ArrayList<>();
        databaseFile = filePath.toFile();

        if (databaseFile.isDirectory())
            throw new IllegalArgumentException("filePath does not lead to a file");

        boolean isCreated;
        try {
            isCreated = databaseFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!isCreated)
            readFileToMemory();
    }

    private void readFileToMemory() {
        try (FileReader reader = new FileReader(databaseFile)) {
            Scanner scanner = new Scanner(reader);
            nextFreeId = scanner.nextLong();
            while (scanner.hasNext()) {
                var employee = Employee.builder()
                        .id(scanner.nextLong())
                        .surname(scanner.next())
                        .name(scanner.next())
                        .patronymic(scanner.next())
                        .jobTitle(scanner.next())
                        .email(scanner.next())
                        .status(EmployeeStatus.valueOf(scanner.next()))
                        .login(scanner.next())
                        .password(scanner.next())
                        .build();

                employees.add(employee);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeMemoryToFile() {
        try (FileWriter fileWriter = new FileWriter(databaseFile, false);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println(nextFreeId.toString());
            for (var employee : employees) {
                printWriter.println(employee.getId().toString());
                printWriter.println(employee.getSurname());
                printWriter.println(employee.getName());
                printWriter.println(employee.getPatronymic());
                printWriter.println(employee.getJobTitle());
                printWriter.println(employee.getEmail());
                printWriter.println(employee.getStatus().toString());
                printWriter.println(employee.getLogin());
                printWriter.println(employee.getPassword());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create(Employee newEntity) {
        newEntity.setId(nextFreeId++);

        employees.add(newEntity);

        writeMemoryToFile();
    }

    @Override
    public void update(Employee updated) {
        Employee employeeFromDB = getById(updated.getId())
                .orElseThrow(NoSuchElementException::new);

        copyProperties(updated, employeeFromDB, false);

        writeMemoryToFile();
    }

    @Override
    public Optional<Employee> getById(Long id) {
        for (var employee : employees) {
            if (Objects.equals(employee.getId(), id))
                return Optional.of(employee);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Employee> getByLogin(String login) {
        for (var employee : employees) {
            if (Objects.equals(employee.getLogin(), login))
                return Optional.of(employee);
        }

        return Optional.empty();
    }

    @Override
    public List<Employee> getAll() {
        return List.copyOf(employees);
    }

    @Override
    public void deleteById(Long id) {
        employees.removeIf(employee -> Objects.equals(employee.getId(), id));
        writeMemoryToFile();
    }

    private static void copyProperties(Employee from, Employee to, boolean copyId) {
        if (copyId)
            to.setId(from.getId());

        to.setSurname(from.getSurname());
        to.setName(from.getName());
        to.setPatronymic(from.getPatronymic());
        to.setJobTitle(from.getJobTitle());
        to.setEmail(from.getEmail());
        to.setStatus(from.getStatus());
        to.setLogin(from.getLogin());
        to.setPassword(from.getPassword());
    }
}
