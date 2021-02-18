package biz.gelicon.capital.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
@Schema(description = "Сущность Единица измерения для меры измерения")
@Table(name = "measureunit")
public class Measureunit {

    @Schema(description = "Идентификатор")
    @Id
    @Column(name = "measureunit_id")
    private Integer measureunitId;

    @Schema(description = "Мера измерения")
    @Column(name = "measure_id", nullable = false, columnDefinition = "Мера измерения")
    private Integer measureId;

    @Schema(description = "Единица измерения")
    @Column(name = "unitmeasure_id", nullable = false, columnDefinition = "Единица измерения")
    private Integer unitmeasureId;

    @Schema(description = "Приоритет", example = "0-высший, 9-низщий, ..99-наинижайший")
    @Column(name = "measureunit_priority", nullable = false, columnDefinition = "Приоритет (0-высший, 9-низщий, ..99-наинижайший)")
    private Integer measureunitPriority;

    public Measureunit(
            Integer measureunitId,
            Integer measureId,
            Integer unitmeasureId,
            Integer measureunitPriority
    ) {
        this.measureunitId = measureunitId;
        this.measureId = measureId;
        this.unitmeasureId = unitmeasureId;
        this.measureunitPriority = measureunitPriority;
    }

    public Measureunit() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы


    public Integer getMeasureunitId() {
        return measureunitId;
    }

    public void setMeasureunitId(Integer measureunitId) {
        this.measureunitId = measureunitId;
    }

    public Integer getId() {
        return measureunitId;
    }

    public void setId(Integer id) {
        this.measureunitId = id;
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

    public Integer getMeasureunitPriority() {
        return measureunitPriority;
    }

    public void setMeasureunitPriority(Integer measureunitPriority) {
        this.measureunitPriority = measureunitPriority;
    }

    @Override
    public String toString() {
        return "Measureunit{"
                + "measureunitId=" + measureunitId + ", "
                + "measureId=" + measureId + ", "
                + "unitmeasureId=" + unitmeasureId + ", "
                + "measureunitPriority=" + measureunitPriority
                + "}";
    }

}
