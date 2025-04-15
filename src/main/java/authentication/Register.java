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

public class Register extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter printWriter=resp.getWriter();
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		String name = req.getParameter("name");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/authentication?user=root&&password=root");
			preparedStatement=connection.prepareStatement("select * from data where email=?");
			preparedStatement.setString(1, email);
			resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				printWriter.println("<html><body>");
				printWriter.println("<p style='color:red'>USER ALREADY PRESENT PLEASE PRESS LOGIN</p> ");
				printWriter.println("</body></html>");
				RequestDispatcher requestDispatcher=req.getRequestDispatcher("register.html");
				requestDispatcher.include(req, resp);
			}
				preparedStatement=connection.prepareStatement("insert into data values(?,?,?)");
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, name);
				preparedStatement.executeUpdate();
			
				RequestDispatcher requestDispatcher=req.getRequestDispatcher("login.html");
				requestDispatcher.forward(req, resp);
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
