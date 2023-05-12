package model.database.dataObjects;

public class BaseEntity implements java.io.Serializable {

	private int id;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
