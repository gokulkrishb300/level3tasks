package runner;

import inputcenter.InputCenter;

import manualexception.ManualException;
import accountdeclare.Account;
import api.AtmAPI;

public class Runner {

	AtmAPI api = new AtmAPI();
	
	InputCenter input = new InputCenter();
	
	private static void menu()
	{
		System.out.println();
		System.out.println("1) Put Customer Detail");
		System.out.println("2) Read Customer General");
		System.out.println("3) Read Key");
		System.out.println("4) Tabular Customer Details");
		System.out.println();
	}
	
	private void customerDetail()
	{
		Account account = new Account();
		account.setAccountHolder(input.getString("Account Holder Name : "));
		account.setPinNumber(input.getInt("Account Pin No. "));
		account.setBalance(input.getInt("Balance : "));
		
		try
		{
		System.out.println(api.customerDetails(account));
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void readCustomer()
	{
		try
		{
			System.out.println(api.readCustomerDetails());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void readKey() throws ManualException
	{
		try
		{
			System.out.println(api.readKey());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void load()
	{
		try
		{
			System.out.println(api.load());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private void tabularCustomerDetails()
	{
		try
		{
			System.out.println(api.customerTable());
		}
		catch(ManualException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) throws ManualException
	{
		InputCenter input = new InputCenter();
		Runner run = new Runner();
		run.load();
		boolean flag = true;
		
		while(flag)
		{
			menu();
			int choice = input.getInt("");
			
			switch(choice)
			{
			case 0:
				flag = false;
				System.out.println("Runner Terminated");
				break;
			case 1:
				run.customerDetail();
				break;
			case 2:
				run.readCustomer();
				break;
			case 3:
				run.readKey();
				break;

			case 4:
				run.tabularCustomerDetails();
				break;
			}
		}
	}
}
