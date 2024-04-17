package com.hazbinhotel.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hazbinhotel.entity.RoomType;
import com.zimlewis.ZQL;

public class RoomTypeDAO implements DAO<RoomType>{

    private Connection connection;

    public RoomTypeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public RoomType get(int id) {
        RoomType b = null;
        ArrayList<Map<String, Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from room_type where id = ?", id);

        if (result.size() != 0){
            b = new RoomType(
                (int) result.get(0).get("id"),
                String.valueOf(result.get(0).get("name")),
                (int) result.get(0).get("capacity"),
                Double.parseDouble(String.valueOf(result.get(0).get("price")))
            );
        }

        return b;
    }

    @Override
    public List<RoomType> getAll() {
        List<RoomType> list = new ArrayList<RoomType>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from room_type");

        for (int i = 0; i < result.size(); i++){
            RoomType b = new RoomType(
                (int) result.get(i).get("id"),
                String.valueOf(result.get(i).get("name")),
                (int) result.get(i).get("capacity"),
                Double.parseDouble(String.valueOf(result.get(i).get("price")))
            );

            list.add(b);
        }

        return list;
    }

    @Override
    public int insert(RoomType t) {
        int result = ZQL.excuteQuery(
            connection, 
            "insert into room_type(name, capacity, price) values (?, ?, ?)",
            t.getName(),
            t.getCapacity(),
            new BigDecimal(t.getPrice())
        );

        return result;
    }

    @Override
    public int update(RoomType t) {
        int result = ZQL.excuteQuery(
            connection, 
            "update room_type set name = ?, capacity = ?, price = ? where id = ?",
            t.getName(),
            t.getCapacity(),
            new BigDecimal(t.getPrice()),
            t.getId()
        );

        return result;
    }

    @Override
    public int delete(int id) {
        int result = ZQL.excuteQuery(
            connection, 
            "delete from room_type where id = ?", 
            id
        );
        
        return result;
    }
        
}
