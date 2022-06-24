package api;
import java.util.Formatter;

import org.json.simple.JSONObject;

import com.google.gson.Gson;

import accountdeclare.Account;
import json.Json;
import manualexception.ManualException;

public class AtmAPI {
	
	private int accNo = 100;
	
	JSONObject accountMap = new JSONObject();
	JSONObject keyMap = new JSONObject();
	JSONObject bankATM = new JSONObject();
	
	Gson gson = new Gson();
	Json json = new Json();
	
	@SuppressWarnings("unchecked")
	
	private int accountNo() throws ManualException
	{
		keyMap.put("Account.No",String.valueOf(++accNo));
		json.jsonWrite(keyMap,"KeyMap.json");
		return accNo;
	}
	

//	private void createCustomerFile(int accNo) throws ManualException
//	{
//		try(FileWriter writer = new FileWriter(accNo+".txt",true);)
//		{
//			String data = (String)accountMap.get(accNo);
//			
//			Account account = gson.fromJson(data, Account.class);
//			
//			writer.write(account.getAccNo());
//			writer.write(account.getPinNumber());
//			writer.write(account.getAccountHolder());
//			writer.write(account.getBalance());
//		}
//		catch(IOException e)
//		{
//			throw new ManualException("File Writing Failed for "+accNo+".txt");
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public String customerDetails(Account account) throws ManualException
	{
		int accountNo = accountNo();
		
		account.setAccNo(accountNo);
		
		String data = gson.toJson(account);
		
		String accNo = String.valueOf(accountNo);
		
//		JSONObject tempMap = new JSONObject();
//		
//		tempMap.put(accNo,data);
		
		accountMap.put(accNo, data);
		
//		json.jsonWrite(tempMap, accountNo+".json");
		
		json.jsonWrite(accountMap, "CustomerDetails.json");
		
		return "\nAccount created for "+account.getAccountHolder();
	}
	
	public JSONObject readCustomerDetails() throws ManualException
	{
		return json.jsonRead("CustomerDetails.json");
	}
	
	public JSONObject readKey() throws ManualException
	{
		return json.jsonRead("KeyMap.json");
	}
	public String load() throws ManualException
	{
		
		keyMap =  readKey();
		
		if(keyMap!=null)
		{
			String data = (String) keyMap.get("Account.No");
			accNo = Integer.valueOf(data);
			
			accountMap = readCustomerDetails();
			
			if(accountMap !=null) {
				return "Load Success";
			}
		}
		
		return "Load Failed";
	
	}
	
	@SuppressWarnings("unchecked")
	public void addATMBalance(String noOfTwos, String noOfFives, String noOfHuns) throws ManualException
	{
		bankATM.put("2000", noOfTwos);
		bankATM.put("500", noOfFives);
		bankATM.put("100", noOfHuns);
		json.jsonWrite(bankATM, "ATM.json");
	}
	
	public Formatter customerTable() throws ManualException {
		
		Formatter fmt = new Formatter();
		fmt.format("%s %15s %15s %15s\n","Acc No","Account Holder","Pin Number","Account Balance");
		for (int i = 101; i <= accNo; i++) {
			String data = (String) accountMap.get(String.valueOf(i));
			Account acc = gson.fromJson(data, Account.class);
			fmt.format("%s %15s %15s %15s\n", acc.getAccNo(), acc.getAccountHolder(), acc.getPinNumber(),acc.getBalance());
			}
		return fmt;
	}

}
