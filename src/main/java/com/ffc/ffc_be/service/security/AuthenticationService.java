package com.ffc.ffc_be.service.security;

import com.ffc.ffc_be.config.security.UserDetailsImpl;
import com.ffc.ffc_be.model.dto.request.LoginRequest;
import com.ffc.ffc_be.model.dto.request.RegisterRequest;
import com.ffc.ffc_be.model.entity.UserInfoModel;
import com.ffc.ffc_be.repository.IUserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserInfoRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserDetailsImpl signup(RegisterRequest request) {
        UserInfoModel user = UserInfoModel.builder()
                .firstName(request.getFirstName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return new UserDetailsImpl(userRepository.save(user));
    }

    public UserDetailsImpl authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return new UserDetailsImpl(userRepository.findByUsername(request.getUsername())
                .orElseThrow());
    }
}
