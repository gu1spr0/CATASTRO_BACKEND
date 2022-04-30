package com.gis.catastro.service;

import org.springframework.stereotype.Service;

@Service
public interface ConnectionLogService {
    void addConnectionLogin(String pUserName);
    void updateConnectionLogout();
}
