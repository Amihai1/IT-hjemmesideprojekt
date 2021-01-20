import java.util.StringTokenizer;

public class CGIPatientJournalValg {
    /**
     * @author Amihai
     */


    private static String session = null;

    public static void main(String[] args) {
        if (args.length > 1 && args[1] != null && args[1].length() > 0) {
            String cookie = args[1];
            handleCookies(new StringTokenizer(cookie, ";\n\r"));
        }
        int check = Integer.parseInt(session);
        try {
            if (check == 1101010101) {
                showRaskGraf();
            } else if (check == 1303030303) {
                showSygGraf();
            } else {
                showError();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showTail();
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

    static void showSygGraf() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \" -//W3C//DTD HTML 3.2//EN\" >");
        System.out.println("<HTML>");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>Fejl i oprettelse! application</TITLE>");
        System.out.println("<META http-equiv=\"content - type\" content=\"text / html; charset = UTF-8 \">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no - cache\">");
        System.out.println("<meta http-equiv=\"refresh\" content=\"0; URL=http://130.226.195.37:39080/PatientJournal.html\"/>");
        System.out.println("</HEAD>");
        System.out.println("<BODY>");

    }

    static void showRaskGraf() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \" -//W3C//DTD HTML 3.2//EN\" >");
        System.out.println("<HTML>");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>Fejl i oprettelse! application</TITLE>");
        System.out.println("<META http-equiv=\"content - type\" content=\"text / html; charset = UTF-8 \">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no - cache\">");
        System.out.println("<meta http-equiv=\"refresh\" content=\"0; URL=http://130.226.195.37:39080/hrv.html\"/>");
        System.out.println("</HEAD>");
        System.out.println("<BODY>");

    }

    static void showError() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \" -//W3C//DTD HTML 3.2//EN\" >");
        System.out.println("<HTML>");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>Fejl i oprettelse! application</TITLE>");
        System.out.println("<META http-equiv=\"content - type\" content=\"text / html; charset = UTF-8 \">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no - cache\">");
        System.out.println("<meta http-equiv=\"refresh\" content=\"0; URL=http://130.226.195.37:39080/Jounal.html\"/>");
        System.out.println("</HEAD>");
        System.out.println("<BODY>");

    }

    private static void showTail() {
        System.out.println("</BODY>\n</HTML>");
    }


}
