package com.beeva.api;


import com.beeva.model.User;
import com.beeva.security.JWTGenerateToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private JWTGenerateToken jwtGenerateToken;

    public LoginController(JWTGenerateToken jwtGenerateToken) {
        this.jwtGenerateToken = jwtGenerateToken;
    }

    @PostMapping("/")
    public ResponseEntity authenticationUser(@RequestBody final User user) {
        try{
                HttpEntity httpEntity = ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " +  jwtGenerateToken.tokenGenerate(user)).build();
                return (ResponseEntity) httpEntity;

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}

