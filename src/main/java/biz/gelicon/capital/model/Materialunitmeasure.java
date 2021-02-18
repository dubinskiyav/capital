package biz.gelicon.capital.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
@Table(name = "materialunitmeasure")
public class Materialunitmeasure  {

    @Id
    @Column(name = "materialunitmeasure_id")
    private Integer materialunitmeasureId;

    @Column(name = "material_id", nullable = false, columnDefinition = "Материал")
    private Integer materialId;

    @Column(name = "unitmeasure_id", nullable = false, columnDefinition = "Единица измерения")
    private Integer unitmeasureId;

    public Materialunitmeasure(
            Integer materialunitmeasureId,
            Integer materialId,
            Integer unitmeasureId
    ) {
        this.materialunitmeasureId = materialunitmeasureId;
        this.materialId = materialId;
        this.unitmeasureId = unitmeasureId;
    }

    public Materialunitmeasure() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы



    public Integer getMaterialunitmeasureId() {
        return materialunitmeasureId;
    }

    public void setMaterialunitmeasureId(Integer materialunitmeasureId) {
        this.materialunitmeasureId = materialunitmeasureId;
    }

    public Integer getId() {
        return materialunitmeasureId;
    }

    public void setId(Integer id) {
        this.materialunitmeasureId = id;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }


    public Integer getUnitmeasureId() {
        return unitmeasureId;
    }

    public void setUnitmeasureId(Integer unitmeasureId) {
        this.unitmeasureId = unitmeasureId;
    }

    @Override
    public String toString() {
        return "Materialmeasureunit{"
                + "materialunitmeasureId=" + materialunitmeasureId + ", "
                + "materialId=" + materialId + ", "
                + "unitmeasureId=" + unitmeasureId
                + "}";
    }

}
