import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class CGILavAftaler {
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static String url2 = "jdbc:mariadb://192.168.239.20:3306/myuser";
    static String url = "jdbc:mariadb://[2001:878:200:4102:20c:f1ff:fe6c:756d]:3306/myuser";
    private static String skade;
    private static String patientid;
    private static String dato;
    private static String varighed;

    String addresse = "jdbc:mariadb://[ip6]:3306/schemanavn";
    private static Connection conn = null;
    private static Statement statement = null;
    private static PreparedStatement prep = null;
    static String inputfraCGI = null;
    static String[] data;
    public static void main(String[] args) {
        showError();
        showTail();
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            //mysql skal  ndres senere til MariaDB, localhost til en IPaddresse -
            String user, pass;
            user = "oskar";
            pass = "123456789";
            // url="jdbc:mysql://localhost:3306/phoenixpoint?serverTimezone=Europe/Amsterdam&amp";

            // Skal man fx. bruge 127.0.0.1 til en remote maskine?
//Connection connection =
// DriverManager.getConnection("jdbc:mariadb://localhost:3306/DB?user=root&password=myPassword");
            //T nk jer om - kan man opn  mariadb forbindelse til en anden maskine uden at  ndre denne her?
            conn = DriverManager.getConnection(url2, user, pass);
            System.out.println("im in");

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String[] data = {in.readLine()};
            inputfraCGI = data[0];
            System.out.println(inputfraCGI);
            String[] clientResponse;
            clientResponse = inputfraCGI.split("&");
            String[] patientidpost;
            patientidpost = clientResponse[0].split("=");
            patientid = patientidpost[1];
            String[] datopost;
            datopost = clientResponse[1].split("=");
            dato = datopost[1];
            clientResponse = inputfraCGI.split("&");
            String[] varighedpost;
            varighedpost = clientResponse[2].split("=");
            varighed = varighedpost[1];
            String[] skadepost;
            skadepost = clientResponse[3].split("=");
            skade = skadepost[1];
            String lokale = "716W";
            String hospital = "Odense Universitetshospital";


            try {
                String sqlFindUser = "INSERT INTO aftaler(patientid,dato,varighed,lokale,behandling,hospital) VALUES(?,?,?,?,?,?)";
                try {
                    prep = conn.prepareStatement(sqlFindUser);
                    prep.setInt(1,Integer.parseInt(patientid));
                    prep.setString(2,dato);
                    prep.setString(3,varighed);
                    prep.setString(4,lokale);
                    prep.setString(5,skade);
                    prep.setString(6,hospital);
                    prep.execute();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (IOException | SQLException | ClassNotFoundException ioe) {
            System.out.println("<P>IOException reading POST data: " + ioe + "</P>");
        }
    }
    static void showError() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \" -//W3C//DTD HTML 3.2//EN\" >");
        System.out.println("<HTML>");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>Fejl i oprettelse! application</TITLE>");
        System.out.println("<META http-equiv=\"content - type\" content=\"text / html; charset = UTF - 8 \">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no - cache\">");
        System.out.println("<meta http-equiv=\"refresh\" content=\"0; URL=http://130.226.195.37:39080\"/>");
        System.out.println("</HEAD>");
        System.out.println("<BODY>");

    }
    private static void showTail() {
        System.out.println("</BODY>\n</HTML>");
    }





}
