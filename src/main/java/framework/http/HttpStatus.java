package framework.http;

public enum HttpStatus {

    OK(200, "OK"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No content"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");


    private final int code;
    private final String status;

    HttpStatus(int code, String status){
        this.code = code;
        this.status = status;
    }

    public int getCode(){return this.code;}
    public String getStatus(){return this.status;}

}
