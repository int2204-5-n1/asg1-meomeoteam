package Data;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionData {
    public static Connection getConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con =DriverManager.getConnection("jdbc:sqlite:dictionary\\src\\Data\\dictionaries.db");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

