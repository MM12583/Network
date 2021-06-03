package network.muilple_thread;

import java.io.Serializable;

public class RequestPackage2 implements Serializable {
	
	// Interpretation type
	RequestType2 type ;
	// Ns¡BNr (How to use ?) not add
//	String packageNumber ;
	String message ;
	
	public RequestPackage2(RequestType2 type, String message) {
		super();
		this.type = type;
		this.message = message;
	}
	// only for sent object
	public RequestPackage2(RequestType2 type) {
		super();
		this.type = type;
	}
	
	public RequestType2 getType() {
		return type;
	}
	
	public void setType(RequestType2 type) {
		this.type = type;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestPackage [type=");
		builder.append(type);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}
