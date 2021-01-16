package biz.gelicon.capital.utils;

import biz.gelicon.capital.exceptions.BadPagingException;
import biz.gelicon.capital.exceptions.FetchQueryException;
import biz.gelicon.capital.exceptions.PostRecordException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
//  аннотация для обработки исключений в приложении Spring MVC
public class RestControllerExceptionHandler {

    /** Отсутствует сортировка при наличии пагинации */
    public static final int BAD_PAGING_NO_SORT = 123;
    /** Ошибка при выборке данных */
    public static final int FETCH_ERROR = 124;
    /** Ошибка при сохранении данных */
    public static final int POST_ERROR = 125;

    @ExceptionHandler(Exception.class)
    // Аннотация работает на уровне контроллера , и он активен только для этого конкретного контроллера
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {

        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        errorResponse.setTimeStamp(new Date().getTime()); // Установим датувремя
        errorResponse.setExceptionClassName(e.getClass().getName()); // установим имя класса
        if (e instanceof BadPagingException) {
            errorResponse.setErrorCode(BAD_PAGING_NO_SORT);
        }
        if (e instanceof FetchQueryException) {
            errorResponse.setErrorCode(FETCH_ERROR);
        }
        if (e instanceof PostRecordException) {
            errorResponse.setErrorCode(POST_ERROR);
            // Надо извлечь свойство bindingResult
            List<FieldError> fieldErrorList = ((PostRecordException) e).getBindingResult().getFieldErrors();
            Map<String,String> fieldErrors = new HashMap<>();
            for (int i = 0; i < fieldErrorList.size(); i++) {
                String field = fieldErrorList.get(i).getField();
                String message = fieldErrorList.get(i).getDefaultMessage();
                fieldErrors.put(field,message);
            }
            // и потом из него сделать Map fieldErrors в errorResponse
            errorResponse.setFieldErrors(fieldErrors);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");

        return new ResponseEntity<ErrorResponse>(errorResponse, headers, HttpStatus.OK);
    }

}
