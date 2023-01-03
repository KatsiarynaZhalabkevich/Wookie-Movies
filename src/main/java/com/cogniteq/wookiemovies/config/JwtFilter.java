package com.cogniteq.wookiemovies.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends GenericFilterBean {

    private final AntPathMatcher anonymousUrlAntPathMatcher;

    @Autowired
    public JwtFilter(AntPathMatcher anonymousUrlAntPathMatcher) {
        this.anonymousUrlAntPathMatcher = anonymousUrlAntPathMatcher;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!isFilteredUrl(request.getRequestURI(),List.of("/movies/**", "/static") , anonymousUrlAntPathMatcher)) {
            chain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("FILTER work");
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String authorizationHeaderValue = request.getHeader("Authorization");

            if (isValidAuthorizationHeader(authorizationHeaderValue)) {
                String token = authorizationHeaderValue.replace("Bearer ", StringUtils.EMPTY);
                System.out.println(token);
                if (StringUtils.equals(token, "Wookie2019")) {
                    chain.doFilter(servletRequest, servletResponse);
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                }
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
            }
        }
    }

    private boolean isValidAuthorizationHeader(String authorizationHeaderValue) {
        return !StringUtils.isBlank(authorizationHeaderValue) && authorizationHeaderValue.startsWith("Bearer ");
    }

    public static boolean isFilteredUrl(String requestUrl, List<String> filteredUrlPatterns,
                                        AntPathMatcher anonymousUrlAntPathMatcher) {
        for (String pattern : filteredUrlPatterns) {
            if (StringUtils.isNotBlank(pattern) && anonymousUrlAntPathMatcher.match(pattern, requestUrl)) {
                return true;
            }
        }
        return false;
    }

}

