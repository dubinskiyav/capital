package biz.gelicon.capital.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "SORT Not Found")
//@ResponseStatus(HttpStatus.OK)
public class BadPagingException extends RuntimeException {

    public BadPagingException(String errorMessage) {
        super(errorMessage);
    }

}
