package biz.gelicon.capital.model;

import biz.gelicon.capital.repository.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
public class Material implements Id {

    private Integer id;

    private Integer materiallevelId;

    @NotEmpty(message="Наименование не может быть пустым")
    @Size(max = 255, message = "Наименование должно содержать не более {1} символов")
    private String name;

    @Size(max = 100, message = "Сокращение должно содержать не более {1} символов")
    private String code;

    public Material(
            Integer id,
            Integer materiallevelId,
            String name,
            String code
    ) {
        this.id = id;
        this.materiallevelId = materiallevelId;
        this.name = name;
        this.code = code;
    }

    public Material() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMateriallevelId() {
        return materiallevelId;
    }

    public void setMateriallevelId(Integer materiallevelId) {
        this.materiallevelId = materiallevelId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Material{"
                + "id=" + id + ", "
                + "materiallevelId=" + materiallevelId + ", "
                + "name=" + name + ", "
                + "code=" + code
                + "}";
    }

}
