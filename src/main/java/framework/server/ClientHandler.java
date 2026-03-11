package framework.server;

import framework.http.ContentType;
import framework.http.HttpStatus;
import framework.http.Request;
import framework.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

//implementa runnable para trabalhar com Threads
public class ClientHandler implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    //final para não mudar a instância do objeto
    private final Socket socket;


    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void handleRequest(BufferedReader reader, OutputStream writer) throws IOException{
        Request request = new Request(reader);
        if(!request.isValid()) return;
       

        String path = request.getPath();
        if(path.equals("/")){
            path = "/index.html";
        }

        Response response = new Response();

        try(InputStream fileStream = getClass().getResourceAsStream(path)){
            if(fileStream != null){
                byte[] fileBytes = fileStream.readAllBytes();
                response.setStatusCode(HttpStatus.OK);
                response.setContentType(ContentType.fromPath(path));
                response.setResponseBodyBytes(fileBytes);
            }
            else{
                response.setStatusCode(HttpStatus.NOT_FOUND);
                response.setContentType(ContentType.HTML);
                response.setResponseBodyBytes("<h1>404 - File Not Found </h1>");
            }
        }

        response.send(writer);


    }

    public void sendInternalServerError(OutputStream writer) throws IOException {
        Response response = new Response();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setResponseBodyBytes("<h1>500 - Internal Server Error</h1>");
        response.setContentType(ContentType.HTML);
        response.send(writer);
    }

    @Override
    public void run() {

        try(socket;
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {

            handleRequest(reader, socket.getOutputStream());

        } catch (Exception e){

            logger.error("Client Error: {}", e.getMessage());
            try{
                sendInternalServerError(socket.getOutputStream());
            } catch(IOException ignored){}

        }
    }


}
