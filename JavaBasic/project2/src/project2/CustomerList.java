package project2;
//学习：项目2-用数组管理customer对象，提供相应的增删改查功能供customerview调用


public class CustomerList {
    private Customer[] customers;
    private int total = 0;  // 保存客户对象的数量
    // constructor
    public CustomerList(int totalCustomer) {  // 初始化数组长度
    	customers = new Customer[totalCustomer];
    }
    

    // method
    public boolean addCustomer(Customer customer) {
    	if(total < customers.length) {
    		customers[total++] = customer;
    		return true;
    	}
    	else {
    		return false;
    	}
    	
    	
    }
    
    public boolean replaceCustomer(int index, Customer cust) {
    	if(index < 0 || index > customers.length-1) {
    		return false;
    	}
    	else {
    		customers[index] = cust;
    		return true;
    	}
    }
    
    
    public boolean deleteCustomer(int index) {
    	if(index < 0 || index > customers.length-1) {
    		return false;
    	}
    	else {
    		// 难点
    		// 删除的是index的位置，不能直接将index位置置为null,因为数组是一个连续的空间
    		// 所以要建立一个循环，把index后的所有对象往前移动
    		for(int i=index;i<customers.length;i++) {
    			customers[index] = customers[index+1];
    		}
    		// 把最后一个元素置空
    		customers[customers.length-1]=null;
    		total--;
    		return true;
    	}
    }
    
    // 获取数组中的所有客户对象
    public Customer[] getAllCustomers() {
    	// 因为有些是null所以只取有用的
    	Customer[] custs = new Customer[total];
    	for(int i=0;i<total;i++) {
    		custs[i] = customers[i];
    	}
    	return custs;
    }
    
    // 获取数组中的指定的客户对象
    public Customer getCustomer(int index) {
    	if(index < total && index >= 0) {
    		return customers[index];
    	}
    	return null;
    }
    
    // 获取数组中的客户的数量  
    public int getTotal() {
    	return total;
    }

}
