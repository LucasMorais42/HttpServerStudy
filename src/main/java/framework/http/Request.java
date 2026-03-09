package framework.http;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {
    private String method; //GET ou POST
    private String path; // /usuarios /produtos

    //usa o bufferedReader para ler o InputStream do Socket
    public Request(BufferedReader reader) throws IOException{
        String firstLine = reader.readLine(); //firstLine da requisicao geralmente é GET /usuarios HTTP/1.1
        if(firstLine==null || firstLine.isEmpty()){
            return;
        }
        String[] parts = firstLine.split(" "); //Separar a firstLine em partes: [GET, /usuarios, HTTP/1.1]
        if(parts.length>=2){
            this.method = parts[0]; //Vai pegar o 'GET', 'POST'
            String fullPath = parts[1]; //o caminho, ou seja /usuarios
            if(fullPath.contains("?")){ //se tiver query params, exemplo /usuarios?nome=Lucas
                this.path = fullPath.split("\\?")[0];
                //retira usando o regex, que \\ -> converte para \, e \? converte para ?
            } else{
                this.path = fullPath;
            }
        }
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public boolean isValid(){
        return method != null && !method.isEmpty();
    }
}
