import java.sql.*;

public class CGIAftaler {
    static String url2 = "jdbc:mariadb://192.168.239.20:3306/myuser";
    private static Connection conn = null;
    private static Statement statement = null;
    private static String Patientid = null;
    private static String dato = null;
    private static String varighed = null;
    private static String lokale = null;
    private static String behandling = null;
    private static String hospital = null;

    public static void main(String[] args) {
        showHead();

        try {

            Class.forName("org.mariadb.jdbc.Driver");
            String user, pass;
            user = "oskar";
            pass = "123456789";
            int patientid = 1;
            conn = DriverManager.getConnection(url2, user, pass);
            String sqlFindaftaler = "select * from aftaler where patientid ='" + patientid + "'";
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlFindaftaler);
            while (rs.next()) {
                Patientid = String.valueOf(rs.getInt("patientid"));
                dato =rs.getString("dato");
                varighed = rs.getString("varighed");
                lokale = rs.getString("lokale");
                behandling = rs.getString("behandling");
                hospital = rs.getString("hospital");
                showBody();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        showTail();
    }

    private static void showHead() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        System.out.println("<HTML>");
        System.out.println("<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\n" +
                "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Lato\">\n" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/standard.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/Brugerside.css\">");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>Loginvalidation application</TITLE>");
        System.out.println("<META http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no-cache\">");
        System.out.println("<META http-equiv=\"expires\" content=\"0\">");
        System.out.println("<form action=\"/cgi-bin/CGIGet\"></form>\n" +
                "<p1>\n" +
                "<!-- Links (sit on top) -->\n" +
                "<div class=\"w3-top topnav\">\n" +
                "    <div class=\"w3-row w3-large\">\n" +
                "        <div class=\"w3-col s3\">\n" +
                "            <a href=\"Brugerside.html\" class=\"w3-button w3-block\"><i class=\"fa fa-fw fa-user-o\"></i>&nbsp Min Side</a>\n" +
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
                "            }\n" +
                "\n" +
                "            tr:nth-child(even) {\n" +
                "                background-color: #dddddd;\n" +
                "            }\n" +
                "table{\n" +
                "                margin-top: 80px;\n" +
                "            }\n" +
                "        </style>");

        System.out.println("</HEAD>");
        System.out.println("<BODY>");

    }

    private static void showBody() {
        System.out.println(
                "\n" +
                        "<table>\n" +
                        "    <tr>\n" +
                        "        <th>Aendre/slet</th>\n" +
                        "        <th>Patientid</th>\n" +
                        "        <th>Dato</th>\n" +
                        "        <th>Varighed</th>\n" +
                        "        <th>Hospital</th>\n" +
                        "        <th>Lokale</th>\n" +
                        "        <th>Behandling</th>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td><button>Slet/Aendre</button></td>\n" +
                        "        <td>" + Patientid + "</td>\n" +
                        "        <td>" + dato + "</td>\n" +
                        "        <td>" + varighed + "</td>\n" +
                        "        <td>" + hospital + "</td>\n" +
                        "        <td>" + lokale + "</td>\n" +
                        "        <td>" + behandling + "</td>\n" +
                        "    </tr>\n" +
                        "\n" +
                        "</table>\n");

    }

    private static void showTail() {
        System.out.println("</BODY>\n</HTML>");
    }


}
