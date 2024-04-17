package com.hazbinhotel.dao;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hazbinhotel.entity.Booking;
import com.hazbinhotel.entity.Room;
import com.zimlewis.ZQL;

public class RoomDAO implements DAO<Room> {

    private Connection connection;

    public RoomDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Room get(int id) {
        Room b = null;
        ArrayList<Map<String, Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from room where id = ?", id);

        if (result.size() != 0){
            b = new Room(
                (int) result.get(0).get("id"),
                String.valueOf(result.get(0).get("name")),
                (int) result.get(0).get("service"),
                (int) result.get(0).get("type")
            );
        }

        return b;
    }

    @Override
    public List<Room> getAll() {
        List<Room> list = new ArrayList<Room>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from room");

        for (int i = 0; i < result.size(); i++){
            Room b = new Room(
                (int) result.get(i).get("id"),
                String.valueOf(result.get(i).get("name")),
                (int) result.get(i).get("service"),
                (int) result.get(i).get("type")
            );

            list.add(b);
        }

        return list;
    }

    @Override
    public int insert(Room t) {
        int result = ZQL.excuteQuery(
            connection, 
            "insert into room(name, service, type) values (?, ?, ?)",
            t.getName(),
            t.getService(),
            t.getType()
        );

        return result;
    }

    @Override
    public int update(Room t) {
        int result = ZQL.excuteQuery(
            connection, 
            "update room set name = ?, service = ?, type = ? where id = ?",
            t.getName(),
            t.getService(),
            t.getType(),
            t.getId()
        );

        return result;
    }

    @Override
    public int delete(int id) {
        int result = ZQL.excuteQuery(
            connection, 
            "delete from room where id = ?", 
            id
        );
        
        return result;
    }

    public boolean isInUsed(int id){
        boolean inUse = false;

        BookingDAO bd = new BookingDAO(connection);
        List<Booking> bl = bd.getCurrentInUse();

        for (Booking b : bl){
            if (b.getRoom() == id){
                inUse = true;
                break;
            }
        }

        return inUse;
    }
    public boolean isInUsed(int id, LocalDateTime from, LocalDateTime to) {
        boolean b = false;
        ArrayList<Map<String, Object>> result = ZQL.excuteQueryToArrayList(
            this.connection, 
            "select * from booking where room = ? and (? <= check_out and ? >= check_in)", 
            id,
            from,
            to
        );

        if (result.size() != 0){
            b = true;
        }

        return b;
    }
    
}
