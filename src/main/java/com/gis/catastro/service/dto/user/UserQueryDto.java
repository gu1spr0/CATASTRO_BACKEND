package com.gis.catastro.service.dto.user;

import lombok.Data;
import java.util.Date;

@Data
public class UserQueryDto {
    private Long id;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String telephone;
    private Date createdDate;
    private Long createdBy;
    private String state;
}
