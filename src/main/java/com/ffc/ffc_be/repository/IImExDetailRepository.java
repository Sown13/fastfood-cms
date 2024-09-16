package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.dto.puredto.ImExDetailHistoryDto;
import com.ffc.ffc_be.model.entity.ImExDetailModel;
import org.hibernate.query.Page;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImExDetailRepository extends JpaRepository<ImExDetailModel, Integer> {
//    @Query("SELECT new com.ffc.ffc_be.model.dto.puredto.ImExDetailHistoryDto()" +
//            "FROM import_export_detail as ied" +
//            " JOIN materials as ms ON ied.material_id = ms.id" +
//            " JOIN recipe as re ON ied.recipe_id = re.id"
//    )
//    List<ImExDetailHistoryDto> getImExDetailHistoryList(Pageable pageable);
}
