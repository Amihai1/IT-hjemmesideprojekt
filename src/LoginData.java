import java.sql.*;

public class LoginData {
    Connection conn = MySQLConnector.getConn();
    public void LoginDataInd() {
        for (int i = 0; i < 1; i++) {
            try {
                PreparedStatement statement = conn.prepareStatement("INSERT INTO login VALUES(?,?,?);");
                statement.setString(1, "0303030303");
                statement.setString(2, "kennet");
                statement.setInt(3, 3);

                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loaddata(){

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM login" );
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData rsMatadata = rs.getMetaData();
            int column = rsMatadata.getColumnCount();
            while(rs.next()){
                int cpr = rs.getInt("cpr");
                String kode = rs.getString("kode");
                int id = rs.getInt("patientid");
                System.out.format("%s,%s,%s\n",cpr, kode, id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}