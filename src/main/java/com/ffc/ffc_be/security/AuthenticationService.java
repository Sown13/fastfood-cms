package com.ffc.ffc_be.security;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.LoginRequest;
import com.ffc.ffc_be.model.dto.request.RegisterRequest;
import com.ffc.ffc_be.model.dto.response.LoginResponse;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.model.enums.RoleEnum;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IUserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    private final JwtService jwtService;

    public UserDetailsImpl signup(RegisterRequest request) {
        UserCmsInfoModel user = UserCmsInfoModel.builder()
                .firstName(request.getFirstName())
                .username(request.getUsername())
                .role(RoleEnum.valueOf(request.getRole()))
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

    public ResponseEntity<ResponseDto<LoginResponse>> requestLogin(LoginRequest request) {
        UserDetailsImpl authenticatedUser;
        try {
            authenticatedUser = authenticate(request);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Login Failed", StatusCodeEnum.STATUSCODE2001);
        }

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseBuilder.okResponse("Login successfully", loginResponse, StatusCodeEnum.STATUSCODE1001);
    }
}
