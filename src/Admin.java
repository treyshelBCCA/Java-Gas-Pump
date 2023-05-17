import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Admin {

    //this allows the administrator view each transaction that's processed
    public static void viewTransactions() throws FileNotFoundException {
        File file = new File("/Users/treyshelton/Desktop/BCCA/unit-7/practices/Java-Gas-Pump/src/transactions.txt");
        Scanner input = new Scanner(file);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            System.out.println(line);
        }

        input.close();
    }

    //this allows the administrator to view how much gas is in the store's tanks
    public static void viewInventory() throws FileNotFoundException {
        File file = new File("/Users/treyshelton/Desktop/BCCA/unit-7/practices/Java-Gas-Pump/src/inventory.txt");
        Scanner input = new Scanner(file);
        while (input.hasNextLine()) {
            String line = input.nextLine();
            System.out.println(line);
        }
        input.close();
    }

    //if the tanks are low to the admin, this refills all of the tanks when called
    public static void refillTanks() throws IOException {
        ArrayList<GasPump> inventory = Main.loadInventoryInFile();
        inventory.get(0).amountOFgallons = 5000.0;
        inventory.get(1).amountOFgallons = 5000.0;
        inventory.get(2).amountOFgallons = 5000.0;
        Main.saveInventoryInFile(inventory);
    }

    /* the admin can now also check the total income
    by getting the indexes of the .amountOFmoney and adding them together
     */
    public static void viewIncome() throws IOException {
        ArrayList<GasPump> inventory = Main.loadInventoryInFile();
        double totalIncome = Math.round(inventory.get(0).amountOFmoney + inventory.get(1).amountOFmoney + inventory.get(2).amountOFmoney);
        System.out.println("Store income: $" + totalIncome);

    }
}