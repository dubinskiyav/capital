package biz.gelicon.capital.model;

import javax.validation.constraints.NotNull;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
public class Measureunitrecalc {

    private Integer id;

    private Integer mainMeasureunitId;

    private Integer measureunitId;

    @NotNull(message = "Коэффициент пересчета не может быть пустым")
    private Float conversionFactor;

    public Measureunitrecalc(
            Integer id,
            Integer mainMeasureunitId,
            Integer measureunitId,
            Float conversionFactor
    ) {
        this.id = id;
        this.mainMeasureunitId = mainMeasureunitId;
        this.measureunitId = measureunitId;
        this.conversionFactor = conversionFactor;
    }

    public Measureunitrecalc() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMainMeasureunitId() {
        return mainMeasureunitId;
    }

    public void setMainMeasureunitId(Integer mainMeasureunitId) {
        this.mainMeasureunitId = mainMeasureunitId;
    }


    public Integer getMeasureunitId() {
        return measureunitId;
    }

    public void setMeasureunitId(Integer measureunitId) {
        this.measureunitId = measureunitId;
    }

    public Float getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(Float conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public String toString() {
        return "Measureunitrecalc{"
                + "id=" + id + ", "
                + "mainMeasureunitId=" + mainMeasureunitId + ", "
                + "measureunitId=" + measureunitId + ", "
                + "conversionFactor=" + conversionFactor
                + "}";
    }

}
