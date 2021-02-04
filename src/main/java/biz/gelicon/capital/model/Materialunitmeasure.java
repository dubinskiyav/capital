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
    @Column(name = "id")
    private Integer id;

    @Column(name = "material_id", nullable = false, columnDefinition = "Материал")
    private Integer materialId;

    @Column(name = "unitmeasure_id", nullable = false, columnDefinition = "Единица измерения")
    private Integer unitmeasureId;

    public Materialunitmeasure(
            Integer id,
            Integer materialId,
            Integer unitmeasureId
    ) {
        this.id = id;
        this.materialId = materialId;
        this.unitmeasureId = unitmeasureId;
    }

    public Materialunitmeasure() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKey() {
        return id;
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
                + "id=" + id + ", "
                + "materialId=" + materialId + ", "
                + "unitmeasureId=" + unitmeasureId
                + "}";
    }

}
