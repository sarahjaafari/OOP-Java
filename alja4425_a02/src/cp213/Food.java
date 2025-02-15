package cp213;

import java.io.PrintStream;

/**
 * Food class definition.
 *
 * @author Sara Aljaafari, 169044425, alja4425@mylaurier.ca
 * @version 2023-10-15
 */
public class Food implements Comparable<Food> {

    // Constants
    public static final String ORIGINS[] = { "Canadian", "Chinese", "Indian", "Ethiopian", "Mexican", "Greek",
	    "Japanese", "Italian", "Moroccan", "Scottish", "Columbian", "English" };

    /**
     * Creates a string of food origins in the format:
     *
     * <pre>
    Origins
    0 Canadian
    1 Chinese
    ...
    11 English
     * </pre>
     *
     * @return A formatted numbered string of valid food origins.
     */
    public static String originsMenu() {
    	StringBuilder menu = new StringBuilder("Origins\n");

        for (int i = 0; i < ORIGINS.length; i++) {
            menu.append(i).append(" ").append(ORIGINS[i]).append("\n");
        }

        return menu.toString();
    }

    // Attributes
    private String name = null;
    private int origin = 0;
    private boolean isVegetarian = false;
    private int calories = 0;

    /**
     * Food constructor.
     *
     * @param name         food name
     * @param origin       food origin code
     * @param isVegetarian whether food is vegetarian
     * @param calories     caloric content of food
     */
    public Food(final String name, final int origin, final boolean isVegetarian, final int calories) {
    	this.name = name;
        this.origin = origin;
        this.isVegetarian = isVegetarian;
        this.calories = calories;
    }

    /*
     * (non-Javadoc) Compares this food against another food.
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    /**
     * Foods are compared by name, then by origin if the names match. Must ignore
     * case.
     */
    @Override
    public int compareTo(final Food target) {
    	int nameComparison = this.name.compareToIgnoreCase(target.name);

        // If the names are the same, compare by origin
        if (nameComparison == 0) {
            return Integer.compare(this.origin, target.origin);
        }

        return nameComparison;
    }

    /**
     * Getter for calories attribute.
     *
     * @return calories
     */
    public int getCalories() {

	return calories;
    }

    /**
     * Getter for name attribute.
     *
     * @return name
     */
    public String getName() {

	return name;
    }

    /**
     * Getter for origin attribute.
     *
     * @return origin
     */
    public int getOrigin() {

	return origin;
    }

    /**
     * Getter for string version of origin attribute.
     *
     * @return string version of origin
     */
    public String getOriginString() {
    	 if (origin >= 0 && origin < ORIGINS.length) {
    	        return ORIGINS[origin];
    	    } else {
    	        return "Unknown";
    	    }
    }

    /**
     * Getter for isVegetarian attribute.
     *
     * @return isVegetarian
     */
    public boolean isVegetarian() {

	return isVegetarian;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object//toString() Creates a formatted string of food data.
     */
    /**
     * Returns a string version of a Food object in the form:
     *
     * <pre>
    Name:       name
    Origin:     origin string
    Vegetarian: true/false
    Calories:   calories
     * </pre>
     */
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
        sb.append("Name:       ").append(name).append("\n");
        sb.append("Origin:     ").append(getOriginString()).append("\n");
        sb.append("Vegetarian: ").append(isVegetarian).append("\n");
        sb.append("Calories:   ").append(calories);
        return sb.toString();
    }

    /**
     * Writes a single line of food data to an open PrintStream. The contents of
     * food are written as a string in the format name|origin|isVegetarian|calories
     * to ps.
     *
     * @param ps The PrintStream to write to.
     */
    public void write(final PrintStream ps) {
    	String foodData = name + "|" + origin + "|" + isVegetarian + "|" + calories;

        ps.println(foodData);
    }

}
