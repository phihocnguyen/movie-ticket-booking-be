package com.example.movieticketbookingbe.dto.theaterowner;

import com.example.movieticketbookingbe.dto.user.UserCreateDTO;

public class TheaterOwnerWithUserCreateDTO {
    private UserCreateDTO user;

    public UserCreateDTO getUser() {
        return user;
    }
    public void setUser(UserCreateDTO user) {
        this.user = user;
    }
} 