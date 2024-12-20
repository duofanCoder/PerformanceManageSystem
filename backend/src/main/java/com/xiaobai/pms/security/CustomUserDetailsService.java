package com.xiaobai.pms.security;

import com.xiaobai.pms.dto.model.common.RoleDto;
import com.xiaobai.pms.dto.model.common.UserDto;
import com.xiaobai.pms.model.enums.UserRoles;
import com.xiaobai.pms.service.common.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author DuoFan
 */
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    // 查看是否有改用户，没有则抛出异常
    // 如果有则传给框架，也就是返回UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = null;
        try {
            userDto = userService.findUserByUsername(username);
            List<GrantedAuthority> authorities = getUserAuthority(userDto.getRole());
            return buildUserForAuthentication(userDto, authorities);
        } catch (RuntimeException e) {
            throw new UsernameNotFoundException("用户邮箱 " + username + " 不存在。");
        }
    }

    private List<GrantedAuthority> getUserAuthority(UserRoles userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(userRoles.name()));
        return new ArrayList<GrantedAuthority>(roles);
    }

    private UserDetails buildUserForAuthentication(UserDto user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


}
