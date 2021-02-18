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
    @Column(name = "materiallevel_id")
    private Integer materiallevelId;

    @Column(name = "materiallevel_idmaster", nullable = false, columnDefinition = "Вышестоящий уровень")
    private Integer materiallevelIdMaster;

    @Size(max = 255, message = "Наименование должно содержать не более {1} символов")
    @Column(name = "materiallevel_name", nullable = false, columnDefinition = "Наименование")
    private String materiallevelName;

    @NotEmpty(message = "Код не может быть пустым")
    @Size(max = 100, message = "Код должно содержать не более {1} символов")
    @Column(name = "materiallevel_code", nullable = true, columnDefinition = "Код")
    private String materiallevelCode;

    @Column(name = "materiallevel_datebeg", nullable = false, columnDefinition = "Дата начала действия")
    private Date materiallevelDatebeg;

    @Column(name = "materiallevel_dateend", nullable = false, columnDefinition = "Дата окончания действия")
    private Date materiallevelDateend;

    public Materiallevel(
            Integer materiallevelId,
            Integer materiallevelIdMaster,
            String materiallevelName,
            String materiallevelCode,
            Date materiallevelDatebeg,
            Date materiallevelDateend
    ) {
        this.materiallevelId = materiallevelId;
        this.materiallevelIdMaster = materiallevelIdMaster;
        this.materiallevelName = materiallevelName;
        this.materiallevelCode = materiallevelCode;
        this.materiallevelDatebeg = materiallevelDatebeg;
        this.materiallevelDateend = materiallevelDateend;
    }

    public Materiallevel() {
    }

    public Integer getMateriallevelId() {
        return materiallevelId;
    }

    public void setMateriallevelId(Integer materiallevelId) {
        this.materiallevelId = materiallevelId;
    }

    public Integer getId() {
        return materiallevelId;
    }

    public Integer getMateriallevelIdMaster() {
        return materiallevelIdMaster;
    }

    public String getMateriallevelName() {
        return materiallevelName;
    }

    public String getMateriallevelCode() {
        return materiallevelCode;
    }

    public Date getMateriallevelDatebeg() {
        return materiallevelDatebeg;
    }

    public Date getMateriallevelDateend() {
        return materiallevelDateend;
    }

    public void setId(Integer id) {
        this.materiallevelId = id;
    }

    public void setMateriallevelIdMaster(Integer materiallevelIdMaster) {
        this.materiallevelIdMaster = materiallevelIdMaster;
    }

    public void setMateriallevelName(String materiallevelName) {
        this.materiallevelName = materiallevelName;
    }

    public void setMateriallevelCode(String materiallevelCode) {
        this.materiallevelCode = materiallevelCode;
    }

    public void setMateriallevelDatebeg(Date materiallevelDatebeg) {
        this.materiallevelDatebeg = materiallevelDatebeg;
    }

    public void setMateriallevelDateend(Date materiallevelDateend) {
        this.materiallevelDateend = materiallevelDateend;
    }

    @Override
    public String toString() {
        return "Materiallevel{" +
                "materiallevelId=" + materiallevelId +
                ", materiallevelIdMaster=" + materiallevelIdMaster +
                ", materiallevelName='" + materiallevelName + '\'' +
                ", materiallevelCode='" + materiallevelCode + '\'' +
                ", materiallevelDatebeg=" + materiallevelDatebeg +
                ", materiallevelDateend=" + materiallevelDateend +
                '}';
    }
}
