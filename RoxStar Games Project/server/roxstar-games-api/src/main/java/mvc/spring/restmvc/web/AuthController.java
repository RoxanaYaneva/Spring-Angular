package mvc.spring.restmvc.web;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.restmvc.exception.AppException;
import mvc.spring.restmvc.exception.EntityAlreadyExistsException;
import mvc.spring.restmvc.model.Role;
import mvc.spring.restmvc.model.User;
import mvc.spring.restmvc.model.enums.UserProfile;
import mvc.spring.restmvc.payload.ApiResponse;
import mvc.spring.restmvc.payload.JwtAuthenticationResponse;
import mvc.spring.restmvc.payload.LoginRequest;
import mvc.spring.restmvc.payload.SignUpRequest;
import mvc.spring.restmvc.security.JwtTokenProvider;
import mvc.spring.restmvc.service.RoleService;
import mvc.spring.restmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userService.getUserByEmail(signUpRequest.getEmail()) != null) {
            throw new EntityAlreadyExistsException(String.format("Email %s is already taken!", signUpRequest.getEmail()));
        }

        User user = new User(signUpRequest.getEmail(), signUpRequest.getPassword(),
                signUpRequest.getFirstName(), signUpRequest.getLastName(), new HashSet<>());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role customerRole = roleService.getRoleByUserProfile(UserProfile.ROLE_CUSTOMER)
                .orElseThrow(() -> new AppException("Customer Role not set."));
        user.setRoles(new HashSet<>(Arrays.asList(customerRole)));

        User result = userService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}