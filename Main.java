import java.util.Scanner;

class BankBalance {
    private double balance;

    public BankBalance(double initialBalance) {
        balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Insufficient funds!");
            return false;
        }
    }
}

class ATM {
    private BankBalance account;
    private Scanner scanner;

    public ATM(BankBalance account) {
        this.account = account;
        scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: RUPEE " + account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: RUPEE");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Deposit successful!");
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: RUPEE");
                    double withdrawAmount = scanner.nextDouble();
                    if (account.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawal successful!");
                    }
                    break;
                case 4:
                    System.out.println("Exiting ATM. Have a nice day!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BankBalance bankAccount = new BankBalance(1000);
        ATM atm = new ATM(bankAccount);
        atm.run();
    }
}
