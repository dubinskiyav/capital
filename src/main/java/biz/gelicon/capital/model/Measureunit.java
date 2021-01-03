package biz.gelicon.capital.model;

import biz.gelicon.capital.repository.Id;
import org.springframework.data.relational.core.sql.In;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
public class Measureunit implements Id {

    private Integer id;

    private Integer measureId;

    private Integer unitmeasureId;

    private Integer priority;

    public Measureunit(
            Integer id,
            Integer measureId,
            Integer unitmeasureId,
            Integer priority
    ) {
        this.id = id;
        this.measureId = measureId;
        this.unitmeasureId = unitmeasureId;
        this.priority = priority;
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

    public Integer getUnitmeasureId() {
        return unitmeasureId;
    }

    public void setUnitmeasureId(Integer unitmeasureId) {
        this.unitmeasureId = unitmeasureId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Measureunit{"
                + "id=" + id + ", "
                + "measureId=" + measureId + ", "
                + "unitmeasureId=" + unitmeasureId + ", "
                + "priority=" + priority
                + "}";
    }

}
