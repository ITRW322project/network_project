///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package serverv2.pkg0;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
///**
// *
// * @author Simeon2
// */
//public class ServerV20 {
//    boolean listening;
//    ServerSocket serverSock;
//    Socket tempsoc;
//    int y =0;
//    public static Session[] sessionarr = new Session[100];
//    /**
//     * @param args the command line arguments
//     */
//    public ServerV20()throws IOException{
//        System.out.println("starting server");
//        serverSock = new ServerSocket(16000);
//        System.out.println("port created");
//        listening = true;
//     
//        while(listening){
//            System.out.println("listening");
//            tempsoc =serverSock.accept();
//            sessionarr[y] = new Session(tempsoc);
//            sessionarr[y].soc = tempsoc;
//            System.out.println("new client connected");
//            sessionarr[y].y=y;
//            y= y+1;       
//        }
//    }
//    public static void main(String[] args) throws IOException {
//       new ServerV20();     
//    }   
//}
//
//class Session implements Runnable{
//    Socket soc;
//    Socket outSoc;
//    int y; 
//    Thread runner;
//    BufferedReader keyRead;
//    OutputStream ostream;        
//    PrintWriter pw;
//    InputStream istream;
//    BufferedReader receiveRead;
//    String receiveMessage,sendMessage;
//    
//    Session(Socket s)throws IOException{
//        try{
//            soc =s;
//            keyRead = new BufferedReader(new InputStreamReader(System.in));//reading from keyboard(keyRead object)
//        
//            istream = soc.getInputStream();//receiving from server(istream object)
//            receiveRead = new BufferedReader(new InputStreamReader(istream));//Stream for receiving data from client
//        
//        }catch(IOException e ){
//            System.out.println(e);   
//        }
//        if(runner == null){
//            runner = new Thread(this);
//            runner.start();
//        }
//    }
//    
//    public void run(){
//        try{
//        while(runner == Thread.currentThread()){
//            if((receiveMessage = receiveRead.readLine())!= null)
//            {
//                System.out.println("client:>"+ receiveMessage);//receive the message from client
//            }
//              put address system here
//              
//            outSoc = ServerV20.sessionarr[y+1].soc;
//            ostream = outSoc.getOutputStream();//sending to client
//            pw = new PrintWriter(ostream, true);
//            sendMessage=keyRead.readLine();
//            sendMessage = receiveMessage;
//            pw.println(sendMessage);
//            System.out.flush();//flush the Stream
//            if(sendMessage.equals("bye"))
//            {
//                break;
//            }  
//        }
//        }catch(IOException e){
//        }
//    }    
//}
