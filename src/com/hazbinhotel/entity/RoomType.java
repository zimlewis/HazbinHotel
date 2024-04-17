package com.hazbinhotel.entity;

public class RoomType {
    int id;
    String name;
    int capacity;
    double price;

    
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getCapacity() {
        return capacity;
    }
    public double getPrice() {
        return price;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public RoomType(int id, String name, int capacity, double price) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.price = price;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + capacity;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        RoomType other = (RoomType) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (capacity != other.capacity)
            return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
            return false;
        return true;
    }

    
}
