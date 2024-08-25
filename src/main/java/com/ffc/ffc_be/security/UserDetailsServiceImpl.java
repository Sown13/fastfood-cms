package com.ffc.ffc_be.security;

import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.repository.IUserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCmsInfoModel> userInfo = userInfoRepository.findByUsername(username);
        if (userInfo.isPresent()) {
            return new UserDetailsImpl(userInfo.get());
        }

        return null;
    }

    public UserDetails loadUserById(Integer id) throws UsernameNotFoundException {
        Optional<UserCmsInfoModel> userBasicInfo = userInfoRepository.findById(id);
        if (userBasicInfo.isPresent()) {
            return new UserDetailsImpl(userBasicInfo.get());
        }

        return null;
    }
}
