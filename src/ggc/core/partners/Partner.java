package ggc.core.partners;

import java.io.Serializable;

import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import ggc.core.products.Batch;
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

	/** The partner's id. */
	private String _id;

	/** The partner's name. */
	private String _name;

	/** The partner's address. */
	private String _address;

	/** The partner's status. */
	private Status _status;

	/** History of all acquisitions transactions. */
	private List<Acquisition> _acquisitions;

	/** History of all sale transactions. */
	private List<Sale> _sales;

	/** History of all credit sale transactions. */
	private List<CreditSale> _creditSales;

	/** History of all breakdown sale transactions. */
	private List<BreakdownSale> _breakdownSales;

	/** Collection of all partner's supplied batches. */
	private Set<Batch> _batches;

	/**
	 * This class is responsible for the partner's point accounting.
	 * The partner's status depends on its delay paying its sales.
	 */
	private class Status implements Serializable {

		/** Serial number for serialization. */
		private static final long serialVersionUID = 202109192006L;

		/** Number of points accumulated. */
		private int _points;

		/** Levels of status classification. */
		private enum classification {
			NORMAL, SELECTION, ELITE;
		}

		/** Classification status. */
		private classification _classification;

		/**
		 * Registers new default status.
		 * Every client starts with 0 points and a "NORMAL" classification.
		 */
		private Status() {
			_points = 0;
			_classification = classification.NORMAL;
		}

		/**
		 * String representation of status.
		 *
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return _classification + "|" + _points;
		}
	}

	/**
	 * Constructor.
	 *
	 * @param id      the partner's id.
	 * @param name    the partner's name.
	 * @param address the partner's address.
	 */
	public Partner(String id, String name, String address) {
		_id = id;
		_name = name;
		_address = address;
		_status = new Status();
		_acquisitions = new ArrayList<>();
		_sales = new ArrayList<>();
		_creditSales = new ArrayList<>();
		_breakdownSales = new ArrayList<>();
		_batches = new TreeSet<>();
	}

	/**
	 * @return the partner's id.
	 */
	public String getId() {
		return _id;
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
	public Collection<Acquisition> getAcquisitions() {
		return Collections.unmodifiableList(_acquisitions);
	}

	/**
	 * @return a collection of all the partner's sale transactions.
	 */
	public Collection<Sale> getSales() {
		return Collections.unmodifiableList(_sales);
	}

	/**
	 * Adds a new acquisition to the partner's history.
	 */
	public void addAcquisition(Acquisition p) {
		_acquisitions.add(p);
	}

	/**
	 * Adds a new sale to the partner's history.
	 */
	public void addSale(Sale s) {
		_sales.add(s);
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
	 * Compares partners by id.
	 */
	@Override
	public int compareTo(Partner other) {
		return _id.compareTo(other.getId());
	}

	/**
	 * Two partners are equal if they have the same id.
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof Partner) &&
			_id.equals(((Partner) other).getId());
	}

	/**
	 * String representation of partner.
	 *
	 * @see java.lang.Object#toString()
	 * @return a string representation of the account.
	 */
	@Override
	public String toString() {
		return _id + "|" +
			_name + "|" +
			_address + "|" +
			_status + "|" +
			(int) getAcquisitionsValue() + "|" +
			(int) getAllSalesValue() + "|" +
			(int) getPaidSalesValue();
	}

}
