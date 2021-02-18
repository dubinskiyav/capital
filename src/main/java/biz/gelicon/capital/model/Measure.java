package biz.gelicon.capital.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
@Table(name = "measure")
public class Measure {

    @Id
    @Column(name = "measure_id")
    private Integer measureId;

    @NotEmpty(message = "Наименование не может быть пустым")
    @Size(max = 100, message = "Наименование должно содержать не более {1} символов")
    @Column(name = "measure_name", nullable = false, columnDefinition = "Наименование")
    private String measureName;

    public Measure(
            Integer measureId,
            String measureName
    ) {
        this.measureId = measureId;
        this.measureName = measureName;
    }

    public Measure() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы


    public Integer getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Integer measureId) {
        this.measureId = measureId;
    }

    public Integer getId() {
        return measureId;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setId(Integer id) {
        this.measureId = id;
    }

    public void setMeasureName(String measureName) {
        this.measureName = measureName;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "measureId=" + measureId +
                ", measureName='" + measureName + '\'' +
                '}';
    }
}
