package SaleTaxCalculator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

class FilterData{

    private static FilterData instance = new FilterData();
    private String inputFilename = "inputFilename.txt";
    private String outputFilename = "output.txt";

    private String IMPORTED = "imported";
    private double importTax = 5;

    private ArrayList<ItemCategory> typeList = new ArrayList<>();
    private ArrayList<Item> itemList = new ArrayList<>();
    private LinkedList<String> inputList = new LinkedList<>();
    private LinkedList<String> outputList = new LinkedList<>();

    private double totalCost = 0;
    private double totalTax = 0;


    private FilterData(){
        ItemCategory BOOKS = ItemCategory.BOOK;
        ItemCategory FOOD = ItemCategory.FOOD;
        ItemCategory MEDICAL = ItemCategory.MEDICAL;
        ItemCategory OTHER = ItemCategory.OTHER;
        // BOOKS
        BOOKS.addWord("books");
        BOOKS.addWord("book");
        // FOOD
        FOOD.addWord("chocolates");
        FOOD.addWord("chocolate");
        // MEDiCAL
        MEDICAL.addWord("pills");
        MEDICAL.addWord("pill");

        // OTHER
        OTHER.addWord("");

        typeList.add(BOOKS);
        typeList.add(FOOD);
        typeList.add(MEDICAL);
        typeList.add(OTHER);
    }

    public double getImportTax() {
        return importTax;
    }

    public void setImportTax(double importTax) {
        this.importTax = importTax;
    }

    public static FilterData getInstance(){
        return instance;
    }

    public LinkedList<String> getOutputData() throws IOException {
        for(String line: this.inputList) {
            this.itemList.add(this.createItem(line));
        }
        int index = 0;
        for(Item item: itemList){
            double extraTax = 0;
            if(item.isImported()){
                extraTax += this.importTax;
            }
            double tax_price = (item.getPrice()*(item.getType().getTax()+extraTax))/100;
            double cost  = item.getPrice() + tax_price;
            String outLine = inputList.get(index).split(" at ")[0] + ": " +String.format("%.2f", cost);
            totalCost += cost;
            totalTax += tax_price;
            outputList.add(outLine);
        }
        outputList.add("Sales Taxes: "+String.format("%.2f", totalTax));
        outputList.add("Total: "+String.format("%.2f", totalCost));
        return outputList;
    }

    public LinkedList<String> getFileInput() throws IOException{
        Path path = Paths.get(this.inputFilename);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            int c=0;
            while ((input = br.readLine()) != null) {
                input = input.trim().replaceAll(" +", " ");
                if(!checkInput(input)) {
                    System.out.println("Your Didn't input product info according to the format\n");
                    System.out.println("Enter items or product to calculate Sale Tax: \n" +
                            "Example 1 book at 12.49\n" +
                            "        1 music CD at 14.99\n" +
                            "        1 chocolate bar at 0.85");
                    System.exit(0);
                }else{
                    inputList.add(input);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return inputList;
    }

    public LinkedList<String> getFileOutput() throws IOException {
        this.getOutputData();
        Path path = Paths.get(this.outputFilename);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            Iterator<String> item = outputList.iterator();
            while (item.hasNext()) {
                String line = item.next();
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputList;
    }

    public Item createItem(String line){
        Item other = null;
        for(ItemCategory i : typeList){
            for(String word: i.getWords()) {
                String [] outData = line.split(" at ");
                double cost = Double.parseDouble(outData[1].trim());
                if (line.contains(word.toLowerCase()) && !(i == ItemCategory.OTHER)) {
                    return new Item(word.toUpperCase(), i, line.toLowerCase().contains(IMPORTED.toLowerCase()), cost);
                }else if(i == ItemCategory.OTHER){
                    other = new Item("OTHER", i, line.toLowerCase().contains(IMPORTED.toLowerCase()), cost);
                }
            }
        }
        return other;
    }

    public boolean checkInput(String input){
        if(!input.contains(" at ")){
            return false;
        }
        String s [] = input.split(" ");
        int len = s.length;
        try{
            Integer.parseInt(s[0]);
            Float.parseFloat(s[len - 1]);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public ArrayList<String> setTax(String catagoryName, double tax){
        Iterator<ItemCategory> item = typeList.iterator();
        while (item.hasNext()) {
            ItemCategory currItem = item.next();
            if(currItem== ItemCategory.valueOf(catagoryName.toUpperCase())){
                currItem.setTax(tax);
                return currItem.getWords();
            }
        }
        return null;
    }

    public double getTax(String catagoryName, double tax){
        Iterator<ItemCategory> item = typeList.iterator();
        while (item.hasNext()) {
            ItemCategory currItem = item.next();
            if(currItem== ItemCategory.valueOf(catagoryName.toUpperCase())){
                return currItem.getTax();
            }
        }
        return -1;
    }


}
