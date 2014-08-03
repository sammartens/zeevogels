package be.inbo.zeevogels.domain;

public class FilterCriterium {

	private String property;
	private String value;

	public FilterCriterium() {
	}

	public FilterCriterium(String property, String value) {
		this.property = property;
		this.value = value;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
