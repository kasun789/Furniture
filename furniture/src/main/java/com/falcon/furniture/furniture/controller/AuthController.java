package com.falcon.furniture.furniture.controller;

import com.falcon.furniture.furniture.dao.RoleDao;
import com.falcon.furniture.furniture.dao.UserDao;
import com.falcon.furniture.furniture.dto.JwtResponse;
import com.falcon.furniture.furniture.dto.LoginRequest;
import com.falcon.furniture.furniture.dto.MessageResponse;
import com.falcon.furniture.furniture.dto.SignupRequest;
import com.falcon.furniture.furniture.dto.enums.UserRole;
import com.falcon.furniture.furniture.model.Role;
import com.falcon.furniture.furniture.model.User;
import com.falcon.furniture.furniture.security.UserDetailsImpl;
import com.falcon.furniture.furniture.security.jwt.JwtUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthority().getAuthority();

        JwtResponse response = new JwtResponse();
        response.setToken(jwt);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        User user = null;
        String strRole = null;

        if(signupRequest != null){
            user = new User(
                    signupRequest.getName(),
                    signupRequest.getAddress(),
                    signupRequest.getUsername(),
                    encoder.encode(signupRequest.getPassword()),
                    signupRequest.getEmail(),
                    signupRequest.getPhoneNo()
            );
        }

        if(user != null){
            if(signupRequest.getUserRole() != null){
                strRole = signupRequest.getUserRole();
            }

            Role role = null;

            if(strRole != null) {
                switch (strRole.toLowerCase()) {
                    case "admin":
                        logger.info("admin", UserRole.ROLE_ADMIN.getDescription());
                        Role adminRole = roleDao.findByName(UserRole.ROLE_ADMIN.getDescription())
                                .orElseThrow(() -> new RuntimeException("User role not found"));
                        role = adminRole;
                        break;
                    default:
                        Role userRole = roleDao.findByName(UserRole.ROLE_USER.getDescription())
                                .orElseThrow(() -> new RuntimeException("User role not found"));
                        role = userRole;
                        break;
                }
            }
            user.setRoleId(role.getId());
            userDao.add(user);
            return ResponseEntity.ok(new MessageResponse("User Added Successfully",true));
        }else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error occured", false));
        }
    }

    @PostMapping("/addRole")
    public ResponseEntity<?> addRole(@RequestBody Role role){
        roleDao.addRole(role);
        return ResponseEntity.ok(200);
    }

}

