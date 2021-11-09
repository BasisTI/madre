package br.com.basis.madre.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.basis.madre.dto.UserInternalDTO;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    RestTemplate restTemplate;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        
        Map<String, Object> map = new HashMap<>();
        map.put("login", login);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        UserInternalDTO user = restTemplate.postForEntity("http://localhost:8088/internal-api/user-details", entity, UserInternalDTO.class).getBody();

        return User.builder()
            .username(user.getLogin())
            .password(user.getSenha())
            .roles("USERS")
            .build();



    }
    
}
