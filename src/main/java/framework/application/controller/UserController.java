package framework.application.controller;

import framework.application.db.Database;
import framework.application.model.User;
import framework.http.ContentType;
import framework.http.HttpStatus;
import framework.http.Request;
import framework.http.Response;

import java.util.List;

public class UserController {

    public void listUsers(Request request, Response response){
        List<User> users = Database.findAll();

        StringBuilder json = new StringBuilder("[");
        for(int i = 0; i<users.size(); i++){
            User u = users.get(i);
            json.append(String.format("{\"id\":%d, \"name\":\"%s\", \"email\":\"%s\"}", u.getId(), u.getNome(), u.getEmail()));
            if(i<users.size()-1) json.append(",");

        }
        json.append("]");
        response.setResponseBodyBytes(json.toString());
        response.setContentType(ContentType.JSON);
        response.setStatusCode(HttpStatus.OK);

    }
}
