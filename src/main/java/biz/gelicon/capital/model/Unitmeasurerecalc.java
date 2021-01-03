package biz.gelicon.capital.model;

import org.springframework.data.relational.core.sql.In;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
public class Unitmeasurerecalc {

    private Integer id;

    private Integer unitmeasurefromId;

    private Integer unitmeasuretoId;

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
