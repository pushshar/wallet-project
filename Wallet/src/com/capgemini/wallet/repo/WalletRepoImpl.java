package com.capgemini.wallet.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.exception.AlreadyRegisteredMobileException;
import com.capgemini.wallet.exception.MobileNotFoundException;

public class WalletRepoImpl implements WalletRepo {

	
	Map<String, Customer> map=new HashMap<>();

	@Override
	public boolean save(Customer customer) throws AlreadyRegisteredMobileException  {

		if(map.containsKey(customer.getMobileno()))
		{
			throw new AlreadyRegisteredMobileException();
		}
		
		map.put(customer.getMobileno(), customer);
		
		
		
		return true;
	}

	@Override
	public Customer findOne(String mobileno) throws MobileNotFoundException {
		// TODO Auto-generated method stub
		
		if(map.containsKey(mobileno))
		{
			return map.get(mobileno);
		}
		throw new MobileNotFoundException();
		
		
	}
	
	

}
