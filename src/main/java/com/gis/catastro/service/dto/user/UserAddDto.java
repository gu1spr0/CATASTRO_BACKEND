package com.gis.catastro.service.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserAddDto {
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String telephone;
}
