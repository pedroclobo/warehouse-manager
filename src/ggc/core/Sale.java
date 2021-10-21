package ggc.core;

public class Sale extends Transaction {
    private double _basePrice;
    private double _effectivePrice;

    public Sale(int id, Partner partner, Product product, int quantity, Date date, double basePrice, double effectivePrice) {
        super(id, partner, product, quantity, date);
        _basePrice = basePrice;
        _effectivePrice = effectivePrice;
    }
}
