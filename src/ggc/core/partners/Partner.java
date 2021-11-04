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

public class Partner implements Comparable, Serializable {

	private static final long serialVersionUID = 202109192006L;
	private String _id;
	private String _name;
	private String _address;
	private Status _status;
	private List<Acquisition> _acquisitions;
	private List<Sale> _sales;
	private List<CreditSale> _creditSales;
	private Set<Batch> _batches;

	public Partner(String id, String name, String address) {
		_id = id;
		_name = name;
		_address = address;
		_status = new Status();
		_acquisitions = new ArrayList<>();
		_sales = new ArrayList<>();
		_creditSales = new ArrayList<>();
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

	public Collection<Batch> getBatches() {
		return Collections.unmodifiableSet(_batches);
	}

	public Collection<Acquisition> getAcquisitions() {
		return Collections.unmodifiableList(_acquisitions);
	}

	public Collection<Sale> getSales() {
		return Collections.unmodifiableList(_sales);
	}

	public void addAcquisition(Acquisition p) {
		_acquisitions.add(p);
	}

	public void addSale(Sale s) {
		_sales.add(s);
	}

	public double getAcquisitionsValue() {
		double value = 0;

		for (Acquisition p: _acquisitions)
			value += p.getPrice();

		return value;
	}

	public double getPaidSalesValue() {
		double value = 0;

		for (CreditSale s: _creditSales)
			if (s.isPaid())
				value += s.getBasePrice();

		return value;
	}

	public double getAllSalesValue() {
		double value = 0;

		for (CreditSale s: _creditSales)
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
		return _id + "|" + _name + "|" + _address + "|" + _status + "|" + (int)getAcquisitionsValue() + "|" + (int)getAllSalesValue() + "|" + (int)getPaidSalesValue();
	}

}
