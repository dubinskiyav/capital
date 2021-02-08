package biz.gelicon.capital.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
@Table(name = "service")
public class Service {

    @Id
    @Column(name = "service_id")
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

}
