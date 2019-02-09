package com.capgemini.wallet.repo;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.exception.AlreadyRegisteredMobileException;
import com.capgemini.wallet.exception.MobileNotFoundException;

public interface WalletRepo {
	
	public boolean save(Customer customer) throws AlreadyRegisteredMobileException;
	public Customer findOne(String mobileno) throws  MobileNotFoundException;
	
	

}
