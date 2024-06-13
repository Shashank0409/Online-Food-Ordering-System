

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import java.io.*;

/**
 * Servlet implementation class Order
 */
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double cost=0,tax=0,totalcost=0;
		PrintWriter out=response.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tt", "root", "root");
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery("select * from table1");
            
            cost = 0;
            int biryani=0,chicken=0,naan=0,burger=0,pizza=0,coke=0;
            
            while (rs.next()) {
                
                biryani+=rs.getInt("biryani");
                chicken+=rs.getInt("chicken");
                naan+=rs.getInt("naan");
                burger+=rs.getInt("burger");
                pizza+=rs.getInt("pizza");
                coke+=rs.getInt("coke");
                
                cost += rs.getDouble("cost");
            }
            
            tax = 0.025 * cost;
            totalcost=cost+tax;
            
            stmt.executeUpdate("delete from table1");
            
            String updatequery="insert into bills(biryani,chicken,naan,burger,pizza,coke,cost,tax,totalcost) values("+biryani+","+chicken+","+naan+","+burger+","+pizza+","+coke+","+cost+","+tax+","+totalcost+")";
            
            stmt.executeUpdate(updatequery);
            
            con.close();
            
            response.sendRedirect("thankyou.html");
            
        } catch (Exception e) {
        	out.println(e);
            e.printStackTrace();
        }
	}

}
