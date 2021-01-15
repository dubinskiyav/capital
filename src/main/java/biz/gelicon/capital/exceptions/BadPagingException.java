package biz.gelicon.capital.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadPagingException extends RuntimeException {

    public BadPagingException(String errorMessage) {
        super(errorMessage);
    }

}
