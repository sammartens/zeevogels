package be.inbo.zeevogels.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ReferenceEntityWithCode extends ReferenceEntity {

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
