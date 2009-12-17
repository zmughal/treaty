package net.java.treaty.script;

import org.antlr.runtime.IntStream;
import org.antlr.runtime.RecognitionException;

public class TreatyRecognitionException extends RecognitionException {
	private static final long serialVersionUID = 1L;
	
	private Throwable cause;
	
	public TreatyRecognitionException(Throwable cause) {
		super();
		this.cause = cause;
	}
	
	public TreatyRecognitionException(IntStream input, Throwable cause) {
		super(input);
		this.cause = cause;
	}
	
	public Throwable getCause() {
		return cause;
	}
	
	@Override
	public String getMessage() {
		if (cause != null)
			return cause.getMessage();
		
		return super.getMessage();
	}
}
