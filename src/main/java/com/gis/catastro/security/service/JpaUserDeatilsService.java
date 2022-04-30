package com.gis.catastro.security.service;

import com.gis.catastro.exception.Message;
import com.gis.catastro.exception.MessageDescription;
import com.gis.catastro.model.entity.Role;
import com.gis.catastro.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import static com.gis.catastro.exception.MessageDescription.UsernameNoEncontrado;

@Service("jpaUserDetailsService")
public class JpaUserDeatilsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(JpaUserDeatilsService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.gis.catastro.model.entity.User user = userService.getUserByUserName(username);


        if (user == null) {
            throw new UsernameNotFoundException(Message.GetNotFound(UsernameNoEncontrado, username).getMessage());

        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Role role : user.getRoles()) {
            logger.info("Role: ".concat(role.getRole()));
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        if (authorities.isEmpty()) {
            throw new UsernameNotFoundException(Message.GetNotFound(MessageDescription.UserWithoutRoles, username).getMessage());
        }

        boolean bandera = true;
        if (user.getState().equals("BLOQUEADO"))
            bandera = false;
        else
            bandera = true;

        return new User(user.getUsername(), user.getPassword(), bandera, true, true, true, authorities);
    }
}
