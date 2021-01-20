/** @author Amihai */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.StringTokenizer;

public class CGISendBesked {
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static String url2 = "jdbc:mariadb://192.168.239.20:3306/myuser";
    static String url = "jdbc:mariadb://[2001:878:200:4102:20c:f1ff:fe6c:756d]:3306/myuser";
    private static String Besked;
    private static String cpr;
    private static String fuldenavn;
    private static String emne;
    private static String session = null;
    private static String cookie = null;
    String addresse = "jdbc:mariadb://[ip6]:3306/schemanavn";
    private static Connection conn = null;
    private static Statement statement = null;
    private static PreparedStatement prep = null;
    static String inputfraCGI = null;
    static String[] data;

    private static void handleCookies(StringTokenizer t) {
        String field;
        while (t.hasMoreTokens()) {
            field = t.nextToken();
            if (field != null) {
                field.trim();
                StringTokenizer tt = new StringTokenizer(field, "=\n\r");
                String s = tt.nextToken();
                if (s.equals("__session")) {
                    s = tt.nextToken();
                    if (s != null) session = s;
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length > 1 && args[1] != null && args[1].length() > 0) {
            cookie = args[1];
            handleCookies(new StringTokenizer(cookie, ";\n\r"));
        }

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
            String[] navnpost;
            navnpost = clientResponse[0].split("=");
            fuldenavn = navnpost[1];
            String navn = java.net.URLDecoder.decode(fuldenavn, StandardCharsets.UTF_8);
            String[] emnepost;
            emnepost = clientResponse[1].split("=");
            emne = emnepost[1];
            String emneresult = java.net.URLDecoder.decode(emne, StandardCharsets.UTF_8);

            String[] beskedpost;
            beskedpost = clientResponse[2].split("=");
            Besked = beskedpost[1];
            String result = java.net.URLDecoder.decode(Besked, StandardCharsets.UTF_8);
            //String beskedvirker = Besked.replaceAll("\\+", " ").replaceAll("%2C", ",").replaceAll("%C3%B8", "ø");
            cpr = session;

            try {
                String sqlFindUser = "INSERT INTO beskeder(patientcpr, sygepersonalenavn, emne, beskedtext) VALUES(?,?,?,?)";
                try {
                    prep = conn.prepareStatement(sqlFindUser);
                    prep.setInt(1, Integer.parseInt(cpr));
                    prep.setString(2, navn);
                    prep.setString(3, emneresult);
                    prep.setString(4, result);
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
        System.out.println("<meta http-equiv=\"refresh\" content=\"0; URL=http://130.226.195.37:39080/SendBesked.html\"/>");
        System.out.println("</HEAD>");
        System.out.println("<BODY>");
    }

    private static void showTail() {
        System.out.println("</BODY>\n</HTML>");
    }


}
