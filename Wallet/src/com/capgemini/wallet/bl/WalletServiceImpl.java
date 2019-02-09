package com.capgemini.wallet.bl;

import java.math.BigDecimal;
import java.util.LinkedHashSet;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.beans.Wallet;
import com.capgemini.wallet.exception.AlreadyRegisteredMobileException;
import com.capgemini.wallet.exception.MobileNotFoundException;
import com.capgemini.wallet.exception.NegativeWithdrwalException;
import com.capgemini.wallet.repo.WalletRepo;

public class WalletServiceImpl implements WalletService {
	
	WalletRepo repo;
	static int id=101;
	
	
	@Override
	public Customer createAccount(String name, String mobileno, BigDecimal amount) throws AlreadyRegisteredMobileException {
		// TODO Auto-generated method stub
		Customer enroll=new Customer();
		enroll.setName(name);
		enroll.setMobileno(mobileno);
		Wallet wallet=new Wallet();
		wallet.setBalance(amount);
		enroll.setWallet(wallet);
		if(repo.save(enroll)!=true)
		{
			return null;
		}
		
		return enroll;
	}

	
	public WalletServiceImpl(WalletRepo repo) {
		super();
		this.repo = repo;
	}

	@Override
	public Customer showBalance(String mobileno) throws MobileNotFoundException {
		// TODO Auto-generated method stub
		Customer enroll=new Customer();
		enroll=repo.findOne(mobileno);
		return enroll;
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws MobileNotFoundException, NegativeWithdrwalException {
		Customer enroll1=new Customer();
		Customer enroll2=new Customer();

		enroll1=withdrawAmount(sourceMobileNo, amount);
		enroll2=depositAmount(targetMobileNo, amount);
		
		
		return enroll1;
	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws MobileNotFoundException {
		
		Customer enroll=new Customer();
		Wallet wallet=new Wallet();
		enroll=repo.findOne(mobileNo);
		
		wallet.setBalance(enroll.getWallet().getBalance().add(amount));
		enroll.setWallet(wallet);
		return enroll;
		
	}

	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws MobileNotFoundException, NegativeWithdrwalException {
		Customer enroll=new Customer();
		Wallet wallet=new Wallet();
		enroll=repo.findOne(mobileNo);

		if((enroll.getWallet().getBalance()).compareTo(amount)>0)
		{

			wallet.setBalance(enroll.getWallet().getBalance().subtract(amount));
			enroll.setWallet(wallet);
			return enroll;
		}
		throw new NegativeWithdrwalException(enroll.getWallet().getBalance());
		

		
	}
	

}
