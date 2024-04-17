package com.hazbinhotel.entity;

import java.time.LocalDateTime;

public class Booking {
    int id;
    int customer;
    int room;
    LocalDateTime checkIn;
    LocalDateTime checkOut;

    
    public Booking(int id, int customer, int room, LocalDateTime checkIn, LocalDateTime checkOut) {
        this.id = id;
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }


    public int getId() {
        return id;
    }


    public int getCustomer() {
        return customer;
    }


    public int getRoom() {
        return room;
    }


    public LocalDateTime getCheckIn() {
        return checkIn;
    }


    public LocalDateTime getCheckOut() {
        return checkOut;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setCustomer(int customer) {
        this.customer = customer;
    }


    public void setRoom(int room) {
        this.room = room;
    }


    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }


    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + customer;
        result = prime * result + room;
        result = prime * result + ((checkIn == null) ? 0 : checkIn.hashCode());
        result = prime * result + ((checkOut == null) ? 0 : checkOut.hashCode());
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
        Booking other = (Booking) obj;
        if (id != other.id)
            return false;
        if (customer != other.customer)
            return false;
        if (room != other.room)
            return false;
        if (checkIn == null) {
            if (other.checkIn != null)
                return false;
        } else if (!checkIn.equals(other.checkIn))
            return false;
        if (checkOut == null) {
            if (other.checkOut != null)
                return false;
        } else if (!checkOut.equals(other.checkOut))
            return false;
        return true;
    }
    
}
