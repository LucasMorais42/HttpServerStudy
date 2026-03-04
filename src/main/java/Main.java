import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);

        while(true){

            Socket socket = serverSocket.accept();
            System.out.println("Conexao recebida: ");
            InputStream inputStream = socket.getInputStream();
            System.out.println("Input Stream: " + inputStream);
            byte[] buffer = new byte[1024];
            System.out.println("Buffer bytes: " + buffer.toString());
            int bytesRead = inputStream.read(buffer);
            System.out.println("Bytes Reads: " + bytesRead);


            if(bytesRead>0){
                String request = new String(buffer, 0, bytesRead);
                System.out.println("Requisicao recebida: " + request);

            }

            String httpResponse = """
                    HTTP/1.1 200 OK
                    Content-Type: text/plain
                    
                    Hello World!
                    """;

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(httpResponse.getBytes(StandardCharsets.UTF_8));

            socket.close();
        }
    }
}
