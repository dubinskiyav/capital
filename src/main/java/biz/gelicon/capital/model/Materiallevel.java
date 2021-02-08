package biz.gelicon.capital.model;

import biz.gelicon.capital.utils.ConvertUnils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

// Обязательно public иначе в шаблоне не увидит!!!! На поля обязательно геттеры и сеттеры!!!!!
// Добавить в репозиторий 2 класса
// Добавить валидатор
@Table(name = "materiallevel")
public class Materiallevel {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "master_id", nullable = false, columnDefinition = "Вышестоящий уровень")
    private Integer masterId;

    @Size(max = 255, message = "Наименование должно содержать не более {1} символов")
    @Column(name = "name", nullable = false, columnDefinition = "Наименование")
    private String name;

    @NotEmpty(message = "Код не может быть пустым")
    @Size(max = 100, message = "Код должно содержать не более {1} символов")
    @Column(name = "code", nullable = true, columnDefinition = "Код")
    private String code;

    @Column(name = "date_beg", nullable = false, columnDefinition = "Дата начала действия")
    private Date dateBeg;

    @Column(name = "date_end", nullable = false, columnDefinition = "Дата окончания действия")
    private Date dateEnd;


    public Materiallevel(
            Integer id,
            Integer masterId,
            String name,
            String code,
            Date dateBeg,
            Date dateEnd
    ) {
        this.id = id;
        this.masterId = masterId;
        this.name = name;
        this.code = code;
        this.dateBeg = dateBeg;
        this.dateEnd = dateEnd;
    }

    public Materiallevel() {
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateBeg() {
        return dateBeg;
    }

    public void setDateBeg(Date dateBeg) {
        // Начало и окончание - даты, а не время
        this.dateBeg = ConvertUnils.datetimeToDate(dateBeg);
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        // Начало и окончание - даты, а не время
        this.dateEnd = ConvertUnils.datetimeToDate(dateEnd);
    }

    @Override
    public String toString() {
        return "Materiallevel{"
                + "id=" + id + ", "
                + "masterId=" + masterId + ", "
                + "name=" + name + ", "
                + "code=" + code + ", "
                + "dateBeg=" + dateBeg + ", "
                + "dateEnd=" + dateEnd + ", "
                + "}";
    }

}
