package be.inbo.zeevogels.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ReferenceEntity extends AbstractEntity {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
