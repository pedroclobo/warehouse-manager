package ggc.core;

public class Sale extends Transaction{
    private float _basePrice;
    private float _effectivePrice;

    public Sale(int id, String type, int quantity, Partner partner, Product product, Date date, float basePrice, float effectivePrice) {
        super(id, type, quantity, partner, product, date);
        _basePrice = basePrice;
        _effectivePrice = effectivePrice;
    }

    public void recalculateEffectivePrice() {}

    public void applyExtras() {}
}
