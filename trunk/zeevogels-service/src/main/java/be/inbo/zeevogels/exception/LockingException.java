package be.inbo.zeevogels.exception;

public class LockingException extends RuntimeException {

	private Long id;
	private String username;
	private String identifier;

	public LockingException(Long id, String username, String identifier) {
		super();
		this.id = id;
		this.username = username;
		this.identifier = identifier;
	}

	public String getMessage() {
		String person = (username != null) ? username : "iemand anders";
		return "De identificatie is ondertussen geediteerd door " + person + ".";
	}

}
