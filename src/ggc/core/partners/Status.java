package ggc.core.partners;

import java.io.Serializable;

public class Status implements Serializable {
	private static final long serialVersionUID = 202109192006L;

	private enum classification {
		NORMAL, SELECTION, ELITE;
	}

	private int _points;
	private classification _classification;

	public Status() {
		_points = 0;
		_classification = classification.NORMAL;
	}

	public String toString() {
		return _classification + "|" + _points;
	}
}
