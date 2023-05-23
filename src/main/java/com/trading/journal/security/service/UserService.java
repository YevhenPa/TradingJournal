package com.trading.journal.security.service;

import com.trading.journal.security.dto.UserDto;
import com.trading.journal.security.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}