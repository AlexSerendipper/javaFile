package service;

import domain.Architect;
import domain.Designer;
import domain.Employee;
import domain.Equipment;
import domain.Notebook;
import domain.Pc;
import domain.Printer;
import domain.Programmer;
import utility.Data;
import static utility.Data.EMPLOYEES;;

// 功能：负责将Data中的数据封装到Employee[]数组中，同时提供相关操作Employee[]的方法 
public class NameListService {
	private Employee[] employees;

	public NameListService() {
//1根据项目提供的Data类构建相应大小的employees数组
//2再根据Data类中的数据构建不同的对象，包括Employee、Programmer、Designer和Architect对象，
//3以及相关联的Equipment子类的对象，将对象存于数组中
		employees = new Employee[EMPLOYEES.length];
		for (int i = 0; i < EMPLOYEES.length; i++) {
			// 获取员工的类型
			int type = Integer.parseInt(EMPLOYEES[i][0]);
			// 获取employee的基本信息
			int id = Integer.parseInt(EMPLOYEES[i][1]);
			String name = EMPLOYEES[i][2];
			int age = Integer.parseInt(EMPLOYEES[i][3]);
			double salary = Integer.parseInt(EMPLOYEES[i][4]);
			int bonus; // 只声明，不处理，避免了有些员工没有该属性
			int stock;
			Equipment equipment;
			switch (type) {
			case Data.EMPLOYEE:
				employees[i] = new Employee(id, name, age, salary);
				break;
			case Data.PROGRAMMER:
				equipment = creatEquipment(i);
				employees[i] = new Programmer(id, name, age, salary, equipment);
				break;
			case Data.DESIGNER:
				equipment = creatEquipment(i);
				bonus = Integer.parseInt(EMPLOYEES[i][5]);
				employees[i] = new Designer(id, name, age, salary, equipment, bonus);
				break;
			case Data.ARCHITECT:
				equipment = creatEquipment(i);
				bonus = Integer.parseInt(EMPLOYEES[i][5]);
				stock = Integer.parseInt(EMPLOYEES[i][6]);
				employees[i] = new Architect(id, name, age, salary, equipment, bonus, stock);
				break;
			}
		}

	}

	/**
	 * 
	 * @Description 获取指定index员工的设备
	 * @author alex_serendipper
	 * @date 2022年11月4日下午3:00:49
	 * @param i
	 * @return
	 */
	public Equipment creatEquipment(int i) {
		int type = Integer.parseInt(Data.EQUIPMENTS[i][0]);
		String model = Data.EQUIPMENTS[i][1];
		String display = Data.EQUIPMENTS[i][2];

		switch (type) {
		case Data.PC:
			return new Pc(model, display);
			
		case Data.NOTEBOOK:
			double price = Double.parseDouble(Data.EQUIPMENTS[i][2]);
			return new Notebook(model, price);
			
		case Data.PRINTER:
			return new Printer(model, display);
			
		}
		return null;
	}

	public void NameListService() {

	}
    /**
     *  
     * @Description 返回所有员工
     * @author alex_serendipper
     * @date 2022年11月5日上午10:36:41
     * @return
     */
	public Employee[] getAllEmployees() {
		return employees;
	}
	
    /**
     * 
     * @Description 返回指定ID的员工
     * @author alex_serendipper
     * @date 2022年11月4日下午3:32:00
     * @param id
     * @return
     * @throws TeamException
     */
	public Employee getEmployee(int id) throws TeamException {
         for(int i = 0; i<employees.length;i++) {
        	 if(employees[i].getId() == id) {
        		 return employees[i];
        	 }
         }
         throw new TeamException("找不到指定的员工");
	}
}
