package ggc.core;

import java.util.List;
import java.util.ArrayList;

public class Recipe {
	private double _alpha;
	private List<Component> _components;

	public Recipe(double alpha, List<Component> components) {
		_alpha = alpha;
		_components = new ArrayList<>(components);
	}

	public double getAlpha() {
		return _alpha;
	}

	public List getComponents() {
		return _components;
	}
}
