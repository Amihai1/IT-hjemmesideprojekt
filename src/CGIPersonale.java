/** @author Amihai */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class CGIPersonale {
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static String url2 = "jdbc:mariadb://192.168.239.20:3306/myuser";
    static String url = "jdbc:mariadb://[2001:878:200:4102:20c:f1ff:fe6c:756d]:3306/myuser";
    private static String idTilDb;
    String addresse = "jdbc:mariadb://[ip6]:3306/schemanavn";
    private static Connection conn = null;
    private static Statement statement = null;
    private PreparedStatement prep = null;
    static String inputfraCGI = null;
    static String[] data;
    private static String kodeTilDb;
    private static String personaleid = null;
    private static String Kode = null;
    private static String patientid = null;

// here we use DBComm as a classname, if you use this method, make sure to change it to whatever class you use the
    // logger from


    public static void main(String[] args) {

        showHead();
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
            String[] cprpost;
            cprpost = clientResponse[0].split("=");
            idTilDb = cprpost[1];
            String[] kodepost;
            kodepost = clientResponse[1].split("=");
            kodeTilDb = kodepost[1];
            if (findUser(idTilDb, kodeTilDb) != null) {
                showBody();
            }
            else{
                showError();
            }


        } catch (IOException | SQLException | ClassNotFoundException ioe) {
            System.out.println("<P>IOException reading POST data: " + ioe + "</P>");
        }
        showTail();

    }


    private static void showHead() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">\n" +
                "<HTML>\n" +
                "<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\n" +
                "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Lato\">\n" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/standard.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/Login.html.css\">\n" +
                "<HEAD>\n" +
                "    <TITLE>Personale side</TITLE>\n" +
                "    <META http-equiv=\"content-type\" content=\"text/html; charset=UTC-8\">\n" +
                "    <META http-equiv=\"Pragma\" content=\"no-cache\">\n" +
                "    <META http-equiv=\"expires\" content=\"0\">\n" +
                "    <style>\n" +
                "        table {\n" +
                "            font-family: arial, sans-serif;\n" +
                "            border-collapse: collapse;\n" +
                "            width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        td, th {\n" +
                "            border: 1px solid #dddddd;\n" +
                "            text-align: left;\n" +
                "            padding: 8px;\n" +
                "        }\n" +
                "\n" +
                "        tr:nth-child(even) {\n" +
                "            background-color: #dddddd;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            margin-top: 80px;\n" +
                "        }\n" +
                "\n" +
                "        input {\n" +
                "            font-size: 26px;\n" +
                "\n" +
                "        }\n" +
                "        p{\n" +
                "            font-size: 24px;\n" +
                "        }\n" +
                "    </style>");


        System.out.println("</HEAD>");
        System.out.println("<BODY>");

    }

    private static void showBody() {
        System.out.println("<div class=\"w3-top topnav\">\n" +
                "    <div class=\"w3-row w3-large\">\n" +
                "        <div class=\"w3-col s3\">\n" +
                "            <a  href=\"Brugerside.html\" class=\"w3-button w3-block\"><i class=\"fa fa-fw fa-user-o\"></i>&nbsp Min Side</a>\n" +
                "        </div>\n" +
                "        <div class=\"w3-col s3 w3-right\">\n" +
                "            <a href=\"/index.html\" class=\"w3-button w3-block\"><i class=\"fa fa-sign-out\" style=\"color:#ff0000\">\n" +
                "            </i>&nbsp\n" +
                "                Log Ud</a>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "</div>\n" +
                "<div class=\"container1\">\n" +
                "    <p><b>Find Patient</b></p>\n" +
                "    <form action=\"/cgi-bin/CGIFindPatient\" method=\"post\">\n" +
                "        <label><p> CPR </p></label>\n" +
                "        <input type=\"number\" name=\"cpr\" id=\"cpr\" value=\"0000000000\">\n" +
                "        <label><p>Fornavn</p></label>\n" +
                "        <input type=\"text\" name=\"fornavn\" id=\"fornavn\" value=\"Indtast fornavn\" >\n" +
                "        <label><p>Efternavn</p></label>\n" +
                "        <input type=\"text\" name=\"efternavn\" id=\"efternavn\" value=\"Indtast efternavn\">\n" +
                "        <input type=\"submit\">\n" +
                "    </form>\n" +
                "</div>");


    }

    private static void showTail() {
        System.out.println("</BODY>\n</HTML>");
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
        System.out.println("<meta http-equiv=\"refresh\" content=\"0; URL=http://130.226.195.37:39080/Personalelogin.html\"/>");
        System.out.println("<script>");
        System.out.println("function myFunction() {\n");
        System.out.println("alert('Forkert brugernavn/password');");
        System.out.println("})");
        System.out.println("</script>");
        System.out.println("</HEAD>");
        System.out.println("<BODY  onload=\"alert('Forkert CPR/Kode')\">");

    }

    private static String findUser(String idTilDb, String kodeTilDb) throws SQLException, ClassNotFoundException, IOException {
        String output = "";
        int Patientid = 0;
        String sqlFindUser = "select * from personalelogin where personaleid ='" + idTilDb + "' and kodeord='" + kodeTilDb + "'";
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlFindUser);
            rs.next();
            personaleid = rs.getString(("personaleid"));
            Kode = rs.getString("kodeord");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return personaleid;

    }
}
