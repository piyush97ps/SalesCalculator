package SaleTaxCalculator;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String arg[]) {
        String[] BOOKS = {"book ","books "};
        String[] FOOD = {"chocolates", "chocolate"};
        String[] MEDICAL = {"pills", "pill"};
        String IMPORTED = "imported";
        Scanner in = new Scanner(System.in);

        ArrayList<String> inputList = new ArrayList<>();
        ArrayList<String> outputList = new ArrayList<>();
        while (true) {
            System.out.println("Enter items or product to calculate Sale Tax: \n" +
                    "Example 1 book at 12.49\n" +
                    "        1 music CD at 14.99\n" +
                    "        1 chocolate bar at 0.85");
            String input = in.nextLine();
            input = input.trim().replaceAll(" +", " ");
            if(!checkInput(input)){
                System.out.println("Your Didn't input product info according to the format\n" +
                        "Please Retry the input else enter N");
                String f = in.nextLine();
                if(f.toLowerCase().equals("n")){
                    break;
                }else {
                    continue;
                }
            }
            inputList.add(input);
            System.out.println("\n\nTO continue inserting enter Y else anything: ");
            String flag = in.nextLine();
            if(flag.toLowerCase().equals("y")) {
                continue;
            }
            break;
        }
        double totalTax = 0;
        double totalCost = 0;
        for(String line: inputList){
            int tax = 0;
            if(line.toLowerCase().contains(IMPORTED.toLowerCase())){
                tax += 5;
            }
            if(!((containAny(line.toLowerCase(),BOOKS)) || (containAny(line.toLowerCase(),FOOD)) || (containAny(line.toLowerCase(),MEDICAL)))){
                tax += 10;
            }
            String [] outData = line.split(" at ");
            double cost = Double.parseDouble(outData[1].trim());
            double tax_price = (cost*tax)/100;
            cost += tax_price;
            String outLine = outData[0] + ": " +String.format("%.2f", cost);
            totalCost += cost;
            totalTax += tax_price;
            outputList.add(outLine);
        }
        outputList.add("Sales Taxes: "+String.format("%.2f", totalTax));
        outputList.add("Total: "+String.format("%.2f", totalCost));
        for(String output: outputList){
            System.out.println(output);
        }

    }

    static  boolean containAny(String line, String[] list){
        for(String i : list){
            if(line.contains(i.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    static boolean checkInput(String input){
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

}
