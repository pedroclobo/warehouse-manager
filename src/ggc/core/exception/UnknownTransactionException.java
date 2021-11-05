package ggc.core.exception;

/** Exception for unknown transaction keys. */
public class UnknownTransactionException extends Exception {

	/** @param key Unknown key to report. */
	public UnknownTransactionException(int key) {
		super("Unknown transaction key: " + key);
	}

}
