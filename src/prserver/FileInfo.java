/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author Yerda
 */
public class FileInfo implements Serializable {
    protected static final long serialVersionUID = 1112122200L;
    LinkedListSortedQueue<File1> sq;
    String IP;
    int PORT;
    public FileInfo(LinkedListSortedQueue<File1> q, String ip, int Port) throws IOException{
      sq=q;
      IP=ip;
      PORT=Port;
    }
 public  LinkedListSortedQueue<File1> getFileQ(){
     return sq; 
 }
 public  String getIP(){
     return IP; 
 }   
 public  int getPort(){
     return PORT; 
 }   
    
}

