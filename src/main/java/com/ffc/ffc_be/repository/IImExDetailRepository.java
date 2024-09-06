package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.ImExDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImExDetailRepository extends JpaRepository<ImExDetailModel, Integer> {
}
