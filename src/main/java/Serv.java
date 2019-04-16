import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Calendar;

public class Serv extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        double[] t = new double[5];

        String url =  "jdbc:mysql://localhost:3306/test?useUnicode=true&useSSL=true&use" +
                "JDBCCompliantTimezoneShift=true" +
                "&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = "user";
        String password = "user";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try(Connection connection = DriverManager.getConnection(url, username,password);){

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from weektomonths ");
            ResultSet res = preparedStatement.executeQuery();

            while (res.next()){
                Date date  = res.getDate("weekdate");

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                double sum = res.getInt("summa");

                for(int i=0; i<5; i++){

                    cal.add(Calendar.DAY_OF_YEAR, i);
                    for(int k=0; k<5;k++){
                        if( cal.get(Calendar.MONTH) == k+1){
                            t[k] = t[k] + sum/5;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }  catch (NullPointerException e){

        }


        PrintWriter pw = response.getWriter();

        pw.println("<html>");
        pw.println("<h1> Hell " + t[0] + "</h1>");
        pw.println("</html>");
    }
}
