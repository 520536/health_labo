package com.labo.service;

import com.labo.pojo.User;

public interface UserService {
    public User findByUsername(String username);
}
