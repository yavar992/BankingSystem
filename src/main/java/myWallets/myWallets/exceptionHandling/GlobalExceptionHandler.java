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

    @ExceptionHandler(BankBranchesNotFoundException.class)
    public ResponseEntity<ErrorMessage> branchesNotFoundException(BankBranchesNotFoundException e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(BankNotFoundException.class)
    public ResponseEntity<ErrorMessage> bankNotFound(BankNotFoundException e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(UnverifiedCustomerException.class)
    public ResponseEntity<ErrorMessage> unverifiedCustomer(UnverifiedCustomerException e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(CustomerAccountException.class)
    public ResponseEntity<ErrorMessage> noCustomerDetailsFound(CustomerAccountException e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorMessage> transactionException(TransactionException e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorMessage> insufficientAmountException(InsufficientBalanceException e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(IncorrectAccountNumber.class)
    public ResponseEntity<ErrorMessage> incorrectAccountNumber(IncorrectAccountNumber e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(ATMAlreadyExist.class)
    public ResponseEntity<ErrorMessage> atmAlradyExist(ATMAlreadyExist e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidAtmDetails.class)
    public ResponseEntity<ErrorMessage> invalidAtmOtp(InvalidAtmDetails e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


    @ExceptionHandler(ATMNotFound.class)
    public ResponseEntity<ErrorMessage> atmNotFound(ATMNotFound e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(CardExpirationException.class)
    public ResponseEntity<ErrorMessage> cardExpirationException(CardExpirationException e , WebRequest webRequest){
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String message = e.getMessage();
        String path = webRequest.getDescription(false);
        ErrorMessage errorMessage = new ErrorMessage(statusCode , message , path);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
