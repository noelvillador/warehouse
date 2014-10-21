package org.warehouse.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.warehouse.dao.EmployeeDao;
import org.warehouse.model.Employee;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Noel on 9/9/14.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        Employee user;

        if (username.equals("admin") && password.equals("admin")) {
            user = new Employee(new BigInteger("1"), new Date(), "Admin", 2, "Male", "admin", "admin", "user");
        } else {

            List<Employee> users = employeeDao.findByUsername(username);
            if (users != null && users.size() > 0) {
                user = users.get(0);
            } else {
                user = null;
            }
        }

        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
