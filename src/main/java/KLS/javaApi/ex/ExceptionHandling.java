package KLS.javaApi.ex;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(MyException.class)
    public ResponseEntity<Map<String, String>> handleMyException(MyException ex) {
        var res = Map.of("message", ex.getMessage());

        return ResponseEntity.internalServerError()
                .body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errrorsssss = new HashMap<>();
        List<ObjectError> allErrors = ex.getAllErrors();
        for(ObjectError error: allErrors){
            FieldError err = (FieldError) error;
            errrorsssss.put(err.getField(), err.getDefaultMessage());
        }
        var res = Map.of("message", "Mot so field chua duoc input thong tin",
                "errors", errrorsssss);

        return ResponseEntity.badRequest()
                .body(res);
    }
}
