package ggc.core.exception;

/** Exception thrown when a product is unavailable. */
public class NoProductStockException extends Exception {

	private String _key;
	private int _requested;
	private int _available;

	/**
	 * @param id        The requested product id.
	 * @param requested Requested amount.
	 * @param available Available amount.
	 */
	public NoProductStockException(String key, int requested, int available) {
		super(Message.unavailable(key, requested, available));
		_key = key;
		_requested = requested;
		_available = available;
	}

	public String getKey() {
		return _key;
	}

	public int getRequested() {
		return _requested;
	}

	public int getAvailable() {
		return _available;
	}

}
