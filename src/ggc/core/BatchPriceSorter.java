package ggc.core;

import java.util.Comparator;

public class BatchPriceSorter implements Comparator<Batch> {
	@Override
	public int compare(Batch b1, Batch b2) {
		return (int) (b1.getPrice() - b2.getPrice());
	}
}
