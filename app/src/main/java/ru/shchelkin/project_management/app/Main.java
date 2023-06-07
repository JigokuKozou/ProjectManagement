package ru.shchelkin.project_management.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.shchelkin.project_management.commons.role.TeamRole;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.controller.employee.EmployeeController;
import ru.shchelkin.project_management.dto.request.employee.*;
import ru.shchelkin.project_management.dto.request.filter.FilterEmployeeByTeamRoleDto;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private final EmployeeController employeeController;

    @Autowired
    public Main(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        // ---------------- Create ---------------------------------------
        final CreateEmployeeDto createEmployeeDto = new CreateEmployeeDto();
        createEmployeeDto.setSurname("Иванов");
        createEmployeeDto.setName("Иван");
        createEmployeeDto.setPatronymic("Иванович");
        createEmployeeDto.setJobTitle("Разработчик");
        createEmployeeDto.setEmail("ivanovDev@mail.com");
        createEmployeeDto.setLogin("ivanov");
        createEmployeeDto.setPassword("12345678");

        var createdEmployee = employeeController.create(createEmployeeDto);
        System.out.println("Созданный сотрудник \n" + createdEmployee);

        // ---------------- Get by id ------------------------------------
        final GetEmployeeByIdDto getEmployeeByIdDto = new GetEmployeeByIdDto();
        getEmployeeByIdDto.setId(createdEmployee.getId());

        var getByIdEmployee = employeeController.getById(getEmployeeByIdDto);
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

        var updatedEmployee = employeeController.update(updateEmployeeDto);
        System.out.println("Обновлен созданный\n" + updatedEmployee);

        // ------------------ Delete -------------------------------------
        final DeleteEmployeeDto deleteEmployeeDto = new DeleteEmployeeDto(createdEmployee.getId());
        employeeController.delete(deleteEmployeeDto);
        System.out.println("Удален");
        employeeController.getAll().forEach(System.out::println);

        // ------------------ Filter DAO ---------------------------------
        final var filterDao = new FilterEmployeeByTeamRoleDto();
        filterDao.setProjectCodeName("projectA");
        filterDao.setTeamRole(TeamRole.DEVELOPER);

        System.out.println("\nПоиск по проекту: '" + filterDao.getProjectCodeName() + "' " +
                "и роли в команде: " + filterDao.getTeamRole());
        employeeController.getAll(filterDao).forEach(System.out::println);

        final var searchDto = new SearchEmployeeDto();
        searchDto.setSurname(null);
        searchDto.setName(null);
        searchDto.setPatronymic(null);
        searchDto.setEmail("@gmail.com");
        searchDto.setLogin(null);

        System.out.println("\nПоиск по: " + searchDto);
        employeeController.getAll(searchDto).forEach(System.out::println);
        // ---------------------------------------------------------------
    }
}
