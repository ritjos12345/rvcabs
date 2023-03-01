package com.rvcabs.microservices.security;

import org.springframework.web.client.HttpClientErrorException;
import java.io.IOException;
import javax.servlet.ServletException;
import org.springframework.security.core.userdetails.UserDetails;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.util.MultiValueMap;
import com.rvcabs.microservices.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.rvcabs.microservices.service.ApplicationService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter
{
    @Autowired
    private ApplicationService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    private MultiValueMap<String, String> headers;
    private Map<String, Object> responceMap;
    
    public JwtRequestFilter() {
        this.headers = null;
    }
    
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws ServletException, IOException, HttpClientErrorException.Unauthorized {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authorizationHeader != null) {
            jwt = authorizationHeader;
            username = this.jwtUtil.extractUsername(jwt);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtUtil.validateToken(jwt, userDetails)) {
                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken((Object)userDetails, (Object)null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails((Object)new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication((Authentication)usernamePasswordAuthenticationToken);
            }
        }
        else {
            SecurityContextHolder.getContext().setAuthentication((Authentication)null);
        }
        chain.doFilter((ServletRequest)request, (ServletResponse)response);
    }
}