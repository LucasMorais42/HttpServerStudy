package framework.server;

import framework.http.HttpHandler;
import framework.http.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    public static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private final Router router = new Router();

    public void addRoute(String method, String path, HttpHandler httpHandler){
        router.register(method, path, httpHandler);
    }

    public void get (String path, HttpHandler httpHandler){
        addRoute("GET", path, httpHandler);
    }

    public void post (String path, HttpHandler httpHandler){
        addRoute("POST", path, httpHandler);
    }

    public void delete (String path, HttpHandler httpHandler){
        addRoute("DELETE", path, httpHandler);
    }

    public void start(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("Server running on port: {}", port);
            ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor();

            while(!serverSocket.isClosed()){
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, router);
                pool.execute(clientHandler);
                logger.info("Thread Pool is terminated: {}", pool.isTerminated());
            }
        } catch (IOException e){
            logger.error("Server error {}", e.getMessage());
        }
    }
}
