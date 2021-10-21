package ggc.core;

import java.util.List;
import java.util.ArrayList;

public class Partner {
	private String _id;
	private String _name;
	private String _address;
	private List<Purchase> _purchases;
	private List<Sale> _sales;

	public Partner(String id, String name, String address) {
		_id = id;
		_name = name;
		_address = address;
		_purchases = new ArrayList<>();
		_sales = new ArrayList<>();
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
		_purchases.add(p);
	}

	public void addSale(Sale s) {
		_sales.add(s);
	}
}
