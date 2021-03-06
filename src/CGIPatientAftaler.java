/** @author Amihai */

import java.sql.*;
import java.util.StringTokenizer;

public class CGIPatientAftaler {
    static String url2 = "jdbc:mariadb://192.168.239.20:3306/myuser";
    private static Connection conn = null;
    private static Statement statement = null;
    private static String Patientid = null;
    private static String dato = null;
    private static String varighed = null;
    private static String lokale = null;
    private static String behandling = null;
    private static String hospital = null;
    private static String bh = null;
    private static int patientid = 0;
    private static String cookie = null;
    private static String session = null;
    private static String aftaleid;

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


    private static void showHead() {
        if (session == null) System.out.println("Set-Cookie: __session=" + session);
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        System.out.println("<HTML lang=\"da\">");
        System.out.println("<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\n" +
                "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Lato\">\n" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/standard.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/Brugerside.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/Login.html.css\">\n");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>Aftaler</TITLE>");
        System.out.println("<META http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no-cache\">");
        System.out.println("<META http-equiv=\"expires\" content=\"0\">");
        //https://www.w3schools.com/jsref/met_his_back.asp
        System.out.println("<script>\n" +
                "function goBack() {\n" +
                "  window.history.back();\n" +
                "}\n" +
                "</script>");
        System.out.println(
                "<p1>\n" +
                        "<!-- Links (sit on top) -->\n" +
                        "<div class=\"w3-top topnav\">\n" +
                        "    <div class=\"w3-row w3-large\">\n" +
                        "        <div class=\"w3-col s3\">\n" +
                        "            <a onclick=\"goBack()\" class=\"w3-button w3-block\"><i class=\"fa fa-arrow-left\"></i>&nbsp Tilbage</a>\n" +
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
                        "        <th>Aftaleid</th>\n" +
                        "        <th>CPR-nummer</th>\n" +
                        "        <th>Dato</th>\n" +
                        "        <th>Varighed</th>\n" +
                        "        <th>Hospital</th>\n" +
                        "        <th>Lokale</th>\n" +
                        "        <th>Behandling</th>\n" +
                        "    </tr>\n");
        System.out.println("</HEAD>");
        System.out.println("<BODY>");


    }

    private static void showBody(StringTokenizer t) {
        String field;
        while (t.hasMoreTokens()) {
            field = t.nextToken();
            if (field != null) {

                StringTokenizer tt = new StringTokenizer(field, "=\n\r");
                String s = tt.nextToken();
                if (s != null) {
                    System.out.println(s);
                    s = tt.nextToken();
                }

            }
        }
        System.out.println(
                "\n" +
                        "    <tr>\n" +
                        "        <td> <input type=\"hidden\" name=\"id\" value=" + aftaleid + ">" + aftaleid + "</td>\n" +
                        "        <td> <input type=\"hidden\" name=\"id\" value=" + Patientid + ">" + Patientid + "</td>\n" +
                        "        <td>" + dato + "</td>\n" +
                        "        <td>" + varighed + "</td>\n" +
                        "        <td>" + hospital + "</td>\n" +
                        "        <td>" + lokale + "</td>\n" +
                        "        <td>" + behandling + "</td>\n" +
                        "    </tr>\n" +
                        "\n");

    }

    private static void showTail() {
        System.out.println("</table>\n" +
                "</BODY>\n</HTML>");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        if (args.length > 1 && args[1] != null && args[1].length() > 0) {
            cookie = args[1];
            handleCookies(new StringTokenizer(cookie, ";\n\r"));
        }

        showHead();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String user, pass;
            user = "oskar";
            pass = "123456789";
            /*if (cookie != null) System.out.println("Cookie: " + cookie + "<BR>");
            if (session != null) System.out.println("Session: " + session + "<BR>");*/
            patientid = Integer.parseInt(session);
            conn = DriverManager.getConnection(url2, user, pass);
            String sqlFindaftaler = "select * from aftaler where cpr ='" + patientid + "'";
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlFindaftaler);
            while (rs.next()) {
                aftaleid = String.valueOf(rs.getInt("aftaleid"));
                Patientid = String.valueOf(rs.getInt("cpr"));
                dato = rs.getString("dato");
                varighed = rs.getString("varighed");
                lokale = rs.getString("lokale");
                behandling = rs.getString("behandling");
                hospital = rs.getString("hospital");
                showBody(new StringTokenizer(args[0], "&\n\r"));

            }


            showTail();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
