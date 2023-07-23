package ru.shchelkin.project_management.business.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.shchelkin.project_management.commons.status.EmployeeStatus;
import ru.shchelkin.project_management.model.Employee;

import java.util.Collection;
import java.util.Objects;

public class AccountDetails implements UserDetails {

    private final Employee employee;

    private final Collection<? extends GrantedAuthority> roles;

    public AccountDetails(Employee employee, Collection<? extends GrantedAuthority> roles) {
        this.employee = employee;
        this.roles = roles;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(employee.getStatus(), EmployeeStatus.ACTIVE);
    }
}
