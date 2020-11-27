import java.util.*;

public class CGIcookie {

    private static String cookie = null;
    private static String session = null;

    private static void handleCookies(StringTokenizer t) {
        String field;
        while ( t.hasMoreTokens() ) {
            field = t.nextToken();
            if (field != null) {
                field.trim();
                StringTokenizer tt = new StringTokenizer(field,"=\n\r");
                String s = tt.nextToken();
                if ( s.equals("__session") ) {
                    s = tt.nextToken();
                    if ( s != null )
                        session = s;
                }
            }
        }
    }

    private static void showHead() {
        if ( session == null )
            System.out.println("Set-Cookie: __session="+(int)(Math.random()*100000000));
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        System.out.println("<HTML>");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>The CGIcookie application</TITLE>");
        System.out.println("<META http-equiv=\"content-type\" content=\"text/html; charset=iso-8859-1\">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no-cache\">");
        System.out.println("<META http-equiv=\"expires\" content=\"0\">");
        System.out.println("</HEAD>");
        System.out.println("<BODY>");
    }

    private static void showTail() {
        System.out.println("</BODY>\n</HTML>");
    }

    private static void showBody(StringTokenizer t) {
        System.out.println("Transferred fields:");
        System.out.println("<TABLE BORDER=\"1\">");
        String field;
        while ( t.hasMoreTokens() ) {
            field = t.nextToken();
            if (field != null) {
                System.out.print("<TR><TD>");
                StringTokenizer tt = new StringTokenizer(field,"=\n\r");
                String s = tt.nextToken();
                if ( s != null ) {
                    System.out.print(s);
                    s = tt.nextToken();
                    if ( s != null )
                        System.out.print("</TD><TD>"+s);
                }
                System.out.println("</TD></TR>");
            }
        }
        System.out.println("</TABLE>");
    }

    public static void main(String[] args) {
        if (args.length > 1 && args[1] != null && args[1].length() > 0) {
            cookie = args[1];
            handleCookies(new StringTokenizer(cookie,";\n\r"));
        }
        showHead();
        if ( cookie != null ) System.out.println("Cookie: " + cookie + "<BR>");
        if ( session != null ) System.out.println("Session: " + session + "<BR>");
        if (args.length > 0 && args[0] != null && args[0].length() > 0)
            showBody(new StringTokenizer(args[0],"&\n\r"));
        else
            System.out.println("<P>No data!</P>");
        for (int i=2; args.length > i; i++ ) {
            if (args[i] != null && args[i].length() > 0)
                System.out.println("Argument "+i+": "+args[i]);
        }
        showTail();
    }

}