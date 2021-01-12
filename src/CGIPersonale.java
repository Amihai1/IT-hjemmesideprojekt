
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
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        System.out.println("<HTML>");
        System.out.println("<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\n" +
                "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Lato\">\n" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/standard.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../CSS/Brugerside.css\">");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>Brugerside</TITLE>");
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
                "</p1>");


        System.out.println("</HEAD>");
        System.out.println("<BODY>");

    }

    private static void showBody() {
        System.out.println("<p2>\n" +
                "<h2>\n" +
                "<div class=\"row1\">\n" +
                "    <div class=\"column1\">\n" +
                "        <div class=\"card1\">\n" +
                "            <a href=\"/hrv.html\"> <img src=\"//images01.nicepage.io/b6/f4/b6f4c452eabd98602023c4a997ae454e.jpeg\" alt=\"Beskeder\"\n" +
                "                 style=\"width:100%\">\n" +
                "                <div class=\"centered\">Beskeder</div>\n" +
                "                <a/>\n" +
                "                <div class=\"container1\">\n" +
                "                    <h3>Se dine beskeder</h3>\n" +
                "                </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    </div>\n" +
                "        <div class=\"row1\">\n" +
                "    <div class=\"column1\">\n" +
                "        <div class=\"card1\">\n" +
                "            <a href=\"/Journaler.html\"><img src=\"//images01.nicepage.io/b5/fe/b5fe5145dc5c44d9f83a05cdad99ab29.jpeg\" alt=\"Journaler\"\n" +
                "                 style=\"width:100%\">\n" +
                "            <div class=\"centered\">Journaler</div>\n" +
                "            <a/>\n" +
                "            <div class=\"container1\">\n" +
                "                <h3>Se dine Journaler fra sygehus</h3>\n" +
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
                "            <img src=\"//Skærmbillede%202020-09-26%20kl.%2010.40.12.png\" height=\"100\" width=\"150\"/>\n" +
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