package com.backend.irire.filter;

import com.backend.irire.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger log=Logger.getLogger(JwtAuthFilter.class.getName());
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public JwtAuthFilter(UserDetailsService userDetailsService,JwtService jwtService){
        this.userDetailsService=userDetailsService;
        this.jwtService=jwtService;
    }

    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String userId=null;
        String email=null;

        if(authHeader !=null && authHeader.startsWith("Bearer")){
            token=authHeader.substring(7);
            try{
                userId=String.valueOf(jwtService.extractId(token));
                email=jwtService.extractEmail(token);

            }catch (Exception e){
                logger.warn("Invalid Jwt token"+e.getMessage());

            }
        }

        if(userId!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=userDetailsService.loadUserByUsername(userId);
            if(jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                request.setAttribute("userId",userId);
                request.setAttribute("email",email);
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        filterChain.doFilter(request,response);
    }
}
