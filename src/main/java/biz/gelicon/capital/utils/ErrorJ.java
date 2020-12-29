package biz.gelicon.capital.utils;

// Вспомогательный класс для пуляния ошибки в форму
public class ErrorJ {

    Integer code;
    String message;
    String exceptionText;

    public ErrorJ() {
        code = 200; // Нет ошибки
    }

    public ErrorJ(
            Integer code,
            String message,
            String exceptionText
    ) {
        this.code = code;
        this.message = message;
        this.exceptionText = exceptionText;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionText() {
        return exceptionText;
    }

    public void setExceptionText(String exceptionText) {
        this.exceptionText = exceptionText;
    }
}
