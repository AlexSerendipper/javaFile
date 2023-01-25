package useoop;
// 学习：实验1
public class experiment1 {
	// 属性
	private int id;  
	private double balance;  // 余额
	private double annualInterestRate;  // 年利率
	// 构造器
	public experiment1(int id, double balance, double annualInterestRate ){
	    this.id = id;
	    this.balance = balance;
	    this.annualInterestRate = annualInterestRate;
	}
	// 方法
	public int getId() {
		return this.id;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public double getAnnualInterestRate()
	{
		return annualInterestRate;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void setAnnualInterestRate(double annualInterestRate){
		this.annualInterestRate = annualInterestRate;
	}
	
	public void withdraw (double amount)//取钱
	{
		if(this.balance - amount<0) {
			System.out.println("余额不足，取钱失败");
		}else {
			this.balance = this.balance - amount;
			System.out.println("成功取出： " + amount);
		}
	}
	
	public void deposit (double amount)//存钱
	{
		this.balance = this.balance + amount;
		System.out.println("成功存入： " + amount);
	}
	
	public static void main(String[] args) {
		// 测试
		Customer cust = new Customer("Jane","Smith");
		experiment1 acc = new experiment1(1000, 2000, 0.0123);
		cust.setAccount(acc);
		acc.deposit(100);
		acc.withdraw(960);
		acc.withdraw(2000);  // 内存图如pdf中所示
        System.out.println("customer[" + cust.getLastName() +","+ cust.getFirstName() +
        		"]has a account:id is " + cust.getAccount().getId()+",annualInterestRate is " + 
        		cust.getAccount().getAnnualInterestRate()*100 + "% ,balance is " + 
        		cust.getAccount().getBalance());  
    }
}


class Customer{
	// 属性
	private String firstName;
	private String lastName;
	private experiment1 account; // （这个也是一个类，所以肯定也能作为属性!!）
	
	// 构造器
	public Customer(String f,String l) {
		firstName = f;
		lastName = l;
	}
	// 方法
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	
	public experiment1 getAccount() {
		return account;
	}
	
	public void setAccount(experiment1 account) {
		this.account = account;
	}
	

}
