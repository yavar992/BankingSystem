package myWallets.myWallets.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFound(UserNotFoundException e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorMessage> loginException(LoginException e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(UserAlreadyLoggedIn.class)
    public ResponseEntity<ErrorMessage> userIsAlreadyLoggedIn(UserAlreadyLoggedIn e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidOTPException.class)
    public ResponseEntity<ErrorMessage> invlalidOTP(InvalidOTPException e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
