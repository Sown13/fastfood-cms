package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.RegisterRequest;
import com.ffc.ffc_be.model.dto.response.RegisterResponse;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.security.AuthenticationService;
import com.ffc.ffc_be.security.UserDetailsImpl;
import com.ffc.ffc_be.security.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@CustomLog
@RequiredArgsConstructor
public class UserCmsInfoService {
    private final AuthenticationService authService;
    private final UserDetailsServiceImpl userDetailsService;
    private final ModelMapper mapper;

    public UserCmsInfoModel getUserInfoFromContext() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            return userDetails.getUser();
        } catch (Exception e) {
            log.error("Failed when get user from context");

            return null;
        }
    }

    public ResponseEntity<ResponseDto<RegisterResponse>> register(@RequestBody @Valid RegisterRequest request) {
        UserDetailsImpl registeredUser;
        try {
            UserDetails usernameExist = userDetailsService.loadUserByUsername(request.getUsername());
            if (usernameExist != null) {
                return ResponseBuilder.badRequestResponse("Create new user failed, please use other username",
                        StatusCodeEnum.STATUSCODE2001);
            }

            UserCmsInfoModel userCmsInfoModel;
            try {
                userCmsInfoModel = getUserInfoFromContext();
                if (userCmsInfoModel == null) {
                    return ResponseBuilder.badRequestResponse("Create new user failed, can not get user info from context",
                            StatusCodeEnum.STATUSCODE2001);
                }
            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse("Create new user failed, error when get user info from context",
                        StatusCodeEnum.STATUSCODE2001);
            }

            registeredUser = authService.signup(request, userCmsInfoModel);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Create new user failed, unexpected error",
                    StatusCodeEnum.STATUSCODE2001);
        }

        RegisterResponse response = mapper.map(registeredUser.getUser(), RegisterResponse.class);

        return ResponseBuilder.okResponse("Create new user successfully",
                response,
                StatusCodeEnum.STATUSCODE1001);
    }
}
