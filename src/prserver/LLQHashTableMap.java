/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prserver;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yerdaulet and Alshyn
 */


public class LLQHashTableMap<K extends Comparable, V> implements HashTableMap<K, V> {

    private LinkedListQueue<KeyValuePair<K, V>>[] buckets;

    private int size;

    public LLQHashTableMap(int numBuckets) {
        size = 0;
        buckets = new LinkedListQueue[numBuckets];
    }

    @Override
    public void define(K key, V value) {
        int x = Math.abs(key.hashCode() % buckets.length);

        if (buckets[ x] == null) {
            buckets[ x] = new LinkedListQueue();
        }

        if (buckets[ x].getSize() == 0) {
            buckets[ x].enqueue(new KeyValuePair(key, value));
        } else {
            boolean met = false;

            for (int i = 0; i < buckets[ x].getSize(); i++) {
                KeyValuePair<K, V> temp = null;

                try {
                    temp = buckets[ x].dequeue();
                } catch (Exception e) {
                }

                if (temp.getKey().equals(key)) {
                    temp.setValue(value);

                    met = true;

                    buckets[ x].enqueue(temp);

                    break;
                }

                buckets[ x].enqueue(temp);
            }

            if (!met) {
                buckets[ x].enqueue(new KeyValuePair(key, value));
            }
        }

        this.size++;
    }

    @Override
    public V getValue(K key) {
        int x = Math.abs(key.hashCode() % buckets.length);

        if (buckets[ x] == null) {
            return null;
        }

        for (int i = 0; i < buckets[ x].getSize(); i++) {
            KeyValuePair<K, V> temp = null;

            try {
                temp = buckets[ x].dequeue();
            } catch (Exception e) {
            }

            if (temp.getKey().equals(key)) {
                buckets[ x].enqueue(temp);

                return temp.getValue();
            }

            buckets[ x].enqueue(temp);
        }

        return null;
    }

    @Override
    public V remove(K key) {
        int x = Math.abs(key.hashCode() % buckets.length);

        if (buckets[ x] != null) {
            if (buckets[ x].getSize() == 0) {
                return null;
            } else {
                for (int i = 0; i < buckets[ x].getSize(); i++) {
                    KeyValuePair<K, V> temp = null;

                    try {
                        temp = buckets[ x].dequeue();
                    } catch (Exception e) {
                    }

                    if (temp.getKey().equals(key)) {
                        this.size--;

                        return temp.getValue();
                    }

                    buckets[ x].enqueue(temp);
                }

                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public KeyValuePair<K, V> removeAny() throws Exception {
        if (this.getSize() == 0) {
            throw new Exception("No elements in LLQHashTableMap");
        }

        for (int i = 0; i < buckets.length; i++) {
            if (buckets[ i] != null
                    && buckets[ i].getSize() > 0) {
                this.size--;

                return buckets[ i].dequeue();
            }
        }

        return null;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void clear() {
        this.size = 0;

        buckets = new LinkedListQueue[buckets.length];
    }

    @Override
    public String toString() {
       String a="";
        for(int i=0;i<buckets.length;i++){
            if(buckets[i]==null){
                continue;
            }
            a = a + buckets[i].toString() + " ";
        }
        return a; 
    }

    @Override
    public int getNumberOfBuckets() {
        return buckets.length;
    }

    @Override
    public int getBucketSize(int index) throws Exception {
        if (index < 0 || index > buckets.length) {
            throw new Exception("Incorrect index");
        }

        if (buckets[ index] == null) {
            return 0;
        }

        return buckets[ index].getSize();
    }

    @Override
    public double getLoadFactor() {
        return (double) this.size / buckets.length;
    }

    @Override
    public double getBucketSizeStandardDev() {
        double sum = 0;

        for (int i = 0; i < buckets.length; i++) {
            if (buckets[ i] == null) {
                sum += Math.pow(0 - this.getLoadFactor(), 2);
            } else {
                sum += Math.pow(buckets[ i].getSize()
                        - this.getLoadFactor(), 2);
            }
        }

        return Math.sqrt(((double) 1 / buckets.length) * sum);
    }

    @Override
    public String bucketsToString() {
        return toString();
    }
}
