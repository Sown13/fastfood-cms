package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.AccountAssetModel;
import com.ffc.ffc_be.model.entity.AccountEquityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountEquityRepository extends JpaRepository<AccountEquityModel, Integer> {
}
