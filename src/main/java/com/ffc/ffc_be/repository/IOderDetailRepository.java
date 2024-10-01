package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.OrderDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOderDetailRepository extends JpaRepository<OrderDetailModel, Integer> {
}
