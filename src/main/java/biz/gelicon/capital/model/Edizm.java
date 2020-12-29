package biz.gelicon.capital.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// Обязательно public иначе в шаблоне не увидит!!!!
// и поля тоже public (если нет геттеров) !!!!
public class Edizm {

    private Integer id;

    @Size(max=50, message = "Имя должно содержать не более {1} символов")
    private String name;

    @NotEmpty(message="Обозначение не может быть пустым")
    @Size(max=15, message = "Обозначение должно содержать не более {1} символов")
    private String notation;

    private Integer blockflag;

    @NotEmpty(message="Код не может быть пустым")
    @Size(max=20, message = "Код должен содержать не более {1} символов")
    private String code;

    public Edizm(
            Integer id,
            String name,
            String notation,
            Integer blockflag,
            String code
    ) {
        this.id = id;
        this.name = name;
        this.notation = notation;
        this.blockflag = blockflag;
        this.code = code;
    }

    public Edizm() {
        // Значения по умолчанию
        this.blockflag = 0;
    }

    // Обязательнго геттеры и сеттеры,
    // иначе не будет работать передача в форму и из формы

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {this.id = id;}

    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name;}

    public String getNotation() {
        return notation;
    }
    public void setNotation(String notation) {this.notation = notation;}

    public Integer getBlockflag() {
        return blockflag;
    }
    public void setBlockflag(Integer blockflag) {this.blockflag = blockflag;}

    public String getCode() {
        return code;
    }
    public void setCode(String code) {this.code = code;}

    // Фиктивное поле BlockflagB типа boolean для CheckBox в форме
    // Геттеры на поля типа boolean начитаются с is
    public Boolean isBlockflagB() {
        return blockflag != null && blockflag != 0;
    }
    public void setBlockflagB(Boolean b) {blockflag = b ? 1 : 0;}

    @Override
    public String toString() {
        return "edizm{"
                + "id=" + id + ", "
                + "name=" + name + ", "
                + "notation=" + notation + ", "
                + "blockflag=" + blockflag + ", "
                + "code=" + code +"}";
    }

}
