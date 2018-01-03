package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ResultListener")
public class ResultListener extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException{
		try {
			boolean makeUpdate = false;
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "exam", "exam");
			int roll = Integer.parseInt(request.getParameter("roll"));
			int qnumber = Integer.parseInt(request.getParameter("ques"));
			makeUpdate = !(request.getParameter("option").equals("x"));
			
			System.out.println(new Date().toString()+"roll : "+roll);
			System.out.println(new Date().toString()+"ques : "+qnumber);
			System.out.println(new Date().toString()+"resp : "+request.getParameter("option"));
			System.out.println(makeUpdate);
			
			if(makeUpdate) {
				String query = "SELECT RESP FROM CANDIDATE WHERE ROLL = " + roll;
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				rs.next();
				String resp = rs.getString("RESP");
				resp = resp+ Integer.valueOf(qnumber).toString()+request.getParameter("option");
				String updateQuery = "UPDATE CANDIDATE SET RESP = '" + resp + "' WHERE ROLL = "+roll;
				int count = st.executeUpdate(updateQuery);
				System.out.println(count);
			}
			
			
			
		}catch(SQLException e) {
			System.out.println(new Date().toString()+"SQL Exception @ ResultListener");
		}catch(ClassNotFoundException e) {
			System.out.println(new Date().toString()+"ClassNotFoundException @ ResultListener");
		}
	}
} //END of ResultListener
