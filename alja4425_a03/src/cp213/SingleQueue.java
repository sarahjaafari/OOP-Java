package cp213;

/**
 * A single linked queue structure of <code>Node T</code> objects. Only the
 * <code>T</code> value contained in the queue is visible through the standard
 * queue methods. Extends the <code>SingleLink</code> class.
 *
 * @author Sara Aljaafari, alja4425@mylaurier.ca, 169044425
 * @version 2023-11-01
 * @param <T> the SingleQueue data type.
 */
public class SingleQueue<T> extends SingleLink<T> {

    /**
     * Combines the contents of the left and right SingleQueues into the current
     * SingleQueue. Moves nodes only - does not refer to values in any way, or call
     * the high-level methods insert or remove. left and right SingleQueues are
     * empty when done. Nodes are moved alternately from left and right to this
     * SingleQueue.
     *
     * You have two source queues named left and right. Move all nodes from these
     * two queues to the current queue. It does not make a difference if the current
     * queue is empty or not, just get nodes from the right and left queues and add
     * them to the current queue. You may use any appropriate SingleLink helper
     * methods available.
     *
     * Do not assume that both right and left queues are of the same length.
     *
     * @param left  The first SingleQueue to extract nodes from.
     * @param right The second SingleQueue to extract nodes from.
     */
    public void combine(final SingleQueue<T> left, final SingleQueue<T> right) {
    	while (!left.isEmpty() || !right.isEmpty()) {
            SingleQueue<T> sourceQueue = left.isEmpty() ? right : right.isEmpty() ? left : left;

            if (!sourceQueue.isEmpty()) {
                moveFrontToRear(sourceQueue);
            }
        }
    }

    /**
     * Adds value to the rear of the queue. Increments the queue length.
     *
     * @param data The value to added to the rear of the queue.
     */
    public void insert(final T data) {
    	SingleNode<T> newNode = new SingleNode<T>(data, null);

        if (isEmpty()) {
            // If the queue is empty, the new node becomes the front and rear node
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
     * Returns the front value of the queue and removes that value from the queue.
     * The next node in the queue becomes the new first node. Decrements the queue
     * length.
     *
     * @return The value at the front of the queue.
     */
    public T remove() {
    	if (isEmpty()) {
            // If the queue is empty, return null (or handle the error as needed)
            return null;
        }

        T frontValue = front.getItem();

        // Update the front reference to the next node
        front = front.getNext();

        // If the queue is now empty, update the rear reference to null
        if (isEmpty()) {
            rear = null;
        }

        length--; // Decrement the queue length

        return frontValue;
    }

    /**
     * Splits the contents of the current SingleQueue into the left and right
     * SingleQueues. Moves nodes only - does not move value or call the high-level
     * methods insert or remove. this SingleQueue is empty when done. Nodes are
     * moved alternately from this SingleQueue to left and right. left and right may
     * already contain values.
     *
     * This is the opposite of the combine method.
     *
     * @param left  The first SingleQueue to move nodes to.
     * @param right The second SingleQueue to move nodes to.
     */
    public void splitAlternate(final SingleQueue<T> left, final SingleQueue<T> right) {
    	boolean moveToLeft = true; // Initialize with moving to the left queue

        while (!isEmpty()) {
            if (moveToLeft) {
                left.moveFrontToRear(this);
            } else {
                right.moveFrontToRear(this);
            }

            moveToLeft = !moveToLeft; // Toggle the move direction
        }
    }
}
