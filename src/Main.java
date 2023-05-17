import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //this makes the input of the number return the type in a string word
    public static String getType(String gas) {
        String type = "";
        if (gas.equals("87")) {
            return "Regular";
        } else if (gas.equals("89")) {
            return "Mid-Grade";
        } else if (gas.equals("92")) {
            return "Premium";
        } else if (gas.equals("Administrator123")) {
            return "Administrator123";
        } return type;
    }

    //this loads the inventory.txt as an array

    public static ArrayList<GasPump> loadInventoryInFile() throws IOException {
        BufferedReader br = null;
        FileReader fr = null;

        //br = new BufferedReader(new FileReader(FILENAME));
        fr = new FileReader("/Users/treyshelton/Desktop/BCCA/unit-7/practices/Java-Gas-Pump/src/inventory.txt");
        br = new BufferedReader(fr);


        String CurrentLine = "";
        String[] reglist = br.readLine().toString().split(", ");
        String[] midlist = br.readLine().toString().split(", ");
        String[] premlist = br.readLine().toString().split(", ");

        GasPump regular =  new GasPump(reglist[0], Double.parseDouble(reglist[1]), Double.parseDouble(reglist[2]));
        GasPump midgrade = new GasPump(midlist[0], Double.parseDouble(midlist[1]), Double.parseDouble(midlist[2]));
        GasPump premium = new GasPump(premlist[0], Double.parseDouble(premlist[1]), Double.parseDouble(premlist[2]));

        return new ArrayList<GasPump>() {
            {
                add(regular);
                add(midgrade);
                add(premium);
            }
        };

    }

    /* this now saves the file to where the ArrayList is:
    index 0 = type of gas, index 1 = amount of gallons,
    index 2 = total income for that specific gas type
     */

    public static void saveInventoryInFile(ArrayList<GasPump> inventory) throws IOException {
        FileWriter writer = new FileWriter("/Users/treyshelton/Desktop/BCCA/unit-7/practices/Java-Gas-Pump/src/inventory.txt");
        writer.write(inventory.get(0).typeOFgas + ", " + inventory.get(0).amountOFgallons + ", " +  inventory.get(0).amountOFmoney + "\n" );
        writer.write(inventory.get(1).typeOFgas + ", " + inventory.get(1).amountOFgallons + ", " + inventory.get(1).amountOFmoney + "\n");
        writer.write(inventory.get(2).typeOFgas + ", " + inventory.get(2).amountOFgallons + ", " + inventory.get(2).amountOFmoney);
        writer.close();
    }

    //this helps append each transaction into the transactions.txt file

    public static void appendDataToFile(String gastype, double printGallons, double printCost) throws IOException {
        FileWriter writer = new FileWriter("/Users/treyshelton/Desktop/BCCA/unit-7/practices/Java-Gas-Pump/src/transactions.txt",true);
        //once the file is opened and written into, it has to be closed also
        writer.write("\n" + gastype + ", " + printGallons + ", " + printCost);
        writer.close();
    }


    public static void main(String[] args) throws IOException {
        //gets the gas type input
        String gastype;
        Scanner gastypeInput = new Scanner(System.in);
        System.out.println("\t\tWelcome to Trey's Place!\nWhat type of gas?\n87 -> Regular ($2.09)\n89 -> Mid-Grade ($2.19)\n92 -> Premium ($2.29)\n");
        gastype = getType(gastypeInput.next());

        /* if the administrator knows the secret code ("Administrator123"),
        the secret admin options will show
        ELSE:
        the user interaction will resume as normal
         */

        if (gastype.equals("Administrator123")) {
            String admin_choice;
            Scanner admin_input = new Scanner(System.in);
            System.out.println("1 - View Transactions\n2 - View Gas Tank\n3 - Store Income\n4 - Refill Gas Tank\n5 - Quit Program");
            admin_choice = admin_input.next();

            if (admin_choice.equals("1")) {
                Admin.viewTransactions();
            } else if (admin_choice.equals("2")) {
                Admin.viewInventory();
            } else if (admin_choice.equals("3")) {
                Admin.viewIncome();
            } else if (admin_choice.equals("4")) {
                Admin.refillTanks();
            }
        } else {

            //this is figuring out whether the user is paying before or after
            String payBeforeOrAfter;
            Scanner beforeOrAfterInput = new Scanner(System.in);
            System.out.println("1 -> Pay Before\n2 -> Pay After\n");
            payBeforeOrAfter = beforeOrAfterInput.next();

            //if the user chooses to pay before, they must pay and receive the gallon amount in the end
            if (payBeforeOrAfter.equals("1")) {
                double total;
                Scanner cashInput = new Scanner(System.in);

                //user inputs how much money they'd like to spend on gas
                System.out.print("Money amount?\n$");
                total = Math.round(Integer.parseInt(cashInput.next()) * 100.00) / 100.00;

                GasPump printGallons = new GasPump(gastype, 0, total);

                /* Math.round(blah * 100.00) / 100.00 keeps the
                gallons from going over two decimal places */
                double total_gallons = Math.round(printGallons.prepay() * 100.00) / 100.00;

                /* Since Java makes you declare the type, my inventory is
                in an ArrayList; the money is also getting added to my file
                 while my gallons are being taken away from the store's tank */
                ArrayList<GasPump> inventory = loadInventoryInFile();
                if (gastype.equals("Regular")) {
                    inventory.get(0).amountOFmoney += total;
                    inventory.get(0).amountOFgallons -= total_gallons;
                } else if (gastype.equals("Mid-Grade")) {
                    inventory.get(1).amountOFmoney += total;
                    inventory.get(1).amountOFgallons -= total_gallons;
                } else if (gastype.equals("Premium")) {
                    inventory.get(2).amountOFmoney += total;
                    inventory.get(2).amountOFgallons -= total_gallons;
                }


                System.out.println("********************************");
                System.out.println("Type of gas: " + gastype);
                System.out.println("Amount paid: $" + total);
                System.out.println("Total gallons received: " + total_gallons);
                System.out.println("********************************");

                //this is the function call to write to the file once the transaction is finished
                appendDataToFile(gastype, total_gallons, total);

                /* this makes the new store income and store's
                gas in the tanks update */
                saveInventoryInFile(inventory);

            } /* if the user chooses to pay after, they must pump
            the gas and however many gallons they received, the
            payment is then shown in the end */
            else if (payBeforeOrAfter.equals("2")) {
                double total;
                Scanner gallonInput = new Scanner(System.in);
                System.out.println("How many gallons?\n");
                double total_gallons = Math.round(Double.parseDouble(gallonInput.next()) * 100.00) / 100.00;

                GasPump printCost = new GasPump(gastype, total_gallons, 0);
                double total_price = Math.round(printCost.payafter() * 100.00) / 100.00;

                ArrayList<GasPump> inventory = loadInventoryInFile();

                if (gastype.equals("Regular")) {
                    inventory.get(0).amountOFmoney += total_price;
                    inventory.get(0).amountOFgallons -= total_gallons;
                } else if (gastype.equals("Mid-Grade")) {
                    inventory.get(1).amountOFmoney += total_price;
                    inventory.get(1).amountOFgallons -= total_gallons;
                } else if (gastype.equals("Premium")) {
                    inventory.get(2).amountOFmoney += total_price;
                    inventory.get(2).amountOFgallons -= total_gallons;
                }


                //prints receipt
                System.out.println("********************************");
                System.out.println("Type of gas: " + gastype);
                System.out.println("Total cost: $" + total_price);
                System.out.println("Amount pumped: " + total_gallons);
                System.out.println("********************************");

                //this is the function call to write to the file once the transaction is finished
                appendDataToFile(gastype, total_gallons, total_price);

                saveInventoryInFile(inventory);

            }
        }
    }
}