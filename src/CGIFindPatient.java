/** @author Amihai */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class CGIFindPatient {
    static String url2 = "jdbc:mariadb://192.168.239.20:3306/myuser";
    private static String patientid = null;
    private static String fornavnid = null;
    private static String efternavnid = null;
    private static String cprid = null;
    private static String result = null;
    private static PreparedStatement prep = null;
    static String inputCGI = null;

    private static void showHead() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        System.out.println("<HTML>");
        System.out.println("<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\n" +
                "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Lato\">\n" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/standard.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/Brugerside.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/Login.html.css\">\n");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>find patient</TITLE>");
        System.out.println("<META http-equiv=\"content-type\" content=\"text/html; charset=UTC-8\">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no-cache\">");
        System.out.println("<META http-equiv=\"expires\" content=\"0\">");
        System.out.println( "<p1>\n" +
                "<!-- Links (sit on top) -->\n" +
                "<div class=\"w3-top topnav\">\n" +
                "    <div class=\"w3-row w3-large\">\n" +
                "        <div class=\"w3-col s3\">\n" +
                "            <a href=\"http://130.226.195.37:39080/cgi-bin/CGIpost\" class=\"w3-button w3-block\"><i class=\"fa fa-fw fa-user-o\"></i>&nbsp Min Side</a>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"w3-col s3 w3-right\">\n" +
                "             <a href=\"/index.html\" class=\"w3-button w3-block\"><i class=\"fa fa-sign-out\" style=\"color:#ff0000\">\n" +
                "            </i>&nbsp\n" +
                "                Log Ud</a>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "</div>\n" +
                "</p1>" +
                "<style>\n" +
                "            table {\n" +
                "                font-family: arial, sans-serif;\n" +
                "                border-collapse: collapse;\n" +
                "                width: 100%;\n" +
                "            }\n" +
                "\n" +
                "            td, th {\n" +
                "                border: 1px solid #dddddd;\n" +
                "                text-align: left;\n" +
                "                padding: 8px;\n" +
                "                font-size: 28px;\n" +
                "            }\n" +
                "\n" +
                "            tr:nth-child(even) {\n" +
                "                background-color: #dddddd;\n" +
                "            }\n" +
                "table{\n" +
                "                margin-top: 80px;\n" +
                "            }\n" +
                "        </style>" +
                "\n" +
                "<table>\n" +
                "    <tr>\n" +
                "        <th>Patientid</th>\n" +
                "        <th>CPR-nummer</th>\n" +
                "        <th>Fornavn</th>\n" +
                "        <th>Efternavn</th>\n" +
                "        <th>Valg af bruger</th>\n" +
                "    </tr>\n");

        System.out.println("</HEAD>");
        System.out.println("<BODY>");


    }

    private static void showBody(){
        System.out.println("<tr>\n" +
                        " <form action=\"/cgi-bin/CGIBrugerValg\" method=\"post\">\n" +
                        "        <td> <input type=\"hidden\" value=" + patientid + ">" + patientid + "</td>\n" +
                        "        <td> <input type=\"hidden\" value=" + cprid + ">" + cprid + "</td>\n" +
                        "        <td>" + result + "</td>\n" +
                        "        <td>" + efternavnid + "</td>\n" +
                        "        <td><button type=\"submit\">Valg af bruger</button></td>\n" +
                        "</form>" +
                        "    </tr>\n" +
                        "\n");
    }

    private static void showTail() {
        System.out.println("</table>\n" +
                "</BODY>\n</HTML>");
    }

    public static void main(String[] args) throws IOException {
        showHead();
        try {

            Class.forName("org.mariadb.jdbc.Driver");

            //mysql skal  ndres senere til MariaDB, localhost til en IPaddresse -
            String user, pass;
            user = "oskar";
            pass = "123456789";
            Connection conn = DriverManager.getConnection(url2, user, pass);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String[] data = {in.readLine()};
            inputCGI = data[0];
            String[] clientResponse;
            clientResponse = inputCGI.split("&");
            String[] cprpost;
            cprpost = clientResponse[0].split("=");
            String cpr = cprpost[1];
            String[] fornavnpost;
            fornavnpost = clientResponse[1].split("=");
            String fornavn = fornavnpost[1];
            String[] efternavnpost;
            efternavnpost = clientResponse[2].split("=");
            String efternavn = efternavnpost[1];
            String sql = "select * from patient";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                cprid = String.valueOf(rs.getInt("cpr"));
                fornavnid = rs.getString("fornavn");
                result = java.net.URLDecoder.decode(fornavnid, StandardCharsets.UTF_8);
                efternavnid = rs.getString("efternavn");
                patientid = String.valueOf(rs.getInt("patientid"));
                showBody();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        showTail();


    }
}
