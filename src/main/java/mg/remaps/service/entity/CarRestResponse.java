package mg.remaps.service.entity;

public class CarRestResponse {
	String id;
	String var_title;
	String var_logo;
	boolean is_car;
	boolean is_truck;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVar_title() {
		return var_title;
	}
	public void setVar_title(String var_title) {
		this.var_title = var_title;
	}
	public String getVar_logo() {
		return var_logo;
	}
	public void setVar_logo(String var_logo) {
		this.var_logo = var_logo;
	}
	public boolean isIs_car() {
		return is_car;
	}
	public void setIs_car(boolean is_car) {
		this.is_car = is_car;
	}
	public boolean isIs_truck() {
		return is_truck;
	}
	public void setIs_truck(boolean is_truck) {
		this.is_truck = is_truck;
	}
	
	
}
