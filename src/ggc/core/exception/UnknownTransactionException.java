package ggc.core.exception;

/** Exception for unknown transaction keys. */
public class UnknownTransactionException extends Exception {

	/** Unknown key to report. */
	private int _key;

	/** @param key unknown key to report. */
	public UnknownTransactionException(int key) {
		super(Message.unknownTransaction(key));
		_key = key;
	}

	/** @return unknown key to report. */
	public int getKey() {
		return _key;
	}

}
