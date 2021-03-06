package biz.gelicon.capital.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
@Schema(description = "Сущность материала")
@Table(name = "material")
public class Material {

    @Schema(description = "Идентификатор")
    @Id
    @Column(name = "material_id")
    private Integer materialId;

    @Schema(description = "Уровень")
    @Column(name = "materiallevel_id", nullable = false, columnDefinition = "Уровень")
    private Integer materiallevelId;

    @Schema(description = "Тип", example = "1 - Материал, 2 - Услуга")
    @Column(name = "material_kind", nullable = false, columnDefinition = "1 - Материал, 2 - Услуга")
    private Integer materialKind;

    @Schema(description = "Наименование")
    @NotEmpty(message="Наименование не может быть пустым")
    @Size(max = 255, message = "Наименование должно содержать не более {1} символов")
    @Column(name = "material_name", nullable = false, columnDefinition = "Наименование")
    private String materialName;

    @Schema(description = "Код")
    @Size(max = 100, message = "Код должно содержать не более {1} символов")
    @Column(name = "material_code", nullable = true, columnDefinition = "Код")
    private String materialCode;

    public Material(
            Integer materialId,
            Integer materiallevelId,
            Integer materialKind,
            String materialName,
            String materialCode
    ) {
        this.materialId = materialId;
        this.materiallevelId = materiallevelId;
        this.materialKind = materialKind;
        this.materialName = materialName;
        this.materialCode = materialCode;
    }

    public Material() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы


    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getId() {
        return materialId;
    }

    public Integer getMateriallevelId() {
        return materiallevelId;
    }

    public Integer getMaterialKind() {
        return materialKind;
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setId(Integer id) {
        this.materialId = id;
    }

    public void setMateriallevelId(Integer materiallevelId) {
        this.materiallevelId = materiallevelId;
    }

    public void setMaterialKind(Integer materialKind) {
        this.materialKind = materialKind;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    @Override
    public String toString() {
        return "Material{" +
                "materialId=" + materialId +
                ", materiallevelId=" + materiallevelId +
                ", materialKind=" + materialKind +
                ", materialName='" + materialName + '\'' +
                ", materialCode='" + materialCode + '\'' +
                '}';
    }

}
