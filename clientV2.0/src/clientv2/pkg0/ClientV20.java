///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package clientv2.pkg0;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//
///**
// *
// * @author Gavin Daly
// */
//public class ClientV20 {
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) 
//    {
//        try{
//        // Create a new socket
//        Socket clientSock = new Socket("169.1.39.136", 16000);
//        System.out.println("Client connected to server");
//        // Reading from keyboard
//        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
//        // Communication stream assosiated with socket
//        OutputStream ostream = clientSock.getOutputStream();
//        //sending message to client (pwrite object)
//        PrintWriter pwrite=new PrintWriter(ostream,true);
//        InputStream istream=clientSock.getInputStream();
//        //receiving from server(receiveRead object)
//        BufferedReader receiveRead=new BufferedReader(new InputStreamReader(istream));
//        System.out.println("to Start the chat, type message and press Enter key");
//        String receiveMessage , sendMessage;
//        while(true)
//        {
//            sendMessage=keyRead.readLine();//keyboard reading
//            pwrite.println(sendMessage);//sending to server
//            System.out.flush();
//            if((receiveMessage=receiveRead.readLine())!=null)//receive from server
//            {
//                System.out.println("server:>"+receiveMessage);//displaying message
//            }
//            if(sendMessage.equals("bye"))
//            {
//                break;
//            }
//        }
//
//    }catch(IOException e){
//        System.out.println(e);
//    }
//    }
//}
