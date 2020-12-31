package biz.gelicon.capital.model;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
public class Materialmeasureunit {

    private Integer id;

    private Integer materialId;

    private Integer measureunitId;

    public Materialmeasureunit(
            Integer id,
            Integer materialId,
            Integer measureunitId
    ) {
        this.id = id;
        this.materialId = materialId;
        this.measureunitId = measureunitId;
    }

    public Materialmeasureunit() {
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


    public Integer getMeasureunitId() {
        return measureunitId;
    }

    public void setMeasureunitId(Integer measureunitId) {
        this.measureunitId = measureunitId;
    }

    @Override
    public String toString() {
        return "Materialmeasureunit{"
                + "id=" + id + ", "
                + "materialId=" + materialId + ", "
                + "measureunitId=" + measureunitId
                + "}";
    }

}
