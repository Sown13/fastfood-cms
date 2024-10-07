package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.dto.puredto.ImExRecipeDetailDto;
import com.ffc.ffc_be.model.dto.response.ImExDetailHistoryResponse;
import com.ffc.ffc_be.model.entity.ImExDetailModel;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImExDetailRepository extends JpaRepository<ImExDetailModel, Integer> {
    // Practice with JPQL (note for me: JPQL does not allow subquery, so for now just use basic paginate)
    @Query("SELECT new com.ffc.ffc_be.model.dto.response.ImExDetailHistoryResponse(" +
            "ied.id, ied.recipeId, ied.createdAt, ied.updatedAt, ied.materialId, ms.name, " +
            "ied.quantity,ied.factoryDate, ied.note, ied.totalValue, ied.valuePerUnit, ied.supplier," +
            "re.createdBy, ucc.fullName," +
            "re.responsibleBy, ucr.fullName," +
            "re.repType, re.purpose" +
            ")" +
            "FROM ImExDetailModel ied " +
            "JOIN MaterialModel ms ON ied.materialId = ms.id " +
            "JOIN ImExRecipeModel re ON ied.recipeId = re.id " +
            "JOIN UserCmsInfoModel ucc ON re.createdBy = ucc.id " +
            "JOIN UserCmsInfoModel ucr ON re.responsibleBy = ucr.id" +
            " WHERE re.repType = :repType"
    )
    List<ImExDetailHistoryResponse> getImExDetailHistoryList(RepTypeEnum repType, Pageable pageable); //note for me: pageable inter face always add limit and offset into the end of query

    @Query("SELECT COUNT(ied) " +
            "FROM ImExDetailModel ied " +
            "JOIN MaterialModel ms ON ied.materialId = ms.id " +
            "JOIN ImExRecipeModel re ON ied.recipeId = re.id " +
            "JOIN UserCmsInfoModel ucc ON re.createdBy = ucc.id " +
            "JOIN UserCmsInfoModel ucr ON re.responsibleBy = ucr.id" +
            " WHERE re.repType = :repType")
    long countImExDetailHistory(@Param("repType") RepTypeEnum repType);

    @Query("SELECT new com.ffc.ffc_be.model.dto.puredto.ImExRecipeDetailDto(" +
            "ied.id, ied.materialId, ms.name, ied.quantity, ied.factoryDate," +
            "ied.note, ied.totalValue, ied.valuePerUnit, ied.supplier)" +
            "FROM ImExDetailModel ied " +
            "JOIN MaterialModel ms ON ied.materialId = ms.id " +
            "JOIN ImExRecipeModel ier ON ied.recipeId = ier.id " +
            "WHERE ied.recipeId = :recipeId")
    List<ImExRecipeDetailDto> getImExRecipeDetailByRecipeId(Integer recipeId);

    @Query(value = "SELECT * FROM fastfood_cms.import_export_detail" +
            " WHERE material_id = ?1 AND type = 'IMPORT' AND queue_status IN ('HEAD', 'ENQUEUE')" +
            " ORDER BY queue_status, factory_date  ASC ",
            nativeQuery = true)
    List<ImExDetailModel> findImportListForQueue(Integer materialId);

    @Query(value = "SELECT * FROM fastfood_cms.import_export_detail" +
            " WHERE material_id = ?1 AND type = 'EXPORT' AND queue_status IS NULL" +
            " ORDER BY created_at ASC",
            nativeQuery = true)
    List<ImExDetailModel> findExportListForQueue(Integer materialId);
}
