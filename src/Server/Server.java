package Server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This class controls the Server side of the program. The Server waits for a
 * connection and obtains a file name, then a file size and finally the file
 * contents and creates a copy of that file in the project folder.
 *
 * @author Nicholas Watkins
 */
public class Server
{

   private Date connectedAt;
   private ServerSocket servSock;
   private Socket sock;
   private InputStream servIn;
   private OutputStream servOut;
   private FileOutputStream fileOutput;
   private String fileName;
   private String sizeString;
   private Long sizeLong;
   private final static int PORT_NUM = 5976;
   private final static int CHUNK_SIZE = 1024;

   public static void main(String args[])
   {
      Server runServer = new Server();
      runServer.run();
   }

   /**
    * This method controls the server having it in an infinite loop to make sure
    * that you can reconnect after the client finishes one transfer.
    */
   public void run()
   {
      System.out.println("Server is running.....");
      while (true)
      {
         try
         {
            if (servSock != null) //Check to see if the servSock is running
            {
               servSock.close();
            }
            servSock = new ServerSocket(PORT_NUM);
            System.out.println("Waiting for connection.....");
            //Listen for connections at the port 5976
            sock = servSock.accept();
            connectedAt = new Date();
            System.out.println("Got a connection: " + connectedAt.toString());
            System.out.println("Connected to: " + sock.getInetAddress() + " "
                    + sock.getPort());
            servIn = sock.getInputStream();
            servOut = sock.getOutputStream();
            //Get file name
            fileName = getNullTerminatedString();
            if (fileName == null || fileName == "")
            {
               System.out.println("No File name recieved");
               sock.close();
            }
            else
            {
               System.out.println("Got file name: " + fileName);
               //Get file size
               sizeString = getNullTerminatedString();
               if (sizeString == null || sizeString == "")
               {
                  System.out.println("No File size recieved ");
                  sock.close();
               }
               else
               {
                  System.out.println("File size: " + sizeString);
                  sizeLong = Long.valueOf(sizeString);
                  //Get contents of the file
                  getFile(fileName, sizeLong);
                  servOut.write('@');

               }
            }
            sock.close();
         }
         catch (IOException ex)
         {
            System.out.println("Error: " + ex);
         }
      }

   }

   /**
    * This method reads the bytes (terminated by ‘\0’) sent from the Client, one
    * byte at a time, and turns the bytes into a String.
    *
    * @return returns either the name of the file to be received or the size of
    * the file if nothing is received, then it returns null.
    */
   private String getNullTerminatedString()
   {
      try
      {
         String stringIn = "";;
         int numByte;
         numByte = servIn.read(); //Get the first byte as an int
         while (numByte > 0)
         {
            if (numByte != -1)
            {
               char ch = (char) numByte; //Get the ACII equivalent of the byte
               stringIn += ch;
               numByte = servIn.read(); //Get the next byte as an int
            }
         }
         return stringIn;
      }
      catch (IOException ex)
      {
         System.out.println("Error: " + ex);
         return null;
      }
   }

   /**
    * This method takes an output file name and its file size, reads the binary
    * data (in a 1024-byte chunk) sent from the Client, and writes to the output
    * file a chunk at a time.
    *
    * @param filename is the name of the file to write to
    * @param size is the size of the file
    */
   private void getFile(String filename, long size)
   {
      try
      {
         fileOutput = new FileOutputStream(filename, true);
         byte buff[] = new byte[CHUNK_SIZE];
         int totalRead = 0;
         //CHUNK_SIZE is 1024 bytes 
         int numRead = 1;
         // try to read some bytes from the socket
         while (totalRead < size)
         {
            numRead = servIn.read(buff);
            if (numRead != -1) //Not at the end of file
            {
               fileOutput.write(buff, 0, numRead);
               totalRead += numRead;
            }
         }
         System.out.println("Got the file.");
         fileOutput.close();
      }
      catch (IOException ex)
      {
         System.out.println("Error: " + ex);
      }
   }
}
