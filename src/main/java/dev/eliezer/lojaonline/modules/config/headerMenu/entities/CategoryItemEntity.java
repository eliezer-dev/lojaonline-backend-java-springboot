package dev.eliezer.lojaonline.modules.config.headerMenu.entities;
import dev.eliezer.lojaonline.modules.product.entities.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity(name = "tb_config_header_menu_categories")
public class CategoryItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "id of menu categories")
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false,unique = true)
    private CategoryEntity category;

    private String descriptionMenu;

    private Long orderMenu;

}
