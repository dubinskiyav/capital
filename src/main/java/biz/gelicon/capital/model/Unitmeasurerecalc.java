package biz.gelicon.capital.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
@Table(name = "unitmeasurerecalc")
public class Unitmeasurerecalc {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "unitmeasurefrom_id", nullable = false, columnDefinition = "Исходная единица измерения")
    private Integer unitmeasurefromId;

    @Column(name = "unitmeasureto_id", nullable = false, columnDefinition = "Целевая единица измерения")
    private Integer unitmeasuretoId;

    @Column(name = "factor", nullable = false, columnDefinition = "Коэффициент пересчета")
    private Float factor;

    public Unitmeasurerecalc(
            Integer id,
            Integer unitmeasurefromId,
            Integer unitmeasuretoId,
            Float factor
    ) {
        this.id = id;
        this.unitmeasurefromId = unitmeasurefromId;
        this.unitmeasuretoId = unitmeasuretoId;
        this.factor = factor;
    }

    public Unitmeasurerecalc() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getunitmeasurefromId() {
        return unitmeasurefromId;
    }

    public void setunitmeasurefromId(Integer unitmeasurefromId) {
        this.unitmeasurefromId = unitmeasurefromId;
    }

    public Integer getunitmeasuretoId() {
        return unitmeasuretoId;
    }

    public void setunitmeasuretoId(Integer unitmeasuretoId) {
        this.unitmeasuretoId = unitmeasuretoId;
    }

    public Float getfactor() {
        return factor;
    }

    public void setfactor(Float factor) {
        this.factor = factor;
    }

    @Override
    public String toString() {
        return "Unitmeasurerecalc{"
                + "id=" + id + ", "
                + "unitmeasurefromId=" + unitmeasurefromId + ", "
                + "unitmeasuretoId=" + unitmeasuretoId + ", "
                + "factor=" + factor
                + "}";
    }

}
