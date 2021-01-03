package biz.gelicon.capital.model;

import biz.gelicon.capital.repository.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
public class Unitmeasure implements Id {

    private Integer id;

    @NotEmpty(message="Наименование не может быть пустым")
    @Size(max = 100, message = "Наименование должно содержать не более {1} символов")
    private String name;

    @Size(max = 20, message = "Обозначение должно содержать не более {1} символов")
    private String shortName;

    public Unitmeasure(
            Integer id,
            String name,
            String shortName
    ) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

    public Unitmeasure() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String name) {
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return "Unitmeasure{"
                + "id=" + id + ", "
                + "name=" + name + ", "
                + "shortName=" + shortName
                + "}";
    }

}
