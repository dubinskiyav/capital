package biz.gelicon.capital.exceptions;

import org.springframework.validation.BindingResult;

public class PostRecordException extends RuntimeException {

    public PostRecordException(
            BindingResult bindingResult) {
        super(bindingResult.toString());
    }
}
