package ru.centralhardware.asiec.inventory.Security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.centralhardware.asiec.inventory.Configuration.Config;
import ru.centralhardware.asiec.inventory.Service.UserDetailService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final Config config;
    @Autowired
    private Ip ip;

    public JwtRequestFilter(UserDetailService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil, Config config) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.config = config;
    }

    /**
     * scan jwt token and validate it
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String username = null;
        var jwtToken = getAuthCookie(request);
        try {
            if (config.enableAuth){
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } else {
                username = "admin";
            }
        } catch (IllegalArgumentException e) {
            log.info("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            log.info("JWT Token has expired");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails) || !config.enableAuth) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().equals("/authenticate") || request.getRequestURI().equals("/healthcheck");
    }

    private String getAuthCookie(HttpServletRequest req) {
        if (req.getCookies() == null) return "";
        return Arrays.stream(req.getCookies())
                .filter(c -> c.getName().equals("authorisation"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

}
