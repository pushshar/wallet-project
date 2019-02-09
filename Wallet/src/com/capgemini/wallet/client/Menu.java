package com.capgemini.wallet.client;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.bl.WalletService;
import com.capgemini.wallet.bl.WalletServiceImpl;
import com.capgemini.wallet.exception.AlreadyRegisteredMobileException;
import com.capgemini.wallet.exception.MobileNotFoundException;
import com.capgemini.wallet.exception.NegativeWithdrwalException;
import com.capgemini.wallet.repo.WalletRepo;
import com.capgemini.wallet.repo.WalletRepoImpl;

public class Menu {

	static WalletRepo repo = new WalletRepoImpl();
	static WalletService service = new WalletServiceImpl(repo);
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Welcome to the WELLNESS WALLET");
		
		while (true) {
			System.out.println("1- Create Account");
			System.out.println("2- Show Balance");

			System.out.println("3- Fund transfer");
			System.out.println("4- Deposit Amount");
			System.out.println("5 - Withdraw Amount");
			
			System.out.println("Any other- to exit\n");

			int n = sc.nextInt();
			sc.nextLine();
			switch (n) {
			case 1:
				createAccount();
				break;

			case 2:
				showBalance();
				break;

			case 3:
				fundTransfer();
				break;

			case 4:
				depositAmount();
				break;

			case 5:
				withdrawAmount();
				break;
				
			
			default:
				System.out.println("Thank You and Take Care");
				break;
			}
		}

	}

	private static void transactionList() {
		
		String mobile;
		String balance;
		Customer customer = new Customer();

		System.out.println("Enter mobile");
		mobile = sc.next();
		mobile = validMobile(mobile);
		
		
		
		
		
	}

	private static void withdrawAmount() {

		String mobile;
		String balance;
		Customer customer = new Customer();
		
		
		System.out.println("Enter mobile");
		mobile = sc.next();
		mobile = validMobile(mobile);
		System.out.println("Enter Balance");
		balance = sc.next();
		balance = validAmount(balance);
		BigDecimal bd = new BigDecimal(balance);
		
		try {
			customer = service.withdrawAmount(mobile, bd);
			
			System.out.println("Your balance now:" + customer.getWallet().getBalance());
		} catch (MobileNotFoundException mnf) {
			System.out.println(mnf.getMessage());
		} catch (NegativeWithdrwalException nw) {

			System.out.println(nw.getMessage());
		}
		
		

	}

	private static void depositAmount() {

		String mobile;
		String balance;
		Customer customer = new Customer();

		System.out.println("Enter mobile");
		mobile = sc.next();
		mobile = validMobile(mobile);
		System.out.println("Enter Balance");
		balance = sc.next();
		balance = validAmount(balance);
		BigDecimal bd = new BigDecimal(balance);
		try {
			customer = service.depositAmount(mobile, bd);
			System.out.println("Your balance now:" + customer.getWallet().getBalance());
		} catch (MobileNotFoundException mnf) {
			System.out.println(mnf.getMessage());

		}
	}

	private static void fundTransfer() {
		// TODO Auto-generated method stub
		String source;
		String target;
		String amount;
		Customer customer = new Customer();
		System.out.println("Enter your Mobile number");
		source = sc.next();
		source = validMobile(source);
		System.out.println("Enter target Mobile number");
		target = sc.next();
		target = validMobile(target);
		System.out.println("Enter Balance");
		amount = sc.next();
		amount = validAmount(amount);

		BigDecimal bd = new BigDecimal(amount);

		try {
			customer = service.fundTransfer(source, target, bd);
			System.out.println(
					"Your Updated balance is:" + customer.getWallet().getBalance() + "\n " + amount + "transfer done");
		} catch (MobileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (NegativeWithdrwalException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

	private static void showBalance() {

		Customer customer = new Customer();
		String mobile;
		System.out.println("Enter your Mobile number\n");
		mobile = sc.next();
		mobile = validMobile(mobile);
		try {

			customer = service.showBalance(mobile);

		}

		catch (MobileNotFoundException mfs) {
			System.out.println(mfs.getMessage());
		}
		System.out.println(customer);

	}

	private static void createAccount() {
		String name;
		String mobile;
		String balance;
		Customer customer = new Customer();
		System.out.println("Enter your name");
		name = sc.nextLine();

		System.out.println("Enter mobile");
		mobile = sc.next();
		mobile = validMobile(mobile);
		System.out.println("Enter Balance");
		balance = sc.next();
		balance = validAmount(balance);
		boolean bool1 = NotNullNameMobileBalance(name, mobile, balance);

		if (bool1 == true) {
			BigDecimal bd = new BigDecimal(balance);

			try {
				customer = service.createAccount(name, mobile, bd);
			} catch (AlreadyRegisteredMobileException arm) {
				System.out.println(arm.getMessage());
			}
			System.out.println("Successfully created\n");
			System.out.println(customer);
		}
	}

	private static boolean NotNullNameMobileBalance(String name, String mobile, String balance) {
		while (name == null || mobile == null || balance == null) {
			if (name == null) {
				System.out.println("Enter your valid name");
				name = sc.nextLine();
			} else if (mobile == null) {
				System.out.println("Enter valid mobile");
				mobile = sc.next();
			} else if (balance == null) {
				System.out.println("Enter Balance");
				balance = sc.next();
			}
		}
		return true;
	}

	private static String validMobile(String mobile) {
		Pattern p = Pattern.compile("[6-9][0-9]{9}");
		Matcher m = p.matcher(mobile);
		
		while (m.matches() != true) {
			System.out.println("Enter valid mobile");
			mobile = sc.next();
			m = p.matcher(mobile);
		}
		return mobile;
	}

	private static String validAmount(String amount) {
		while (Long.parseLong(amount) <= 0) {
			System.out.println("Enter valid Balance");
			amount = sc.next();
		}
		return amount;
	}
}
