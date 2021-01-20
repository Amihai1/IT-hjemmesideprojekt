/** @author Amihai */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class CGISletAftale {
    static String url2 = "jdbc:mariadb://192.168.239.20:3306/myuser";
    private static Connection conn = null;
    private static PreparedStatement statement = null;


    static String inputfraCGI = null;
    static String[] data;
    private static String aftaleid;
    private static String Cpr = null;
    private static String cprTilDb;

    public static void main(String[] args) {
        showError();
        showTail();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String user, pass;
            user = "oskar";
            pass = "123456789";
            conn = DriverManager.getConnection(url2, user, pass);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String[] data = {in.readLine()};
            inputfraCGI = data[0];
            String[] clientResponse;
            clientResponse = inputfraCGI.split("&");
            String[] cprpost;
            cprpost = clientResponse[0].split("=");
            cprTilDb = cprpost[1];
            String[] kodepost;
            kodepost = clientResponse[1].split("=");
            aftaleid = kodepost[1];
            /*if (cookie != null) System.out.println("Cookie: " + cookie + "<BR>");
            if (session != null) System.out.println("Session: " + session + "<BR>");*/
            String sqlSletaftaler = "delete from aftaler where aftaleid = ?  and cpr=?";
            PreparedStatement statement = conn.prepareStatement(sqlSletaftaler);
            statement.setString(1,cprTilDb);
            statement.setString(2,aftaleid);
            statement.executeUpdate();


        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
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
        System.out.println("<meta http-equiv=\"refresh\" content=\"0; URL=http://130.226.195.37:39080/cgi-bin/MinCGI\"/>");
        System.out.println("</HEAD>");
        System.out.println("<BODY>");

    }
    private static void showTail() {
        System.out.println("</BODY>\n</HTML>");
    }

}
