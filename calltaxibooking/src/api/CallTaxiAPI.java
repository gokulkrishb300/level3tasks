package api;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import accountdeclare.Booking;
import accountdeclare.Customer;
import accountdeclare.Points;
import json.Json;
import manualexception.ManualException;

public class CallTaxiAPI {
	
	private int customerId = 0;
	
	private int bookingId = 0;
	
	private int noOfTaxis = 0;
	
	Json json = new Json();
	Gson gson = new Gson();
	JSONObject customerMap = new JSONObject();
	JSONObject keyMap = new JSONObject();
	
	@SuppressWarnings("unchecked")
	private int customerId() throws ManualException
	{
		keyMap.put("customerId", String.valueOf(++customerId));
		json.jsonWrite(keyMap, "KeyDetails.json");
		return customerId;
	}
	
	@SuppressWarnings("unchecked")
	private int bookingId(String taxiNo) throws ManualException
	{
		JSONObject jsonObj = readTaxi(taxiNo);
		String bookingID = (String) jsonObj.get("bookingId");
		bookingId = Integer.valueOf(bookingID);
		jsonObj.put("bookingId", String.valueOf(++bookingId));
		json.jsonWrite(jsonObj, "Taxi-"+taxiNo+".json");
		return bookingId;
	}
	
	@SuppressWarnings("unchecked")
	public String putCustomer(Customer customer) throws ManualException
	{
		customer.setCustomerId(customerId());
		String data = gson.toJson(customer);
		customerMap.put(String.valueOf(customerId), data);
		json.jsonWrite(customerMap, "CustomerDetails.json");
		return "CustomerId for "+customer.getName()+" : "+customerId;
	}
	
	public Customer getCustomer(String customerId) 
	{
		String data = (String) customerMap.get(customerId);
		
		Customer customer = gson.fromJson(data, Customer.class);
		
		return customer;
	}
	
	private JSONObject readTaxi(String taxiNo) throws ManualException
	{
		return json.jsonRead("Taxi-"+taxiNo+".json");
	}
	
	
	private JSONObject readCustomerDetails() throws ManualException
	{
		return json.jsonRead("CustomerDetails.json");
	}
	
	private JSONObject readKey() throws ManualException 
	{
		return json.jsonRead("KeyDetails.json");
	}
	
	public String load() throws ManualException
	{
		keyMap = readKey();
		
		if(keyMap!=null)
		{
			String no_Of_Taxis = (String) keyMap.get("No.Of_Taxis");
		
			noOfTaxis = Integer.valueOf(no_Of_Taxis);
			
			String customerID = (String) keyMap.get("customerId");
			
			customerId = Integer.valueOf(customerID);
			
			customerMap = readCustomerDetails();
			
			if(customerMap!=null)
			{
				return "Key Details Loaded\nCustomer Details Loaded";
			}
		}
		return "Load Failed";
	}
	
	@SuppressWarnings("unchecked")
	public String nFleet(int noOfTaxi_s) throws ManualException
	{
		keyMap.put("No.Of_Taxis", String.valueOf(noOfTaxi_s));
		json.jsonWrite(keyMap, "KeyDetails.json");
		Formatter fmt = new Formatter();
		fmt.format("%s %12s %10s %10s\n","Taxi Name", "Booking-Id","Earnings","Current-Location");
		for(int i = 1 ; i <= noOfTaxi_s ; i++)
		{
			JSONObject jsonTaxi = new JSONObject();
			jsonTaxi.put("Taxi-Name", "T_"+i);
			jsonTaxi.put("bookingId", "1");
			jsonTaxi.put("Earnings", "0");
			jsonTaxi.put("Current-Location", String.valueOf(Points.A));
			json.jsonWrite(jsonTaxi, "Taxi-"+i+".json");
			fmt.format("%3s %15s %8s %15s\n",i, "T"+i+"_"+jsonTaxi.get("bookingId"),jsonTaxi.get("Earnings"),jsonTaxi.get("Current-Location"));
		}
		return fmt+"Call Taxi Operator has a fleet of "+noOfTaxi_s+" cars";
	}
	
	
	public void ticketBooking(Booking booking)
	{
		booking.setTime(System.currentTimeMillis());
		
		
	}
	
	public String currentPlaceAvailableTaxis(String point) throws ManualException
	{
		List<String> list = new ArrayList<>();
		
		for(int i = 1 ; i <= noOfTaxis ; i++)
		{
			JSONObject json = readTaxi(String.valueOf(i));
			String location = (String) json.get("Current-Location");
			if(location.equals(point))
			{
				list.add((String) json.get("Taxi-Name"));
			}
		}
		int size = list.size();
		if(size==0)
		{
			return "Oops!No Taxi's Available";
		}
		return "Taxis available at current location : "+list;
	}
	
	
	
}
