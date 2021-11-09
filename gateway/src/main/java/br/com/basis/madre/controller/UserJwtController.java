package br.com.basis.madre.controller;

import java.util.Collections;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.basis.madre.config.jwt.JWTToken;
import br.com.basis.madre.controller.vm.AuthenticateVM;
import br.gov.nuvem.security.jwt.web.CookieResponseProvider;
import br.gov.nuvem.security.jwt.web.TokenProvider;
import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/api")
public class UserJwtController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CookieResponseProvider cookieUtil;


    @PostMapping("/authenticate")
    public ResponseEntity<Object> authorize(@RequestBody AuthenticateVM authenticateVM, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            authenticateVM.getLogin(), authenticateVM.getSenha());
        
        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = authenticateVM.getRememberMe() == null ? false : true;
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            response.addHeader("Authorization", "Bearer " + jwt );

            cookieUtil.provideResponse(response, jwt);

            return ResponseEntity.ok(new JWTToken(jwt));
        } catch (AuthenticationException ae){
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException", ae.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/logout")
    @Timed
    public void logout(HttpServletResponse response) {
        Cookie cookieJWT = new Cookie("Authentication", null);
        cookieJWT.setSecure(true);
        cookieJWT.setPath("/");
        cookieJWT.setHttpOnly(true);
        cookieJWT.setMaxAge(0);
        response.addCookie(cookieJWT);
    }

    @GetMapping("/user/details")
    public ResponseEntity<Object> getUserDetails() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}