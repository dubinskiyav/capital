package biz.gelicon.capital.exceptions;

import org.springframework.validation.BindingResult;

public class PostRecordException extends RuntimeException {

    private BindingResult bindingResult;

    public PostRecordException(
            BindingResult bindingResult
    ) {
        super(bindingResult.toString());
    }

    public PostRecordException(
            BindingResult bindingResult,
            Throwable err
    ) {
        super(bindingResult.toString(), err);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}
