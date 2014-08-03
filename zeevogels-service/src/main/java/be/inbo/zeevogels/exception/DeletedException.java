package be.inbo.zeevogels.exception;

public class DeletedException extends RuntimeException {

	private Long id;
	private String identifier;

	public DeletedException(Long id) {
		super();
		this.id = id;
	}

	public DeletedException(Long id, String identifier) {
		super();
		this.id = id;
		this.identifier = identifier;
	}

	public String getMessage() {
		return "De identificatie is ondertussen verwijderd";
	}

}
