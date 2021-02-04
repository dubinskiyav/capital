package biz.gelicon.capital.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
@Table(name = "measureunit")
public class Measureunit {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "measure_id", nullable = false, columnDefinition = "Мера измерения")
    private Integer measureId;

    @Column(name = "unitmeasure_id", nullable = false, columnDefinition = "Единица измерения")
    private Integer unitmeasureId;

    @Column(name = "priority", nullable = false, columnDefinition = "Приоритет (0-высший, 9-низщий, 99..99-наинижайший)")
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

    public Integer getKey() {
        return id;
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
