package biz.gelicon.capital.exceptions;

/**
 * Исключение при ошибке удаления записи
 */
public class DeleteRecordException extends RuntimeException {

    public DeleteRecordException(String errorMessage, Throwable err) {

        super(errorMessage, err);
    }
}
