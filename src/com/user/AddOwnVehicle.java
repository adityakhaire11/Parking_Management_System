package com.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.connection.*;

@WebServlet("/AddOwnVehicle")
public class AddOwnVehicle extends HttpServlet 
{

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		Random rand = new Random();
		int ParkingNumber = rand.nextInt(9000000) + 1000000;
		//create session object
		HttpSession hs = request.getSession();
		
		System.out.println("ParkingNumber        " + ParkingNumber);
		
		//get jsp req values
		String catename = request.getParameter("catename");
		String vehcomp = request.getParameter("vehcomp");
		String vehreno = request.getParameter("vehreno");
		String ownername = request.getParameter("ownername");
		String ownercontno = request.getParameter("ownercontno");
		
		int parking_seat=0;
		int addVehicle = 0;
		String status = "";
		int count = 0;
		
		try 
		{
			Connection connection = DBconnection.getConnection();
			Statement statement = connection.createStatement();
			
			ResultSet total_parking_seat_result= statement.executeQuery("select * from tblParkingSeatCapacity");
			
			if(total_parking_seat_result.next())  //if returned result not empty
			{
				parking_seat = total_parking_seat_result.getInt(1);
			}
			
			ResultSet resultSet = statement.executeQuery("select count(*) from tblvehicle where status=''");
			
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
			
			if (parking_seat > count) //if parking seats available
			{
				//insert data
				addVehicle = statement.executeUpdate(
						"insert into tblvehicle(ParkingNumber,VehicleCategory,VehicleCompanyname,RegistrationNumber,OwnerName,OwnerContactNumber,status)values('"
								+ ParkingNumber + "','" + catename + "','" + vehcomp + "','" + vehreno + "','"
								+ ownername + "','" + ownercontno + "','" + status + "')");
			}
			else { //no parking seat available
				 //set msg response page session attribut
				String message="Parking slot is full, Wait for sometimes";
				hs.setAttribute("message", message);
				//select which page to load
				response.sendRedirect("add-user-vehicle.jsp");
			}
			if (addVehicle > 0) 
			{
				response.sendRedirect("add-user-vehicle.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
