package biz.gelicon.capital.model;

import biz.gelicon.capital.repository.IdField;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
public class Service implements IdField {

    private Integer serviceId;

    public Service(
            Integer serviceId
    ) {
        this.serviceId = serviceId;
    }

    public Service() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
    @Override
    public String toString() {
        return "Service{"
                + "serviceId=" + serviceId
                + "}";
    }

    @Override
    public Integer getId() {
        return getServiceId();
    }
}
