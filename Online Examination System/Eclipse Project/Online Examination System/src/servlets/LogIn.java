package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LogIn")
public class LogIn extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = null;
		try {
				Class.forName("oracle.jdbc.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "exam", "exam");
				int roll = Integer.parseInt(request.getParameter("roll"));
				String password = request.getParameter("pass");
				Statement st = con.createStatement();
				String query = "SELECT LOGPASS FROM CANDIDATE WHERE ROLL = " + roll;

				ResultSet rs = st.executeQuery(query);
				rs.next();
				if (rs.getString("LOGPASS").equals(password)) {
					Cookie cook = new Cookie("roll", Integer.valueOf(roll).toString());
					cook.setMaxAge(75600);

					response.addCookie(cook);
					view = request.getRequestDispatcher("/exam.html");
					view.forward(request, response);
				}else {
					view= request.getRequestDispatcher("/PasswordWrong.html");
					view.forward(request,response);
				}

			} catch (ClassNotFoundException e) {
				System.out.println(new Date().toString() + "Class Not Found Exception");
			} catch (SQLException e) {
				System.out.println(new Date().toString() + "SQLException");
				view= request.getRequestDispatcher("/PasswordWrong.html");
				view.forward(request,response);
			}
//		}
	}
}
