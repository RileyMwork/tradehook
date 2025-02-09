package com.project;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.project.entity.APost;
import com.project.entity.TvPost;
import com.project.entity.User;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        User u = new User(1,"test@gmail.com","Test123");
        System.out.println(u.toString());
        TvPost t = new TvPost(1, 1, "TvPost message", Timestamp.valueOf(LocalDateTime.now()));
        System.out.println(t.toString());
        APost a = new APost(1, 1, 1, "APost message", Timestamp.valueOf(LocalDateTime.now()), "AAPL", "market", 
                        "buy", true, 300.00, 1.00, "GTC", "adj398u9uihsdd", "aewur93u9u2903hsdds");
        System.out.println(a.toString());
    }
}
