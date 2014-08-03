package be.inbo.zeevogels.rest.util;

public class ResponseWrapper<T> {

	private T data;
	private boolean success;
	private String message;
	private Long total;
	
	private ResponseWrapper(T data, boolean success, String message, Long total) {
		this.data = data;
		this.success = success;
		this.message = message;
		this.total = total;
	}
	
	public static <T> ResponseWrapper<T> wrapIt(T data, boolean success) {
		return new ResponseWrapper<T>(data, success, null, null);
	}
	
	public static <T> ResponseWrapper<T> wrapIt(T data, boolean success, Long total) {
		return new ResponseWrapper<T>(data, success, null, total);
	}
	
	public static <T> ResponseWrapper<T> wrapIt(T data, boolean success, String message) {
		return new ResponseWrapper<T>(data, success, message, null);
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
}
