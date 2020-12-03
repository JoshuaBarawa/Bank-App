package userdatabase;

public class LoginDetails {
	
	private int accountNumber;
	private int pinNumber;
	
	
	public LoginDetails(int accountNumber, int pinNumber) {
		super();
		this.accountNumber = accountNumber;
		this.pinNumber = pinNumber;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}
	
	

}
