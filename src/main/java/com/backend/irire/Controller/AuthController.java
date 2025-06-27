package com.backend.irire.Controller;

import com.backend.irire.Dto.Request.UserAuthRequest;
import com.backend.irire.Dto.Response.UserAuthResponse;
import com.backend.irire.Model.RefreshToken;
import com.backend.irire.Model.User;
import com.backend.irire.Service.AuthService;
import com.backend.irire.Service.JwtService;
import com.backend.irire.Service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService,JwtService jwtService,RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@RequestBody UserAuthRequest userAuthRequest) {
      try{
          User user = authService.RegisterUser(userAuthRequest);
          String token=jwtService.generateAccessToken(user);
          RefreshToken refreshToken=refreshTokenService.createRefreshToken(user.getId());
          return new ResponseEntity<>(user.toString()+" token is:"+token+"\n"+"refresh token:"+refreshToken.toString(), HttpStatus.CREATED);
    }catch (Exception e){
          return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
    }

    @PostMapping("/login")
    public ResponseEntity<?> LoginUser(@RequestBody UserAuthRequest userAuthRequest) {
        try {
            User user = authService.LoginUser(userAuthRequest);
            String accessToken = jwtService.generateAccessToken(user);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
            return ResponseEntity.ok(new UserAuthResponse(accessToken, refreshToken.getToken()));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
