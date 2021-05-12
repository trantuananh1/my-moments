package com.hunganh.mymoments.service;

import com.hunganh.mymoments.constant.ResponseConstant;
import com.hunganh.mymoments.dto.AuthenticationResponse;
import com.hunganh.mymoments.dto.LoginRequest;
import com.hunganh.mymoments.dto.NotificationEmail;
import com.hunganh.mymoments.dto.RegisterRequest;
import com.hunganh.mymoments.exception.AuthenticationException;
import com.hunganh.mymoments.exception.EmailAlreadyExistsException;
import com.hunganh.mymoments.exception.EmailNotExistsException;
import com.hunganh.mymoments.exception.UsernameAlreadyExistsException;
import com.hunganh.mymoments.model.Profile;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.model.VerificationToken;
import com.hunganh.mymoments.repository.UserRepository;
import com.hunganh.mymoments.repository.VerificationTokenRepository;
import com.hunganh.mymoments.util.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtProvider jwtProvider;
    private final MailService mailService;

    public void signup(RegisterRequest registerRequest) throws UnknownHostException {
        log.info("registering user {}", registerRequest.getUsername());
        // check exceptions
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            log.warn("username {} already exists.", registerRequest.getUsername());
            throw new UsernameAlreadyExistsException(
                    String.format("username %s already exists", registerRequest.getUsername()));
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            log.warn("email {} already exists.", registerRequest.getEmail());
            throw new EmailAlreadyExistsException(
                    String.format("email %s already exists", registerRequest.getEmail()));
        }
        if (!mailService.isAddressValid(registerRequest.getEmail())) {
            log.warn("email {} doesn't exist.", registerRequest.getEmail());
            throw new EmailNotExistsException(
                    String.format("email %s doesn't exist", registerRequest.getEmail()));
        }
        //save user
        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword());
        userRepository.save(user);
        Profile profile = Profile.builder()
                .userId(user.getId())
                .fullName(registerRequest.getFullName())
                .build();
        user.setProfile(profile);
        userRepository.save(user);
        log.info("registered user {}", registerRequest.getUsername());
        System.out.println(user);
        //send verify email
        VerificationToken verificationToken = generateVerificationToken(user);
        String ip = InetAddress.getLocalHost().getHostAddress();
        mailService.sendMail(new NotificationEmail("Activate your Account",
                user.getEmail(), "Thank you for signing up to My Moments with username <b>" + user.getUsername() + "</b>, " +
                "please click on the below url to activate your account : " +
                "http://" + ip + "/api/auth/verify/" + verificationToken.getToken()));

    }

    public Map<String, Object> login(LoginRequest loginRequest) {
        Map<String, Object> result = new HashMap<>();
        if (!userRepository.existsByUsername(loginRequest.getUsername())) {
            throw (new UsernameNotFoundException(
                    String.format("username '%s' doesn't exist", loginRequest.getUsername())));
        }
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = jwtProvider.generateToken(authenticate);
            result.put("Login", new AuthenticationResponse(token, loginRequest.getUsername()));
        } catch (Exception e) {
            throw new AuthenticationException(ResponseConstant.CAN_NOT_LOGIN);
        }
        return result;
    }


    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new RuntimeException("Invalid Token")));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        long userId = verificationToken.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private VerificationToken generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUserId(user.getId());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    @Bean
    CommandLineRunner demo(UserRepository userRepository) {
        return args -> {

//            userRepository.deleteAll();

//            User greg = new User();
//            greg.setUsername("anhtrt");
//            User roy = new User();
//            roy.setUsername("roy");
//            User craig = new User();
//            craig.setUsername("craig");
//
//            userRepository.save(greg);
//            userRepository.save(roy);
//            userRepository.save(craig);

        };
    }
}
