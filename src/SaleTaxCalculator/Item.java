package SaleTaxCalculator;

class Item{
    private String name;
    private ItemCategory type;
    private boolean isImported;
    private double price;

    public Item(String name, ItemCategory type, boolean isImported, double price) {
        this.name = name;
        this.type = type;
        this.isImported = isImported;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public double getTax(){
        return type.getTax();
    }

    public double setTax(double tax){
        type.setTax(this.getTax() + tax);
        return getTax();
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemCategory getType() {
        return type;
    }

    public void setType(ItemCategory type) {
        this.type = type;
    }

    public boolean isImported() {
        return isImported;
    }

    public void setImported(boolean imported) {
        isImported = imported;
    }
}
