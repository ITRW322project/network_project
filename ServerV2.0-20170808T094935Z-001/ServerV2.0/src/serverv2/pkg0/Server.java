/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverv2.pkg0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.print.DocFlavor;



/**
 *
 * @author Gavin Daly
 */
public class Server extends javax.swing.JFrame {
 
    
    
    /**
     * Creates new form Server
     */
    public Server() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Ta_chatter = new javax.swing.JTextArea();
        BtnStart = new javax.swing.JButton();
        BtnStop = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Ta_chatter.setColumns(20);
        Ta_chatter.setRows(5);
        jScrollPane1.setViewportView(Ta_chatter);

        BtnStart.setText("start");
        BtnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnStartActionPerformed(evt);
            }
        });

        BtnStop.setText("Stop");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(BtnStart)
                        .addGap(50, 50, 50)
                        .addComponent(BtnStop)))
                .addContainerGap(159, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnStart)
                    .addComponent(BtnStop))
                .addContainerGap(84, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnStartActionPerformed
        // TODO add your handling code here:
        BtnStart.setEnabled(false);
        
        new listenings();
     
    }//GEN-LAST:event_BtnStartActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnStart;
    private javax.swing.JButton BtnStop;
    public static javax.swing.JTextArea Ta_chatter;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

class listenings implements Runnable{
    Thread runner;
    boolean listening;
    ServerSocket serverSock;
    Socket tempsoc;
    int y =0;
    public static Session[] sessionarr = new Session[100];
    listenings(){
         if(runner == null){
            runner = new Thread(this);
            runner.start();
    }
    
    }
    public void run(){
        try{
        
         System.out.println("starting server");
         Server.Ta_chatter.append("starting server\n");
        serverSock = new ServerSocket(16000);
        System.out.println("port created");
        Server.Ta_chatter.append("port created\n");
        listening = true;
     
        while(listening){
            System.out.println("listening");
            Server.Ta_chatter.append("listening\n");
            tempsoc =serverSock.accept();
            sessionarr[y] = new Session(tempsoc);
            //sessionarr[y].soc = tempsoc;
            System.out.println("new client connected");
            Server.Ta_chatter.append("new client connected\n");
            sessionarr[y].y=y;
            y= y+1;       
        }
        }catch(IOException e){
        }
   
    

}
    
}


class Session implements Runnable{
    Socket soc;
    Socket outSoc;
    int y; 
    Thread runner;
    BufferedReader keyRead;
    OutputStream ostream;        
    PrintWriter pw;
    InputStream istream;
    BufferedReader receiveRead;
    String receiveMessage,sendMessage;
    String[] parts;
    String[] log;
    
    Session(Socket s)throws IOException{
        try{
            soc =s;
            keyRead = new BufferedReader(new InputStreamReader(System.in));//reading from keyboard(keyRead object)
        
            istream = soc.getInputStream();//receiving from server(istream object)
            receiveRead = new BufferedReader(new InputStreamReader(istream));//Stream for receiving data from client
            String recieve=String.valueOf(receiveRead); 
            //check if string converted correctly
            parts = recieve.split("#");
            if (parts[0].equals("T")){
                
            }else if (parts[0].equals("L")){
               log=parts[2].split("|");
               Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3309/stockcontrol", "root", "root");
            Statement st = con.createStatement();
            }
        }catch(Exception e ){
            System.out.println(e);   
        }
        if(runner == null){
            runner = new Thread(this);
            runner.start();
        }
    }
    
    public void run(){
        try{
        while(runner == Thread.currentThread()){
            if((receiveMessage = receiveRead.readLine())!= null)
            {
                System.out.println("client:>"+ receiveMessage);//receive the message from client
                Server.Ta_chatter.append("client:>"+ receiveMessage+"\n");
            }
              //put address system here
              
            outSoc = listenings.sessionarr[Integer.parseInt(parts[1])].soc;
            ostream = outSoc.getOutputStream();//sending to client
            pw = new PrintWriter(ostream, true);
            //sendMessage=keyRead.readLine();
            sendMessage = receiveMessage;
            pw.println(sendMessage);
            System.out.flush();//flush the Stream
            if(sendMessage.equals("bye"))
            {
                break;
            }  
        }
        }catch(IOException e){
        }
    }    
}

//
//Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3309/stockcontrol", "root", "root");
//            Statement st = con.createStatement();
//
//private Connection connection;
//    public Calendar cal = Calendar.getInstance();
//    static ArrayList<Products> productsArray = new ArrayList<Products>();
//    Model tableModel;



//
//public void connectDBStock(String sql,String product) throws SQLException
//    { 
//        try {
//                Class.forName("com.mysql.jdbc.Driver");
//                connection = DriverManager.getConnection("jdbc:mysql:"+ "//localhost:3309/StockControl", "root", "root");
//                System.out.println("Database connected");
//                Statement state = connection.createStatement();
//                ResultSet code = state.executeQuery(sql);
//                    JtaDisplay.append("report on : "+product+"\n");
//                    JtaDisplay.append("||ID\t|Price\t|Quantity\t|| \n");
//                    
//                while (code.next())
//                {
//                    JtaDisplay.append("||"+code.getString(1)+"\t|"+code.getString(2)+"\t|"+code.getString(3)+"\t|| \n");
//                }
//                JtaDisplay.append("end of product "+product+" report\n");
//                
//            }
//        catch(  SQLException | ClassNotFoundException sqle)
//            {
//                System.out.println("Error" + sqle);
//            }
//        connection.close();
//        }