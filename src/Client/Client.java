package Client;

import java.net.*;
import java.io.*;
import javax.swing.filechooser.*;

/**
 * This class controls the client side of the program and lets the client
 * connect to the server and send over a file to be copied to the project
 * folder. The client disconnects from the server if anything goes wrong.
 *
 * @author Nicholas Watkins
 */
public class Client extends javax.swing.JFrame
{

   private final static int PORT_NUM = 5976;
   private final static int BYTE_CHUNK = 1024;
   private File file;
   private Socket sock = null;
   private OutputStream writeSock;
   private InputStream readSock;
   private String fileName;
   private Long fileSize;

   /**
    * Creates new form Client
    */
   public Client()
   {
      initComponents();
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents()
   {

      clientPanel = new javax.swing.JPanel();
      fileSelector = new javax.swing.JFileChooser();
      jScrollPane1 = new javax.swing.JScrollPane();
      clientOutput = new javax.swing.JTextArea();
      connectUploadButt = new javax.swing.JButton();
      clientOutputLabel = new javax.swing.JLabel();
      fileSelectorLabel = new javax.swing.JLabel();
      serverAddressLabel = new javax.swing.JLabel();
      serverAddress = new javax.swing.JTextField();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      setTitle("Program 3 Client");

      clientPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      clientPanel.setPreferredSize(new java.awt.Dimension(595, 700));

      fileSelector.setFileFilter(new FileNameExtensionFilter("MSWord","doc","docx"));
      fileSelector.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      fileSelector.addActionListener(new java.awt.event.ActionListener()
      {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
            fileSelectorActionPerformed(evt);
         }
      });

      clientOutput.setEditable(false);
      clientOutput.setColumns(20);
      clientOutput.setRows(5);
      jScrollPane1.setViewportView(clientOutput);

      connectUploadButt.setText("Connect & Upload");
      connectUploadButt.addMouseListener(new java.awt.event.MouseAdapter()
      {
         public void mouseClicked(java.awt.event.MouseEvent evt)
         {
            connectUploadButtMouseClicked(evt);
         }
      });
      connectUploadButt.addActionListener(new java.awt.event.ActionListener()
      {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
            connectUploadButtActionPerformed(evt);
         }
      });

      clientOutputLabel.setText("Client Output");

      fileSelectorLabel.setText("Document Selector");

      serverAddressLabel.setText("Server Address");

      serverAddress.setText("localhost");

      javax.swing.GroupLayout clientPanelLayout = new javax.swing.GroupLayout(clientPanel);
      clientPanel.setLayout(clientPanelLayout);
      clientPanelLayout.setHorizontalGroup(
         clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(clientPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(clientPanelLayout.createSequentialGroup()
                  .addComponent(fileSelectorLabel)
                  .addGap(0, 0, Short.MAX_VALUE))
               .addGroup(clientPanelLayout.createSequentialGroup()
                  .addComponent(serverAddressLabel)
                  .addGap(18, 18, 18)
                  .addComponent(serverAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(connectUploadButt)
                  .addGap(113, 113, 113))
               .addGroup(clientPanelLayout.createSequentialGroup()
                  .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                     .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(fileSelector, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .addComponent(clientOutputLabel, javax.swing.GroupLayout.Alignment.LEADING))
                  .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
      );
      clientPanelLayout.setVerticalGroup(
         clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(clientPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(fileSelectorLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(fileSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(serverAddressLabel)
               .addComponent(serverAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(connectUploadButt))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(clientOutputLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
            .addContainerGap())
      );

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(clientPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(clientPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(128, Short.MAX_VALUE))
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void connectUploadButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_connectUploadButtActionPerformed
   {//GEN-HEADEREND:event_connectUploadButtActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_connectUploadButtActionPerformed

   private void fileSelectorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_fileSelectorActionPerformed
   {//GEN-HEADEREND:event_fileSelectorActionPerformed
      // TODO add your handling code here:
   }//GEN-LAST:event_fileSelectorActionPerformed

   /**
    * This method calls the methods needed to send the file and the file's size
    * and name. This method runs the client side connection with the Server.
    *
    * @param evt when the client clicks the connect and upload button.
    */
   private void connectUploadButtMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_connectUploadButtMouseClicked
   {//GEN-HEADEREND:event_connectUploadButtMouseClicked
      try
      {
         clientOutput.append("Making socket connection to server..." + '\n');
         String hostAddress = serverAddress.getText();
         sock = new Socket(hostAddress, PORT_NUM);
         clientOutput.append("Connected" + '\n');
         writeSock = sock.getOutputStream();
         readSock = sock.getInputStream();
         file = fileSelector.getSelectedFile();
         if (file != null) //A file is selected
         {
            fileName = file.getName();
            //Send the file name to the Server
            sendNullTerminatedString(fileName);
            clientOutput.append("Sent file name: " + fileName + '\n');
            fileSize = file.length();
            //Send the file size to the Server
            sendNullTerminatedString(fileSize.toString());
            clientOutput.append("Sent file size: " + fileSize + '\n');
            String path = file.getPath();
            clientOutput.append("Sending file...." + '\n');
            //Send the contents of the file to the Server
            sendFile(path);
            clientOutput.append("File sent waiting for server..." + '\n');
            //Wait for Server to reply
            char reply = (char) readSock.read();
            if (reply == '@')
            {
               clientOutput.append("Upload Ok" + '\n');
            }
            sock.close();
            clientOutput.append("Disconnected" + '\n');
         }
         else
         {
            clientOutput.append("Please select a file." + "\n");
            sock.close();
         }

      }
      catch (IOException ex)
      {
         clientOutput.append("Error: " + ex + "\n");
         sock = null;
      }
   }//GEN-LAST:event_connectUploadButtMouseClicked

   /**
    * @param args the command line arguments
    */
   public static void main(String args[])
   {
      /* Set the Nimbus look and feel */
      //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
       * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
       */
      try
      {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
         {
            if ("Nimbus".equals(info.getName()))
            {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;

            }
         }
      }
      catch (ClassNotFoundException ex)
      {
         java.util.logging.Logger.getLogger(Client.class
                 .getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }

      catch (InstantiationException ex)
      {
         java.util.logging.Logger.getLogger(Client.class
                 .getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }

      catch (IllegalAccessException ex)
      {
         java.util.logging.Logger.getLogger(Client.class
                 .getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }

      catch (javax.swing.UnsupportedLookAndFeelException ex)
      {
         java.util.logging.Logger.getLogger(Client.class
                 .getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable()
      {
         public void run()
         {
            new Client().setVisible(true);
         }
      });
   }

   /**
    * This method takes a full-path file name, decomposes the file into smaller
    * chunks (each with 1024 bytes), and sends the chunks one by one to the
    * Server (loop until all bytes are sent.) A null character ‘\0’ is sent to
    * the Server right after the whole file is sent.
    *
    * @param fullPathFileName is the path of the file we want to transfer
    */
   private void sendFile(String fullPathFileName)
   {
      try
      {
         FileInputStream fileReader;
         fileReader = new FileInputStream(fullPathFileName);
         byte buff[] = new byte[BYTE_CHUNK];
         //CHUNK_SIZE is 1024 bytes 
         int numRead = fileReader.read(buff);
         // try to read some bytes from the socket
         while (numRead > 0) //While not at the end of the file
         {
            if (numRead != -1) //Not at the end of file
            {
               writeSock.write(buff, 0, numRead);
               numRead = fileReader.read(buff);
            }
         }
         fileReader.close();
      }
      catch (IOException ex)
      {
         clientOutput.append("Error: " + ex + "\n");
      }
   }

   /**
    * This method takes a String s (either a file name or a file size,) as a
    * parameter, turns String s into a sequence of bytes ( byte[] ) by calling
    * getBytes() method, and sends the sequence of bytes to the Server. A null
    * character ‘\0’ is sent to the Server right after the byte sequence.
    *
    * @param s is either the name of the file or the size of the file
    */
   private void sendNullTerminatedString(String s)
   {
      try
      {
         s += '\0';  //Null terminate the string
         byte fileByte[];
         fileByte = s.getBytes(); //Convert the string to an byte array
         writeSock.write(fileByte);
      }
      catch (IOException ex)
      {
         System.out.println("Error: " + ex + "\n");
      }
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JTextArea clientOutput;
   private javax.swing.JLabel clientOutputLabel;
   private javax.swing.JPanel clientPanel;
   private javax.swing.JButton connectUploadButt;
   private javax.swing.JFileChooser fileSelector;
   private javax.swing.JLabel fileSelectorLabel;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JTextField serverAddress;
   private javax.swing.JLabel serverAddressLabel;
   // End of variables declaration//GEN-END:variables
}