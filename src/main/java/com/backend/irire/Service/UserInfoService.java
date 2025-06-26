package com.backend.irire.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.backend.irire.Repository.UserRepository;
import com.backend.irire.Model.User;

import java.util.Collections;
import java.util.List;


@Service
public class UserInfoService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserInfoService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User users=userRepository.findById(Long.valueOf(id));
        if(users==null){
            throw new UsernameNotFoundException(id+"not found");
        }

        List<SimpleGrantedAuthority> authorities = (users.getRole() != null)
                ? List.of(new SimpleGrantedAuthority(users.getRole().toString()))
                : Collections.emptyList();

        return new org.springframework.security.core.userdetails.User(
                users.getEmail(),
                users.getPassword(),
                authorities
        );
    } 
}