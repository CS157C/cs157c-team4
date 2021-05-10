package adoptable.java.action.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datastax.driver.core.exceptions.DriverException;

import adoptable.java.transferObject.*;
import adoptable.java.userBusiness.*;
//import adoptable.java.util.DBUtil;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.BoundStatement;

@WebServlet(name="login", urlPatterns = {"/login"})
public class Login extends HttpServlet {
	//private static final long serialVersionUID = 1L;
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
//		dispatcher.forward(request,response);
//	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			//Cluster c = DBUtil.getCluster();
			RegularUser logined = RuserBusiness.login(username, password);
			if(logined != null) {
				request.getSession().setAttribute("CURRENT_USER", logined);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/home/index.jsp");
				dispatcher.forward(request,response);
				System.out.print(logined.getUsername()+"test");
			} else
			{
				request.setAttribute("err", "Wrong username or password");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
				dispatcher.forward(request,response);
			}
		} catch (Exception e) {
			request.setAttribute("err", "system error "+e.getMessage());
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request,response);
		}
		
		
	}

}
