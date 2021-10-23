package ggc.core;

import java.util.HashMap;
import java.util.TreeSet;

public class Partner implements Comparable {
	private String _id;
	private String _name;
	private String _address;
	private HashMap<Integer,Purchase> _purchases;
	private HashMap<Integer,Sale> _sales;
	private TreeSet<Batch> _batches;

	public Partner(String id, String name, String address) {
		_id = id;
		_name = name;
		_address = address;
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

	public void addBatch(Batch b) {
		_batches.add(b);
	}

	public void removeBatch(Batch b) {
		_batches.remove(b);
	}

	public int compareTo(Object o) {
		Partner other = (Partner) o;
		return _id.compareTo(other.getId());
	}
}
