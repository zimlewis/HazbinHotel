package com.hazbinhotel.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hazbinhotel.entity.Booking;
import com.zimlewis.ZQL;
import java.time.LocalDateTime;


public class BookingDAO implements DAO<Booking>{

    Connection connection;

    

    public BookingDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Booking get(int id) {
        Booking b = null;
        ArrayList<Map<String, Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from booking where id = ?", id);

        if (result.size() != 0){
            b = new Booking(
                (int) result.get(0).get("id"), 
                (int) result.get(0).get("customer"), 
                (int) result.get(0).get("room"),
                ((Timestamp) result.get(0).get("check_in")).toLocalDateTime(),
                ((Timestamp) result.get(0).get("check_out")).toLocalDateTime()
            );
        }

        return b;
    }



    @Override
    public List<Booking> getAll() {
        List<Booking> list = new ArrayList<Booking>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from booking");



        for (int i = 0; i < result.size(); i++){
            Booking b = new Booking(
                (int) result.get(i).get("id"), 
                (int) result.get(i).get("customer"), 
                (int) result.get(i).get("room"),
                ((Timestamp) result.get(i).get("check_in")).toLocalDateTime(),
                ((Timestamp) result.get(i).get("check_out")).toLocalDateTime()
            );

            list.add(b);
        }

        return list;
    }


    @Override
    public int insert(Booking booking) {
        int result = ZQL.excuteQuery(
            connection, 
            "insert into booking(customer, room, check_in, check_out) values (?, ?, ?, ?)",
            booking.getCustomer(),
            booking.getRoom(),
            booking.getCheckIn(),
            booking.getCheckOut()
        );

        return result;
    }

    @Override
    public int update(Booking booking) {
        int result = ZQL.excuteQuery(
            connection, 
            "update booking set customer = ?, room = ?, check_in = ?, check_out = ? where id = ?",
            booking.getCustomer(),
            booking.getRoom(),
            booking.getCheckIn(),
            booking.getCheckOut(),
            booking.getId()
        );

        return result;
    }

    @Override
    public int delete(int id) {
        int result = ZQL.excuteQuery(
            connection, 
            "delete from booking where id = ?", 
            id
        );
        
        return result;
    }

    public List<Booking> getBookingByRoom(int id){
        List<Booking> l = new ArrayList<>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from booking where room = ?", id);

        for (int i = 0; i < result.size(); i++){
            Booking b = new Booking(
                (int) result.get(i).get("id"), 
                (int) result.get(i).get("customer"), 
                (int) result.get(i).get("room"),
                ((Timestamp) result.get(i).get("check_in")).toLocalDateTime(),
                ((Timestamp) result.get(i).get("check_out")).toLocalDateTime()
            );

            l.add(b);
        }

        return l;
    }

    public List<Booking> getBookingByCustomer(int id){
        List<Booking> l = new ArrayList<>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from booking where customer = ?", id);

        for (int i = 0; i < result.size(); i++){
            Booking b = new Booking(
                (int) result.get(i).get("id"), 
                (int) result.get(i).get("customer"), 
                (int) result.get(i).get("room"),
                ((Timestamp) result.get(i).get("check_in")).toLocalDateTime(),
                ((Timestamp) result.get(i).get("check_out")).toLocalDateTime()
            );

            l.add(b);
        }

        return l;
    }

    public List<Booking> getCurrentInUse(){
        List<Booking> l = new ArrayList<>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from booking where ? between check_in and check_out", LocalDateTime.now());

        for (int i = 0; i < result.size(); i++){
            Booking b = new Booking(
                (int) result.get(i).get("id"), 
                (int) result.get(i).get("customer"), 
                (int) result.get(i).get("room"),
                ((Timestamp) result.get(i).get("check_in")).toLocalDateTime(),
                ((Timestamp) result.get(i).get("check_out")).toLocalDateTime()
            );

            l.add(b);
        }

        return l;
    }

    public Booking getWithRoomBookTime(int id, LocalDateTime checkIn, LocalDateTime checkOut) {
        Booking b = null;
        ArrayList<Map<String, Object>> result = ZQL.excuteQueryToArrayList(
            this.connection, 
            "select * from booking where room = ? and check_in = ? and check_out = ?", 
            id,
            checkIn,
            checkOut
        );

        if (result.size() != 0){
            b = new Booking(
                (int) result.get(0).get("id"), 
                (int) result.get(0).get("customer"), 
                (int) result.get(0).get("room"),
                ((Timestamp) result.get(0).get("check_in")).toLocalDateTime(),
                ((Timestamp) result.get(0).get("check_out")).toLocalDateTime()
            );
        }

        return b;
    }

    public List<Booking> get(int id, LocalDateTime checkIn, LocalDateTime checkOut){
        List<Booking> list = new ArrayList<>();
        
        ArrayList<Map<String, Object>> result = ZQL.excuteQueryToArrayList(
            this.connection,
            "select * from booking where room = ? and (? <= check_out and ? >= check_in)", 
            id,
            checkIn,
            checkOut
        );

        for (int i = 0; i < result.size(); i++){
            Booking b = new Booking(
                (int) result.get(i).get("id"), 
                (int) result.get(i).get("customer"), 
                (int) result.get(i).get("room"),
                ((Timestamp) result.get(i).get("check_in")).toLocalDateTime(),
                ((Timestamp) result.get(i).get("check_out")).toLocalDateTime()
            );

            list.add(b);
        }

        return list;
    }

    public List<Booking> getPastBooking(){
        List<Booking> list = new ArrayList<Booking>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from booking where check_out < GETDATE()");



        for (int i = 0; i < result.size(); i++){
            Booking b = new Booking(
                (int) result.get(i).get("id"), 
                (int) result.get(i).get("customer"), 
                (int) result.get(i).get("room"),
                ((Timestamp) result.get(i).get("check_in")).toLocalDateTime(),
                ((Timestamp) result.get(i).get("check_out")).toLocalDateTime()
            );

            list.add(b);
        }

        return list;
    }

    public List<Booking> getFutureBooking(){
        List<Booking> list = new ArrayList<Booking>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from booking where check_in > GETDATE()");


        for (int i = 0; i < result.size(); i++){
            Booking b = new Booking(
                (int) result.get(i).get("id"), 
                (int) result.get(i).get("customer"), 
                (int) result.get(i).get("room"),
                ((Timestamp) result.get(i).get("check_in")).toLocalDateTime(),
                ((Timestamp) result.get(i).get("check_out")).toLocalDateTime()
            );

            list.add(b);
        }

        return list;
    }
    
}
