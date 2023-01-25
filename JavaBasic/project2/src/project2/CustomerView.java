package project2;
//学习：项目2——负责菜单的显示和处理用户操作

public class CustomerView {
	// 创建最大十个对象的数组
	CustomerList customerList = new CustomerList(10);

	// constructor

	public CustomerView() { // 默认加一个人在里边
		Customer c = new Customer("hyq", '1', 21, "123123123", "123@qq.com");
		customerList.addCustomer(c);
	}

	// 显示主菜单
	public void enterMainMenu() {
		boolean flag = true;
		while (flag) {
			System.out.println("\n-------------------客户信息管理软件-------------------");
			System.out.println("                    1添加客户 ");
			System.out.println("                    2修改客户 ");
			System.out.println("                    3删除客户 ");
			System.out.println("                    4客户列表 ");
			System.out.println("                    5退出客户 ");
			System.out.println("                 请选择(1-5):");
			char menu = CMUtility.readMenuSelection();
			switch (menu) {
			case '1':
				addNewCustomer();
				break;
			case '2':
				modifyCustomer();
				break;
			case '3':
				deleteCustomer();
				break;
			case '4':
				listAllCustomers();
				break;
			case '5':
				System.out.println("确认是否退出(Y/N)");
				char isExit = CMUtility.readConfirmSelection();
				if (isExit == 'Y') {
					flag = false;
				} else {
					break;
				}
			}
		}
	}

	// 分别完成“添加客户”、“修改客户”、“删除客户”和“客户列表”等各菜单功能。这四个方法仅供enterMainMenu()方法调用。
	private void addNewCustomer() {
		System.out.println("-------------------------添加客户------------------------");
		System.out.println("姓名： ");
		String name = CMUtility.readString(10);
		System.out.println("性别： ");
		char gender = CMUtility.readChar();
		System.out.println("年龄： ");
		int age = CMUtility.readInt();
		System.out.println("电话： ");
		String phone = CMUtility.readString(13);
		System.out.println("邮箱： ");
		String email = CMUtility.readString(30);
		Customer cust = new Customer(name, gender, age, phone, email);
		boolean isSuccess = customerList.addCustomer(cust);
		if (isSuccess) {
			System.out.println("-------------------------添加客户成功------------------------");
		} else {
			System.out.println("-------------------------添加客户失败，目录已满------------------------");
		}
	}

	private void modifyCustomer() {
		System.out.println("-------------------------修改客户------------------------");
		for (;;) {
			System.out.println("请输入要修改的用户编号(-1)退出");
			int n = CMUtility.readInt();
			if (n == -1) {
				return; // 退出当前方法
			} else if (customerList.getCustomer(n - 1) == null) {
				System.out.println("无法找到指定的客户");
			} else {
				String oldName = customerList.getCustomer(n - 1).getName();
				System.out.println("姓名" + "(" + oldName + "): ");
				String name = CMUtility.readString(10, oldName);
				char oldGender = customerList.getCustomer(n - 1).getGender();
				System.out.println("性别" + "(" + oldGender + "): ");
				char gender = CMUtility.readChar(oldGender);
				int oldAge = customerList.getCustomer(n - 1).getAge();
				System.out.println("年龄" + "(" + oldAge + "): ");
				int age = CMUtility.readInt(oldAge);
				String oldPhone = customerList.getCustomer(n - 1).getPhone();
				System.out.println("电话" + "(" + oldPhone + "): ");
				String phone = CMUtility.readString(13, oldPhone);
				String oldEmail = customerList.getCustomer(n - 1).getEmail();
				System.out.println("邮箱" + "(" + oldEmail + "): ");
				String email = CMUtility.readString(30, oldEmail);
				Customer cust1 = new Customer(name, gender, age, phone, email);
				Customer cust = new Customer();
				boolean isReplaced = customerList.replaceCustomer(n - 1, cust1);
				if (isReplaced) {
					System.out.println("-------------------------修改客户完成------------------------");
				} else {
					System.out.println("修改用户信息失败");
				}
				break;
			}
		}
	}

	private void deleteCustomer() {
		System.out.println("-------------------------删除客户------------------------");
		int n;
		for (;;) {
			System.out.println("请输入要删除的用户编号(-1)退出");
			n = CMUtility.readInt();
			if (n == -1) {
				return; // 退出当前方法
			} else if (customerList.getCustomer(n - 1) == null) {
				System.out.println("无法找到指定的客户");
			} else {
				break;
			}
		}
		
		System.out.println("确认是否删除(Y/N)  " + customerList.getCustomer(n - 1).getName());
		char isDelete = CMUtility.readConfirmSelection();
		if (isDelete == 'Y') {
			boolean isSuccess = customerList.deleteCustomer(n - 1);
			if (isSuccess) {
				System.out.println("-------------------------删除成功------------------------");
			}else {
				System.out.println("-------------------------删除失败------------------------");
			}
		} else {
			return;
		}
	}

	private void listAllCustomers() {
		System.out.println("-------------------------客户列表------------------------");
		int total = customerList.getTotal();
		if (total == 0) {
			System.out.println("没有客户记录!");
		} else {
			System.out.println("编号\t姓名\t性别\t年龄\t电话\t\t邮箱\t");
			Customer[] custs = customerList.getAllCustomers();
			for (int i = 0; i < total; i++) {
				System.out.println((i + 1) + "\t" + custs[i].getName() + "\t" + custs[i].getGender() + "\t"
						+ custs[i].getAge() + "\t" + custs[i].getPhone() + "\t" + custs[i].getEmail());
			}
		}

		System.out.println("-------------------------客户列表完成------------------------");
	}

	public static void main(String[] args) {
		CustomerView view = new CustomerView();
		view.enterMainMenu();
	}

}
