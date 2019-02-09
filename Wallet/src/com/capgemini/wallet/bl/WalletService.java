package com.capgemini.wallet.bl;

import java.math.BigDecimal;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.exception.AlreadyRegisteredMobileException;
import com.capgemini.wallet.exception.MobileNotFoundException;
import com.capgemini.wallet.exception.NegativeWithdrwalException;

public interface WalletService {
	
	public Customer createAccount(String name, String mobileno, BigDecimal amount) throws AlreadyRegisteredMobileException;
	public Customer showBalance(String mobileno) throws MobileNotFoundException;
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo,BigDecimal amount) throws MobileNotFoundException, NegativeWithdrwalException;
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws MobileNotFoundException;
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws MobileNotFoundException, NegativeWithdrwalException;
	

}
