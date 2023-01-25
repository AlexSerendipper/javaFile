package useoop;
// 学习：实验2

class experiment2 {
	public static void main(String[] args) {
		Bank b = new Bank();
		b.addCustomer("Jane", "Smith");
		// ✔✔✔连续操作
		b.getCustomer(0).setAccount(new Account(2000));
		b.getCustomer(0).getAccount().withdraw(100);
		System.out.println("客户：" + b.getCustomer(0).getFirstName() +" 余额为："+  b.getCustomer(0).getAccount().getBalance());
		System.out.println("__________________________");
		b.addCustomer("huang", "yuqi");
		System.out.println("银行现在有的客户为：" + b.numberOfCustomer);	
   }	
}


class Account{
	private double balance;
	public Account(double init_balance) {
		balance = init_balance;
	}
	public double getBalance() {
		return balance;
	}
	
	public void withdraw (double amt)//取钱
	{
		if(this.balance - amt<0) {
			System.out.println("余额不足，取钱失败");
		}else {
			this.balance = this.balance - amt;
			System.out.println("成功取出： " + amt);
		}
	}
	
	public void deposit (double amt)//存钱
	{
		this.balance = this.balance + amt;
		System.out.println("成功存入： " + amt);
	}	
}

class Customer1{
	// 属性
	private String firstName;
	private String lastName;
	private Account account; // ✔✔✔（这个也是一个类，所以肯定也能作为属性!!） 关联~
	
	// 构造器
	public Customer1(String f,String l) {
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
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
}


class Bank{
	private Customer1[] customers;  // 对象数组
	int numberOfCustomer;
	
	public Bank() {
		customers = new Customer1[10];
		numberOfCustomer = 0;
	}
	
	public void addCustomer(String f, String l) {
		Customer1 cust = new Customer1(f,l);  // 所以创建的对象可以同名？
		customers[numberOfCustomer] = cust;
		numberOfCustomer++;
	}
	
	public int geNumberOfCustomer() {
		return numberOfCustomer; 
	}
	
	public Customer1 getCustomer(int index) {
		if(index >= 0 && index<numberOfCustomer){
			return customers[index];
		}
		return null;  // 没有return null可能就没有返回值，所以报错
	}
}
