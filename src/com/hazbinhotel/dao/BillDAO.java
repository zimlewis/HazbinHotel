package com.hazbinhotel.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hazbinhotel.entity.Bill;
import com.zimlewis.ZQL;

public class BillDAO implements DAO<Bill>{

    Connection connection;

    public BillDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public Bill get(int id) {
        Bill b = null;
        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from bill where id = ?", id);

        if (result.size() != 0){
            b = new Bill(
                (int) result.get(0).get("id"), 
                (int) result.get(0).get("booking"), 
                ((BigDecimal)result.get(0).get("total")).doubleValue(), 
                (Integer) result.get(0).get("staff"), 
                ((Timestamp) result.get(0).get("invoice_date")).toLocalDateTime(), 
                (result.get(0).get("payment_date")!=null)?((Timestamp) result.get(0).get("payment_date")).toLocalDateTime():null
            );
        }

        return b;
    }

    @Override
    public List<Bill> getAll() {
        List<Bill> billList = new ArrayList<Bill>();

        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from bill");

        for (int i = 0; i < result.size(); i++){
            Bill b = new Bill(
                (int) result.get(i).get("id"), 
                (int) result.get(i).get("booking"), 
                ((BigDecimal)result.get(i).get("total")).doubleValue(), 
                (Integer) result.get(i).get("staff"),
                ((Timestamp) result.get(i).get("invoice_date")).toLocalDateTime(), 
                (result.get(i).get("payment_date")!=null)?((Timestamp) result.get(0).get("payment_date")).toLocalDateTime():null
            );

            billList.add(b);
        }

        return billList;
    }


    @Override
    public int insert(Bill bill) {
        int result = ZQL.excuteQuery(
            connection, 
            "insert into bill(booking, total, staff, invoice_date, payment_date) values (?, ?, ?, ?, ?)", 
            bill.getBooking(),
            bill.getTotal(),
            bill.getStaff(),
            bill.getInvoiceDate(),
            bill.getPaymentDate()
        );

        return result;
    }

    @Override
    public int update(Bill bill) {
        int result = ZQL.excuteQuery(
            connection, 
            "update bill set booking = ?, total = ?, staff = ?, invoice_date = ?, payment_date = ? where id = ?",
            bill.getBooking(),
            bill.getTotal(),
            bill.getStaff(),
            bill.getInvoiceDate(),
            bill.getPaymentDate(),
            bill.getId()
        );

        return result;
    }

    @Override
    public int delete(int id) {
        int result = ZQL.excuteQuery(
            connection, 
            "delete from bill where id = ?", 
            id
        );
        
        return result;
    }

    public Bill getByBooking(int booking){
        Bill b = null;
        ArrayList<Map<String,Object>> result = ZQL.excuteQueryToArrayList(this.connection, "select * from bill where booking = ?", booking);

        if (result.size() != 0){
            b = new Bill(
                (int) result.get(0).get("id"), 
                (int) result.get(0).get("booking"), 
                ((BigDecimal)result.get(0).get("total")).doubleValue(), 
                (Integer) result.get(0).get("staff"), 
                ((Timestamp) result.get(0).get("invoice_date")).toLocalDateTime(), 
                (result.get(0).get("payment_date")!=null)?((Timestamp) result.get(0).get("payment_date")).toLocalDateTime():null
            );
        }

        return b;
    }
    
}
