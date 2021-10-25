package ggc.core;

import java.util.Comparator;
import java.io.Serializable;

public class BatchPriceSorter implements Comparator<Batch>, Serializable {
	private static final long serialVersionUID = 202109192006L;

	@Override
	public int compare(Batch b1, Batch b2) {
		int diff = (int) (b1.getPrice() - b2.getPrice());

		if (diff != 0)
			return diff;
		else
			return 1;
	}
}
