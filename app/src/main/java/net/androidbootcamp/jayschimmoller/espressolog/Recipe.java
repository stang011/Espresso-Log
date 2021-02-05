package net.androidbootcamp.jayschimmoller.espressolog;

import java.text.DecimalFormat;

public class Recipe {

    private double espressoAmount;
    private double outputAmount;
    private double calculatedRatio;
    private int brewTime;
    private int grinderSetting;
    private double grinderTime;
    private String coffeeName;
    private String tastingNotes;

    private DecimalFormat df = new DecimalFormat("##.0");

    public Recipe(double espressoAmount, double outputAmount, double calculatedRatio, int brewTime, int grinderSetting, double grinderTime, String coffeeName, String tastingNotes) {
        this.espressoAmount = espressoAmount;
        this.outputAmount = outputAmount;
        this.calculatedRatio = calculatedRatio;
        this.brewTime = brewTime;
        this.grinderSetting = grinderSetting;
        this.grinderTime = grinderTime;
        this.coffeeName = coffeeName;
        this.tastingNotes = tastingNotes;
    }

    public Recipe(String fromFile) {
        String[] array = fromFile.split(",");

        espressoAmount = Double.parseDouble(array[0]);
        outputAmount = Double.parseDouble(array[1]);
        calculatedRatio = Double.parseDouble(array[2]);
        brewTime = Integer.parseInt(array[3]);
        grinderSetting = Integer.parseInt(array[4]);
        grinderTime = Double.parseDouble(array[5]);
        coffeeName = array[6];
        tastingNotes = array[7];
        for(int i = 8; i < array.length; i++) {
            tastingNotes += "," + array[i];
        }
    }

    public double getEspressoAmount() {
        return espressoAmount;
    }
    public double getOutputAmount() {
        return outputAmount;
    }
    public double getCalculatedRatio() {
        return calculatedRatio;
    }
    public int getBrewTime() {
        return brewTime;
    }
    public int getGrinderSetting() {
        return grinderSetting;
    }
    public double getGrinderTime() {
        return grinderTime;
    }
    public String getCoffeeName() { return coffeeName; }
    public String getTastingNotes() {
        return tastingNotes;
    }

    // Return rounded values to be used in the file-management system
    public String toString() {
        return df.format(espressoAmount) + "," + df.format(outputAmount) + "," + df.format(calculatedRatio) + "," + brewTime + "," + grinderSetting + "," + df.format(grinderTime) + "," + coffeeName + "," + tastingNotes;
    }

    public String displayString() {
        String returnString = "";

        returnString += "Grind for " + grinderTime + " seconds on setting " + grinderSetting + "\n";
        returnString += df.format(espressoAmount) + "g in, " + df.format(outputAmount) + "g out for a 1:" + df.format(calculatedRatio) + " ratio" + "\n";
        returnString += brewTime + " second brew time" + "\n";
        //returnString += "Grind for " + grinderTime + " seconds on setting " + grinderSetting + "\n";
        returnString += coffeeName + "\n";
        returnString += tastingNotes;

        return returnString;
    }
}