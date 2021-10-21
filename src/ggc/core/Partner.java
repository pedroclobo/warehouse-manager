package ggc.core;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Partner {
	private String _name;
	private String _address;
	private Set<Purchase> _purchases;
	private Set<Sale> _sales;

	public Partner(String name, String address) {
		_name = name;
		_address = address;
		_purchases = new HashSet<>();
		_sales = new HashSet<>();
	}

	public String getName() {
		return _name;
	}

	public String getAddress() {
		return _address;
	}

	public boolean equals(Partner other) {
		return _name.equals(other._name);
	}

	public void addPurchase(Purchase p) {
		_purchases.add(p);
	}
}
