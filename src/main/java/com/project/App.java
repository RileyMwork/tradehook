package com.project;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.project.dao.DatabaseConnector;
import com.project.dao.TvPostDAO;
import com.project.dao.UserDAO;
import com.project.entity.APost;
import com.project.entity.TvPost;
import com.project.entity.User;

public class App {
    public static void main( String[] args ) {

        // User user = new User(1,"test@gmail.com","Test123","kasjfhasdlfjldjl","afjdlfdskjajsdlfjdkj","lksjdfjasfkfjkjdsfjk");
        // System.out.println(u.toString());
        // TvPost tp = new TvPost(1, 1, "TvPost message", Timestamp.valueOf(LocalDateTime.now()));
        // System.out.println(tp.toString());
        // APost a = new APost(1, 1, 1, "APost message", Timestamp.valueOf(LocalDateTime.now()), "AAPL", "market", 
        //                 "buy", true, 300.00, 1.00, "GTC", "adj398u9uihsdd", "aewur93u9u2903hsdds");
        // System.out.println(a.toString());
        // UserDAO u = new UserDAO();
        // u.deleteUserById(1);
        // System.out.println(u.insertNewUser(user));
        // System.out.println(u.updateUserAlpacaApiKeys(1, "newalpadsfsadfca", "SEEEHHIEsdfaadafdasaHHTEHI"));
        TvPostDAO t = new TvPostDAO();
        // System.out.println(t.insertNewTvPost(tp));
        t.deleteAllTvPostsByUserId(1);
    }
}
