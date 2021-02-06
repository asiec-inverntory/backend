package ru.centralhardware.asiec.inventory.Web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.centralhardware.asiec.inventory.Security.Ip;
import ru.centralhardware.asiec.inventory.Security.JwtTokenUtil;
import ru.centralhardware.asiec.inventory.Security.LoggingAttemptService;
import ru.centralhardware.asiec.inventory.Security.Model.JwtRequest;
import ru.centralhardware.asiec.inventory.Service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userDetailsService;
    private final LoggingAttemptService loginAttemptService;
    @Autowired
    private Ip ip;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JwtTokenUtil jwtTokenUtil,
                                    UserService userDetailsService,
                                    LoggingAttemptService loginAttemptService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.loginAttemptService = loginAttemptService;
    }

    /**
     * handler authenticated post request
     * @param authenticationRequest object with password and username
     * @return jwt token
     */
    @ApiOperation(value = "authenticate user",
            notes = "use for getting jwt token",
            httpMethod = "POST",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved jwt token"),
            @ApiResponse(code = 401, message = "login is blocked for pretend brute farce attack"),
            @ApiResponse(code = 403, message = "user is blocked")
    })
    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest authenticationRequest, HttpServletResponse response) {
        if (loginAttemptService.isBlocked(ip.getClientIP())){
            return ResponseEntity.status(401).build();
        }
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            loginAttemptService.loginSucceeded(ip.getClientIP());
            userDetailsService.setLastLogin(authenticationRequest.getUsername());
        } catch (Exception ex){
            log.warn("", ex);
            loginAttemptService.loginFailed(ip.getClientIP());
            return ResponseEntity.status(403).build();
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        Cookie cookie = new Cookie("authorisation",jwtTokenUtil.generateToken(userDetails));
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) JwtTokenUtil.JWT_TOKEN_VALIDITY);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    /**
     * check user authorisation
     * @param username user username
     * @param password user password
     * @throws Exception invalid credentials or user disabled
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            log.info("USER_DISABLED: " + username);
            throw new Exception(e);
        } catch (BadCredentialsException e) {
            log.info("INVALID_CREDENTIALS -  username: " + username + " , password: " + password);
            throw new Exception(e);
        }
    }

}
