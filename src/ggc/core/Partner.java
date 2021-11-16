package ggc.core;

import java.io.Serializable;

import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Partners buy and sell products to and from the Warehouse.
 * Each partner is identified by a unique string.
 */
public class Partner implements Comparable<Partner>, Notifiable, Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** The partner's key. */
	private String _key;

	/** The partner's name. */
	private String _name;

	/** The partner's address. */
	private String _address;

	/** The partner's status. */
	private Status _status;

	/** History of all acquisitions transactions. */
	private Set<Acquisition> _acquisitions;

	/** History of all sale transactions. */
	private Set<Sale> _sales;

	/** History of all credit sale transactions. */
	private Set<CreditSale> _creditSales;

	/** History of all breakdown sale transactions. */
	private Set<BreakdownSale> _breakdownSales;

	/** Collection of all partner's supplied batches. */
	private List<Batch> _batches;

	/** Collection of all partner's notifications. */
	private List<Notification> _notifications;

	/** Partner's preferred notification delivery method. */
	private NotificationDeliveryMethod _deliveryMethod;

	/**
	 * Creates a new partner.
	 *
	 * @param key     the partner's key.
	 * @param name    the partner's name.
	 * @param address the partner's address.
	 */
	Partner(String key, String name, String address) {
		_key = key;
		_name = name;
		_address = address;
		_status = new NormalStatus(this, 0);
		_acquisitions = new TreeSet<>();
		_sales = new TreeSet<>();
		_creditSales = new TreeSet<>();
		_breakdownSales = new TreeSet<>();
		_batches = new ArrayList<>();
		_notifications = new ArrayList<>();
		_deliveryMethod = new DefaultNotificationDeliveryMethod();
	}

	/**
	 * @return the partner's key.
	 */
	String getKey() {
		return _key;
	}

	/**
	 * @return the partner's name.
	 */
	String getName() {
		return _name;
	}

	/**
	 * @return the partner's address.
	 */
	String getAddress() {
		return _address;
	}

	/**
	 * Change partner's status.
	 */
	void changeStatus(Status status) {
		_status = status;
	}

	/**
	 * @return a collection of all partner's supplied batches.
	 */
	Collection<Batch> getBatches() {
		List<Batch> copy = new ArrayList<>(_batches);
		Collections.sort(copy);
		return copy;
	}

	/**
	 * Adds a acquisition transaction to the partner's collection.
	 *
	 * @param transaction the acquisition transaction to add.
	 */
	void addTransaction(Acquisition transaction) {
		_acquisitions.add(transaction);
	}

	/**
	 * Adds a credit sale transaction to the partner's collection.
	 *
	 * @param transaction the credit sale transaction to add.
	 */
	void addTransaction(CreditSale transaction) {
		_creditSales.add(transaction);
		_sales.add(transaction);
	}

	/**
	 * Adds a breakdown sale transaction to the partner's collection.
	 *
	 * @param transaction the breakdown sale transaction to add.
	 */
	void addTransaction(BreakdownSale transaction) {
		_breakdownSales.add(transaction);
		_sales.add(transaction);
	}

	/**
	 * @return a collection of all partner's acquisition transactions.
	 */
	Collection<Acquisition> getAcquisitionTransactions() {
		return Collections.unmodifiableSet(_acquisitions);
	}

	/**
	 * @return a collection of all partner's sale transactions.
	 */
	Collection<Sale> getSaleTransactions() {
		return Collections.unmodifiableSet(_sales);
	}

	/**
	 * @return a collection of all partner's paid sale transactions.
	 */
	Collection<Sale> getPaidTransactions() {
		Set<Sale> paidSales = new TreeSet<>();

		for (Sale sale: _sales) {
			if (sale.isPaid()) {
				paidSales.add(sale);
			}
		}

		return paidSales;
	}

	/**
	 * Calculates the partner's balance on acquisitions.
	 *
	 * @return partner's total acquisitions balance.
	 */
	double getAcquisitionsValue() {
		double value = 0;

		for (Acquisition p: _acquisitions) {
			value += p.getPrice();
		}

		return value;
	}

	/**
	 * Calculates the partner's balance on already paid sales.
	 *
	 * @return partner's total paid sales balance.
	 */
	double getPaidSalesValue() {
		double value = 0;

		for (CreditSale s: _creditSales) {
			if (s.isPaid()) {
				value += s.getPrice();
			}
		}

		return value;
	}

	/**
	 * Calculates the partner's balance on all sales.
	 *
	 * @return partner's total sales balance.
	 */
	double getAllSalesValue() {
		double value = 0;

		for (CreditSale s: _creditSales) {
			value += s.getBasePrice();
		}

		return value;
	}

	/**
	 * Adds a batch to the partner's collection.
	 *
	 * @param batch the batch to add.
	 */
	void addBatch(Batch b) {
		_batches.add(b);
		Collections.sort(_batches);
	}

	/**
	 * Removes a batch from the partner's collection.
	 *
	 * @param batch the batch to remove.
	 */
	void removeBatch(Batch b) {
		_batches.remove(b);
		Collections.sort(_batches);
	}

	/**
	 * Processes the payment of a credit sale transaction.
	 *
	 * @param transaction the credit sale transaction to pay.
	 */
	void payTransaction(CreditSale transaction) {
		_status.payTransaction(transaction);
	}

	/**
	 * Calculates the credit sale current price, based on the partner's classification.
	 *
	 * @param transaction the credit sale.
	 * @return the current price of the credit sale.
	 */
	double getTransactionPrice(CreditSale transaction) {
		return _status.getTransactionPrice(transaction);
	}

	/**
	 * Processes the payment of a breakdown sale transaction.
	 *
	 * @param transaction the breakdown sale transaction to pay.
	 */
	void payTransaction(BreakdownSale transaction) {
		_status.payTransaction(transaction);
	}

	/**
	 * Updates the partner's notifications with a new one.
	 *
	 * @param notification the new notification.
	 */
	@Override
	public void updateNotifications(Notification notification) {
		_notifications.add(notification);
		_deliveryMethod.deliverNotification(notification);
	}

	/**
	 * Returns the partner's collection of notifications.
	 * After getting the notifications, they're all cleared.
	 *
	 * @return the partner's collection of notifications.
	 */
	Collection<Notification> getNotifications() {
		Collection<Notification> copy = new ArrayList<>(_notifications);
		_notifications.clear();

		return copy;
	}

	/**
	 * Compares partners by id.
	 */
	@Override
	public int compareTo(Partner other) {
		return _key.compareTo(other.getKey());
	}

  	/** @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(Object other) {
		return (other instanceof Partner) &&
			_key.equals(((Partner) other).getKey());
	}

	/**
	 * String representation of partner.
	 *
	 * @see java.lang.Object#toString()
	 * @return a string representation of the account.
	 */
	@Override
	public String toString() {
		return _key + "|" +
			_name + "|" +
			_address + "|" +
			_status + "|" +
			Math.round(getAcquisitionsValue()) + "|" +
			Math.round(getAllSalesValue()) + "|" +
			Math.round(getPaidSalesValue());
	}

}
