package com.hunganh.mymoments.service;

import com.hunganh.mymoments.constant.ResponseConstant;
import com.hunganh.mymoments.dto.AuthenticationResponse;
import com.hunganh.mymoments.dto.LoginRequest;
import com.hunganh.mymoments.dto.RegisterRequest;
import com.hunganh.mymoments.exception.AuthenticationException;
import com.hunganh.mymoments.exception.EmailNotExistsException;
import com.hunganh.mymoments.model.Profile;
import com.hunganh.mymoments.model.User;
import com.hunganh.mymoments.repository.UserRepository;
import com.hunganh.mymoments.util.JwtProvider;
import com.hunganh.mymoments.util.MailUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public void signup(RegisterRequest registerRequest) {
        log.info("registering user {}", registerRequest.getUsername());
        // check exception
//        if (userRepository.existsByUsername(registerRequest.getUsername())) {
//            log.warn("username {} already exists.", registerRequest.getUsername());
//            throw new UsernameAlreadyExistsException(
//                    String.format("username %s already exists", registerRequest.getUsername()));
//        }
//        if (userRepository.existsByEmail(registerRequest.getEmail())) {
//            log.warn("email {} already exists.", registerRequest.getEmail());
//            throw new EmailAlreadyExistsException(
//                    String.format("email %s already exists", registerRequest.getEmail()));
//        }
//        if (!MailUtil.isAddressValid(registerRequest.getEmail())) {
//            log.warn("email {} doesn't exist.", registerRequest.getEmail());
//            throw new EmailNotExistsException(
//                    String.format("email %s doesn't exist", registerRequest.getEmail()));
//        }
        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword(), null, true);
        userRepository.save(user);
        Profile profile = Profile.builder()
                .userId(user.getId())
                .fullName(registerRequest.getFullName())
                .build();
        user.setProfile(profile);
        userRepository.save(user);
        System.out.println(user);

//
//        VerificationToken verificationToken = generateVerificationToken(user);
//        mailService.sendMail(new NotificationEmail("Activate your Account",
//                profile.getEmail(), "Thank you for signing up to My Moments with username <b>" + user.getUsername() + "</b>, " +
//                "please click on the below url to activate your account : " +
//                "http://localhost:8081/api/auth/verify/" + verificationToken.getToken()));
//        assocBaseRepository.addAssoc(user, SnwAssocType.HAS_VERTIFICATION_TOKEN, verificationToken, "", new Date().getTime());

    }

    public Map<String, Object> login(LoginRequest loginRequest) {
        Map<String, Object> result = new HashMap<>();
        if (!userRepository.existsByUsername(loginRequest.getUsername())) {
            throw (new EmailNotExistsException(
                    String.format("email %s doesn't exist", loginRequest.getUsername())));
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
//        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
//        fetchUserAndEnable(verificationToken.orElseThrow(() -> new MyMomentsException("Invalid Token")));
    }

//    private void fetchUserAndEnable(VerificationToken verificationToken) {
//        String username = verificationToken.getVerificationOwnerships().iterator().next().getStartNode().getUsername();
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new MyMomentsException("User not found with name - " + username));
//        user.setEnabled(true);
//        userRepository.save(user);
//    }
//
//    private VerificationToken generateVerificationToken(User user) {
//        String token = UUID.randomUUID().toString();
//        VerificationToken verificationToken = new VerificationToken();
//        verificationToken.setToken(token);
//
//        verificationTokenRepository.save(verificationToken);
//        return verificationToken;
//    }
//
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
