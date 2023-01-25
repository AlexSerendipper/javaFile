package view;

import domain.Employee;
import domain.Programmer;
import service.NameListService;
import service.TeamException;
import service.TeamService;
import utility.TSUtility;

public class TeamView {
	private NameListService listSvc = new NameListService();
	private TeamService teamSvc = new TeamService();

	public void enterMainmenu() {
		boolean flag = true;
		char menu=0;
		while (flag) {
			if (menu != '1') {  // 除非选1，否则不呈现员工列表
				listAllEmployees();
			}

			System.out.print("1-团队列表  2-添加团队成员  3-删除团队成员 4-退出   请选择(1-4)：");
			menu = TSUtility.readMenuSelection();
			switch (menu) {
			case '1':
				getTeam();
				break;
			case '2':
				addMember();
				break;
			case '3':
				deleteMember();
				break;
			case '4':
				System.out.println("请确认是否退出(y/n)");
				char isExit = TSUtility.readConfirmSelection();
				if (isExit == 'Y') {
					flag = false;
				}
				break;
			}
		}
	}

	/**
	 * 
	 * @Description 显示所有的员工信息
	 * @author alex_serendipper
	 * @date 2022年11月5日上午10:33:59
	 */
	private void listAllEmployees() {
		System.out.println("\n-------------------------------开发团队调度软件--------------------------------\n");
		Employee[] employees = listSvc.getAllEmployees();
		if (employees.length == 0) {
			System.out.println("没有客户记录！");
		} else {
			System.out.println("ID\t姓名\t年龄\t工资\t职位\t状态\t奖金\t股票\t领用设备");
		}

		for (int i = 0; i < employees.length; i++) {
			System.out.println(employees[i]);
		}
		System.out.println("-------------------------------------------------------------------------------");
	}

	/**
	 * 
	 * @Description 查看当前团队列表
	 * @author alex_serendipper
	 * @date 2022年11月5日上午10:41:33
	 */
	private void getTeam() {
	   System.out.println("\n--------------------团队成员列表---------------------\n");
	   Programmer[] team = teamSvc.getTeam();
       if(team==null || team.length == 0) {
    	   System.out.println("开发团队目前没有成员！");
       }else {
    	   System.out.println("TID/ID\t姓名\t年龄\t工资\t职位\t奖金\t股票");
       }
       for(int i=0;i<team.length;i++) {
    	   int TID = team[i].getMemberId();
    	   int id = team[i].getId();
    	   System.out.println(team[i].getTeamDetail());
       }
		System.out.println("-----------------------------------------------------");
	}

	private void addMember() {
		System.out.println("---------------------添加成员---------------------");
		System.out.print("请输入要添加的员工ID：");
		try {
			int id = TSUtility.readInt();
			teamSvc.addMember(listSvc.getEmployee(id));
			System.out.println("添加成功");
		} catch (TeamException e) {
			// TODO Auto-generated catch block
			System.out.println("添加失败，原因：" + e.getMessage());
		}
		// 按回车键继续
		TSUtility.readReturn();
		
	}

	private void deleteMember() {
		System.out.println("---------------------删除成员---------------------");
		System.out.print("请输入要删除员工的TID：");
		int TID = TSUtility.readInt();
		System.out.print("确认是否删除(Y/N)：");
		char yn = TSUtility.readConfirmSelection();
		if(yn == 'N') {
			return;
		}
		
		try {
			teamSvc.removeMember(TID);
			System.out.println("删除成功");
		} catch (TeamException e) {
			// TODO Auto-generated catch block
			System.out.println("删除失败，原因：" + e.getMessage());
		}
		
		// 按回车键继续...
		TSUtility.readReturn();
	}

	public static void main(String[] args) {
		TeamView view = new TeamView();
		view.enterMainmenu();
		
	}
}
