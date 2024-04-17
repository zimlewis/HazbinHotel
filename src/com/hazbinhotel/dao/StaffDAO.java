package com.hazbinhotel.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hazbinhotel.entity.Staff;
import com.zimlewis.ZQL;

public class StaffDAO implements DAO<Staff>{

    private Connection connection;

    public StaffDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Staff get(int id) {
        Staff b = null;
        ArrayList<Map<String, Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from staff where id = ?", id);

        if (result.size() != 0){
            b = new Staff(
                (int) result.get(0).get("id"),
                String.valueOf(result.get(0).get("name")),
                String.valueOf(result.get(0).get("email")),
                String.valueOf(result.get(0).get("phone")),
                String.valueOf(result.get(0).get("password")),
                String.valueOf(result.get(0).get("personal_id")),
                ((Date) result.get(0).get("birthday")).toLocalDate(),
                String.valueOf(result.get(0).get("address")),
                (boolean) result.get(0).get("gender"),
                (byte[]) result.get(0).get("avatar"),
                (Integer) result.get(0).get("manager"),
                ((boolean) result.get(0).get("role"))?1:0
            );
        }

        return b;
    }

    @Override
    public List<Staff> getAll() {
        List<Staff> list = new ArrayList<Staff>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from staff");

        for (int i = 0; i < result.size(); i++){
            Staff b = new Staff(
                (int) result.get(i).get("id"),
                String.valueOf(result.get(i).get("name")),
                String.valueOf(result.get(i).get("email")),
                String.valueOf(result.get(i).get("phone")),
                String.valueOf(result.get(i).get("password")),
                String.valueOf(result.get(i).get("personal_id")),
                ((Date) result.get(i).get("birthday")).toLocalDate(),
                String.valueOf(result.get(i).get("address")),
                (boolean) result.get(i).get("gender"),
                (byte[]) result.get(i).get("avatar"),
                (Integer) result.get(i).get("manager"),
                ((boolean) result.get(i).get("role"))?1:0
            );

            list.add(b);
        }

        return list;
    }

    public List<Staff> getManagers(){
        List<Staff> list = new ArrayList<Staff>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from staff where role = 1");

        for (int i = 0; i < result.size(); i++){
            Staff b = new Staff(
                (int) result.get(i).get("id"),
                String.valueOf(result.get(i).get("name")),
                String.valueOf(result.get(i).get("email")),
                String.valueOf(result.get(i).get("phone")),
                String.valueOf(result.get(i).get("password")),
                String.valueOf(result.get(i).get("personal_id")),
                ((Date) result.get(i).get("birthday")).toLocalDate(),
                String.valueOf(result.get(i).get("address")),
                (boolean) result.get(i).get("gender"),
                (byte[]) result.get(i).get("avatar"),
                (Integer) result.get(i).get("manager"),
                ((boolean) result.get(i).get("role"))?1:0
            );

            list.add(b);
        }

        return list;
    }

    public List<Staff> getStaffOfManagers(int id){
        List<Staff> list = new ArrayList<Staff>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from staff where manager = ?", id);

        for (int i = 0; i < result.size(); i++){
            Staff b = new Staff(
                (int) result.get(i).get("id"),
                String.valueOf(result.get(i).get("name")),
                String.valueOf(result.get(i).get("email")),
                String.valueOf(result.get(i).get("phone")),
                String.valueOf(result.get(i).get("password")),
                String.valueOf(result.get(i).get("personal_id")),
                ((Date) result.get(i).get("birthday")).toLocalDate(),
                String.valueOf(result.get(i).get("address")),
                (boolean) result.get(i).get("gender"),
                (byte[]) result.get(i).get("avatar"),
                (Integer) result.get(i).get("manager"),
                ((boolean) result.get(i).get("role"))?1:0
            );

            list.add(b);
        }

        return list;
    }

    @Override
    public int insert(Staff t) {
        int result = ZQL.excuteQuery(
            connection, 
            "insert into staff(name, email, phone, password, personal_id, birthday, address, gender, role, manager, avatar) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            t.getName(),
            t.getEmail(),
            t.getPhone(),
            t.getPassword(),
            t.getPersonalId(),
            t.getBirthday(),
            t.getAddress(),
            t.isFemale(),
            t.getRole(),
            t.getManager(), 
            t.getAvatar()
        );

        return result;
    }

    @Override
    public int update(Staff t) {
        int result = ZQL.excuteQuery(
            connection, 
            "update staff set name = ?, email = ?, phone = ?, password = ?, personal_id = ?,birthday = ?, address = ?, gender = ?, avatar = ?, manager = ?, role = ? where id = ?",
            t.getName(),
            t.getEmail(),
            t.getPhone(),
            t.getPassword(),
            t.getPersonalId(),
            t.getBirthday(),
            t.getAddress(),
            t.isFemale(),
            t.getAvatar(),
            t.getManager(),
            t.getRole(),
            t.getId()
        );

        return result;
    }

    @Override
    public int delete(int id) {
        int result = ZQL.excuteQuery(
            connection, 
            "delete from staff where id = ?", 
            id
        );
        
        return result;
    }
    
    
}
