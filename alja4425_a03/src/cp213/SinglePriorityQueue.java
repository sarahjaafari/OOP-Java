package cp213;

/**
 * A single linked priority queue structure of <code>Node T</code> objects.
 * These data objects must be Comparable - i.e. they must provide the compareTo
 * method. Only the <code>T</code> data contained in the priority queue is
 * visible through the standard priority queue methods. Extends the
 * <code>SingleLink</code> class.
 *
 * @author Sara Aljaafari, alja4425@mylaurier.ca, 169044425
 * @version 2023-11-01
 * @param <T> the SinglePriorityQueue data type.
 */
public class SinglePriorityQueue<T extends Comparable<T>> extends SingleLink<T> {

    /**
     * Combines the contents of the left and right SinglePriorityQueues into the
     * current SinglePriorityQueue. Moves nodes only - does not move value or call
     * the high-level methods insert or remove. left and right SinglePriorityQueues
     * are empty when done. Nodes are moved alternately from left and right to this
     * SinglePriorityQueue. When finished all nodes must be in priority order from
     * front to rear.
     *
     * Do not use the SinglePriorityQueue insert and remove methods.
     *
     * Do not assume that both right and left priority queues are of the same
     * length.
     *
     * @param left  The first SinglePriorityQueue to extract nodes from.
     * @param right The second SinglePriorityQueue to extract nodes from.
     */
    public void combine(final SinglePriorityQueue<T> left, final SinglePriorityQueue<T> right) {
    	assert this.front == null : "May combine into an empty Priority Queue only";

        // Initialize a flag to determine which source queue to move the node from
        boolean moveFromLeft = true;

        while (!left.isEmpty() || !right.isEmpty()) {
            // Determine which source queue to move the node from
            SinglePriorityQueue<T> sourceQueue = moveFromLeft ? left : right;

            if (!sourceQueue.isEmpty()) {
                // If the source queue is not empty, move the front node to the current queue
                moveFrontToRear(sourceQueue);
            }

            // Flip the flag to alternate between left and right source queues
            moveFromLeft = !moveFromLeft;
        }
    }

    /**
     * Adds value to the SinglePriorityQueue. Data is stored in priority order, with
     * highest priority value at the front of the SinglePriorityQueue, and lowest at
     * the rear. Priority is determined by simple comparison - lower values have
     * higher priority. For example, 1 has a higher priority than 2 because 1 is a
     * lower value than 2. (Think of the phrase, "We're number one!" as an
     * indication of priority.)
     *
     * When inserting value to the priority queue, the queue must remain sorted.
     * Hence you need to find the proper location of inserting value. use the head
     * pointer to go through the queue. e.g., use SingleNode&lt;T&gt; current =
     * this.head;
     *
     * use current = current.getNext(); to traverse the queue.
     *
     * To get access to the value inside a node of queue use current.getValue().
     *
     * @param data value to insert in sorted order in priority queue.
     */
    public void insert(final T data) {
    	SingleNode<T> newNode = new SingleNode<T>(data, null);

        if (isEmpty() || data.compareTo(front.getItem()) <= 0) {
            // If the queue is empty or the new data has higher or equal priority than the front
            // Insert the new node at the front
            newNode.setNext(front);
            front = newNode;
        } else {
            // Use two pointers to traverse the queue and find the proper location for insertion
            SingleNode<T> current = front;
            SingleNode<T> previous = null;

            while (current != null && data.compareTo(current.getItem()) > 0) {
                previous = current;
                current = current.getNext();
            }

            // Insert the new node between previous and current
            previous.setNext(newNode);
            newNode.setNext(current);
        }

        length++;
    }

    /**
     * Returns the highest priority value in the SinglePriorityQueue. Decrements the
     * count.
     *
     * @return the highest priority value currently in the SinglePriorityQueue.
     */
    public T remove() {
    	if (isEmpty()) {
            // If the queue is empty, return null (or handle the error as needed)
            return null;
        }

        // Get the highest priority value from the front of the queue
        T highestPriorityValue = front.getItem();

        // Remove the front node by updating the front reference
        front = front.getNext();

        length--; // Decrement the queue count

        return highestPriorityValue;
    }

    /**
     * Splits the contents of this SinglePriorityQueue into the higher and lower
     * SinglePriorityQueue. Moves nodes only - does not move value or call the
     * high-level methods insert or remove. this SinglePriorityQueue is empty when
     * done. Nodes with priority value higher than key are moved to the
     * SinglePriorityQueue higher. Nodes with a priority value lower than or equal
     * to key are moved to the SinglePriorityQueue lower.
     *
     * Do not use the SinglePriorityQueue insert and remove methods.
     *
     * @param key    value to compare against node values in SinglePriorityQueue
     * @param higher an initially empty SinglePriorityQueue queue that ends up with
     *               all values with priority higher than key.
     * @param lower  an initially empty SinglePriorityQueue queue that ends up with
     *               all values with priority lower than or equal to key.
     */
    public void splitByKey(final T key, final SinglePriorityQueue<T> higher, final SinglePriorityQueue<T> lower) {
    	while (!isEmpty()) {
            T value = remove(); // Remove the highest priority value from the current queue

            if (value.compareTo(key) > 0) {
                // If the value has higher priority than the key, move it to the higher priority queue
                higher.insert(value);
            } else {
                // If the value has lower or equal priority to the key, move it to the lower priority queue
                lower.insert(value);
            }
        }
    }
}
