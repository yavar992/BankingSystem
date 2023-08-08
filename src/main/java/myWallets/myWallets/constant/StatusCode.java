package myWallets.myWallets.constant;

public enum StatusCode {
    // Informational 1xx
    CONTINUE(100, "Continue"),
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),

    // Success 2xx
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),

    // Redirection 3xx
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    SEE_OTHER(303, "See Other"),
    NOT_MODIFIED(304, "Not Modified"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    PERMANENT_REDIRECT(308, "Permanent Redirect"),

    // Client Errors 4xx
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CONFLICT(409, "Conflict"),

    // Server Errors 5xx
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    USER_IS_ALREADY_LOGGED_IN(404, "User Is Logged In"),

    UNVERIFIED_ACCOUNT(404,"Your account has not verified yet plz verifiy your account to access these endpoints"),

    USER_IS_NOT_LOGGED_IN(501,"You are not logged in"),
    BRANCH_NOT_EXIST(404 ,"Branch not found for  the id "),

    WRONG_CREDENTIAL(401, "Wrong credentials Either password or email is worng"),
    CUSTOMER_NOT_FOUND(404,"Customer not found");


    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

