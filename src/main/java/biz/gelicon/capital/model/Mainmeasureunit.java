package biz.gelicon.capital.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
public class Mainmeasureunit {

    private Integer mainMeasureunitId;

    public Mainmeasureunit(
            Integer mainMeasureunitId
    ) {
        this.mainMeasureunitId = mainMeasureunitId;
    }

    public Mainmeasureunit() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы

    public Integer getMainMeasureunitId() {
        return mainMeasureunitId;
    }

    public void setMainMeasureunitId(Integer mainMeasureunitId) {
        this.mainMeasureunitId = mainMeasureunitId;
    }
    @Override
    public String toString() {
        return "Mainmeasureunit{"
                + "mainMeasureunitId=" + mainMeasureunitId
                + "}";
    }

}
