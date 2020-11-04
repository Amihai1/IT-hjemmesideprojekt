import java.sql.*;
import java.time.LocalDateTime;


public class MySQLConnector {
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static String url = "jdbc:mariadb://192.168.239.20:3306/myuser?";
    private static Connection conn = null;
    private static Statement statement = null;
    private PreparedStatement prep = null;

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

            //find out which columns are in current table:
            /*ResultSetMetaData rsMetaData = rs.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();

            // get the column names; column indexes start from 1
            for (int i = 1; i < numberOfColumns + 1; i++) {
                String columnName = rsMetaData.getColumnName(i);
                // Get the name of the column's table name
                String tableName = rsMetaData.getTableName(i);
                System.out.println("column name=" + columnName);
            }*/


            //db.getHomeData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
}