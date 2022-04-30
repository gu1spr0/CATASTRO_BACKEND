package com.gis.catastro.service.dto.connection;

import lombok.Data;
import java.util.Date;

@Data
public class ConnectionQueryDto {
    private Long id;
    private String username;
    private Date loginDate;
    private Date logoutDate;
}
