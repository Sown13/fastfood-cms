package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.entity.UserInfoModel;
import com.ffc.ffc_be.security.UserDetailsImpl;
import lombok.CustomLog;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@CustomLog
public class UserInfoService {
    public UserInfoModel getUserInfoFromContext() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            return userDetails.getUser();
        } catch (Exception e) {
            log.error("Failed when get user from context");

            return null;
        }
    }
}
