package com.labo.dao;

import com.labo.pojo.User;

public interface UserDao {
    public User findByUsername(String username);
}
