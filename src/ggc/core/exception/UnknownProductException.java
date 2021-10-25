package ggc.core.exception;

/** Exception for unknown product keys. */
public class UnknownProductException extends Exception {

	/** @param key Unknown key to report. */
	public UnknownProductException(String key) {
		super("Unknown product key: " + key);
	}

}
