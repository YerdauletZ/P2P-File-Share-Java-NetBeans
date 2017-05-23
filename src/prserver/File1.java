/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prserver;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 *
 * @author Yerdaulet Zeinolla & Yersaiyn Amangeldiev
 */
public class File1 extends Object
      implements Serializable, Comparable<File1> {
    String name,pathName;
    
    long lastMod;
    long length;
    public File1(String name1, long lastMod1, long length1, String path){
        name=name1;
        lastMod=lastMod1;
        length=length1;
        pathName=path;
    }
    public String getName(){
        return name;
    }
    public long lastModified(){
        return lastMod;
    } 
    public long length(){
        return length;
    }
    public String toString(){
        return name;
    }
    String getPath(){
        return pathName;
    }

   

    @Override
    public int compareTo(File1 o) {
        return pathName.compareTo(o.getPath());
    }
    
}
