import framework.server.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args)  {
        HttpServer server = new HttpServer();
        server.start(8080);
    }
}
