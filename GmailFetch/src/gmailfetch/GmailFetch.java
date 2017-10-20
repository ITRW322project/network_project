package gmailfetch;

import static com.google.api.client.json.webtoken.JsonWebSignature.parser;
import clientv2.pkg0.client_gui;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.*;
import javax.mail.internet.InternetAddress;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class GmailFetch {

  public static void main( String[] args ) throws Exception {


      
      //System.out.println(System.getProperty("user.home") + "\\.credentials\\calendar-java-quickstart\\StoredCredential");

      
      
    //
    
    String startDate = "";
    PrintWriter writer;
    PrintWriter writer2;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");   
    
    
    FileReader fileReader = 
                new FileReader(path);
    BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
    
    File f = new File(path);
    if(!(f.exists() && !f.isDirectory())) { 
     
        writer = new PrintWriter(path, "UTF-8");
        f.getParentFile().mkdirs();
        f.createNewFile();
        writer.println("10/10/2017 00:00:01");
        writer.close();
        
    }
    try 
    {

        //br = new BufferedReader(new FileReader(FILENAME));
      
	String sCurrentLine;

	sCurrentLine = bufferedReader.readLine();
        
        System.out.println(sCurrentLine);
        
        if(sCurrentLine == null)
            sCurrentLine = "10/10/2017 00:00:00";
        System.out.println(sCurrentLine);
        startDate = sCurrentLine;
        bufferedReader.close();
        
        writer2 = new PrintWriter(path, "UTF-8");
        writer2.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
        writer2.close();

    } 
    catch (IOException e) {

	e.printStackTrace();

	} 
    finally 
    {

	bufferedReader.close();
        fileReader.close();
    }

    System.out.println("start date = " + startDate);
    Date datum = df.parse(startDate);
    
    System.out.println(datum.toString());
    
    SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.GT, datum);
    
    Properties props = System.getProperties();
    props.setProperty("mail.store.protocol", "imaps");
    Session session = Session.getDefaultInstance(props, null);
    Store store = session.getStore("imaps");
    store.connect("imap.gmail.com", 993, "allersjohann@gmail.com", "0110  Badd");
    
    
    Folder inbox = store.getFolder( "INBOX" );
    inbox.open( Folder.READ_ONLY );
      

    // Fetch unseen messages from inbox folder
    Message[] messages = inbox.search(newerThan); 
    Message[] fmessages = new Message[50];
    
    
    
    int k = 0;
    try {
        for(int i = 0; i < messages.length;i++)
        {
            if(messages[i].getSubject() != null)
            if ((messages[i].getSubject().contains("Digital Appointment Request"))&& messages[i].getReceivedDate().compareTo(datum)>0)
            {
                Multipart mp = (Multipart) messages[i].getContent();
                BodyPart bp = mp.getBodyPart(0);
                String content = (bp.getContent().toString());
                
                int indexOf = content.indexOf("Start DateTime: ");
                int indexOf2 = content.indexOf("End DateTime: ");
                
                System.out.println(messages[i].getReceivedDate() + " " + datum);
                System.out.println(messages[i].getReceivedDate().compareTo(datum));
                
                System.out.println(indexOf2 + " " + content.length());

                
                String dt = content.substring(indexOf + 16, indexOf + 26);
                String tm1 = dt + " " + content.substring(indexOf + 27, indexOf + 35);
                String tm2 = dt + " " + content.substring(indexOf2 + 25, indexOf2 + 33);

                System.out.println(dt + " " + tm1 + " " + tm2);
                
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                Date d, t1, t2;
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                d = sdf2.parse(dt);
                t1 = sdf.parse(tm1);
                t2 = sdf.parse(tm2);

                
                if(Quickstart.testAppointmentAvailability(d,t1,t2))
                {
                    fmessages[k] = messages[i];
                    k++;
                }
                else
                {
                    String sender = InternetAddress.toString(messages[k].getFrom());
                    sender = sender.substring(sender.indexOf("<")+1);
                    sender = sender.substring(0,sender.length()-1);
                    //client_gui.sendEmail(sender, "Appointment declined: Appointment time unavailable", Content, from, userName, password);
                }
            }
        } 
    }
    catch (MessagingException ex) {
        System.out.println("err");
        ex.printStackTrace();
    }
    
    
    // Sort messages from recent to oldest
    if(fmessages[0]!=null)
    {
    /*Arrays.sort( fmessages, ( m1, m2 ) -> {
    
    System.out.println(fmessages.length);
    
      try {
        return m2.getSentDate().compareTo( m1.getSentDate() );
      } catch ( MessagingException e ) {
        throw new RuntimeException( e );
      }
    } );
    
    System.out.println("1");*/
    
    String content;
        
        
    for ( int l = 0; l < k; l++) {
        Multipart multipart = (Multipart) fmessages[l].getContent();
        //get content as string
        BodyPart bodyPart = multipart.getBodyPart(0);
        content = (bodyPart.getContent().toString());
        
        
        if(JOptionPane.showConfirmDialog(null, content, "Appointment Request", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
        {
            System.out.println( 
            "sendDate: " + fmessages[l].getSentDate()
            + " subject:" + fmessages[l].getSubject() );
            System.out.println( " From: " + InternetAddress.toString(fmessages[l].getFrom()));
            String[] input = content.split("\\r?\\n");
            for(int i = 0; i < input.length; i ++)
            {
                input[i] = input[i].substring(input[i].indexOf(":")+2);
            }
            String sender = InternetAddress.toString(fmessages[l].getFrom());
            sender = sender.substring(sender.indexOf("<")+1);
            sender = sender.substring(0,sender.length()-1);
            
            System.out.println(sender);
            
            Quickstart.addEvent(input,sender);
            //client_gui.sendEmail(sender, "Appointment confirmed", content, from, userName, password);
        }
        else
        {
            //client_gui.sendEmail(sender, "Appointment declined", Content, from, userName, password);
        }
    }
    }
    else
    {
        JOptionPane.showMessageDialog(null, "No new appointments");
    }
  }

    
    
    
}