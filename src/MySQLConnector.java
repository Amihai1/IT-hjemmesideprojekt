import java.sql.*;
import java.time.LocalDateTime;


public class MySQLConnector {
    static String url = "jdbc:mariadb://192.168.239.20:3306/myuser?";
    private static Connection conn = null;
    //private static Statement statement = null;
    //private PreparedStatement prep = null;

    public static void main(String[] args) {
        LoginData loginData = new LoginData();
        loginData.loaddata();
    }
    public static Connection getConn(){
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            //mysql skal  ndres senere til MariaDB, localhost til en IPaddresse -
            String user, pass;
            user = "oskar";
            pass = "123456789";
            conn = DriverManager.getConnection(url, user, pass);
            if (conn != null) {

                System.out.println("Im in");
            } else {
                System.out.println("connection not made");
            }



            //db.getHomeData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
}