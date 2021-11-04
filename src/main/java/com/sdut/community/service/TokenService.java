package com.sdut.community.service;

import com.sdut.community.model.domain.User;

public interface TokenService {
    public String getToken(User user);
}
