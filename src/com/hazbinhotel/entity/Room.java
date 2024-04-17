package com.hazbinhotel.entity;

public class Room {
    int id;
    int service;
    int type;
    String name;

    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public int getService() {
        return service;
    }
    public int getType() {
        return type;
    }

    
    public void setId(int id) {
        this.id = id;
    }
    public void setService(int service) {
        this.service = service;
    }
    public void setType(int type) {
        this.type = type;
    }


    public Room(int id, String name, int service, int type) {
        this.id = id;
        this.service = service;
        this.type = type;
        this.name = name;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + service;
        result = prime * result + type;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Room other = (Room) obj;
        if (id != other.id)
            return false;
        if (service != other.service)
            return false;
        if (type != other.type)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }


}
