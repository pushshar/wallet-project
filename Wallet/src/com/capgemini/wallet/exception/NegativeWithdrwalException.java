package com.capgemini.wallet.exception;

import java.math.BigDecimal;

public class NegativeWithdrwalException extends Exception {

	BigDecimal balance;
	public NegativeWithdrwalException(BigDecimal balance) {
			
		this.balance=balance;
			
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Your Account has "+balance+"\n Please withdraw respectively\n";
	}
	
	

}
