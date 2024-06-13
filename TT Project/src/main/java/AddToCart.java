

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.*;
import java.io.PrintWriter;


public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strbiryani=request.getParameter("biryani");
		String strchicken=request.getParameter("chicken");
		String strnaan=request.getParameter("naan");
		String strburger=request.getParameter("burger");
		String strpizza=request.getParameter("pizza");
		String strcoke=request.getParameter("coke");
		
		int biryani=Integer.parseInt(strbiryani);
		int chicken=Integer.parseInt(strchicken);
		int naan=Integer.parseInt(strnaan);
		int burger=Integer.parseInt(strburger);
		int pizza=Integer.parseInt(strpizza);
		int coke=Integer.parseInt(strcoke);
		
		PrintWriter out=response.getWriter();
		
		double totalcost=biryani*250+chicken*180+naan*30+burger*150+pizza*400+coke*50;
		
		double tax=0.025*totalcost;
		
		String updateString="insert into table1 values("+biryani+","+chicken+","+naan+","+burger+","+pizza+","+coke+","+totalcost+","+tax+")";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tt","root","root");
			
			Statement stmt=con.createStatement();
			
			stmt.executeUpdate(updateString);
			
			response.sendRedirect("cart.jsp");
			
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			out.println(e);
		}
	}

}
