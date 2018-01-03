package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/JavaScriptServlet")
public class JavaScriptServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException{
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "exam", "exam");
			Statement st = con.createStatement();
			String query = "SELECT QUESTION,OPTION1,OPTION2,OPTION3,OPTION4 FROM QUESTIONS";
			
			ResultSet rs = st.executeQuery(query);
			
			String questions = "" ;
			String option1 = "";
			String option2 = "";
			String option3 = "";
			String option4 = "";
			
			while(rs.next()) {
				questions = questions +"\""+ rs.getString("QUESTION")+"\"" + ",";
				option1 = option1 + "\""+rs.getString("OPTION1") +"\""+ ",";
				option2 = option2 + "\""+rs.getString("OPTION2") +"\""+ ",";
				option3 = option3 + "\""+rs.getString("OPTION3") +"\""+ ",";
				option4 = option4 + "\""+rs.getString("OPTION4") +"\""+ ",";
			}
			questions = questions.trim();
			option1 = option1.trim();
			option2 = option2.trim();
			option3 = option3.trim();
			option4 = option4.trim();
			questions = questions.substring(0,questions.length()-1);
			option1 = option1.substring(0,option1.length()-1);
			option2 = option2.substring(0,option2.length()-1);
			option3 = option3.substring(0,option3.length()-1);
			option4 = option4.substring(0,option4.length()-1);
			
			String javaScript = "";
			javaScript = javaScript + "var xhr;    //GLOBAL\r\n" + 
					"function create(){\r\n" + 
					"    xhr = new XMLHttpRequest();\r\n" + 
					"    draw1Question();\r\n" + 
					"}\r\n" + 
					"function removeCookies(){\r\n" + 
					"    // alert(\"You are Quiting Exam!!\");\r\n" + 
					"    ";
			javaScript = javaScript + "document.cookie = \"roll\"";
			javaScript = javaScript + " + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';\r\n" + 
					"    \r\n" + 
					"    window.close();\r\n" + 
					"}\r\n" + 
					"function draw1Question(){\r\n" + 
					"    let ques = document.getElementById(\"question\");\r\n" + 
					"    ques.innerHTML = questions[0];\r\n" + 
					"    let opt1 = document.getElementById(\"option1Label\");\r\n" + 
					"    opt1.innerHTML = options_1[0];\r\n" + 
					"    let opt2 = document.getElementById(\"option2Label\");\r\n" + 
					"    opt2.innerHTML = options_2[0];\r\n" + 
					"    let opt3 = document.getElementById(\"option3Label\");\r\n" + 
					"    opt3.innerHTML = options_3[0];\r\n" + 
					"    let opt4 = document.getElementById(\"option4Label\");\r\n" + 
					"    opt4.innerHTML = options_4[0];\r\n" + 
					"    document.getElementById(\"qnumber\").innerHTML = 1;\r\n" + 
					"}\r\n" + 
					"var questions=[";
			javaScript = javaScript + questions + "\r\n" + 
					"];";
			javaScript = javaScript + "\r\n" + 
					"\r\n" + 
					"var options_1 = [";
			javaScript = javaScript + option1 + "\r\n" + 
					"];";
			javaScript = javaScript + "\r\n" + 
					"\r\n" + 
					"var options_2=[";
			javaScript = javaScript + option2 + "\r\n" + 
					"];";
			javaScript = javaScript + "\r\n" + 
					"\r\n" + 
					"var options_3=[";
			javaScript = javaScript + option3 + "\r\n" + 
					"];";
			javaScript = javaScript + "\r\n" + 
					"\r\n" + 
					"var options_4=[";
			javaScript = javaScript + option4 + "\r\n" + 
					"];\r\n" + 
					"";
			javaScript = javaScript + "var next_ = false;\r\n" + 
					"\r\n" + 
					"function computeValue(){\r\n" + 
					"    let arr = [\"option1\",\"option2\",\"option3\",\"option4\"];\r\n" + 
					"    for(let i=0;i<=3;i++){\r\n" + 
					"        let obj = document.getElementById(arr[i]);\r\n" + 
					"        if(obj.checked){\r\n" + 
					"            return obj.value;\r\n" + 
					"        }\r\n" + 
					"    }\r\n" + 
					"    return \"x\";\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"function makeRequest(){\r\n" + 
					"    var value = computeValue();";
			javaScript = javaScript + "var url=\"";
			javaScript = javaScript + "ResultListener?"+"option=\"+value+\"&ques=\"+document.getElementById(\"qnumber\").innerHTML+\"&roll=\"+getRollFromCookie();";
			javaScript = javaScript + "    xhr.open(\"GET\",url,true);\r\n" + 
					"    xhr.onreadystatechange = doUpdate;\r\n" + 
					"    xhr.send(null); //Make GET Request\r\n" + 
					"    \r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"function doUpdate(){\r\n" + 
					"    if(xhr.readyState == 4){\r\n" + 
					"        console.log(\"SUCCESS\") ;\r\n" + 
					"    }\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"function next(){\r\n" + 
					"    next_ = true;\r\n" + 
					"    console.log(\"NEXT Clicked\");\r\n" + 
					"    drawNextQuestion();\r\n" + 
					"    makeRequest();\r\n" + 
					"}\r\n" + 
					"function prev(){\r\n" + 
					"    next_ = false;\r\n" + 
					"    console.log(\"PREVIOUS Clicked\");\r\n" + 
					"    drawPrevQuestion();\r\n" + 
					"    makeRequest();\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"function drawNextQuestion(){\r\n" + 
					"    console.log(\"drawNextQuestion () Called....\");\r\n" + 
					"    let qno = document.getElementById(\"qnumber\").innerHTML;\r\n" + 
					"    console.log(qno);\r\n" + 
					"    if(qno < questions.length){\r\n" + 
					"        let ques = document.getElementById(\"question\");\r\n" + 
					"        ques.innerHTML = questions[qno];\r\n" + 
					"        let opt1 = document.getElementById(\"option1Label\");\r\n" + 
					"        console.log(opt1);\r\n" + 
					"        opt1.innerHTML = options_1[qno];\r\n" + 
					"        let opt2 = document.getElementById(\"option2Label\");\r\n" + 
					"        opt2.innerHTML = options_2[qno];\r\n" + 
					"        let opt3 = document.getElementById(\"option3Label\");\r\n" + 
					"        opt3.innerHTML = options_3[qno];\r\n" + 
					"        let opt4 = document.getElementById(\"option4Label\");\r\n" + 
					"        opt4.innerHTML = options_4[qno];\r\n" + 
					"        document.getElementById(\"qnumber\").innerHTML = ++qno;\r\n" + 
					"        \r\n" + 
					"    }\r\n" + 
					"    \r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"function drawPrevQuestion(){\r\n" + 
					"    console.log(\"drawPrevQuestion() Called\");\r\n" + 
					"    let qno = document.getElementById(\"qnumber\").innerHTML;\r\n" + 
					"    if(qno > 1){\r\n" + 
					"        let ques = document.getElementById(\"question\");\r\n" + 
					"        ques.innerHTML = questions[qno-2];\r\n" + 
					"        let opt1 = document.getElementById(\"option1Label\");\r\n" + 
					"        opt1.innerHTML = options_1[qno-2];\r\n" + 
					"        let opt2 = document.getElementById(\"option2Label\");\r\n" + 
					"        opt2.innerHTML = options_2[qno-2];\r\n" + 
					"        let opt3 = document.getElementById(\"option3Label\");\r\n" + 
					"        opt3.innerHTML = options_3[qno-2];\r\n" + 
					"        let opt4 = document.getElementById(\"option4Label\");\r\n" + 
					"        opt4.innerHTML = options_4[qno-2];\r\n" + 
					"        document.getElementById(\"qnumber\").innerHTML = --qno;\r\n" + 
					"        \r\n" + 
					"    }\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"function getRollFromCookie(){\r\n" + 
					"    return Number(document.cookie.substring(document.cookie.indexOf(\"roll=\")+5,document.cookie.length));\r\n" + 
					"}";
			response.setContentType("text/javascript");
			PrintWriter pw = response.getWriter();
			pw.println(javaScript);
			
			
			
		}catch(ClassNotFoundException e) {
			System.out.println("No Class Found Exception @JSServlet");
		}catch(SQLException e) {
			System.out.println("SQL Exception @JSServlet");
		}
	}
	
}	//End of Servlet
