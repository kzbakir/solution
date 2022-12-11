package com.example.solution.auth;

import java.util.stream.Collectors;

import com.example.solution.auth.model.SecurityUser;
import com.example.solution.model.entity.User;
import com.example.solution.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return createUser(user.get());
    }

    private SecurityUser createUser(User user) {
        return SecurityUser.builder()
                .withUserName(user.getUsername())
                .withPassword(user.getPassword())
                .withGrantedAuthorityList(user.getRoles().stream()
                        .map(x -> new SimpleGrantedAuthority(x.getName()))
                        .collect(Collectors.toList()));
    }
}
