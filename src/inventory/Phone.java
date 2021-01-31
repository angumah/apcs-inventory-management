package inventory;

public class Phone {
	private int id;
	private String name;
	private String manufacturer;
	private int units;
	private int supply;
	private int cost;
	private String description;
	public Phone(int id, String name, String manufacturer, int units, int supply, int cost, String description) {
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
		this.units = units;
		this.supply = supply;
		this.cost = cost;
		this.description = description;
	}
	
	public int getId () {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getManufacturer() {
		return this.manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setSupply(int supply) {
		this.supply = supply;
	}
	public int getUnits() {
		return this.units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	public int getSupply() {
		return this.supply;
	}
	public int getCost() {
		return this.cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void purchaseMe() {
		if (this.supply > 0) {
			this.supply--;
		}
	}
	public void returnMe() {
		if(this.supply < this.units) {
			this.supply++;
		}
	}
}
