package ggc.core.products;

import java.util.Comparator;
import java.io.Serializable;

/**
 * Class BatchPriceSorter sorts Batch by price.
 */
public class BatchPriceSorter implements Comparator<Batch>, Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202109192006L;

	/**
	 * Compares two batches.
	 */
	@Override
	public int compare(Batch b1, Batch b2) {
		int i = (int) (b1.getPrice() - b2.getPrice());
		if (i != 0)
			return i;

		i = b1.getProduct().compareTo(b2.getProduct());
		if (i != 0)
			return i;

		i = b1.getPartner().compareTo(b2.getPartner());
		if (i != 0)
			return i;

		i = b1.getStock() - b2.getStock();
		return i;
	}

}
