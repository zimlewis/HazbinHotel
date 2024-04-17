package com.hazbinhotel.entity;

import java.time.LocalDate;

public class Customer {
    int id;
    String name;
    String phone;
    String personalId;
    LocalDate birthday;
    String type;
    String email;
    String note;


    
    public void setId(int id) {
        this.id = id;
    }



    public void setName(String name) {
        this.name = name;
    }



    public void setPhone(String phone) {
        this.phone = phone;
    }



    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }



    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }



    public void setType(String type) {
        this.type = type;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    public void setNote(String note) {
        this.note = note;
    }



    public int getId() {
        return id;
    }



    public String getName() {
        return name;
    }



    public String getPhone() {
        return phone;
    }



    public String getPersonalId() {
        return personalId;
    }



    public LocalDate getBirthday() {
        return birthday;
    }



    public String getType() {
        return type;
    }



    public String getEmail() {
        return email;
    }



    public String getNote() {
        return note;
    }



    public Customer(int id, String name, String phone, String personalId, LocalDate birthday, String type,
            String email, String note) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.personalId = personalId;
        this.birthday = birthday;
        this.type = type;
        this.email = email;
        this.note = note;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((personalId == null) ? 0 : personalId.hashCode());
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
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
        Customer other = (Customer) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (personalId == null) {
            if (other.personalId != null)
                return false;
        } else if (!personalId.equals(other.personalId))
            return false;
        if (birthday == null) {
            if (other.birthday != null)
                return false;
        } else if (!birthday.equals(other.birthday))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        return true;
    }


}
