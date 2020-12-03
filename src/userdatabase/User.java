package userdatabase;
import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5013944385371546451L;
	private String userName;
	private String gender;
	private int idNumber;
	private int accountNumber;
	private int pinNumber;
	private int accountBalance;
	private int withDraw;
	private int depostCash;
	
	public User(String userName, String gender,int idNumber, int accountNumber, int pinNumber, int accountBalance){
		
		this.userName = userName;
		this.gender = gender;
		this.idNumber = idNumber;
		this.accountNumber = accountNumber;
		this.pinNumber = pinNumber;
		this.accountBalance = accountBalance;
	}
	
	public User() {
		
	}

	
	public int getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;

	}

	public int getWithDraw() {
		return withDraw;
	}

	public void setWithDraw(int withDraw) {
		this.withDraw = withDraw;
	}

	public double getDepostCash() {
		return depostCash;
	}

	public void setDepostCash(int depostCash) {
		this.depostCash = depostCash;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}

	public int calcCheckingWithDraw(int amount) {
		accountBalance = (accountBalance - amount);
		return accountBalance;
	}

	public int calcDepositCash(int amount) {
		accountBalance = (accountBalance + amount);
		return accountBalance;
	}

	public String toString() {
		return userName + " " + gender + " " + idNumber + " " + accountNumber + " " + pinNumber + " " + accountBalance;
	}

}
