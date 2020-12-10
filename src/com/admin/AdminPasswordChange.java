package com.admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.connection.*;

@WebServlet("/AdminPasswordChange")
public class AdminPasswordChange extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		//values sent from JSP
		String cpassword = request.getParameter("cpassword");
		String password = request.getParameter("password");
		String confpass = request.getParameter("confpass");
		String pass = "";
		
		HttpSession session = request.getSession();
		try {
			Connection con = DBconnection.getConnection();  //create db connnection 
			Statement statement = con.createStatement();	//create stmt object to run sql queries
			//execute query and store result
			ResultSet resultset = statement.executeQuery("select password from tbladmin where password='" + cpassword+ "' and username='" + session.getAttribute("uname") + "'");
			//if result not null
			if (resultset.next()) {
				pass = resultset.getString(1);
			}
			if (password.equals(confpass)) //check if both entered new passwords matches or not
			{
				if (pass.equals(cpassword))  //if user entered current passowrd and actual user pass matches
				{ //update the password field
					statement.executeUpdate("update tbladmin set password='" + password + "' where username='"+ session.getAttribute("uname") + "' ");
					response.sendRedirect("change-password.jsp");
					statement.close();
					con.close();		//close connection
				} 
				else {	 //if user pass not found 
					response.sendRedirect("change-password.jsp");
				}
			} else {//if both pass not matched
				response.sendRedirect("change-password.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
