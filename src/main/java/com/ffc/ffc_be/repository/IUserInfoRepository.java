package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.UserInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserInfoRepository extends JpaRepository<UserInfoModel, Integer> {
    Optional<UserInfoModel> findByUsername(String username);
}
