import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;


public class MySQLConnector {
    static String url = "jdbc:mariadb://192.168.239.20:3306/myuser?";
    private static Connection conn = null;

    //private static Statement statement = null;
    //private PreparedStatement prep = null;

    public static void main(String[] args) {
        Connection conn = getConn();
        try{
            int valg = valgAfKode();
            System.out.println("valg af funktion:"+valg);
            if(valg==1){
            try {
                int cpr = Integer.parseInt(askForcpr());
                System.out.println("Brugeren tastede: " + cpr);
                String kode = MySQLConnector.askForkode();
                System.out.println("Brugeren indtastede: " + kode);
                int patientid = Integer.parseInt(askForPatientid());
                System.out.println("Brugeren indtastede: " + patientid);

                // Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?serverTimezone=Europe/Amsterdam&amp", "root", "Johari");
                String query = " insert into login (cpr, kode, patientid)"
                        + " values (?, ?, ?)";
                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(1, cpr);
                preparedStmt.setString(2, kode);
                preparedStmt.setInt(3, patientid);
                // execute the preparedstatement
                preparedStmt.execute();
                conn.close();
            } catch (Exception e) {
                System.err.println("Got an exception!" + e);
                // System.err.println(e.getMessage());
            }}

            if(valg ==2){
            try {
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM login");
                ResultSet rs = preparedStatement.executeQuery();
                ResultSetMetaData rsMatadata = rs.getMetaData();
                int column = rsMatadata.getColumnCount();
                while (rs.next()) {
                    int cpr = rs.getInt("cpr");
                    String kode = rs.getString("kode");
                    int id = rs.getInt("patientid");
                    System.out.format("%s,%s,%s\n", cpr, kode, id);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
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

    public static int valgAfKode() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("tast 1 for indsæt data. tast 2 for hente data:");
        int valg = keyboard.nextInt();
        return valg;
    }

    public static String askForcpr() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Indtast CPR");
        String cpr = keyboard.nextLine();
        return cpr;
    }

    public static String askForkode() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Indtast kode ");
        String kode = keyboard.nextLine();
        return kode;
    }

    public static String askForPatientid() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Indtast patientid ");
        String patientid = keyboard.nextLine();
        return patientid;
    }

}