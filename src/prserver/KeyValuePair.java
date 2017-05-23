
package prserver;

/**
 * Key-value pairs to be used with the Map interface
 * @param <K>
 * @param <V>
 */
public class KeyValuePair<K, V> {
    
    private K key;
    private V value;
    
    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return the key
     */
    public K getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public V getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "<" + key + ";\n " + value + ">";
    }
}