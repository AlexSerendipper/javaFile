package service;

import domain.Architect;
import domain.Designer;
import domain.Employee;
import domain.Programmer;

// 功能：关于开发团队成员的管理：添加、删除等。
public class TeamService {
	private static int counter = 1; // 主要就是给memberId赋值
	private final int MAX_MEMBER = 5;
	private Programmer[] team = new Programmer[MAX_MEMBER];
	private int total = 0; // 记录开发团队的实际人数

	public TeamService() {
		super();
	}

	/**
	 * 
	 * @Description 获取开发团队的实际人数
	 * @author alex_serendipper
	 * @date 2022年11月4日下午8:35:47
	 * @return
	 */
	public Programmer[] getTeam() {
		Programmer[] team = new Programmer[total];
		for (int i = 0; i < team.length; i++) {
			team[i] = this.team[i];
		}
		return team; // 不能直接返回team，应该是有几个成员返回几个，所以新造了数组
	}

	public void addMember(Employee e) throws TeamException{
//		成员已满，无法添加
//		该成员不是开发人员，无法添加
//		该员工已在本开发团队中
//		该员工已是某团队成员 
//		该员正在休假，无法添加
//		团队中至多只能有一名架构师
//		团队中至多只能有两名设计师
//		团队中至多只能有三名程序员

		if (total >= MAX_MEMBER) {
			throw new TeamException("成员已满，无法添加");
		}
		if (!(e instanceof Programmer)) {
			throw new TeamException("该成员不是开发人员，无法添加");
		}
		if (isExist(e)) {
			throw new TeamException("该员工已在本开发团队中");
		}

		// 流程能走到这，肯定可以转
		Programmer p = (Programmer) e;
		if ("BUSY".equals(p.getStatus().getNAME())) {
			throw new TeamException("该员工已是某团队成员");
		} else if ("VOCATION".equals(p.getStatus().getNAME())) {
			throw new TeamException("该员正在休假，无法添加");
		}

		// 获取team中已有架构师、设计师、程序员的个数
		int numOfArch = 0;
		int numOfDesi = 0;
		int numOfProg = 0;
		for (int i = 0; i < team.length; i++) {
			if (team[i] instanceof Architect) {
				numOfArch++;
			} else if (team[i] instanceof Designer) {
				numOfDesi++;
			} else if (team[i] instanceof Programmer) {
				numOfProg++;
			}
		}

		// 判断新入成员是否符合要求,这样写有异常，因为第一项条件不满足会执行下面的代码
		// 比如有两个设计师，来了个架构师，会报错"团队中至多只能有两名设计师"
//		if (p instanceof Architect && numOfArch >= 1) {
//			throw new TeamException("团队中至多只能有一名架构师");
//		} else if (p instanceof Designer && numOfDesi >= 2) {
//			throw new TeamException("团队中至多只能有两名设计师");
//		} else if (p instanceof Programmer && numOfProg >= 3) {
//			throw new TeamException("团队中至多只能有三名程序员");
//		}

		if (p instanceof Architect) {
			if (numOfArch >= 1) { // 这样写，进到这一层以后，下面的else if就不会再执行了
				throw new TeamException("团队中至多只能有一名架构师");
			}
		} else if (p instanceof Designer) {
			if (numOfDesi >= 2) {
				throw new TeamException("团队中至多只能有两名设计师");
			}
		} else if (p instanceof Programmer) {
			if (numOfProg >= 3) {
				throw new TeamException("团队中至多只能有三名程序员");
			}
		}
		// 无异常，p的属性赋值
		p.setStatus(Status.BUSY);
		p.setMemberId(counter++);

		// 添加成员
		team[total++] = p;

	}

	/**
	 * 
	 * @Description 删除指定memberId的员工
	 * @author alex_serendipper
	 * @date 2022年11月4日下午8:27:53
	 * @param memberId
	 */
	public void removeMember(int memberId) throws TeamException{
		boolean flag = true;
		for (int i = 0; i < total; i++) {
			if (team[i].getMemberId() == memberId) {
				team[i].setStatus(Status.FREE);
                // 和以前删除的想法一样，后一个覆盖前一个，最后一个设置为Null；
				for (int j = i; j < total-1; j++) {
                    team[j] = team[j+1];            
				}
				flag = false;
				team[--total]= null;  // total记录的是个数，这里最后一个人是索引为4的那个人
				
				
			}
		}
		
		// 未找到memberid
		if(flag) {
			throw new TeamException("找不到指定memberId的员工");
		}
	}

	/**
	 * 
	 * @Description 判断指定的员工是否存在开发团队中
	 * @author alex_serendipper
	 * @date 2022年11月4日下午8:59:43
	 * @param e
	 * @return
	 */
	public boolean isExist(Employee e) {
		for (int i = 0; i < team.length; i++) {
			if (team[i]==null) {
				return false;
			}else if(team[i].getId() == e.getId()) {
				return true;
			}
		}
		return false;
	}
}
