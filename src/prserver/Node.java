
package prserver;
import java.io.Serializable;

/**
 * Generic node class for creating linked-lists
 * @param <T>
 */
public class Node<T> implements Serializable {
    
    private T value;
    private Node<T> link;
    
    /**
     * Construct a node with the given value, and a null link
     * @param val to set on the node
     */
    public Node(T val) {
        value = val;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * @return the link
     */
    public Node<T> getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(Node<T> link) {
        this.link = link;
    }
    
    /**
     * @return the string representation of the value 
     */
    public String toString() {
        return value.toString();
    }
}
