package ggc.core;

public class Partner {
	private String _name;
	private String _address;
	//private Notification[] _notifications;

	public Partner(String name, String address) {
		_name = name;
		_address = address;
	}

	public String getName() {
		return _name;
	}

	public String getAddress() {
		return _address;
	}

	public Partner copy() {
		return new Partner(_name, _address);
	}

	public void turnOnNotifications(Product product) {}

	public void turnOffNotifications(Product product) {}

	public void clearNotifications() {}

	public void updateStatus() {}
}

