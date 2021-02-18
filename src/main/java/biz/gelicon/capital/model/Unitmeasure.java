package biz.gelicon.capital.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
@Table(name = "unitmeasure")
public class Unitmeasure {

    @Id
    @Column(name = "unitmeasure_id")
    private Integer unitmeasureId;

    @NotEmpty(message="Наименование не может быть пустым")
    @Size(max = 100, message = "Наименование должно содержать не более {1} символов")
    @Column(name = "unitmeasure_name", nullable = false, columnDefinition = "Наименование")
    private String unitmeasureName;

    @Size(max = 20, message = "Обозначение должно содержать не более {1} символов")
    @Column(name = "unitmeasure_shortname", nullable = true, columnDefinition = "Обозначение")
    private String unitmeasureShortname;

    public Unitmeasure(
            Integer unitmeasureId,
            String unitmeasureName,
            String unitmeasureShortname
    ) {
        this.unitmeasureId = unitmeasureId;
        this.unitmeasureName = unitmeasureName;
        this.unitmeasureShortname = unitmeasureShortname;
    }

    public Unitmeasure() {
    }

    public Integer getUnitmeasureId() {
        return unitmeasureId;
    }

    public void setUnitmeasureId(Integer unitmeasureId) {
        this.unitmeasureId = unitmeasureId;
    }

    public Integer getId() {
        return unitmeasureId;
    }

    public String getUnitmeasureName() {
        return unitmeasureName;
    }

    public String getUnitmeasureShortname() {
        return unitmeasureShortname;
    }

    public void setId(Integer id) {
        this.unitmeasureId = id;
    }

    public void setUnitmeasureName(String unitmeasureName) {
        this.unitmeasureName = unitmeasureName;
    }

    public void setUnitmeasureShortname(String unitmeasureShortname) {
        this.unitmeasureShortname = unitmeasureShortname;
    }

    @Override
    public String toString() {
        return "Unitmeasure{" +
                "unitmeasureId=" + unitmeasureId +
                ", unitmeasureName='" + unitmeasureName + '\'' +
                ", unitmeasureShortname='" + unitmeasureShortname + '\'' +
                '}';
    }
}
