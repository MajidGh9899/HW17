package ir.maktab127;


import ir.maktab127.config.ApplicationContext;
import ir.maktab127.entities.Card;
import ir.maktab127.entities.Transaction;
import ir.maktab127.entities.User;
import ir.maktab127.repositories.CardRepository;
import ir.maktab127.repositories.TransactionRepository;
import ir.maktab127.repositories.UserRepository;
import ir.maktab127.services.CardService;
import ir.maktab127.services.TransactionService;
import ir.maktab127.services.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static ApplicationContext context = ApplicationContext.getInstance();
    private static UserRepository userRepository = new UserRepository(context);
    private static CardRepository cardRepository = new CardRepository(context);
    private static TransactionRepository transactionRepository = new TransactionRepository(context);

    private static UserService userService = new UserService(userRepository);
    private static CardService cardService = new CardService(cardRepository);
    private static TransactionService transactionService = new TransactionService(transactionRepository, cardRepository);

    public static void main(String[] args) {
        try {

            userRepository.createUserTable();
            cardRepository.createCardTable();
            transactionRepository.createTransactionTable();


            while (true) {
                System.out.println("===== Bank Management System =====");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        registerUser();
                        break;
                    case 2:
                        loginUser();
                        break;
                    case 3:
                        System.out.println("Good buy");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerUser() throws Exception {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new User(username, password);
        userService.register(user);
        System.out.println("User registered successfully!");
    }

    private static void loginUser() throws Exception {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);
        if (user == null) {
            System.out.println("Invalid username or password.");
            return;
        }

        System.out.println("Login successful!");
        showUserMenu(user);
    }

    private static void showUserMenu(User user) throws Exception {
        while (true) {
            System.out.println("\n===== User Menu =====");
            System.out.println("1. Card Operations");
            System.out.println("2. Financial Operations");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showCardMenu(user);
                    break;
                case 2:
                    showFinancialMenu(user);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showCardMenu(User user) throws Exception {
        while (true) {
            System.out.println("\n===== Card Operations =====");
            System.out.println("1. Add Card");
            System.out.println("2. Delete Card");
            System.out.println("3. Show Card by Number");
            System.out.println("4. Show Cards by Bank Name");
            System.out.println("5. Show All Cards");
            System.out.println("6. Back to User Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addCard(user);
                    break;
                case 2:
                    deleteCard();
                    break;
                case 3:
                    showCardByNumber();
                    break;
                case 4:
                    showCardsByBank();
                    break;
                case 5:
                    showAllCards();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addCard(User user) throws Exception {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Enter bank name: ");
        String bankName = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        Card card = new Card(user.getId(), cardNumber, bankName, balance);
        cardService.addCard(card);
        System.out.println("Card added successfully!");
    }

    private static void deleteCard() throws Exception {
        System.out.print("Enter card number to delete: ");
        String cardNumber = scanner.nextLine();

        cardService.deleteCard(cardNumber);
        System.out.println("Card deleted successfully!");
    }

    private static void showCardByNumber() throws Exception {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();

        Card card = cardService.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found.");
        } else {
            System.out.println("Card Details:");
            System.out.println("Card Number: " + card.getCardNumber());
            System.out.println("Bank Name: " + card.getBankName());
            System.out.println("Balance: " + card.getBalance());
        }
    }

    private static void showCardsByBank() throws Exception {
        System.out.print("Enter bank name: ");
        String bankName = scanner.nextLine();

        List<Card> cards = cardService.getCardsByBank(bankName);
        if (cards.isEmpty()) {
            System.out.println("No cards found for this bank.");
        } else {
            System.out.println("Cards in " + bankName + ":");
            for (Card card : cards) {
                System.out.println("Card Number: " + card.getCardNumber() + ", Balance: " + card.getBalance());
            }
        }
    }

    private static void showAllCards() throws Exception {
        List<Card> cards = cardService.getAllCards();
        if (cards.isEmpty()) {
            System.out.println("No cards found.");
        } else {
            System.out.println("All Cards:");
            for (Card card : cards) {
                System.out.println("Card Number: " + card.getCardNumber() + ", Bank: " + card.getBankName() + ", Balance: " + card.getBalance());
            }
        }
    }

    private static void showFinancialMenu(User user) throws Exception {
        while (true) {
            System.out.println("\n===== Financial Operations =====");
            System.out.println("1. Normal Transfer (Card to Card)");
            System.out.println("2. Paya Transfer (Individual)");
            System.out.println("3. Paya group Transfer");
            System.out.println("4. Satna Transfer");
            System.out.println("5. View Transaction History");
            System.out.println("6. Back to User Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Transfer("normal");
                    break;
                case 2:
                    Transfer("paya");
                    break;
                case 3:
                    Transfer("groupPaya");
                    break;
                case 4:
                    Transfer("satna");
                    break;
                case 5:
                    viewTransactionHistory(user);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewTransactionHistory(User user) throws Exception {
        System.out.print("Enter your card number: ");
        String cardNumber = scanner.nextLine();

        List<Transaction> transactions = transactionService.getTransactionHistory(cardNumber);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this card.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
                System.out.println("-----------------------------");
            }
        }
    }
    private static void Transfer(String type) throws Exception {
        System.out.print("Enter source card number: ");
        String fromCard = scanner.nextLine();
        System.out.print("Enter destination card number: ");
        String toCard = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        transactionService.transfer(fromCard, toCard, amount, type);
        System.out.println(type+" transfer completed successfully!");
    }



    private static void payaGroupTransfer() throws Exception {
        System.out.println("Group transfers  not implemented .");
    }


}