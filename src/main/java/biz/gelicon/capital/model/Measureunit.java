package biz.gelicon.capital.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
public class Measureunit {

    private Integer id;

    private Integer measureId;

    @NotEmpty(message="Наименование не может быть пустым")
    @Size(max = 100, message = "Наименование должно содержать не более {1} символов")
    private String name;

    @Size(max = 20, message = "Сокращение должно содержать не более {1} символов")
    private String shortName;

    public Measureunit(
            Integer id,
            Integer measureId,
            String name,
            String shortName
    ) {
        this.id = id;
        this.measureId = measureId;
        this.name = name;
        this.shortName = shortName;
    }

    public Measureunit() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Integer measureId) {
        this.measureId = measureId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return name;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return "Measureunit{"
                + "id=" + id + ", "
                + "measureId=" + measureId + ", "
                + "name=" + name + ", "
                + "shortName=" + shortName
                + "}";
    }

}
