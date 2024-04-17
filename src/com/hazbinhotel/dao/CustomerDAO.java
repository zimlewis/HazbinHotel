package com.hazbinhotel.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hazbinhotel.entity.Customer;
import com.zimlewis.ZQL;

public class CustomerDAO implements DAO<Customer>{

    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Customer get(int id) {
        Customer b = null;
        ArrayList<Map<String, Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from customer where id = ?", id);

        if (result.size() != 0){
            b = new Customer(
                (int) result.get(0).get("id"),
                String.valueOf(result.get(0).get("name")),
                String.valueOf(result.get(0).get("phone")),
                String.valueOf(result.get(0).get("personal_id")),
                ((Date) result.get(0).get("birthday")).toLocalDate(),
                String.valueOf(result.get(0).get("type")),
                String.valueOf(result.get(0).get("email")),
                String.valueOf(result.get(0).get("note"))
            );
        }

        return b;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<Customer>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from customer");

        for (int i = 0; i < result.size(); i++){
            Customer b = new Customer(
                (int) result.get(i).get("id"),
                String.valueOf(result.get(i).get("name")),
                String.valueOf(result.get(i).get("phone")),
                String.valueOf(result.get(i).get("personal_id")),
                ((Date) result.get(i).get("birthday")).toLocalDate(),
                String.valueOf(result.get(i).get("type")),
                String.valueOf(result.get(i).get("email")),
                String.valueOf(result.get(i).get("note"))
            );

            list.add(b);
        }

        return list;
    }

    @Override
    public int insert(Customer customer) {
        int result = ZQL.excuteQuery(
            connection, 
            "insert into customer(name, phone, personal_id, birthday, type, email, note) values (? , ?, ? , ?, ?, ?, ?)",
            customer.getName(),
            customer.getPhone(),
            customer.getPersonalId(),
            customer.getBirthday(),
            customer.getType(),
            customer.getEmail(),
            customer.getNote()
        );

        return result;
    }

    @Override
    public int update(Customer t) {
        int result = ZQL.excuteQuery(
            connection, 
            "update customer set name = ?, phone = ?, personal_id = ?, birthday = ?, type = ?, email = ?, note = ? where id = ?",
            t.getPhone(),
            t.getPhone(),
            t.getPersonalId(),
            t.getBirthday(),
            t.getType(),
            t.getEmail(),
            t.getNote(),
            t.getId()
        );

        return result;
    }

    @Override
    public int delete(int id) {
        int result = ZQL.excuteQuery(
            connection, 
            "delete from customer where id = ?", 
            id
        );
        
        return result;
    }
    
}
