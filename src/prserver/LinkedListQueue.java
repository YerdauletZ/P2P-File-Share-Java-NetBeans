/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prserver;

/**
 *
 * @author Yerdaulet and Alshyn
 * @param <T>
 */
public class LinkedListQueue<T> implements Queue<T> {

   
    private int size;
    private Node<T> front;
    private Node<T> back;

    public LinkedListQueue() {
        size = 0;
        front = null;
        back = null;
    }

    @Override
    public void enqueue(T value) {
        Node<T> newNode = new Node(value);
        if (size == 0) {
            front = newNode;
            back = newNode;
            front.setLink(null);
            back.setLink(null);

        } else {
            back.setLink(newNode);
            back = newNode;
            back.setLink(null);

        }
        size++;
    }

    @Override
    public T dequeue() throws Exception {
        if (size == 0) {
            throw new Exception("The size of the list is zero");
        }
        T result = front.getValue();

        if (size == 1) {
            front=null;
            back=null;
            size--;

        } else {

            front = front.getLink();
            size--;
        }

        return result;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        front = null;
        back = null;
    }

    public String toString() {
        String s = "";
        Node<T> temp = front;
        while (temp!= null) {
            s+=temp.toString()+" ";
            temp = temp.getLink();
            
        }
        return s;
        
    }

}
