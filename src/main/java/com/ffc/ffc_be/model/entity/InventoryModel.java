package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.BaseEntity;
import com.ffc.ffc_be.model.dto.response.InventoryResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "inventory",
        indexes = {
                @Index(name = "idx_material_id", columnList = "material_id") //indexing for better performance of paginate
        })
@Entity
@NamedNativeQuery(
        name = "InventoryModel.getNewestInventory",
        query = "SELECT inv.id as id, " +
                "inv.created_at as createdAt, inv.updated_at as updatedAt," +
                "inv.material_id as materialId, mt.name as name, mt.code as code, inv.quantity as quantity, " +
                "mt.unit_type as unitType, mt.shelf_life as shelfLife, mt.description as description," +
                "inv.note as note, mt.deprecated as deprecated" +
                " FROM " +
                "(SELECT inv.material_id FROM inventory inv LIMIT :limit OFFSET :offset) as temp" + // core trick for better performance paginate
                " LEFT JOIN inventory inv ON inv.material_id = temp.material_id" +
                " LEFT JOIN materials mt ON mt.id = inv.material_id",
        resultSetMapping = "Mapping.InventoryResponse")
@SqlResultSetMapping(
        name = "Mapping.InventoryResponse",
        classes = @ConstructorResult(
                targetClass = InventoryResponse.class,
                columns = {
                        @ColumnResult(name = "id"),
                        @ColumnResult(name = "createdAt", type = LocalDateTime.class),
                        @ColumnResult(name = "updatedAt", type = LocalDateTime.class),
                        @ColumnResult(name = "materialId"),
                        @ColumnResult(name = "name"),
                        @ColumnResult(name = "code"),
                        @ColumnResult(name = "quantity"),
                        @ColumnResult(name = "unitType"),
                        @ColumnResult(name = "shelfLife"),
                        @ColumnResult(name = "description"),
                        @ColumnResult(name = "note"),
                        @ColumnResult(name = "deprecated")
                }
        )
)
public class InventoryModel extends BaseEntity {
    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "note")
    private String note;

    @Column(name = "quantity")
    private Integer quantity;
}
