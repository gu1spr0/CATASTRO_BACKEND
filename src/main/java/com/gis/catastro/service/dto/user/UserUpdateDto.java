package com.gis.catastro.service.dto.user;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String telephone;
    private String state;
}
