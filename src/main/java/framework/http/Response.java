package framework.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Response {
    private int statusCode; //200, 400, 404
    private byte[] responseBodyBytes; //bytes por padrão do navegador
    private String contentType; //Content-Type: plain-text ou json

    public void send(OutputStream outputStream) throws IOException {

        byte[] actualBody = (responseBodyBytes == null) ? new byte[0] : responseBodyBytes;
        try{
            outputStream.write(("HTTP/1.1 " + statusCode + " " + getReasonCode(statusCode) + "\r\n").getBytes());
            outputStream.write(("Content-Type: " + contentType + "\r\n").getBytes());
            outputStream.write(("Content-Length: " + actualBody.length + "\r\n").getBytes());
            outputStream.write(("\r\n").getBytes());
            outputStream.write(actualBody);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setResponseBodyBytes(byte[] responseBodyBytes) {
        this.responseBodyBytes = responseBodyBytes;
    }

    public void setResponseBodyBytes(String responseBodyBytes) {
        this.responseBodyBytes = responseBodyBytes.getBytes();
    }

    public void setContentType(String contentType){
        this.contentType = contentType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    private String getReasonCode(int statusCode){
        return switch (statusCode){
            case 200 -> "OK";
            case 201 -> "Created";
            case 204 -> "No Content";
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            default ->  "Internal Server Error";
        };
    }
}
