package service;
/**
 * 
 * @Description 表示员工的状态
 * @author alex_serendipper Email:642671525@qq.com
 * @version
 * @date 2022年11月4日下午2:09:48
 *
 */
public class Status {




	// 之前说的简陋版的单例
	private final String NAME;
	
	private Status(String name) {
		this.NAME = name;
	}
	
    public static final Status FREE = new Status("FREE");
    public static final Status BUSY = new Status("BUSY");
    public static final Status VOCATION = new Status("VOCATION");
    
	public String getNAME() {
		return NAME;
	}
	
	@Override
	public String toString() {
		return NAME;
	}
}
