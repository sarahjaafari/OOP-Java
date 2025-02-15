package cp213;

/**
 * A single linked list structure of <code>Node T</code> objects. These data
 * objects must be Comparable - i.e. they must provide the compareTo method.
 * Only the <code>T</code> value contained in the priority queue is visible
 * through the standard priority queue methods. Extends the
 * <code>SingleLink</code> class.
 *
 * @author Sara Aljaafari, alja4425@mylaurier.ca, 169044425
 * @version 2023-11-01
 * @param <T> this SingleList data type.
 */
public class SingleList<T extends Comparable<T>> extends SingleLink<T> {

    /**
     * Searches for the first occurrence of key in this SingleList. Private helper
     * methods - used only by other ADT methods.
     *
     * @param key The value to look for.
     * @return A pointer to the node previous to the node containing key.
     */
    private SingleNode<T> linearSearch(final T key) {
    	SingleNode<T> current = front;
        SingleNode<T> previous = null;

        while (current != null) {
            if (current.getItem().compareTo(key) == 0) {
                // Key found, return the previous node
                return previous;
            }

            previous = current;
            current = current.getNext();
        }

        // Key not found in the list
        return null;
    }

    /**
     * Appends data to the end of this SingleList.
     *
     * @param data The value to append.
     */
    public void append(final T data) {
    	SingleNode<T> newNode = new SingleNode<T>(data, null);

        if (isEmpty()) {
            // If the list is empty, the new node becomes the front and rear node
            front = newNode;
            rear = newNode;
        } else {
            // Link the new node to the current rear node and update the rear to be the new node
            rear.setNext(newNode);
            rear = newNode;
        }

        length++;
    }

    /**
     * Removes duplicates from this SingleList. The list contains one and only one
     * of each value formerly present in this SingleList. The first occurrence of
     * each value is preserved.
     */
    public void clean() {
    	if (isEmpty() || length == 1) {
            // No duplicates to remove if the list is empty or contains only one element
            return;
        }

        SingleNode<T> current = front;

        while (current != null) {
            SingleNode<T> runner = current;

            // Iterate through the list with a "runner" node to find and remove duplicates
            while (runner.getNext() != null) {
                if (current.getItem().equals(runner.getNext().getItem())) {
                    // Duplicate found, remove the duplicate node
                    runner.setNext(runner.getNext().getNext());
                    length--; // Decrement the list length
                } else {
                    runner = runner.getNext();
                }
            }

            current = current.getNext();
        }
    }

    /**
     * Combines contents of two lists into a third. Values are alternated from the
     * origin lists into this SingleList. The origin lists are empty when finished.
     * NOTE: data must not be moved, only nodes.
     *
     * @param left  The first list to combine with this SingleList.
     * @param right The second list to combine with this SingleList.
     */
    public void combine(final SingleList<T> left, final SingleList<T> right) {
    	front = null;
        rear = null;
        length = 0;

        // Combine nodes from the left and right lists alternately into the current list
        SingleNode<T> leftCurrent = left.front;
        SingleNode<T> rightCurrent = right.front;

        while (leftCurrent != null || rightCurrent != null) {
            if (leftCurrent != null) {
                append(leftCurrent.getItem());
                leftCurrent = leftCurrent.getNext();
            }
            if (rightCurrent != null) {
                append(rightCurrent.getItem());
                rightCurrent = rightCurrent.getNext();
            }
        }

        // Clear the left and right lists
        left.front = null;
        left.rear = null;
        left.length = 0;

        right.front = null;
        right.rear = null;
        right.length = 0;
    }

    /**
     * Determines if this SingleList contains key.
     *
     * @param key The key value to look for.
     * @return true if key is in this SingleList, false otherwise.
     */
    public boolean contains(final T key) {
    	SingleNode<T> current = front;

        while (current != null) {
            if (current.getItem().equals(key)) {
                // Found the key in the list
                return true;
            }
            current = current.getNext();
        }

        // Key was not found in the list
        return false;
    }

    /**
     * Finds the number of times key appears in list.
     *
     * @param key The value to look for.
     * @return The number of times key appears in this SingleList.
     */
    public int count(final T key) {
    	int count = 0;
        SingleNode<T> current = front;

        while (current != null) {
            if (current.getItem().equals(key)) {
                // Found a node with the key, increment the count
                count++;
            }
            current = current.getNext();
        }

        return count;
    }

    /**
     * Finds and returns the value in list that matches key.
     *
     * @param key The value to search for.
     * @return The value that matches key, null otherwise.
     */
    public T find(final T key) {
    	SingleNode<T> current = front;

        while (current != null) {
            if (current.getItem().equals(key)) {
                // Found a node with the matching key, return its value
                return current.getItem();
            }
            current = current.getNext();
        }

        // Key was not found in the list, return null
        return null;
    }

    /**
     * Get the nth item in this SingleList.
     *
     * @param n The index of the item to return.
     * @return The nth item in this SingleList.
     * @throws ArrayIndexOutOfBoundsException if n is not a valid index.
     */
    public T get(final int n) throws ArrayIndexOutOfBoundsException {
    	if (n < 0 || n >= length) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + n);
        }

        SingleNode<T> current = front;
        for (int i = 0; i < n; i++) {
            current = current.getNext();
        }

        return current.getItem();
    }

    /**
     * Determines whether two lists are identical.
     *
     * @param source The list to compare against this SingleList.
     * @return true if this SingleList contains the same values in the same order as
     *         source, false otherwise.
     */
    public boolean identical(final SingleList<T> source) {
    	SingleNode<T> current1 = front;
        SingleNode<T> current2 = source.front;

        while (current1 != null && current2 != null) {
            if (!current1.getItem().equals(current2.getItem())) {
                // Values at the current positions are not equal, lists are not identical
                return false;
            }
            current1 = current1.getNext();
            current2 = current2.getNext();
        }

        // Lists are identical only if both iterators reach the end at the same time
        return current1 == null && current2 == null;
    }

    /**
     * Finds the first location of a value by key in this SingleList.
     *
     * @param key The value to search for.
     * @return The index of key in this SingleList, -1 otherwise.
     */
    public int index(final T key) {
    	int index = 0;
        SingleNode<T> current = front;

        while (current != null) {
            if (current.getItem().equals(key)) {
                // Found the key, return its index
                return index;
            }
            current = current.getNext();
            index++;
        }

        // Key was not found in the list, return -1
        return -1;
    }

    /**
     * Inserts value into this SingleList at index i. If i greater than the length
     * of this SingleList, append data to the end of this SingleList.
     *
     * @param i    The index to insert the new data at.
     * @param data The new value to insert into this SingleList.
     */
    public void insert(int i, final T data) {
    	if (i < 0) {
            throw new IllegalArgumentException("Index must be non-negative.");
        }

        // If i is greater than or equal to the length of the list, append data to the end
        if (i >= length) {
            append(data);
            return;
        }

        // Create a new node with the provided data
        SingleNode<T> newNode = new SingleNode<T>(data, null);

        // Special case: Inserting at the beginning
        if (i == 0) {
            newNode.setNext(front);
            front = newNode;
            length++;
            return;
        }

        // Traverse the list to find the node at position i-1
        SingleNode<T> current = front;
        for (int index = 0; index < i - 1; index++) {
            if (current == null) {
                // Handle the case where i is out of bounds
                throw new IllegalArgumentException("Index is out of bounds.");
            }
            current = current.getNext();
        }

        // Insert the new node after the node at position i-1
        newNode.setNext(current.getNext());
        current.setNext(newNode);

        // Increase the length of the list
        length++;
    }

    /**
     * Creates an intersection of two other SingleLists into this SingleList. Copies
     * data to this SingleList. left and right SingleLists are unchanged. Values
     * from left are copied in order first, then values from right are copied in
     * order.
     *
     * @param left  The first SingleList to create an intersection from.
     * @param right The second SingleList to create an intersection from.
     */
    public void intersection(final SingleList<T> left, final SingleList<T> right) {
    	clear();

        // Copy values from the left list in order
        SingleNode<T> leftCurrent = left.front;
        while (leftCurrent != null) {
            append(leftCurrent.getItem());
            leftCurrent = leftCurrent.getNext();
        }

        // Copy values from the right list in order
        SingleNode<T> rightCurrent = right.front;
        while (rightCurrent != null) {
            append(rightCurrent.getItem());
            rightCurrent = rightCurrent.getNext();
        }
    }

    /**
     * Finds the maximum value in this SingleList.
     *
     * @return The maximum value.
     */
    public T max() {
    	if (isEmpty()) {
            // Return null if the list is empty
            return null;
        }

        // Initialize max to the first value in the list
        T max = front.getItem();
        SingleNode<T> current = front.getNext();

        while (current != null) {
            // Compare the current value with the current max
            if (current.getItem().compareTo(max) > 0) {
                // If the current value is greater, update max
                max = current.getItem();
            }
            current = current.getNext();
        }

        return max;
    }

    /**
     * Finds the minimum value in this SingleList.
     *
     * @return The minimum value.
     */
    public T min() {
    	if (isEmpty()) {
            // Return null if the list is empty
            return null;
        }

        // Initialize min to the first value in the list
        T min = front.getItem();
        SingleNode<T> current = front.getNext();

        while (current != null) {
            // Compare the current value with the current min
            if (current.getItem().compareTo(min) < 0) {
                // If the current value is smaller, update min
                min = current.getItem();
            }
            current = current.getNext();
        }

        return min;
    }

    /**
     * Inserts value into the front of this SingleList.
     *
     * @param data The value to insert into the front of this SingleList.
     */
    public void prepend(final T data) {
    	SingleNode<T> newNode = new SingleNode<T>(data, null);

        if (isEmpty()) {
            // If the list is empty, the new node becomes the front node
            front = newNode;
        } else {
            // Link the new node to the current front node
            newNode.setNext(front);
            // Update the front node to be the new node
            front = newNode;
        }

        // Increment the length of the list
        length++;
    }

    /**
     * Finds, removes, and returns the value in this SingleList that matches key.
     *
     * @param key The value to search for.
     * @return The value matching key, null otherwise.
     */
    public T remove(final T key) {
    	if (isEmpty()) {
            // The list is empty, so there's nothing to remove
            return null;
        }

        if (front.getItem().equals(key)) {
            // If the front node contains the key, remove and return it
            T removedValue = front.getItem();
            front = front.getNext();
            length--;
            return removedValue;
        }

        SingleNode<T> current = front;
        while (current.getNext() != null) {
            // Check if the next node contains the key
            if (current.getNext().getItem().equals(key)) {
                T removedValue = current.getNext().getItem();
                // Remove the node by updating the next reference
                current.setNext(current.getNext().getNext());
                length--;
                return removedValue;
            }
            current = current.getNext();
        }

        // Key not found in the list
        return null;
    }

    /**
     * Removes the value at the front of this SingleList.
     *
     * @return The value at the front of this SingleList.
     */
    public T removeFront() {
    	if (isEmpty()) {
            // The list is empty, so there's nothing to remove
            return null;
        }

        // Get the value at the front of the list
        T removedValue = front.getItem();
        
        // Update the front reference to the next node
        front = front.getNext();
        
        length--;

        return removedValue;
    }

    /**
     * Finds and removes all values in this SingleList that match key.
     *
     * @param key The value to search for.
     */
    public void removeMany(final T key) {
    	SingleNode<T> current = front;
        SingleNode<T> previous = null;

        while (current != null) {
            if (current.getItem().equals(key)) {
                // Match found, remove the node
                if (previous != null) {
                    previous.setNext(current.getNext());
                } else {
                    // If the match is at the front, update the front reference
                    front = current.getNext();
                }
                length--;
            } else {
                // Move to the next node
                previous = current;
            }
            current = current.getNext();
        }
    }

    /**
     * Reverses the order of the values in this SingleList.
     */
    public void reverse() {
    	SingleNode<T> current = front;
        SingleNode<T> previous = null;
        SingleNode<T> next = null;

        while (current != null) {
            next = current.getNext();  // Store the next node reference
            current.setNext(previous); // Reverse the link
            previous = current;
            current = next; // Move to the next node
        }

        // Update the front reference to the new first node
        front = previous;
    }

    /**
     * Splits the contents of this SingleList into the left and right SingleLists.
     * Moves nodes only - does not move value or call the high-level methods insert
     * or remove. this SingleList is empty when done. The first half of this
     * SingleList is moved to left, and the last half of this SingleList is moved to
     * right. If the resulting lengths are not the same, left should have one more
     * item than right. Order is preserved.
     *
     * @param left  The first SingleList to move nodes to.
     * @param right The second SingleList to move nodes to.
     */
    public void split(final SingleList<T> left, final SingleList<T> right) {
    	int originalLength = this.getLength();

        // Calculate the length of the left and right lists
        int leftLength = originalLength / 2;
        int rightLength = originalLength - leftLength;

        // Initialize pointers for splitting
        SingleNode<T> current = this.front;
        SingleNode<T> previous = null;

        // Move nodes from the original list to the left list
        for (int i = 0; i < leftLength; i++) {
            if (current != null) {
                left.append(current.getItem());
                previous = current;
                current = current.getNext();
            }
        }

        // Update the front of the original list to the current node
        this.front = current;

        // Move nodes from the original list to the right list
        right.front = current;
        right.rear = previous;
        right.length = rightLength;

        // Clear the original list
        this.front = null;
        this.rear = null;
        this.length = 0;
    }

    /**
     * Splits the contents of this SingleList into the left and right SingleLists.
     * Moves nodes only - does not move value or call the high-level methods insert
     * or remove. this SingleList is empty when done. Nodes are moved alternately
     * from this SingleList to left and right. Order is preserved.
     *
     * @param left  The first SingleList to move nodes to.
     * @param right The second SingleList to move nodes to.
     */
    public void splitAlternate(final SingleList<T> left, final SingleList<T> right) {
    	SingleNode<T> leftCurrent = null;
        SingleNode<T> rightCurrent = null;

        // Traverse the original list and move nodes alternately to the left and right lists
        SingleNode<T> current = this.front;
        boolean isLeftTurn = true;

        while (current != null) {
            if (isLeftTurn) {
                // Move the node to the left list
                if (leftCurrent == null) {
                    left.front = current;
                    leftCurrent = current;
                } else {
                    leftCurrent.setNext(current);
                    leftCurrent = current;
                }
                left.length++;
            } else {
                // Move the node to the right list
                if (rightCurrent == null) {
                    right.front = current;
                    rightCurrent = current;
                } else {
                    rightCurrent.setNext(current);
                    rightCurrent = current;
                }
                right.length++;
            }

            // Move to the next node in the original list
            current = current.getNext();
            isLeftTurn = !isLeftTurn;
        }

        // Clear the original list
        this.front = null;
        this.rear = null;
        this.length = 0;
    }
    
    /**
     * Clears the contents of this SingleList, making it empty.
     */
    public void clear() {
        front = null;
        length = 0;
    }

    /**
     * Creates a union of two other SingleLists into this SingleList. Copies value
     * to this list. left and right SingleLists are unchanged. Values from left are
     * copied in order first, then values from right are copied in order.
     *
     * @param left  The first SingleList to create a union from.
     * @param right The second SingleList to create a union from.
     */
    public void union(final SingleList<T> left, final SingleList<T> right) {
    	clear();

        // Traverse the left list and copy its values to this list
        SingleNode<T> leftCurrent = left.front;
        while (leftCurrent != null) {
            append(leftCurrent.getItem());
            leftCurrent = leftCurrent.getNext();
        }

        // Traverse the right list and copy its values to this list
        SingleNode<T> rightCurrent = right.front;
        while (rightCurrent != null) {
            append(rightCurrent.getItem());
            rightCurrent = rightCurrent.getNext();
        }
    }
}
