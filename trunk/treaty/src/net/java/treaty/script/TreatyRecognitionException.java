package net.java.treaty.script;

import org.antlr.runtime.IntStream;
import org.antlr.runtime.RecognitionException;

public class TreatyRecognitionException extends RecognitionException {
	private static final long serialVersionUID = 1L;
	
	private final Throwable cause;
	private final String message;
	
	public TreatyRecognitionException(Throwable cause) {
		super();
		this.cause = cause;
		this.message = null;
	}
	
	public TreatyRecognitionException(IntStream input, Throwable cause) {
		super(input);
		this.cause = cause;
		this.message = null;
	}
	
	public TreatyRecognitionException(IntStream input, String message) {
		super(input);
		this.cause = null;
		this.message = message;
	}
	
	public Throwable getCause() {
		return cause;
	}
	
	@Override
	public String getMessage() {
		if (message != null)
			return message;
		if (cause != null)
			return cause.getMessage();
		
		return super.getMessage();
	}
}
