package framework.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    public static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public void start(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            logger.info("Server running on port: {}", port);
            ExecutorService pool = Executors.newVirtualThreadPerTaskExecutor();

            while(!serverSocket.isClosed()){
                Socket clientSocket = serverSocket.accept();
                pool.submit(new ClientHandler(clientSocket));
                logger.info("Thread Pool is terminated: {}", pool.isTerminated());
            }
        } catch (IOException e){
            logger.error("Server error {}", e.getMessage());
        }
    }
}
