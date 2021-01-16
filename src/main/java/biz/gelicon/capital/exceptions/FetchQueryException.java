package biz.gelicon.capital.exceptions;

public class FetchQueryException extends Exception {
    public FetchQueryException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }

}
