package SaleTaxCalculator;

import java.io.IOException;


public class Main {

    public static void main(String arg[]) throws IOException {
        FilterData data = FilterData.getInstance();
        data.getFileInput(); //getInputData();
        for(String output: data.getFileOutput()){
            System.out.println(output);
        }
    }

}
