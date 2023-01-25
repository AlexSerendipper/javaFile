package domain;

public class Pc implements Equipment {
	private String model;  // 型号
	private String display;  // 显示器名称
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public Pc(String model, String display) {
		super();
		this.model = model;
		this.display = display;
	}

	@Override
	public String getDescription() {
		return model + "(" + display + ")";
	}

}
