package com.gis.catastro.service.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserQueryPageableDto {
    private List<UserQueryDto> userQueryDtoList;
    private long totalRows;
}
