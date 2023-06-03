package ru.shchelkin.project_management.commons.status;

public enum EmployeeStatus {
    ACTIVE, DELETED;

    private static EmployeeStatus[] values = EmployeeStatus.values();

    public static EmployeeStatus valueOf(int ordinal) {
        return values[ordinal];
    }
}