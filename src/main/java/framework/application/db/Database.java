package framework.application.db;

import framework.application.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {
    //synchronized para trabalhar com threads
    private static final List<User> users = Collections.synchronizedList(new ArrayList<>());
    private static final AtomicInteger idGenerator = new AtomicInteger(0);
    //também para trabalhar com concorrência

    static {
        users.add(new User(idGenerator.getAndIncrement(), "Lucas", "lucasmorais@gmail.com"));
        users.add(new User(idGenerator.getAndIncrement(), "Pedro", "Pedro@gmail.com"));
        users.add(new User(idGenerator.getAndIncrement(), "Rafael", "rafaelcosta@gmail.com"));
    }

    public static List<User> findAll(){
        return new ArrayList<>(users);
    }

    public static User findById(int id){
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static User save(User user){
        if(user.getId() == 0){
            user.setId(idGenerator.getAndIncrement());
            users.add(user);
        }
        else{
            delete(user.getId());
            users.add(user);
        }
        return user;
    }

    public static boolean delete(int id){
        return users.removeIf(user -> user.getId() == id);
    }
}
