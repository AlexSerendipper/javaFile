package useoop;
//学习：实验3继承&super.pdf

public class experiment3 {
	public static void main(String[] args) {
		Account1 acc = new Account1(1122, 20000, 0.045);
		acc.withdraw(30000);
		acc.withdraw(2500);
		acc.deposit(3000);
		System.out.println("您的账户余额为:" + acc.getBalance());
		System.out.println("月利率为:" + acc.getMonthlyInterest());
		System.out.println("------------------------------");
		Account1 acc1 = new CheckAccount(1122, 20000, 0.045,5000);
		acc1.withdraw(5000);
		acc1.withdraw(18000);
		acc1.withdraw(3000);

	}
}

class Account1 {
	// 属性
	private int id;
	private double balance;
	private double annualInterestRate;

	// 构造器
	public Account1() {
		
	}
	public Account1(int id, double balance, double annualInterestRate) {
		this.id = id;
		this.balance = balance;
		this.annualInterestRate = annualInterestRate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	// 方法
	public double getMonthlyInterest() {
		return annualInterestRate / 12;
	}

	public void withdraw(double amount) {
		if (amount < balance) {
			balance -= amount;
			System.out.println("取款成功，您的账户余额为:" + this.getBalance());
		} else {
			System.out.println("余额不足");
		}
	}

	public void deposit(double amount) {
		balance += amount;
		System.out.println("存款成功，您的账户余额为:" + this.getBalance());
	}
}

class CheckAccount extends Account1{
	double overdraft;  // 可透支额度
	
	public CheckAccount(int id, double balance, double annualInterestRate,double overdraft) {
		super(id,balance,annualInterestRate);
		this.overdraft = overdraft;
	}
	
	public void withdraw(double amount) {
		double balance = this.getBalance();  // 此处可以把blance属性改为Protected！！！
		if(amount <= balance) {
			balance -= amount;
			this.setBalance(balance);
			System.out.println("取款成功，您的账户余额为:" + this.getBalance() + "剩余可用额度为：" + overdraft);	
            // 方法二
            // super.withdraw(amount);
		}else {
			double needOverdraft = amount - balance;
			if(needOverdraft<overdraft) {
				overdraft -= needOverdraft;
				this.setBalance(0);
				System.out.println("取款成功，您的账户余额为:" + this.getBalance() + "剩余可用额度为：" + overdraft);
			}else {
				System.out.println("超过透支额度");
			}
		}
	}
}