package cp213;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utilities for working with Food objects.
 *
 * @author Sara Aljaafari, 169044425, alja4425@mylaurier.ca
 * @version 2023-10-15
 */
public class FoodUtilities {

    /**
     * Determines the average calories in a list of foods. No rounding necessary.
     * Foods list parameter may be empty.
     *
     * @param foods a list of Food
     * @return average calories in all Food objects
     */
    public static int averageCalories(final ArrayList<Food> foods) {
    	if (foods.isEmpty()) {
            return 0; // 0 if the list is empty to avoid division by 0.
        }

        int totalCalories = 0;

        for (Food food : foods) {
            totalCalories += food.getCalories();
        }
        int average = totalCalories / foods.size();

        return average;
    }

    /**
     * Determines the average calories in a list of foods for a particular origin.
     * No rounding necessary. Foods list parameter may be empty.
     *
     * @param foods  a list of Food
     * @param origin the origin of the Food
     * @return average calories for all Foods of the specified origin
     */
    public static int averageCaloriesByOrigin(final ArrayList<Food> foods, final int origin) {
    	if (foods.isEmpty()) {
            return 0; // 0 if the list is empty to avoid division by 0.
        }

        int totalCalories = 0;
        int count = 0; 
        for (Food food : foods) {
            if (food.getOrigin() == origin) {
                totalCalories += food.getCalories();
                count++;
            }
        }

        if (count == 0) {
            return 0; // 0 if there are no matching foods to avoid division by 0.
        }

        int average = totalCalories / count;

        return average;
    }

    /**
     * Creates a list of foods by origin.
     *
     * @param foods  a list of Food
     * @param origin a food origin
     * @return a list of Food from origin
     */
    public static ArrayList<Food> getByOrigin(final ArrayList<Food> foods, final int origin) {
    	ArrayList<Food> foodsFromOrigin = new ArrayList<>();

        for (Food food : foods) {
            if (food.getOrigin() == origin) {
                foodsFromOrigin.add(food);
            }
        }

        return foodsFromOrigin;
    }

    /**
     * Creates a Food object by requesting data from a user. Uses the format:
     *
     * <pre>
    Name: name
    Origins
     0 Canadian
     1 Chinese
    ...
    11 English
    Origin: origin number
    Vegetarian (Y/N): Y/N
    Calories: calories
     * </pre>
     *
     * @param keyboard a keyboard Scanner
     * @return a Food object
     */
    public static Food getFood(final Scanner keyboard) {
    	System.out.print("Name: ");
        String name = keyboard.nextLine();

        System.out.println("Origins");
        for (int i = 0; i < Food.ORIGINS.length; i++) {
            System.out.printf("%2d %s%n", i, Food.ORIGINS[i]);
        }

        System.out.print("Origin: ");
        int origin = keyboard.nextInt();
        keyboard.nextLine();

        System.out.print("Vegetarian (Y/N): ");
        String vegetarianInput = keyboard.nextLine();
        boolean isVegetarian = "Y".equalsIgnoreCase(vegetarianInput);

        System.out.print("Calories: ");
        int calories = keyboard.nextInt();
        keyboard.nextLine();

        return new Food(name, origin, isVegetarian, calories);
    }

    /**
     * Creates a list of vegetarian foods.
     *
     * @param foods a list of Food
     * @return a list of vegetarian Food
     */
    public static ArrayList<Food> getVegetarian(final ArrayList<Food> foods) {
    	ArrayList<Food> veggie = new ArrayList<>();

        for (Food food : foods) {
            if (food.isVegetarian()) {
                veggie.add(food);
            }
        }

        return veggie;
    }

    /**
     * Creates and returns a Food object from a line of string data.
     *
     * @param line a vertical bar-delimited line of food data in the format
     *             name|origin|isVegetarian|calories
     * @return the data from line as a Food object
     */
    public static Food readFood(final String line) {
    	String[] temp = line.split("\\|");
        
    	String name = temp[0];
    	int origin = Integer.parseInt(temp[1]);
    	boolean isVegetarian = Boolean.parseBoolean(temp[2]);
    	int calories = Integer.parseInt(temp[3]);
    	
    	Food food = new Food(name, origin, isVegetarian, calories);
    	return food;
    }

    /**
     * Reads a file of food strings into a list of Food objects.
     *
     * @param fileIn a Scanner of a Food data file in the format
     *               name|origin|isVegetarian|calories
     * @return a list of Food
     */
    public static ArrayList<Food> readFoods(final Scanner fileIn) {
    	ArrayList<Food> foods = new ArrayList<>();

        while (fileIn.hasNextLine()) {
            String line = fileIn.nextLine();
            Food food = readFood(line);
            foods.add(food);
        }

        return foods;
    }

    /**
     * Searches for foods that fit certain conditions.
     *
     * @param foods        a list of Food
     * @param origin       the origin of the food; if -1, accept any origin
     * @param maxCalories  the maximum calories for the food; if 0, accept any
     * @param isVegetarian whether the food is vegetarian or not; if false accept
     *                     any
     * @return a list of foods that fit the conditions specified
     */
    public static ArrayList<Food> foodSearch(final ArrayList<Food> foods, final int origin, final int maxCalories,
    		final boolean isVegetarian) {
    	ArrayList<Food> food_search = new ArrayList<>();

        if (!(foods.isEmpty())) {
        	for (Food food: foods) {
        		if ((food.getOrigin()==origin || origin == -1) && (food.getCalories() !=0)
        			&& (food.isVegetarian() == isVegetarian)) {
        			food_search.add(food);
        			}
        	}
        }
        return food_search;
    }

    /**
     * Writes the contents of a list of Food to a PrintStream.
     *
     * @param foods a list of Food
     * @param ps    the PrintStream to write to
     */
    public static void writeFoods(final ArrayList<Food> foods, PrintStream ps) {
    	for (Food food : foods) {
            food.write(ps);
        }
    }
}
