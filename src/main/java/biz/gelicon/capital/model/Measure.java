package biz.gelicon.capital.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
@Table(name = "measure")
public class Measure {

    @Id
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message="Наименование не может быть пустым")
    @Size(max = 100, message = "Наименование должно содержать не более {1} символов")
    private String name;

    public Measure(
            Integer id,
            String name
    ) {
        this.id = id;
        this.name = name;
    }

    public Measure() {
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

    @Override
    public String toString() {
        return "Measure{"
                + "id=" + id + ", "
                + "name=" + name
                + "}";
    }

}
