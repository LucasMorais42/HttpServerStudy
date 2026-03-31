import framework.application.controller.UserController;
import framework.application.model.User;
import framework.server.HttpServer;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args)  {
        int port = Integer.parseInt(JOptionPane.showInputDialog("Digite a porta desejada."));
        HttpServer server = new HttpServer();
        UserController userController = new UserController();

        server.get("/api/users", userController::listUsers);
        //só funciona assim pq o HttpHandler é uma interface funcional (1 unico metodo)
        //e a assinatura de ambos (httphnandler e listUsers) é igual
        /*
            server.get("/api/users", new HttpHandler() {
            @Override
            public void handle(Request request, Response response) {
                userController.listUsers(request, response);
            }
            });
            mesma coisa do codigo acima
        */
        server.start(port);

    }
}
