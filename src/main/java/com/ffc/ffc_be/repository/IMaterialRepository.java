package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.MaterialModel;
import org.springframework.stereotype.Repository;

@Repository
public interface IMaterialRepository extends ICRUDBaseRepository<MaterialModel, Integer> {
}
