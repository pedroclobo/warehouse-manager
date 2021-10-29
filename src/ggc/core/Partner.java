package ggc.core;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.io.Serializable;

public class Partner implements Comparable, Serializable {

	private static final long serialVersionUID = 202109192006L;
	private String _id;
	private String _name;
	private String _address;
	private Status _status;
	private Map<Integer, Purchase> _purchases;
	private Map<Integer, Sale> _sales;
	private TreeSet<Batch> _batches;

	public Partner(String id, String name, String address) {
		_id = id;
		_name = name;
		_address = address;
		_status = new Status();
		_purchases = new HashMap<>();
		_sales = new HashMap<>();
		_batches = new TreeSet<>();
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getAddress() {
		return _address;
	}

	public void addPurchase(Purchase p) {
		_purchases.put(p.getId(), p);
	}

	public void addSale(Sale s) {
		_sales.put(s.getId(), s);
	}

	public double getPurchasesValue() {
		double value = 0;

		for (Purchase p: _purchases.values())
			value += p.getPrice();

		return value;
	}

	public double getPaidSalesValue() {
		double value = 0;

		for (Sale s: _sales.values())
			if (s.isPaid())
				value += s.getBasePrice();

		return value;
	}

	public double getAllSalesValue() {
		double value = 0;

		for (Sale s: _sales.values())
			value += s.getBasePrice();

		return value;
	}

	public boolean addBatch(Batch b) {
		return _batches.add(b);
	}

	public void removeBatch(Batch b) {
		_batches.remove(b);
	}

	public int compareTo(Object o) {
		Partner other = (Partner) o;
		return _id.compareTo(other.getId());
	}

	public boolean equals(Partner other) {
		return _id.equalsIgnoreCase(other.getId());
	}

	public String toString() {
		return _id + "|" + _name + "|" + _address + "|" + _status + "|" + (int)getPurchasesValue() + "|" + (int)getAllSalesValue() + "|" + (int)getPaidSalesValue();
	}

}
