package ggc.core.exception;

/** Exception for unknown product keys. */
public class UnknownProductException extends Exception {

	/** Unknown key to report. */
	private String _key;

	/** @param key Unknown key to report. */
	public UnknownProductException(String key) {
		super(Message.unknownProduct(key));
		_key = key;
	}

	/** @return unknown key to report. */
	public String getKey() {
		return _key;
	}

}
