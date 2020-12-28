import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static final Scanner scanner = new Scanner(System.in);

    public static final Random random = new Random();

    public static HashMap<String, Account> accountsData = new HashMap<> ();

    public static void main(String[] args) {

        System.out.println();

        displayMainMenu();

    }

    public static void displayMainMenu(){

        int userInput = -1;

        do{

            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");

            try {
                if (scanner.hasNext()) {
                    userInput = scanner.nextInt();


                }
            } catch(Exception e) {
                System.out.println("Wrong Input");
                return;
            }


            System.out.println();

            switch (userInput){
                case 1:{

                    Account newAccount = new Account();

                    accountsData.put(newAccount.cardNumber,newAccount);

                    newAccount.displayDetails();

                    break;
                }
                case 2:{
                    logIn();
                    break;
                }

            }

        }while(userInput != 0);

        System.out.println("Bye!");


    }


    static public  void logIn(){

        System.out.println("Enter your card number:");
        String cardNumber = scanner.next();

        System.out.println("Enter your PIN:");
        int pin = scanner.nextInt();

        if (!isValidCardNumber(cardNumber.split(""))){
            System.out.println("\nWrong card number or PIN!\n");
            return;
        }

        Account fetchAccount = accountsData.get(cardNumber);

        if (fetchAccount != null) {

            if (fetchAccount.pin == pin){

                System.out.println("\nYou have successfully logged in!\n");

                int userInput = -1;

                do{

                    System.out.println("1. Balance\n" +
                            "2. Log out\n" +
                            "0. Exit");

                    try {
                        if (scanner.hasNext()) {
                            userInput = scanner.nextInt();
                        }
                    } catch(Exception e) {
                        System.out.println("Wrong Input");
                        break;
                    }

                    System.out.println();

                    switch (userInput){
                        case 1:{

                            fetchAccount.displayBalance();

                            break;

                        }
                        case 2:{

                            System.out.println("\nYou have successfully logged out!\n");

                            return;
                        }

                    }

                }while(userInput != 0);


                return;
            }

        }

        System.out.println("\nWrong card number or PIN!\n");

    }

    private static boolean isValidCardNumber(String[] cardNumber){

        try{
            int sum = 0;

            for (int i = 0; i < 16; i++) {
                if (i % 2 == 0){
                    sum += generateValidDigit(true,Integer.parseInt(cardNumber[i]));
                } else {
                    sum += generateValidDigit(false, Integer.parseInt(cardNumber[i]));
                }

            }

            return sum % 10 == 0;

        } catch (Exception e){
            return false;
        }


    }


    private static int generateValidDigit(boolean isOddPosition, int digit){

        if (isOddPosition) { // odd number
            digit *= 2;
        }

        return digit > 9 ? digit - 9 : digit;

    }



    static class Account{

        private final String cardNumber;

        private final long balance;

        private final int pin;

        public  Account(){

            balance = 0;

            cardNumber = generateCardNumber();

            pin = random.nextInt(9999 - 1000) + 1000;

        }

        private String generateCardNumber(){

            int[] uniqueNumber =new int[]{
                    random.nextInt(10),
                    random.nextInt(10),
                    random.nextInt(10),
                    random.nextInt(10),
                    random.nextInt(10),
                    random.nextInt(10),
                    random.nextInt(10),
                    random.nextInt(10),
                    random.nextInt(10)
            };

            int checksum = 10 - (8  +
                    (Main.generateValidDigit(true, uniqueNumber[0]) +
                            Main.generateValidDigit(false, uniqueNumber[1]) +
                            Main.generateValidDigit(true, uniqueNumber[2]) +
                            Main.generateValidDigit(false, uniqueNumber[3]) +
                            Main.generateValidDigit(true, uniqueNumber[4]) +
                            Main.generateValidDigit(false, uniqueNumber[5]) +
                            Main.generateValidDigit(true, uniqueNumber[6]) +
                            Main.generateValidDigit(false, uniqueNumber[7]) +
                            Main.generateValidDigit(true, uniqueNumber[8]))) % 10;

            return (
                    "4" +
                            "0" +
                            "0" +
                            "0" +
                            "0" +
                            "0" +
                            uniqueNumber[0] +
                            uniqueNumber[1] +
                            uniqueNumber[2] +
                            uniqueNumber[3] +
                            uniqueNumber[4] +
                            uniqueNumber[5] +
                            uniqueNumber[6] +
                            uniqueNumber[7] +
                            uniqueNumber[8] +
                            (checksum > 9 ? 0 : checksum));

        }

        public void displayDetails() {

            System.out.println("Your card has been created");

            System.out.println("Your card number:\n" + this.cardNumber + "\nYour card PIN:\n"+ this.pin + "\n");

        }

        public void displayBalance() {
            System.out.println("Balance: " + this.balance + "\n");
        }

    }


}