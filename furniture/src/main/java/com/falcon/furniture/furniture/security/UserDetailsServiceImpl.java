package com.falcon.furniture.furniture.security;

import com.falcon.furniture.furniture.dao.RoleDao;
import com.falcon.furniture.furniture.dao.UserDao;
import com.falcon.furniture.furniture.dto.AuthenticatedUserDto;
import com.falcon.furniture.furniture.model.Role;
import com.falcon.furniture.furniture.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        AuthenticatedUserDto authUser = new AuthenticatedUserDto();
        authUser.setId(user.getId());
        authUser.setUsername(user.getUserName());
        authUser.setEmail(user.getEmail());
        authUser.setPassword(user.getPassword());
        if(user.getRoleId() != null){
            Role role = roleDao.findByRoleId(user.getRoleId());
            authUser.setUserRole(role.getUserRole());
        }
        return UserDetailsImpl.build(authUser);
    }
}
