package biz.gelicon.capital.model;

import biz.gelicon.capital.repository.IdField;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
public class Materialunitmeasure implements IdField {

    private Integer id;

    private Integer materialId;

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
