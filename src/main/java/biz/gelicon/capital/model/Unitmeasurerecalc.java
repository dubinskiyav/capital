package biz.gelicon.capital.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
@Schema(description = "Сущность Пересчет единиц измерения")
@Table(name = "unitmeasurerecalc")
public class Unitmeasurerecalc {

    @Schema(description = "Идентификатор")
    @Id
    @Column(name = "unitmeasurerecalc_id")
    private Integer unitmeasurerecalcId;

    @Schema(description = "Исходная единица измерения")
    @Column(name = "unitmeasure_idfrom", nullable = false, columnDefinition = "Исходная единица измерения")
    private Integer unitmeasureIdFrom;

    @Schema(description = "Целевая единица измерения")
    @Column(name = "unitmeasure_idto", nullable = false, columnDefinition = "Целевая единица измерения")
    private Integer unitmeasureIdTo;

    @Schema(description = "Коэффициент пересчета - цисло, на которое надо умножить исходную единицу измерения, чтобы получить конечную",
            example = "Исходная - метр, конечная - километр, коэффициент - 0.001")
    @Column(name = "unitmeasurerecalc_factor", nullable = false, columnDefinition = "Коэффициент пересчета")
    private Double unitmeasurerecalcFactor;

    public Unitmeasurerecalc(
            Integer unitmeasurerecalcId,
            Integer unitmeasureIdFrom,
            Integer unitmeasureIdTo,
            Double unitmeasurerecalcFactor
    ) {
        this.unitmeasurerecalcId = unitmeasurerecalcId;
        this.unitmeasureIdFrom = unitmeasureIdFrom;
        this.unitmeasureIdTo = unitmeasureIdTo;
        this.unitmeasurerecalcFactor = unitmeasurerecalcFactor;
    }

    public Unitmeasurerecalc() {
    }

    public Integer getUnitmeasurerecalcId() {
        return unitmeasurerecalcId;
    }

    public void setUnitmeasurerecalcId(Integer unitmeasurerecalcId) {
        this.unitmeasurerecalcId = unitmeasurerecalcId;
    }

    public Integer getId() {
        return unitmeasurerecalcId;
    }

    public Integer getUnitmeasureIdFrom() {
        return unitmeasureIdFrom;
    }

    public Integer getUnitmeasureIdTo() {
        return unitmeasureIdTo;
    }

    public Double getUnitmeasurerecalcFactor() {
        return unitmeasurerecalcFactor;
    }

    public void setId(Integer id) {
        this.unitmeasurerecalcId = id;
    }

    public void setUnitmeasureIdFrom(Integer unitmeasureIdFrom) {
        this.unitmeasureIdFrom = unitmeasureIdFrom;
    }

    public void setUnitmeasureIdTo(Integer unitmeasureIdTo) {
        this.unitmeasureIdTo = unitmeasureIdTo;
    }

    public void setUnitmeasurerecalcFactor(Double unitmeasurerecalcFactor) {
        this.unitmeasurerecalcFactor = unitmeasurerecalcFactor;
    }

    @Override
    public String toString() {
        return "Unitmeasurerecalc{" +
                "unitmeasurerecalcId=" + unitmeasurerecalcId +
                ", unitmeasureIdFrom=" + unitmeasureIdFrom +
                ", unitmeasureIdTo=" + unitmeasureIdTo +
                ", unitmeasurerecalcFactor=" + unitmeasurerecalcFactor +
                '}';
    }

}
