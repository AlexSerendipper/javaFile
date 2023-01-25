package domain;
import service.Status;

public class Programmer extends Employee{
	


	private int memberId;  // 开发团队中的ID
	private Status status= Status.FREE;
	private Equipment equipment;
	
	
	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}


	public Programmer(int id, String name, int age, double salary, Equipment equipment) {
		super(id, name, age, salary);
		this.equipment = equipment;
		this.status = status;
	}
	
	
    /**
     * 用来产生负责项目员工的构造器
     * @param id
     * @param name
     * @param age
     * @param salary
     * @param memberId
     * @param status
     * @param equipment
     */
	public Programmer(int id, String name, int age, double salary, int memberId, Status status, Equipment equipment) {
		super(id, name, age, salary);
		this.memberId = memberId;
		this.status = status;
		this.equipment = equipment;
	}

	public Programmer() {
		super();
	}
	
	@Override
	public String toString() {
		return super.toString() + "\t程序员" + "\t" + status + "\t\t\t" + equipment.getDescription();
	}
	
	public String getTeamDetail() {
		return memberId + "/" + getId() + "\t" + getName() + "\t" + getAge() + "\t" + getSalary() + "\t程序员";
	}

}
