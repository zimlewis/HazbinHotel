package com.hazbinhotel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bill {
    int id;
    int booking;
    double total;
    Integer staff;
    LocalDateTime invoiceDate;
    LocalDateTime paymentDate;

    public Bill(int id, int booking, double total, Integer staff, LocalDateTime invoice_date, LocalDateTime payment_date) {
        this.id = id;
        this.booking = booking;
        this.total = total;
        this.staff = staff;
        this.invoiceDate = invoice_date;
        this.paymentDate = payment_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBooking(int booking) {
        this.booking = booking;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public void setStaff(int staff) {
        this.staff = staff;
    }
    public void setInvoiceDate(LocalDateTime invoice_date) {
        this.invoiceDate = invoice_date;
    }
    public void setPaymentDate(LocalDateTime payment_date) {
        this.paymentDate = payment_date;
    }
    
    public int getId() {
        return id;
    }
    public int getBooking() {
        return booking;
    }
    public double getTotal() {
        return total;
    }
    public Integer getStaff() {
        return staff;
    }
    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + booking;
        long temp;
        temp = Double.doubleToLongBits(total);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + staff;
        result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
        result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bill other = (Bill) obj;
        if (id != other.id)
            return false;
        if (booking != other.booking)
            return false;
        if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
            return false;
        if (staff != other.staff)
            return false;
        if (invoiceDate == null) {
            if (other.invoiceDate != null)
                return false;
        } else if (!invoiceDate.equals(other.invoiceDate))
            return false;
        if (paymentDate == null) {
            if (other.paymentDate != null)
                return false;
        } else if (!paymentDate.equals(other.paymentDate))
            return false;
        return true;
    }


}
