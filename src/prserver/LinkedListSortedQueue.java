/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prserver;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;

/**
 *
 * @author Ердаулет
 */
public class LinkedListSortedQueue<T extends Comparable> implements Serializable {
    protected static final long serialVersionUID = 1112122200L;
    private Node<T> front;
    int size;
    
    public LinkedListSortedQueue() {
        front = null;
        size = 0;
    }

    public void insert(Comparable value) {
        Node<T> newNode = new Node(value);
        if (size == 0) { //starts the queue
            front = newNode; 
        } else {
            if (value.compareTo(front.getValue()) <= 0) {//if value of front less than inserting value, than insert
                newNode.setLink(front);
                front = newNode;
            } else {
                Node<T> next = null, prev = null;//next->coming value prev->previous next value 
                next = front;
                while (next != null) {
                    if (value.compareTo(next.getValue()) < 0) {
                        break;
                    }
                    prev = next;
                    next = next.getLink();//next goes through the whole queue
                }
                if (prev == null) {//if the inserted value is the largest one
                    newNode.setLink(front); 
                    front=newNode;
                } else { 
                    newNode.setLink(next);
                    prev.setLink(newNode);
                }
            }
        }
        size++;
    }

    public T dequeue() throws Exception {
        T val = front.getValue();
        front = front.getLink();
        size--;
        return val;
    }

    public int getSize() {
        return size;
    }

    public void clear() {
        front = null;
        size = 0;
    }

    public String toString() {

        String a = "";
        Node b = front;
        int i = size;
        while (i != 0) {
            a += b.getValue() + "\n";

            b = b.getLink();
            i--;
        }

        return a;
    }

}
