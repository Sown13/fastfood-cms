package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserInfoRepository extends JpaRepository<UserCmsInfoModel, Integer> {
    Optional<UserCmsInfoModel> findByUsername(String username);
}
