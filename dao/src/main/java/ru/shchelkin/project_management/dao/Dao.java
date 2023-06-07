package ru.shchelkin.project_management.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void create(T entity);

    void update(T updated);

    Optional<T> getById(Long id);

    Optional<T> getByLogin(String login);

    List<T> getAll();

    void deleteById(Long id);
}
