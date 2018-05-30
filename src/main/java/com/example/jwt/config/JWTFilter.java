package com.example.jwt.config;

import com.example.jwt.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends GenericFilterBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getRequestURI();
        String token = getToken(req);
        if (token != null && !url.equalsIgnoreCase("/auth")
                && !url.equalsIgnoreCase("/")
                && !url.startsWith("/h2")) {
            try {
                if (tokenService.validateToken(token)) {
                    Authentication authentication = tokenService.parseToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                sendJsonErrorResp(res, e.getMessage());
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private HttpServletResponse sendJsonErrorResp(HttpServletResponse response, String exMessage) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addHeader("WWW-Authenticate", "Realm=\"" + "GATSBY_TOKEN_AUTH" + "\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        objectMapper.writeValue(response.getWriter(), exMessage);
        return response;
    }

    private String getToken(HttpServletRequest header) {
        String token = header.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
