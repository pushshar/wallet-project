package com.capgemini.wallet.testing;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.beans.Wallet;
import com.capgemini.wallet.bl.WalletService;
import com.capgemini.wallet.bl.WalletServiceImpl;
import com.capgemini.wallet.exception.AlreadyRegisteredMobileException;
import com.capgemini.wallet.exception.MobileNotFoundException;
import com.capgemini.wallet.exception.NegativeWithdrwalException;
import com.capgemini.wallet.repo.WalletRepo;
import com.capgemini.wallet.repo.WalletRepoImpl;

public class WalletTest {

	WalletRepo repo;
	WalletService service;
	
	@Before
	public void setUp() throws Exception {
		repo=new WalletRepoImpl();
		service=new WalletServiceImpl(repo);
	}

	@Test(expected=AlreadyRegisteredMobileException.class)
	public void WhenMobileAlreadyRegisteredSystemThrowException() throws AlreadyRegisteredMobileException {
	
		service.createAccount("raj","6789456123",new BigDecimal("600"));
		service.createAccount("raju","6789456123",new BigDecimal("6050"));
	}
	
	@Test
	public void WhenAccountSuccessfullyCreated() throws AlreadyRegisteredMobileException  {
		
		Customer customer=new Customer();
		Wallet wallet=new Wallet();
		
		customer.setMobileno("6123456789");
		customer.setName("raj");
		wallet.setBalance(new BigDecimal("600"));
		customer.setWallet(wallet);
		
		assertEquals(customer,service.createAccount("raj","6123456789",new BigDecimal("600")));
		
	}
	
	@Test(expected=MobileNotFoundException.class)
	public void WhenShowingDetailsMobileIsNotRegistered() throws  MobileNotFoundException {
	
		service.showBalance("6789451523");
		
	}
	
	@Test
	public void WhenBalanceSuccessfullyDisplayed() throws MobileNotFoundException, AlreadyRegisteredMobileException    {
		
		Customer customer=new Customer();
		Wallet wallet=new Wallet();
		
		customer.setMobileno("6123456789");
		customer.setName("raj");
		wallet.setBalance(new BigDecimal("600"));
		customer.setWallet(wallet);
		repo.save(customer);
		assertEquals(customer,service.showBalance("6123456789"));
		
	}
	
	@Test(expected=NegativeWithdrwalException.class)
	public void WhenAfterWithdrawalAmountBecomesLessThan0() throws NegativeWithdrwalException, MobileNotFoundException, AlreadyRegisteredMobileException    {
		
		service.createAccount("raj","6789456123",new BigDecimal("600"));
		service.createAccount("raju","6789456124",new BigDecimal("700"));
		service.fundTransfer("6789456123","6789456124", new BigDecimal("700"));
		
	}

	
	@Test(expected=MobileNotFoundException.class)
	public void DuringWithdrawingMobileNotRegisteredSystemThrowException() throws NegativeWithdrwalException, MobileNotFoundException, AlreadyRegisteredMobileException    {
		
		service.createAccount("raj","6789456123",new BigDecimal("600"));
		service.createAccount("raju","6789456124",new BigDecimal("700"));
		service.fundTransfer("6789456323","6789456124", new BigDecimal("300"));
		
	}
	
	@Test
	public void whenfundTransferSuccessfully() throws NegativeWithdrwalException, MobileNotFoundException, AlreadyRegisteredMobileException    {
		
		Customer customer1=new Customer();
		Wallet wallet1=new Wallet();
		Customer customer2=new Customer();
		Wallet wallet2=new Wallet();
		
		customer1.setMobileno("6123456789");
		customer1.setName("raj");
		wallet1.setBalance(new BigDecimal("600"));
		customer1.setWallet(wallet1);
		repo.save(customer1);
		
		customer2.setMobileno("6123456783");
		customer2.setName("raj");
		wallet2.setBalance(new BigDecimal("600"));
		customer2.setWallet(wallet2);
		repo.save(customer2);
		
		assertEquals(new BigDecimal("300"), service.fundTransfer("6123456789","6123456783", new BigDecimal("300")).getWallet().getBalance());
		assertEquals(new BigDecimal("900"), service.showBalance("6123456783").getWallet().getBalance());
		
	}
	
	@Test(expected=MobileNotFoundException.class)
	public void DuringFundTransferSourceMobileNotRegisteredSystemThrowException() throws NegativeWithdrwalException, MobileNotFoundException, AlreadyRegisteredMobileException    {
		
		service.createAccount("raj","6789456123",new BigDecimal("600"));
		service.createAccount("raju","6789456124",new BigDecimal("700"));
		service.fundTransfer("6789256123","6789456124", new BigDecimal("300"));
		
	}
	
	@Test(expected=MobileNotFoundException.class)
	public void DuringFundTransferTargetMobileNotRegisteredSystemThrowException() throws NegativeWithdrwalException, MobileNotFoundException, AlreadyRegisteredMobileException    {
		
		service.createAccount("raj","6789456123",new BigDecimal("600"));
		service.createAccount("raju","6789456124",new BigDecimal("700"));
		service.fundTransfer("6789256123","6789454124", new BigDecimal("300"));
		
	}
	
	@Test(expected=NegativeWithdrwalException.class)
	public void DuringFundTransferBalanceReachesToNegativeSystemThrowException() throws NegativeWithdrwalException, MobileNotFoundException, AlreadyRegisteredMobileException    {
		
		service.createAccount("raj","6789456123",new BigDecimal("600"));
		service.createAccount("raju","6789456124",new BigDecimal("700"));
		service.fundTransfer("6789456123","6789456124", new BigDecimal("700"));
		
	}
	
	@Test(expected=MobileNotFoundException.class)
	public void DuringDepositingAmountMobileIsNotRegisteredSystemThrowException() throws NegativeWithdrwalException, MobileNotFoundException, AlreadyRegisteredMobileException    {
		
		service.createAccount("raj","6789456123",new BigDecimal("600"));
		service.depositAmount("6789446123", new BigDecimal("700"));
		
	}
	
	@Test
	public void whenMoneyDepositedSuccessfully() throws NegativeWithdrwalException, MobileNotFoundException, AlreadyRegisteredMobileException    {
		
		Customer customer=new Customer();
		Wallet wallet=new Wallet();
		
		customer.setMobileno("6123456789");
		customer.setName("raj");
		wallet.setBalance(new BigDecimal("600"));
		customer.setWallet(wallet);
		repo.save(customer);
		assertEquals(customer,service.depositAmount("6123456789", new BigDecimal("600")));
		
	}
}

