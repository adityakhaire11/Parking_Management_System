package com.user;

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
//servlet for user login 
@WebServlet("/UserLogin")   //we can use this name in jsp like action= UserLogin
public class UserLogin extends HttpServlet
{
	

	private static final long serialVersionUID = 1L;  //we don't use this

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String uname = request.getParameter("username");  //input from jsp
		String pass = request.getParameter("password"); 
	
		try {   //may raise execp
			HttpSession hs = request.getSession();		//session object
			String tokens = UUID.randomUUID().toString();
			//create connection with our DB
			Connection con = DBconnection.getConnection();
			//statement object to execute query
			Statement st = con.createStatement();
			ResultSet resultset = st.executeQuery("select * from tbluser where uname='" + uname + "' AND password='" + pass + "'");
			
			//resultset returend
			if (resultset.next())   //if resultset not empty
			{
				String fullName=resultset.getString(2);  //get name from result set 
				//set sessions
				hs.setAttribute("uname", uname);
				hs.setAttribute("fullName", fullName);
				hs.setAttribute("mobileNo", resultset.getString(3));
				//when user logins redirect to dashboard
				response.sendRedirect("user-dashboard.jsp?_tokens='" + tokens + "'");
			} 
			//user not found in DB
			else 
			{
				String message = "Invalid credential";
				hs.setAttribute("message", message); //set sessions
				response.sendRedirect("user-login.jsp");  //redirect back to login.jsp
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}	

}
