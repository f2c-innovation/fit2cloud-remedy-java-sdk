package com.fit2cloud.selfService.sdk.common.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class JwtTokenFilter implements Filter {

    private JwtService jwtService;

    public JwtTokenFilter(@Autowired JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String authorization = servletRequest.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwtService
                    .parse(authorization.replaceAll("Bearer ", ""))
                    .ifPresent(jwtAuthentication -> SecurityContextHolder
                            .getContext()
                            .setAuthentication(jwtAuthentication));
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
