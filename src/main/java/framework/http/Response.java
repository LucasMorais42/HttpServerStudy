package framework.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Response {
    private HttpStatus status; //200, 400, 404
    private byte[] responseBodyBytes; //bytes por padrão do navegador
    private ContentType contentType; //Content-Type: plain-text ou json

    public void send(OutputStream outputStream) throws IOException {

        byte[] actualBody = (responseBodyBytes == null) ? new byte[0] : responseBodyBytes;
        try{
            outputStream.write(("HTTP/1.1 " + status.getCode() + " " + status.getStatus() + "\r\n").getBytes());
            outputStream.write(("Content-Type: " + contentType.getMimeType() + "\r\n").getBytes());
            outputStream.write(("Content-Length: " + actualBody.length + "\r\n").getBytes());
            outputStream.write(("\r\n").getBytes());
            outputStream.write(actualBody);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public void setStatusCode(HttpStatus status) {
        this.status = status;
    }

    public void setResponseBodyBytes(byte[] responseBodyBytes) {
        this.responseBodyBytes = responseBodyBytes;
    }

    public void setResponseBodyBytes(String responseBodyBytes) {
        this.responseBodyBytes = responseBodyBytes.getBytes();
    }

    public void setContentType(ContentType contentType){
        this.contentType = contentType;
    }



}
