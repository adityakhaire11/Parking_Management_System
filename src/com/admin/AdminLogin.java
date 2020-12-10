package com.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.connection.*;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet 
{

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		// get the values from jsp req
		String uname = request.getParameter("username");
		String pass = request.getParameter("password");
		
		try {  //as db stmt may give excep
			HttpSession hs = request.getSession();  //create session object
			String tokens = UUID.randomUUID().toString();	//generating token 
			Connection con = DBconnection.getConnection();	//creating connection with DB
			Statement st = con.createStatement();  //createtatement obj to execute queries
			//execute query and store rettuened result
			ResultSet resultset = st.executeQuery("select * from tbladmin where username='" + uname + "' AND password='" + pass + "'");
			//if result not null i.e admin is present in DB
			if (resultset.next()) {
				hs.setAttribute("uname", uname);
				response.sendRedirect("dashboard.jsp?_tokens='" + tokens + "'");
			} 
		  else { //admin with login credencial not present
				response.sendRedirect("admin-login.jsp");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
