package ggc.core.products;

import java.io.Serializable;

/**
 * This class represents a component.
 * Each component is made up by a product, in a certain quantity.
 */
public class Component implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/** The product of the component. */
	private Product _product;

	/** The quantity of product. */
	private int _quantity;

	/** Initializes a component, made of a product in a certain
	 * quantity.
	 *
	 * @param product  the product of the component.
	 * @param quantity the quantity of product.
	 */
	public Component(Product product, int quantity) {
		_product = product;
		_quantity = quantity;
	}

	/**
	 * @return the product of the component.
	 */
	public Product getProduct() {
		return _product;
	}

	/**
	 * @return the quantity of product of the component.
	 */
	public int getQuantity() {
		return _quantity;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return _product.getId() + ":" + _quantity;
	}

}
