
/**
 * Created a public class Queue similar to Princeton Example with an extra method.
 * @param <Item>
 */
public class Queue<Item> {
    private Node<Item> first;
    private Node<Item> last;

    private class Node<Item> {
        Node<Item> next;
        Item item;
    }

    /**
     * Takes a queue and assigns the nodes to the this queue.
     * Update Nodes first and last depending on if this queue is empty or not.
     * @param queue
     */
    public void transferNodes(Queue<Item> queue) {
        if (!queue.isEmpty()) {
            if (isEmpty()) {
                this.first = queue.first;
                last = first;
                while (last.next != null) {
                    last = last.next;
                }
            }
            else last.next = queue.first;
        }
    }

    public Queue(){
        first = null;
        last = null;
    }

    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        }
        else oldLast.next = last;
    }

    public Item dequeue() {
        if (isEmpty()) return null;
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        return item;
    }

    public boolean isEmpty() {
        return first == null;
    }
}
