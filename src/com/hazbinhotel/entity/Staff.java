package com.hazbinhotel.entity;

import java.time.LocalDate;
import java.util.Arrays;

public class Staff {
    int id;
    String name;
    String email;
    String phone;
    String password;
    String personalId;
    LocalDate birthday;
    String address;
    boolean female;
    byte[] avatar;
    Integer manager;
    Integer role;

    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public boolean isFemale() {
        return female;
    }
    public void setFemale(boolean female) {
        this.female = female;
    }
    public byte[] getAvatar() {
        return avatar;
    }
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
    public Integer getManager() {
        return manager;
    }
    public void setManager(Integer manager) {
        this.manager = manager;
    }
    public Integer getRole() {
        return role;
    }
    public void setRole(Integer role) {
        this.role = role;
    }
    public String getPersonalId() {
        return personalId;
    }
    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }
    public Staff(int id, String name, String email, String phone, String password, String personalId, LocalDate birthday, String address,
            boolean female, byte[] avatar, Integer manager, Integer role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthday = birthday;
        this.address = address;
        this.female = female;
        this.avatar = avatar;
        this.manager = manager;
        this.role = role;
        this.personalId = personalId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((personalId == null) ? 0 : personalId.hashCode());
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + (female ? 1231 : 1237);
        result = prime * result + Arrays.hashCode(avatar);
        result = prime * result + ((manager == null) ? 0 : manager.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
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
        Staff other = (Staff) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
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
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (female != other.female)
            return false;
        if (!Arrays.equals(avatar, other.avatar))
            return false;
        if (manager == null) {
            if (other.manager != null)
                return false;
        } else if (!manager.equals(other.manager))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        return true;
    }

}
