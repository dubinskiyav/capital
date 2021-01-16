package biz.gelicon.capital.exceptions;

import org.springframework.validation.BindingResult;

public class PostRecordException extends RuntimeException {

    private BindingResult bindingResult;
    private static final String errText = "Ошибка модификации данных в базе";

    public PostRecordException(
            BindingResult bindingResult
    ) {
        super(errText);
    }

    public PostRecordException(
            BindingResult bindingResult,
            Throwable err
    ) {
        super(errText, err);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}
