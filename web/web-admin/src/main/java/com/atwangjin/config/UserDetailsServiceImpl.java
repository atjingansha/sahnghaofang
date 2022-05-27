package com.atwangjin.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atwangjin.entity.Admin;
import com.atwangjin.service.AdminService;
import com.atwangjin.service.PermissionService;
import org.junit.experimental.theories.internal.ParameterizedAssertionError;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author WangJin
 * @create 2022-05-26 15:43
 */

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Admin admin=adminService.getByUserName(username);

       if (admin==null){
           throw new UsernameNotFoundException("没有此用户名");
       }

    List<String> codeList= permissionService.findCdoeListByAdminId(admin.getId());

        Collection<GrantedAuthority> authorities=new ArrayList<>();

        for (String code : codeList) {
            if (StringUtils.isEmpty(code)){
                continue;
            }else {
                authorities.add(new SimpleGrantedAuthority(code));
            }

        }

        return new User(admin.getUsername(), admin.getPassword(),authorities  );
    }
}
