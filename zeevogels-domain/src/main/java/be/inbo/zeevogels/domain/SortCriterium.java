package be.inbo.zeevogels.domain;

public class SortCriterium {

	private String property;
	private String direction;

	public SortCriterium() {

	}

	public SortCriterium(String property, String direction) {
		this.property = property;
		this.direction = direction;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
