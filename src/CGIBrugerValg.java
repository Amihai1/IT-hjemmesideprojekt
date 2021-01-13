import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.StringTokenizer;

public class CGIBrugerValg {

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static String url2 = "jdbc:mariadb://192.168.239.20:3306/myuser";
    static String url = "jdbc:mariadb://[2001:878:200:4102:20c:f1ff:fe6c:756d]:3306/myuser";
    private static String cprTilDb;
    private static String fornavn = null;
    private static String efternavn = null;
    private static String fornavnid;

    private static Connection conn = null;
    private static Statement statement = null;
    static String inputfraCGI = null;
    private static String cookie = null;
    private static String session = null;
    private static String Cpr = null;
    private static String Patientid=null;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
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
        try {
            conn = DriverManager.getConnection(url2, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        fornavnid = kodepost[1];
        cookie = cprTilDb;
        handleCookies(new StringTokenizer(cookie, ";\n\r"));
        showHead();
        if (cookie != null) System.out.println("Cookie: " + cookie + "<BR>");
        if (session != null) System.out.println("session: " + session + "<BR>");
        try {
            if (findUser(cprTilDb, fornavnid) != null) {
                showBody(new StringTokenizer(args[0], "&\n\r"));
            }


        } catch (IOException | SQLException | ClassNotFoundException ioe) {
            System.out.println("<P>IOException reading POST data: " + ioe + "</P>");

            showTail();
        }
    }

    private static void showHead() {
        if (session == null) System.out.println("Set-Cookie: __session=" + cookie);
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
        System.out.println("<TITLE>Vis Brugerside</TITLE>");
        System.out.println("<META http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no-cache\">");
        System.out.println("<META http-equiv=\"expires\" content=\"0\">");
        System.out.println("<p1>\n" +
                "<!-- Links (sit on top) -->\n" +
                "<div class=\"w3-top topnav\">\n" +
                "    <div class=\"w3-row w3-large\">\n" +
                "        <div class=\"w3-col s3\">\n" +
                "            <a href=\"/index.html\" class=\"w3-button w3-block\"><i class=\"fa fa-fw fa-user-o\"></i>&nbsp Min Side</a>\n" +
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
                "</p1>");

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
        System.out.println("<p2>\n" +
                "<h2>\n" +
                "<div class=\"row1\">\n" +
                "    <div class=\"column1\">\n" +
                "        <div class=\"card1\">\n" +
                "            <form action=\"/cgi-bin/\" method=\"post\"><button type=\"submit\"><img src=\"//images01.nicepage.io/b6/f4/b6f4c452eabd98602023c4a997ae454e.jpeg\"\n" +
                "                 alt=\"Aftaler\" style=\"width:100%\">\n" +
                "            <div class=\"centered\">Send Besked</div>\n" +
                "            </button></form>\n" +
                "            <div class=\"container1\">\n" +
                "                <h3>Send besked til patient her</h3>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"row1\">\n" +
                "    <div class=\"column1\">\n" +
                "        <div class=\"card1\">\n" +
                "            <form action=\"/cgi-bin/\" method=\"post\"><button type=\"submit\"><img src=\"//images01.nicepage.io/fd/64/fd645fffe75a3b862c96027a9831be32.jpeg\"\n" +
                "                 alt=\"Aftaler\" style=\"width:100%\">\n" +
                "            <div class=\"centered\">Aftaler</div>\n" +
                "            </button></form>\n" +
                "            <div class=\"container1\">\n" +
                "                <h3>Se dine kommende aftaler</h3>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    </div>\n" +
                "\n" +
                "        <div class=\"row1\">\n" +
                "    <div class=\"column1\">\n" +
                "        <div class=\"card1\">\n" +
                "            <form action=\"/cgi-bin/\" method=\"post\"><button type=\"submit\"><img src=\"//images01.nicepage.io/b5/fe/b5fe5145dc5c44d9f83a05cdad99ab29.jpeg\"\n" +
                "                 alt=\"Aftaler\" style=\"width:100%\">\n" +
                "            <div class=\"centered\">Journal</div>\n" +
                "            </button></form>\n" +
                "            <div class=\"container1\">\n" +
                "                <h3>Se Patientjournaler</h3>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</h2>\n" +
                "</p2>\n" +
                "\n" +
                "\n" +
                "        <!-- Footer -->\n" +
                "\n" +
                "        <footer class=\"w3-center\">\n" +
                "            <br>\n" +
                "            <img src=\"../billeder/Skærmbillede%202020-09-26%20kl.%2010.40.12.png\" height=\"100\" width=\"150\"/>\n" +
                "            </br>\n" +
                "            <p>Powered by DTU Sundtek</p>\n" +
                "        </footer>\n" +
                "\n" +
                "\n" +
                "\n");

    }

    private static void showTail() {
        System.out.println("</BODY>\n</HTML>");
    }

    private static String findUser(String cprTilDb, String fornavnid) throws
            SQLException, ClassNotFoundException, IOException {
        String output = "";
        String sqlFindUser = "select * from patient where cpr ='" + cprTilDb + "' and fornavn='" + fornavnid + "'";
        try {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlFindUser);
            rs.next();
            Cpr = String.valueOf(rs.getInt(("cpr")));
            fornavn = rs.getString("fornavn");
            efternavn = rs.getString("efternavn");
            Patientid = String.valueOf(rs.getInt("patientid"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Cpr;

    }

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


}

