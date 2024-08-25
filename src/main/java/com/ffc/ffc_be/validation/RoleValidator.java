package com.ffc.ffc_be.validation;

import com.ffc.ffc_be.annotation.IsValidRole;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.model.enums.RoleEnum;
import com.ffc.ffc_be.service.UserCmsInfoService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleValidator implements ConstraintValidator<IsValidRole, String> {
    private final UserCmsInfoService userCmsInfoService;

    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
        RoleEnum requestRole;
        try {
            requestRole = RoleEnum.valueOf(role.toUpperCase());
        } catch (Exception e) {
            return false;
        }

        UserCmsInfoModel userInfo;
        try {
            userInfo = userCmsInfoService.getUserInfoFromContext();
            if (userInfo == null) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Error validate, can't get user info from context")
                        .addConstraintViolation();
                return false;
            }
        } catch (Exception e) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Error validate when get user info from context")
                    .addConstraintViolation();
            return false;
        }

        if (requestRole.equals(RoleEnum.BOSS)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Can not create user with this role")
                    .addConstraintViolation();
            return false;
        }

        if (userInfo.getRole().equals(RoleEnum.BOSS)) {
            return true;
        } else if (userInfo.getRole().equals(RoleEnum.MANAGER) && (requestRole.equals(RoleEnum.SALE) || requestRole.equals(RoleEnum.SHIPPER))) {
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
