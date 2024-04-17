package com.hazbinhotel.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hazbinhotel.entity.Service;
import com.zimlewis.ZQL;

public class ServiceDAO implements DAO<Service>{

    private Connection connection;

    public ServiceDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Service get(int id) {        
        Service b = null;
        ArrayList<Map<String, Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from service where id = ?", id);

        if (result.size() != 0){
            b = new Service(
                (int) result.get(0).get("id"),
                String.valueOf(result.get(0).get("name")),
                String.valueOf(result.get(0).get("note")),
                Double.parseDouble(String.valueOf(result.get(0).get("price")))
            );
        }

        return b;
    }

    @Override
    public List<Service> getAll() {
        List<Service> list = new ArrayList<Service>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from service");

        for (int i = 0; i < result.size(); i++){
            Service b = new Service(
                (int) result.get(i).get("id"),
                String.valueOf(result.get(i).get("name")),
                String.valueOf(result.get(i).get("note")),
                Double.parseDouble(String.valueOf(result.get(i).get("price")))
            );

            list.add(b);
        }

        return list;
    }

    @Override
    public int insert(Service t) {
        int result = ZQL.excuteQuery(
            connection, 
            "insert into service(name, note, price) values (?, ?, ?)",
            t.getName(),
            t.getNote(),
            new BigDecimal(t.getPrice())
        );

        return result;
    }

    @Override
    public int update(Service t) {
        int result = ZQL.excuteQuery(
            connection, 
            "update service set name = ?, note = ?, price = ? where id = ?",
            t.getName(),
            t.getNote(),
            new BigDecimal(t.getPrice()),
            t.getId()
        );

        return result;
    }

    @Override
    public int delete(int id) {
        int result = ZQL.excuteQuery(
            connection, 
            "delete from service where id = ?", 
            id
        );
        
        return result;
    }
    
}
