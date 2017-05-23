/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//The code is written with a help of a link
//https://github.com/dmelichar/Matura/tree/master/schriftlich/sew/java/ChatBeispiel 

package prserver;

/**
 *
 * @author Yerdaulet Zeinolla & Yersaiyn Amangeldiev
 */
import java.net.*;
import java.io.*;
import java.util.*;

/*
 * The Client that can be run both as a console or a GUI
 */
public class PrClient {

    // for I/O
    private ObjectInputStream sInput;		// to read from the socket
    private ObjectOutputStream sOutput;		// to write on the socket
    private Socket socket;
    private static File directory = new File("src/inputfiles");
    LinkedListSortedQueue<File1> sq = new LinkedListSortedQueue();

    // if I use a GUI or not
    private ClientGUI cg;

    // the server, the port and the username
    private String server, username;
    private int port;
    private static int portFileShare = 1236;
    String fHost = "localhost";
    Socket serverSocket;
    static String ip = "";

    /*
	 *  Constructor called by console mode
	 *  server: the server address
	 *  port: the port number
	 *  username: the username
     */
    PrClient(String server, int port, String username, int port2) throws IOException {
        // which calls the common constructor with the GUI set to null
        this(server, port, username, null, port2);

    }

    /*
	 * Constructor call when used from a GUI
	 * in console mode the ClienGUI parameter is null
     */
    PrClient(String server, int port, String username, ClientGUI cg, int portFile) throws IOException {
        this.serverSocket = new Socket();
        this.server = server;
        this.port = port;
        this.username = username;
        // save if we are in GUI mode or not
        this.cg = cg;
    }

    /*
	 * To start the dialog
     */
    public boolean start() {

        // try to connect to the server
        try {

            socket = new Socket(server, port);
        } // if it failed not much I can so
        catch (Exception ec) {
            display("Error connectiong to server:" + ec);
            return false;
        }

        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

        /* Creating both Data Stream */
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        // creates the Thread to listen from the server 
        new ListenFromServer().start();
        // Send our username to the server this is the only message that we
        // will send as a String. All other messages will be ChatMessage objects
        try {
            sOutput.writeObject(username);

        } catch (IOException eIO) {
            display("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        // success we inform the caller that it worked
        return true;
    }

    /*
	 * To send a message to the console or the GUI
     */
    private void display(String msg) {
        if (cg == null) {
            System.out.println(msg);      // println in console mode
        } else {
            cg.append(msg + "\n");		// append to the ClientGUI JTextArea (or whatever)
        }
    }

    /*
	 * To send a message to the server
     */
    void sendMessage(Message msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            display("Exception writing to server: " + e);
        }
    }

    void sendFiles(FileInfo files) throws Exception {
        int i = files.getFileQ().getSize();
        try {
            sOutput.writeObject(files);
        } catch (IOException e) {
            display("Exception writing to server: " + e);
        }
//        while (i != 0) {
//            File temp = files.getFileQ().dequeue();
//            display(temp.getName() + " " + temp.length() + "bytes");
//            i--;
//        }

    }

    /*
	 * When something goes wrong
	 * Close the Input/Output streams and disconnect not much to do in the catch clause
     */
    private void disconnect() {
        try {
            if (sInput != null) {
                sInput.close();
            }
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (sOutput != null) {
                sOutput.close();
            }
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        } // not much else I can do

        // inform the GUI
        if (cg != null) {
            cg.connectionFailed();
        }

    }

    
    public static void main(String[] args) throws IOException, Exception {
        // default values

        int portNumber = 1500;
        int filePort = 1236;
        String serverAddress = "localhost";
        String userName = "Anonymous";

        // depending of the number of arguments provided we fall through
        switch (args.length) {
            // > javac Client username portNumber serverAddr
            case 3:
                serverAddress = args[2];
            // > javac Client username portNumber
            case 2:
                try {
                    portNumber = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
                    return;
                }
            // > javac Client username
            case 1:
                userName = args[0];
            // > java Client
            case 0:
                break;
            // invalid number of arguments
            default:
                System.out.println("Usage is: > java Client [username] [portNumber] {serverAddress]");
                return;
        }
        // create the Client object
        PrClient client = new PrClient(serverAddress, portNumber, userName, portFileShare);
        // test if we can start the connection to the Server
        // if it failed nothing we can do
        if (!client.start()) {
            return;
        }
/////////////////////////////////////*****************/////////////////////

///////////////////////*******************************///////////////////////////////        
        IfFile(client);
        // wait for messages from user
        Scanner scan = new Scanner(System.in);
        // loop forever for message from the user
        while (true) {
            System.out.print("> ");
            // read message from user
            String msg = scan.nextLine();

            // logout if message is LOGOUT
            if (msg.equalsIgnoreCase("LOGOUT")) {
                client.sendMessage(new Message(Message.LOGOUT, ""));
                // break to do the disconnect
                break;
            } // message WhoIsIn
            else if (msg.equalsIgnoreCase("WHOISIN")) {
                client.sendMessage(new Message(Message.WHOISIN, ""));
            } else if (msg.equalsIgnoreCase("FILES")) {

                IfFile(client);

            } else if (msg.equalsIgnoreCase("DOWNLOAD")) {
                msg = scan.nextLine();
                client.sendMessage(new Message(Message.DOWNLOAD, msg));

            } else {				// default to ordinary message
                //client.sendMessage(new Message(Message.MESSAGE, msg));
            }
        }
        // done disconnect
        client.disconnect();
    }

    //Find Files in A DIRECTORY
    public static void IfFile(PrClient client) throws IOException, Exception {

        client.sendMessage(new Message(Message.FILES, ""));
        LinkedListSortedQueue<File1> sq = new LinkedListSortedQueue();
        if (directory.exists()) {

            if (directory.isDirectory()) {
                File[] contents = directory.listFiles();
                for (File file : contents) {
                    if (file.isFile()) {
                        File1 file1 = new File1(file.getName(), file.lastModified(), (int) file.length(), file.getPath());
                        sq.insert(file1);

                    }
                }

            } else if (directory.isFile()) {
                File1 file1 = new File1(directory.getName(), directory.lastModified(), (int) directory.length(), directory.getPath());
                sq.insert(file1);

            }

        }
        //get ip address
        ip = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                InetAddress addr = addresses.nextElement();
                ip = addr.getHostAddress();
                //System.out.println(iface.getDisplayName() + " " + ip);
                break;

            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        FileInfo temp = new FileInfo(sq, ip, portFileShare);
        client.sendFiles(temp);

    }

    /*
	 * a class that waits for the message from the server and append them to the JTextArea
	 * if we have a GUI or simply System.out.println() it in console mode
     */
    class ListenFromServer extends Thread {

        public void run() {
            while (true) {
                try {

                    String msg = (String) sInput.readObject();
                    String[] splitStr = msg.trim().split("\\s+");
                    //msg = !Send IP_of_sender File_path IP_of_Receiver
                    if (splitStr[1].equals("!Send") && splitStr[4].equals(ip)) {
                        FileServer fs = new FileServer(1236, splitStr[3]);
                        fs.start();
                    }
                    if (splitStr[1].equals("!Send") && splitStr[2].equals(ip)) {
                        System.out.println("GOT HERE " + splitStr[3]);
                       FileClient fc = new FileClient(splitStr[4], 1236, "src//inputfiles//"+splitStr[3]);
                    }
                    
                    // if console mode print the message and add back the prompt
                    if (cg == null) {
                        System.out.println(msg);
                        System.out.print("> ");
                    } else {
                        cg.append(msg);
                    }
                } catch (IOException e) {
                    display("Server has close the connection: " + e);
                    if (cg != null) {
                        cg.connectionFailed();
                    }
                    break;
                } // can't happen with a String object but need the catch anyhow
                catch (ClassNotFoundException e2) {
                }
            }
        }
    }
}
