package biz.gelicon.capital.exceptions;

public class BadPagingException extends RuntimeException {

    public BadPagingException(String errorMessage) {
        super(errorMessage);
    }

}
