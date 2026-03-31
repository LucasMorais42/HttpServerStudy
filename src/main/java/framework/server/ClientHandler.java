package framework.server;

import framework.http.*;
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


    private final Router router;

    public ClientHandler(Socket socket, Router router) {
        this.socket = socket;
        this.router = router;
    }

    @Override
    public void run() {

        try(socket;
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream writer = socket.getOutputStream();
        )
        {
            Request request = new Request(reader);
            if(!request.isValid()) return;

            Response response = new Response();
            boolean handled = router.dispatch(request, response);
            if(!handled){
                response.setStatusCode(HttpStatus.NOT_FOUND);
                response.setResponseBodyBytes("{\"error\": \"Endpoint not found\"}");
                response.setContentType(ContentType.JSON);

            }
            response.send(writer);

        } catch (Exception e){
            logger.error("Client Error: {}", e.getMessage());

        }
    }


}
