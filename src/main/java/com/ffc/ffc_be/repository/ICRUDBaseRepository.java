package com.ffc.ffc_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICRUDBaseRepository<T, ID> extends JpaRepository<T, ID> {
}
