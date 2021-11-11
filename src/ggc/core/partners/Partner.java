package ggc.core.partners;

import java.io.Serializable;

import java.util.Collections;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import ggc.core.products.Batch;
import ggc.core.transactions.Transaction;
import ggc.core.transactions.Acquisition;
import ggc.core.transactions.Sale;
import ggc.core.transactions.CreditSale;
import ggc.core.transactions.BreakdownSale;

/**
 * Partners buy and sell products to and from the Warehouse.
 * Each partner is identified by a unique string.
 */
public class Partner implements Comparable<Partner>, Serializable {

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
	private Set<Batch> _batches;

	/**
	 * Constructor.
	 *
	 * @param key     the partner's key.
	 * @param name    the partner's name.
	 * @param address the partner's address.
	 */
	public Partner(String key, String name, String address) {
		_key = key;
		_name = name;
		_address = address;
		_status = new NormalStatus(this, 0);
		_acquisitions = new TreeSet<>();
		_sales = new TreeSet<>();
		_creditSales = new TreeSet<>();
		_breakdownSales = new TreeSet<>();
		_batches = new TreeSet<>();
	}

	/**
	 * @return the partner's key.
	 */
	public String getKey() {
		return _key;
	}

	/**
	 * @return the partner's name.
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @return the partner's address.
	 */
	public String getAddress() {
		return _address;
	}

	/**
	 * @return a collection of all the partner's supplied batches.
	 */
	public Collection<Batch> getBatches() {
		return Collections.unmodifiableSet(_batches);
	}

	/**
	 * @return a collection of all the partner's acquisition transactions.
	 */
	public Collection<Acquisition> getAcquisitionTransactions() {
		return Collections.unmodifiableSet(_acquisitions);
	}

	/**
	 * @return a collection of all the partner's sale transactions.
	 */
	public Collection<Sale> getSaleTransactions() {
		return Collections.unmodifiableSet(_sales);
	}

	public Collection<Sale> getPaidTransactions() {
		Set<Sale> paidSales = new TreeSet<>();

		for (Sale sale: _sales)
			if (sale.isPaid())
				paidSales.add(sale);

		return paidSales;
	}

	public void addTransaction(Transaction transaction) {
		if (transaction instanceof Acquisition) {
			_acquisitions.add((Acquisition) transaction);
		} else if (transaction instanceof CreditSale) {
			_creditSales.add((CreditSale) transaction);
			_sales.add((CreditSale) transaction);
		} else if (transaction instanceof BreakdownSale) {
			_breakdownSales.add((BreakdownSale) transaction);
			_sales.add((BreakdownSale) transaction);
		}
	}

	/**
	 * Calculates the partner's balance on acquisitions.
	 *
	 * @return partner's total acquisitions balance.
	 */
	public double getAcquisitionsValue() {
		double value = 0;

		for (Acquisition p: _acquisitions)
			value += p.getPrice();

		return value;
	}

	/**
	 * Calculates the partner's balance on already paid sales.
	 *
	 * @return partner's total paid sales balance.
	 */
	public double getPaidSalesValue() {
		double value = 0;

		for (CreditSale s: _creditSales)
			if (s.isPaid())
				value += s.getBasePrice();

		return value;
	}

	/**
	 * Calculates the partner's balance on all sales.
	 *
	 * @return partner's total sales balance.
	 */
	public double getAllSalesValue() {
		double value = 0;

		for (CreditSale s: _creditSales)
			value += s.getBasePrice();

		return value;
	}

	/**
	 * Adds a batch to the partner's collection.
	 *
	 * @param batch the batch to add.
	 * @return true, if the operation was successful; false, otherwise.
	 */
	public boolean addBatch(Batch b) {
		return _batches.add(b);
	}

	/**
	 * Removes a batch to the partner's collection.
	 *
	 * @param batch the batch to remove.
	 */
	public void removeBatch(Batch b) {
		_batches.remove(b);
	}

	/**
	 * Processes the payment of a acquisition transaction.
	 *
	 * @param transaction the acquisition transaction to pay.
	 */
	public void payTransaction(Acquisition transaction) {
		_status.payTransaction(transaction);
	}

	/**
	 * Processes the payment of a credit sale transaction.
	 *
	 * @param transaction the credit sale transaction to pay.
	 */
	public void payTransaction(CreditSale transaction) {
		_status.payTransaction(transaction);
	}

	public double getTransactionPrice(CreditSale transaction) {
		return _status.getTransactionPrice(transaction);
	}

	/**
	 * Processes the payment of a breakdown sale transaction.
	 *
	 * @param transaction the breakdown sale transaction to pay.
	 */
	public void payTransaction(BreakdownSale transaction) {
		_status.payTransaction(transaction);
	}

	/**
	 * Compares partners by id.
	 */
	@Override
	public int compareTo(Partner other) {
		return _key.compareTo(other.getKey());
	}

	/**
	 * Two partners are equal if they have the same id.
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
			(int) getAcquisitionsValue() + "|" +
			(int) getAllSalesValue() + "|" +
			(int) getPaidSalesValue();
	}

}
