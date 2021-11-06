package ggc.core.exception;

/** Exception for unknown product keys. */
public class UnknownProductException extends Exception {

	private String _key;

	/** @param key Unknown key to report. */
	public UnknownProductException(String key) {
		super(Message.unknownProductKey(key));
		_key = key;
	}

	public String getKey() {
		return _key;
	}

}
