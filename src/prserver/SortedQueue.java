/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prserver;

/**
 * @author Yerdaulet Zeinolla & Alshyn Mamyshov
 *
 * A queue of primitive integers
 * @param <T>
 */
public interface SortedQueue<T extends Comparable> {
    
    /**
     * Adds an element to the end of the queue
     * 
     * @param value element to be added to the end of the queue
     */
    public void insert(T value);
    
    /**
     * Removes and returns the front most element of the queue
     * 
     * @return the front most element of the queue
     * @throws Exception if the queue is empty
     */
    public T dequeue() throws Exception;
    
    /**
     * Returns the size of the queue
     * 
     * @return the size of the queue
     */
    public int getSize();
    
    /**
     * Removes all the elements from the queue
     */
    public void clear();
    
    /**
     * Returns a string representation of the queue
     * 
     * @return s string representation of the queue
     */
    @Override
    public String toString();
}
