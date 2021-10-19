package ggc.core;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Partner {
	private String _name;
	private String _address;
	private List<Notification> _notifications;
	private Set<Transaction> _transactions;

	public Partner(String name, String address) {
		_name = name;
		_address = address;
		_notifications = new ArrayList<>();
		_transactions = new HashSet<>();
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

	public void paySale(Sale sale) {}

	public void addNotification(Notification notification) {
		if (!_notifications.contains(notification))
			_notifications.add(notification);
	}

	public void clearNotifications() {
		_notifications.clear();
	}

	public void updateStatus() {}
}

