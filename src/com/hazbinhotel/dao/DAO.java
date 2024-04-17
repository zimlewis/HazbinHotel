package com.hazbinhotel.dao;

import java.util.List;

public interface DAO<T> {
    T get(int id);
    List<T> getAll();
    int insert(T t);
    int update(T t);
    int delete(int id);
}
