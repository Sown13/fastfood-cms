package com.ffc.ffc_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICRUDBaseRepository<T, ID> extends JpaRepository<T, ID> {
}
