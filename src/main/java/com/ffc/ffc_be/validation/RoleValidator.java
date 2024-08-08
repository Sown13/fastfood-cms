package com.ffc.ffc_be.validation;

import com.ffc.ffc_be.annotation.IsValidRole;
import com.ffc.ffc_be.model.entity.UserInfoModel;
import com.ffc.ffc_be.model.enums.RoleEnum;
import com.ffc.ffc_be.service.UserInfoService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleValidator implements ConstraintValidator<IsValidRole, String> {
    private final UserInfoService userInfoService;

    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
        try {
            RoleEnum.valueOf(role.toUpperCase());
        } catch (Exception e) {
            return false;
        }

        UserInfoModel userInfo = userInfoService.getUserInfoFromContext();
        if (userInfo.getRole().equals("BOSS")) {
            return true;
        } else if (userInfo.getRole().equals("MANAGER") && (role.equals("SALE") || role.equals("SHIPPER"))) {
            return true;
        }

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate("You do not have permission for creating account with this ROLE").addConstraintViolation();
        return false;
    }

    @Override
    public void initialize(IsValidRole constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


}
