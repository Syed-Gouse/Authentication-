package authentication;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String email=req.getParameter("email");
		String password=req.getParameter("password");
		
		PrintWriter printWriter=resp.getWriter();
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/authentication?user=root&&password=root");
			preparedStatement=connection.prepareStatement("select * from data where email=? and password=? ");
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				RequestDispatcher rd=req.getRequestDispatcher("home.html");
				rd.forward(req, resp);
			}
			else {
				printWriter.println("<html><body>");
				printWriter.println("<p style='color:red'>Wrong id or password </p> ");
				printWriter.println("</body></html>");
				RequestDispatcher requestDispatcher=req.getRequestDispatcher("login.html");
				requestDispatcher.include(req, resp);
			}
			
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
				try {
					if(resultSet!=null)
						resultSet.close();
					if(preparedStatement!=null)
						preparedStatement.close();
					if(connection!=null)
						connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
    }

}
