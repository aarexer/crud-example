package com.github.aarexer.crud.dao;

import com.github.aarexer.crud.model.DaoModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

interface DAO<T extends DaoModel> {
    /**
     * Add element to the database.
     *
     * @param element which would added.
     * @throws SQLException if operation can't be executed.
     */
    void add(T element) throws SQLException;

    /**
     * Remove element by id from the database.
     *
     * @param id of element which would be removed.
     * @throws SQLException if operation can't be executed.
     */
    void remove(int id) throws SQLException;

    /**
     * Get element with specify id from database.
     *
     * @param id of element which would be added.
     * @return Element.
     * @throws SQLException if operation can't be executed.
     */
    Optional<T> get(int id) throws SQLException;

    /**
     * Get all elements from database.
     *
     * @return all elements of in database
     * @throws SQLException if operation can't be executed.
     */
    Optional<List<T>> getAll() throws SQLException;

    /**
     * Clear table.
     *
     * @throws SQLException if operation can't be executed.
     */
    void clear() throws SQLException;

    /**
     * Add elements to database.
     *
     * @param elements which would be added.
     * @throws SQLException if operation can't be executed.
     */
    void addAll(List<T> elements) throws SQLException;
}
