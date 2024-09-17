package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.dto.response.ImExRecipeResponse;
import com.ffc.ffc_be.model.entity.ImExRecipeModel;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IImExRecipeRepository extends JpaRepository<ImExRecipeModel, Integer> {
    @Query(value = "SELECT new com.ffc.ffc_be.model.dto.response.ImExRecipeResponse( " +
            "ier.id , ier.createdAt, ier.updatedAt," +
            "ier.createdBy, uci.fullName," +
            "ier.responsibleBy, ucr.fullName," +
            "ier.description, ier.repType, ier.purpose)" +
            " FROM ImExRecipeModel ier" +
            " JOIN UserCmsInfoModel uci ON uci.id = ier.createdBy" +
            " JOIN UserCmsInfoModel ucr ON ucr.id = ier.responsibleBy" +
            " WHERE ier.repType = :repType")
    Page<ImExRecipeResponse> getImExRecipeList(RepTypeEnum repType, Pageable pageable);
}
