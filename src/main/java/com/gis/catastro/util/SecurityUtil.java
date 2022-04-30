package com.gis.catastro.util;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.User;
import com.gis.catastro.model.repository.UserRepository;
import com.gis.catastro.service.dto.user.UserQueryDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SecurityUtil {
    public SecurityUtil(){
    }

    @Autowired
    public UserRepository userRepository;

    public Long getIdOfAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Map<String, Long> info = (Map<String, Long>) authentication.getDetails();
        Long idlog = info.get("Id");
        return idlog;
    }

    public static String getUserOfAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication.getPrincipal().toString();
    }

    public UserQueryDto getCurrentUser() {
        String pUserName = this.getUserOfAuthenticatedUser();
        //TODO VALIDAR ID EXISTENTE
        User vUser= userRepository.getUserByUsername(pUserName).orElse(null);
        if(vUser == null){
            Object[] obj = {"Usuario", "username", pUserName};
            throw Message.GetBadRequest(MessageDescription.notExists, obj);
        }
        UserQueryDto vUserQueryDto = new UserQueryDto();
        BeanUtils.copyProperties(vUser, vUserQueryDto);
        return vUserQueryDto;
    }
}
