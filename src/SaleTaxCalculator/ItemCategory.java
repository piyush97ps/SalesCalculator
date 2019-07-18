package SaleTaxCalculator;

import java.util.ArrayList;

enum ItemCategory{
    BOOK(0), FOOD(0), MEDICAL(0), OTHER(10);
    double tax;
    ArrayList<String> words;

    ItemCategory(double tax) {
        this.tax = tax;
        words = new ArrayList<>();
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void addWord(String word){
        words.add(word);
    }

    public ArrayList<String> getWords() {
        return new ArrayList<>(words);
    }
}
