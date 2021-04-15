package com.hunganh.mymoments.controller;

import com.hunganh.mymoments.constant.ResponseConstant;
import com.hunganh.mymoments.dto.LoginRequest;
import com.hunganh.mymoments.dto.RegisterRequest;
import com.hunganh.mymoments.exception.AuthenticationException;
import com.hunganh.mymoments.exception.EmailAlreadyExistsException;
import com.hunganh.mymoments.exception.EmailNotExistsException;
import com.hunganh.mymoments.exception.UsernameAlreadyExistsException;
import com.hunganh.mymoments.response.SnwErrorResponse;
import com.hunganh.mymoments.response.SnwSuccessResponse;
import com.hunganh.mymoments.service.AuthService;
import com.hunganh.mymoments.util.TemplateUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/auth", produces = "application/json")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        try {
            authService.signup(registerRequest);
            return new ResponseEntity(new SnwSuccessResponse(), OK);
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            return new ResponseEntity(new SnwErrorResponse(e.getMessage()), CONFLICT);
        } catch (EmailNotExistsException e) {
            return new ResponseEntity(new SnwErrorResponse(e.getMessage()), PRECONDITION_FAILED);
        } catch (Exception e) {
            return new ResponseEntity(new SnwErrorResponse(e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        try {
            Map<String, Object> result = authService.login(loginRequest);
            return new ResponseEntity(TemplateUtil.generateJson(result), OK);
        } catch (EmailNotExistsException e) {
            return new ResponseEntity(new SnwErrorResponse(e.getMessage()), PRECONDITION_FAILED);
        } catch (AuthenticationException e) {
            return new ResponseEntity(new SnwErrorResponse(e.getMessage()), UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity(new SnwErrorResponse(e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verify(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity(new SnwSuccessResponse(), OK);
    }

    @PostMapping("/send_email")
    public ResponseEntity<String> sendEmail() {
        return new ResponseEntity<>("", OK);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            return new ResponseEntity(new SnwSuccessResponse(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new SnwErrorResponse(ResponseConstant.CAN_NOT_LOGOUT), HttpStatus.BAD_REQUEST);
        }
    }
}
