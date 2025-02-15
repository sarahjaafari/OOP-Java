package cp213;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Wraps around an Order object to ask for MenuItems and quantities.
 *
 * @author Sara Aljaafari
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2023-12-07
 */
public class Cashier {

    // Attributes
    private Menu menu = null;

    /**
     * Constructor.
     *
     * @param menu A Menu.
     */
    public Cashier(Menu menu) {
	this.menu = menu;
    }

    /**
     * Prints the menu.
     */
    private void printCommands() {
	System.out.println("\nMenu:");
	System.out.println(menu.toString());
	System.out.println("Press 0 when done.");
	System.out.println("Press any other key to see the menu again.\n");
    }

    /**
     * Asks for commands and quantities. Prints a receipt when all orders have been
     * placed.
     *
     * @return the completed Order.
     */
    public Order takeOrder() {
	System.out.println("Welcome to WLU Foodorama!");
	
	int command = -1;
    Order order = new Order();
    printCommands();

    try (Scanner input = new Scanner(System.in)) {
        while (command != 0) {
            int q = 0;
            System.out.print("Command: ");
            try {
                command = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Not a valid number");
                continue;
            } finally {
                input.nextLine();
            }

            if (command == 0) {
            } else if (command < 0 || command > menu.size()) {
                printCommands();
            } else {
                while (q == 0) {
                    System.out.print("How many do you want? ");
                    try {
                        q = input.nextInt();
                        if (q <= 0) {
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Not a valid number");
                    } finally {
                        input.nextLine();
                    }
                }

                if (q > 0) {
                    order.add(menu.getItem(command - 1), q);
                }
            }
        }
    }

    // prob shouldnt be in here maybe?
    System.out.println(order);

    return order;
    }
}