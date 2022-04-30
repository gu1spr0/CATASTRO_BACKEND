package com.gis.catastro.service.impl;

import com.gis.catastro.model.entity.ConnectionLog;
import com.gis.catastro.model.repository.ConnectionLogRepository;
import com.gis.catastro.service.ConnectionLogService;
import com.gis.catastro.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConnectionLogServiceImpl implements ConnectionLogService {
    @Autowired
    private ConnectionLogRepository connectionLogRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public void addConnectionLogin(String pUserName) {
        ConnectionLog vConnectionLog = this.getConnectionLog(pUserName);
        if (vConnectionLog != null){
            vConnectionLog.setLogoutDate(new Date());
            connectionLogRepository.save(vConnectionLog);
        }
        vConnectionLog = new ConnectionLog();
        vConnectionLog.setUsername(pUserName);
        vConnectionLog.setLoginDate(new Date());
        connectionLogRepository.save(vConnectionLog);
    }

    @Override
    public void updateConnectionLogout() {
        String vUserName = SecurityUtil.getUserOfAuthenticatedUser();
        ConnectionLog vConnectionLog = this.getConnectionLog(vUserName);
        if (vConnectionLog != null){
            vConnectionLog.setLogoutDate(new Date());
            connectionLogRepository.save(vConnectionLog);
        }
    }
    private ConnectionLog getConnectionLog(String pUserName){
        ConnectionLog vConnectionLog = connectionLogRepository.getConnectionLogByUsername(pUserName).orElse(null);
        return vConnectionLog;
    }
}
