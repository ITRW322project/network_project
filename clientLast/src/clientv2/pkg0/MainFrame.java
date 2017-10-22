
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientv2.pkg0;

import java.awt.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import javax.swing.WindowConstants;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.swing.border.Border;
import javax.swing.border.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.*;  
import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.ParseConversionEvent;



/**
 *
 * @author Jaco
 */
public class MainFrame extends javax.swing.JFrame{

    /**
     * Creates new form MainFrame
     */

    
    static JTextArea txtEvents;
    public boolean clicked = false;
    public String selected;
    public String email, password;
    
    int todaysYear,todaysMonth,todaysDay;
           String selectedtxt2, selDay;
    String selectedDay;
    private JTextField tf;
    public MainFrame() throws IOException
    {
        File file = new File(System.getProperty("user.home") + "\\.credentials\\calendar-java-quickstart\\StoredCredential");
        String dr = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        
        File f = new File(dr+"\\Datums");
        deleteAllFiles(f);
        
        
        
        
      if(file.delete())
        {
            System.out.println("File deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the file");
        }
        
        Quickstart.getEvent();

        JPanel panel = new JPanel();
       
                
                    
                
        initComponents();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 550);
        
        //Kry vandag se Datum
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        System.out.println(dtf.format(localDate)); //2016/11/16
         todaysYear = localDate.getYear();
         todaysMonth = localDate.getMonthValue();
         todaysDay = localDate.getDayOfMonth();
        
        System.out.println(""+ todaysYear +" "+ todaysMonth);
        
        
        for(int i=0;i<textFields.length;i++)
        {
            
            textFields[i] = new JTextField();
            textFields[i].setEditable(false);
            textFields[i].setHorizontalAlignment(JTextField.CENTER);
            panel.add(textFields[i]);
            
             
            
            
        }
        
        
        
        Quickstart.getEvent();
        //showCalendar(todaysYear, todaysMonth);
        
       
        Container contentPane = this.getContentPane();
        contentPane  = new JPanel();
        //contentPane.setBackground(new Color(0,0,0));
      
     //contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//Moontlik kak
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        
        
         JComboBox yearSelectCombobox = new JComboBox(getYears());
         
         yearSelectCombobox.setBounds(10, 11, 160, 43);
         contentPane.add(yearSelectCombobox);
         
         System.out.println(yearSelectCombobox.getSelectedItem());
         
        LocalDate today = LocalDate.now();
        yearSelectCombobox.setSelectedIndex(today.getYear()-1900);
        System.out.println(yearSelectCombobox.getSelectedItem());
        System.out.println(today.getYear());
        
         JComboBox monthSelectCombobox = new JComboBox(getMonths());
        monthSelectCombobox.setBounds(180, 11, 160, 43);
        contentPane.add(monthSelectCombobox);
        
        monthSelectCombobox.setSelectedIndex(today.getMonthValue()-1);
        
        initCalDate(todaysYear, todaysMonth, todaysDay);
        
        JTextArea textArea = new JTextArea(10,30);
        contentPane.add(textArea);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        
        JButton btnNewButton = new JButton("Show");
        btnNewButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0){
                
                     System.out.println("" + yearSelectCombobox.getSelectedItem());
                      int yearSelected =  Integer.parseInt(yearSelectCombobox.getSelectedItem().toString());
                      int monthSelected = monthSelectCombobox.getSelectedIndex();// As jy maand name soek maak dit net selected items
                      
                      System.out.println( yearSelected +" "+ monthSelected);
                      
                      //Hier wys die dae op die kalender
                    //showCalendar(yearSelected, monthSelected);
                    getFolderNames(yearSelected,monthSelected);
                   
            }
        });
        btnNewButton.setBounds(350, 11, 112, 43);
        contentPane.add(btnNewButton);
        
        int yearSelected =  Integer.parseInt(yearSelectCombobox.getSelectedItem().toString());
                      int monthSelected = monthSelectCombobox.getSelectedIndex();// 
                      getFolderNames(yearSelected,monthSelected);
        
        //Text Area waar die events vertoon word
        JPanel pnlEvents = new JPanel();
        pnlEvents.setBounds(500, 70, 200, 200);
        
         //txtEvents.setWrapStyleWord(true);
        
        txtEvents = new JTextArea(15,5);
       txtEvents.setBounds(10,30, 200,200);
        txtEvents.setWrapStyleWord(true);
        txtEvents.setLayout(null);
        JScrollPane jsp = new JScrollPane(txtEvents,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(jsp);
       
        //contentPane.add(txtEventScrollPane);
        //pnlEvents.add(txtEventScrollPane);
        txtEvents.setLineWrap(true);
        txtEvents.setLocation(10, 800);
        txtEvents.setLayout(new BorderLayout());
        contentPane.add(pnlEvents);
        pnlEvents.add(txtEvents);
        pnlEvents.setVisible(true);
        
        
       
        
        
        panel.setBounds(10, 76, 452, 262);
        contentPane.add(panel);
        panel.setLayout(new GridLayout(7, 7, 0, 0));
        
        //Add appointment button
         JButton btnAdd = new JButton("Add appointment");
         btnAdd.setBounds(500, 340,112, 43);
        contentPane.add(btnAdd);
        
        
        
        //Add event panel
        JPanel pnlAddEvent = new JPanel();
       // pnlAdd.setBackground(Color.red);
        pnlAddEvent.setBounds(500, 20, 250, 300);
        pnlAddEvent.setLayout(new GridLayout(0,1,1,1));
       // GridBagConstraints c = new GridBagConstraints();
        //c.fill =GridBagConstraints.RELATIVE;
        pnlAddEvent.setVisible(false);
        
        //Add appointment panel
        JPanel pnlAdd = new JPanel();
        pnlAdd.setBounds(500, 20, 250, 300);
        pnlAdd.setLayout(new GridLayout(0,1,1,1));
        pnlAdd.setVisible(false);
        
        
        JLabel lblTitle = new JLabel("Title :");
        pnlAdd.add(lblTitle);
        
         TextField txtTitle = new TextField("",20);
        pnlAdd.add(txtTitle);
        
        JLabel lblDescription = new JLabel("Description :");
        pnlAdd.add(lblDescription);
        
        JTextField txtDesc = new JTextField("",20);
        pnlAdd.add(txtDesc);
        
        JLabel lblLocation = new JLabel("Location :");
        JTextField txtLocation = new JTextField();
        pnlAdd.add(lblLocation);
        pnlAdd.add(txtLocation);
        
        JLabel lblAtendee = new JLabel("Atendee");
        JTextField txtAttendee = new JTextField();
        pnlAdd.add(lblAtendee);
        pnlAdd.add(txtAttendee);
        
      
        
       
        
       
        
        
        JLabel lblTimeStart = new JLabel("Start time :");
        pnlAdd.add(lblTimeStart);
        
        
         String[] times =
        {"00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30","04:00","04:30","05:00","05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30"};
        //Add event comboboxes
        
        JComboBox cmbStart = new JComboBox(times);
        JComboBox cmbEnd = new JComboBox(times);
        
        pnlAdd.add(cmbStart);
        
        JLabel lblTimeEnd = new JLabel("End time :");
        pnlAdd.add(lblTimeEnd);
        
        pnlAdd.add(cmbEnd);
        
       
        
         JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(615, 340,112, 43);
        //btnCancel.setBackground(Color.red);
        btnCancel.setVisible(false);
       
        
        
        
       
        
          //Add event panel
        
         
       JButton btnAddEvent = new JButton("Add Event");
        btnAddEvent.setBounds(615, 340,112, 43);
        btnAddEvent.setVisible(true);
        contentPane.add(btnAddEvent);
       
       
        JLabel lblTitleEvent = new JLabel("Title :");
        pnlAddEvent.add(lblTitleEvent);
        
        TextField txtTitleEvent = new TextField("",20);
        pnlAddEvent.add(txtTitleEvent);
        
        JLabel lblDescriptionEvent = new JLabel("Description :");
        pnlAddEvent.add(lblDescriptionEvent);
        
        JTextField txtDescEvent = new JTextField("",20);
        pnlAddEvent.add(txtDescEvent);
        
        JLabel lblLocationEvent = new JLabel("Location :");
        JTextField txtLocationEvent = new JTextField();
        pnlAddEvent.add(lblLocationEvent);
        pnlAddEvent.add(txtLocationEvent);
        
        JLabel lblTimeStartEvent = new JLabel("Start time :");
        pnlAddEvent.add(lblTimeStartEvent);
        
        JComboBox cmbStartEvent = new JComboBox(times);
        JComboBox cmbEndEvent = new JComboBox(times);
        
        pnlAddEvent.add(cmbStartEvent);
        
        JLabel lblTimeEndEvent = new JLabel("End time :");
        pnlAddEvent.add(lblTimeEndEvent);
        
        pnlAddEvent.add(cmbEndEvent);
        
        JButton btnCreateEvent = new JButton("Create Event");
        btnCreateEvent.setBounds(500, 340,112, 43);
        btnCreateEvent.setVisible(true);
        contentPane.add(btnCreateEvent);
        
        JButton btnCreate = new JButton("Create Appointment");
        btnCreate.setBounds(500, 340,112, 43);
        setVisible(false);
        contentPane.add(btnCreate);
        
        
        
        JButton btnCancelEvent = new JButton("Cancel");
        btnCancelEvent.setBounds(615, 340,112, 43);
        //btnCancel.setBackground(Color.red);
        btnCancelEvent.setVisible(false);
        
        JButton btnViewApp = new JButton("View appointments");
        btnViewApp.setBounds(500, 380,224, 30);
        contentPane.add(btnViewApp);
        
       
        
        contentPane.add(btnCancel);
        
       
       contentPane.add(pnlAdd);
       contentPane.add(pnlAddEvent);
       
       
       
        //Add appointment panel visibility
         btnAdd.addActionListener(new ActionListener()
         {
              public void actionPerformed(ActionEvent arg0)
                 {
                     selDay = selectedtxt2;
                     
                     if(selDay!=null && selDay!="")
                     {
                         if(selDay.length() < 2)
                         selDay = "0"+selDay;
                     btnAddEvent.setVisible(false);
                     
                      JLabel lblemail = new JLabel("E-mail Adrdess :");
                     
                     JLabel lblpassword = new JLabel("Password :");
                     JTextField txtEmail = new JTextField("");
                     JPasswordField txtPassword = new JPasswordField("");
                     JPanel pnlDialog = new JPanel(new GridLayout(0,1));
                     pnlDialog.add(lblemail);
                     pnlDialog.add(txtEmail);
                     pnlDialog.add(lblpassword);
                     pnlDialog.add(txtPassword);
                     btnCreate.setVisible(true);
                     
                    

                      int result = JOptionPane.showConfirmDialog(null, pnlDialog, "Log in",
                         JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                      if(!(Pattern.matches( "[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$",txtEmail.getText())))
                      {
                          JOptionPane.showMessageDialog(null, "Please enter a valid email","Error",JOptionPane.ERROR_MESSAGE);
                      }
                          
                      else if
                       (result == JOptionPane.OK_OPTION) 
                      {
                                pnlAdd.setVisible(true);
                                pnlEvents.setVisible(false);
                                btnCreate.setVisible(true);
                                btnAdd.setVisible(false);btnAddEvent.setVisible(false);
                                btnCreateEvent.setVisible(false);
                                btnCancel.setVisible(true);
                                email = txtEmail.getText();
                                char pw[] = txtPassword.getPassword();
                                for(int i=0; i<pw.length; i++)
                                    password += pw[i];
                                tf.setBackground(Color.DARK_GRAY);
                                //System.out.println(email+ " "+ password);
                      }
                        else
                      {
                         System.out.println("Cancelled");
                         btnAdd.setVisible(true);
                         btnAddEvent.setVisible(true);
                      }    
                     }
                     else
                     JOptionPane.showMessageDialog(null, "Please select a day","Invalid Input",JOptionPane.ERROR_MESSAGE);
                 }
         });
         
          btnCancel.addActionListener(new ActionListener()
         {
              public void actionPerformed(ActionEvent arg0)
                 {
                     tf.setBackground(Color.white);
                     btnNewButton.doClick();
                     pnlAdd.setVisible(false);
                     pnlAddEvent.setVisible(false);
                     pnlEvents.setVisible(true);
                     btnCreate.setVisible(false);
                     btnAdd.setVisible(true);
                     btnCancel.setVisible(false);
                     btnAddEvent.setVisible(true);
                 }
         });
          
            btnViewApp.addActionListener(new ActionListener()
         {
              public void actionPerformed(ActionEvent arg0)
                 {
                      JLabel lblemail = new JLabel("E-mail Adrdess :");
                     
                     JLabel lblpassword = new JLabel("Password :");
                     JTextField txtEmail = new JTextField("");
                     JPasswordField txtPassword = new JPasswordField("");
                     JPanel pnlDialog = new JPanel(new GridLayout(0,1));
                     pnlDialog.add(lblemail);
                     pnlDialog.add(txtEmail);
                     pnlDialog.add(lblpassword);
                     pnlDialog.add(txtPassword);
                     
                     int result = JOptionPane.showConfirmDialog(null, pnlDialog, "Log in",
                         JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                      if(!(Pattern.matches( "[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$",txtEmail.getText())))
                      {
                          JOptionPane.showMessageDialog(null, "Please enter a valid email","Error",JOptionPane.ERROR_MESSAGE);
                      }
                          
                      else if
                       (result == JOptionPane.OK_OPTION) 
                      {
                                
                                email = txtEmail.getText();
                                char pw[] = txtPassword.getPassword();
                                password = "";
                                for(int i=0; i<pw.length; i++)
                                    password += pw[i];
                                
                                System.out.println("================================================ " +email+ " "+ password);
                      }
                     
                     FileReader fileReader = null;
    try 
    {

        String startDate = "";
        PrintWriter writer;
        PrintWriter writer2;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\Datums\\email\\"+email+"\\LastAppointmentDate.txt";
        String path2 = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\Datums\\email\\"+email;
        
        File dir = new File(path2);
        if (! dir.exists())
            dir.mkdir();
        
        File f = new File(path);
        if(!f.exists())
            f.createNewFile();
        
	fileReader = new FileReader(path);
        BufferedReader bufferedReader =
                new BufferedReader(fileReader);
        
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
            
            try {
                bufferedReader.close();
                fileReader.close();
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        Message[] messages = null;
        Date datum = null;
        try
        {
            System.out.println("start date = " + startDate);
            datum = df.parse(startDate);
            System.out.println(datum.toString());
            SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.GT, datum);
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", 993, email, password);
            Folder inbox = store.getFolder( "INBOX" );
            inbox.open( Folder.READ_ONLY );
            // Fetch unseen messages from inbox folder
            messages = inbox.search(newerThan);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Incorrect credentials");
        }
        
        
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
                            EmailController.sendEmail(sender, "Appointment declined: Appointment time unavailable", content, email, email, password);
                        }
                    }
            }
        }
        catch (MessagingException ex) {
            System.out.println("err");
            ex.printStackTrace();
        }             catch (ParseException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
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
                    try {
                        System.out.println(
                                "sendDate: " + fmessages[l].getSentDate()
                                        + " subject:" + fmessages[l].getSubject() );
                    } catch (MessagingException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
                    
                    try {
                        Quickstart.addEvent(input,sender);
                        EmailController.sendEmail(sender, "Appointment confirmed", content, email, email, password);
                    } catch (IOException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    String sender = InternetAddress.toString(fmessages[l].getFrom());
                            sender = sender.substring(sender.indexOf("<")+1);
                            sender = sender.substring(0,sender.length()-1);
                    EmailController.sendEmail(sender, "Appointment declined", content, email, email, password);
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No new appointments");
        }

    } 
    catch (FileNotFoundException ex) {

	              Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);

	}         catch (UnsupportedEncodingException ex) { 
                      Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                  } catch (IOException ex) {
                      Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                  } catch (MessagingException ex) {
                      Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                  } 
    finally 
    {

                         try {
                             fileReader.close();
                         } catch (IOException ex) {
                             Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                         }
        
    }
                     
                 }
         });
          
           btnCreateEvent.addActionListener(new ActionListener()
         {
              public void actionPerformed(ActionEvent arg0)
                 {
                   if(cmbStartEvent.getSelectedIndex()>cmbEndEvent.getSelectedIndex())
                     {
                         JOptionPane.showMessageDialog(null,"Please select a start time that occurs before the end time", "Incorrect input",2);
                     }
                     else if(txtDescEvent.getText().equals("")||txtLocationEvent.getText().equals("")||txtTitleEvent.getText().equals(""))
                     {
                         JOptionPane.showMessageDialog(null,"Please fill in all fields", "Incorrect input",2);
                     }
                     else if(selDay == null || selDay == "")
                     {
                         JOptionPane.showMessageDialog(null,"Please be sure to select a day", "Incorrect input",2);
                     }
                     else
                     {
                        
                       try {
                           int mn = monthSelectCombobox.getSelectedIndex() + 1;
                           String mon = mn + "";
                           if(mon.length()==1)
                               mon = "0" + mn;
                           String[] input = {txtTitleEvent.getText(), txtLocationEvent.getText(), txtDescEvent.getText(),
                               yearSelectCombobox.getSelectedItem() + "-" + mon + "-" + selDay + "T" + cmbStartEvent.getSelectedItem() +
                                   ":00+02:00", yearSelectCombobox.getSelectedItem() +
                                   "-" + mon + "-" + selDay + "T" +
                                   cmbEndEvent.getSelectedItem() + ":00+02:00"};
                           Quickstart.addEvent(input, "");
                           tf.setBackground(Color.white);
                           btnNewButton.doClick();
                           btnCancel.doClick();
                           // getFolderNames(yearSelectCombobox.getSelectedIndex()+1900, mon);
                       } catch (IOException ex) {
                           Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                       }
                     }
                 }
         });
          
            btnAddEvent.addActionListener(new ActionListener()
         {
              public void actionPerformed(ActionEvent arg0)
                 {
                     selDay = selectedtxt2;
                     if(selDay == null || selDay == "")
                     {
                         JOptionPane.showMessageDialog(null,"Please be sure to select a day", "Incorrect input",2);
                     }
                     else
                     {
                         if(selDay.length() < 2)
                         selDay = "0"+selDay;
                         tf.setBackground(Color.DARK_GRAY);
                        btnCreateEvent.setBounds(500, 340,112, 43);
                        btnCancel.setVisible(true);
                        btnAdd.setVisible(false);
                        btnAddEvent.setVisible(false);
                        pnlAdd.setVisible(false);
                        pnlAddEvent.setVisible(true);
                        pnlEvents.setVisible(false);
                        btnCreateEvent.setVisible(true);
                     }
                      
                     //btnCancel.setVisible(true);
                     
                     
                 }
         });
          
           btnCreate.addActionListener((ActionEvent arg0) -> {
               //Allers tik jou code hier in
               if(cmbStart.getSelectedIndex()>cmbEnd.getSelectedIndex())
               {
                   JOptionPane.showMessageDialog(null,"Please select a start time that occurs before the end time", "Incorrect input",2);
               }
               else if(txtDesc.getText().equals("")||txtLocation.getText().equals("")||txtTitle.getText().equals("")||txtAttendee.getText().equals(""))
               {
                   JOptionPane.showMessageDialog(null,"Please fill in all fields" , "Incorrect input",2);
               }
               else
               {
                   int indx = monthSelectCombobox.getSelectedIndex() + 1;
                   String indxm = indx+"";
                   if(indxm.length()==1)
                       indxm = "0" + indx;
                   String content = "Summary: " + txtTitle.getText() + "\r\nLocation: " +
                           txtLocation.getText() + "\r\nDescription: " + txtDesc.getText() +
                           "\r\nStart DateTime: " + yearSelectCombobox.getSelectedItem() +
                           "-" +indxm +
                           "-" + selDay + "T" + cmbStart.getSelectedItem() +
                           ":00+02:00\r\nEnd DateTime: " + yearSelectCombobox.getSelectedItem() +
                           "-" + indxm + "-" + selDay + "T" +
                           cmbEnd.getSelectedItem() + ":00+02:00";
                   System.out.println(content);
                   try
                   {
                       EmailController.sendEmail(txtAttendee.getText(), "Digital Appointment Request", content, email, email, password);
                       JOptionPane.showMessageDialog(null,"Request sent" );
                   }
                   catch (HeadlessException e)
                   {
                       JOptionPane.showMessageDialog(null,"Request failed" );
                   }
                   tf.setBackground(Color.white);
                   btnNewButton.doClick();
               }
               
               
               //txtDesc - description
               //txtLocation - location
               //cmbStart - start time
               //cmbEnd - endtime
        });
       
        
     
        
        JLabel[] labels = new JLabel[7];
        String[] dayNames = {"sat","sun","mon","Tue","wed","thu","Fri"};
        for(int i=0;i<labels.length;i++)
        {
            labels[i] = new JLabel(dayNames[i]);
            labels[i].setHorizontalAlignment(JLabel.CENTER);
            panel.add(labels[i]);
        }
        
        // Text wat buttons moet word
       
        
    }
    
   
    
    
    
   public void initCalDate(int todaysYear,int todaysMonth, int todaysDay )
    {
        //showCalendar(todaysYear, todaysMonth,null);
        yearSelectCombobox.setSelectedIndex(2);
        monthSelectCombobox.setSelectedItem("April");
        
       
            
        
        //monthSelectCombobox.setSelectedIndex(todaysMonth);
        //Sit yearSelected combobox se waarde hier in
    }
   
   public void clearFile(String chosenDay)
   {
       txtEvents.setText("");
       selectedDay=chosenDay;
       
       
   }
   
   
   
   public void readFile(String lerName ,int year, int month)
   {
       System.out.println ( " Die selected file is : "+lerName);
       String selectedLer = lerName;
       
       String dr = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
       String jr = Integer.toString(year);
       String mnd = Integer.toString(month+1);
        
        if(mnd.length() < 2)
            mnd = "0" + mnd;
        
        if(selectedLer.length() < 2)
            selectedLer = "0" + selectedLer;
            
            
        //Naam van die leer wat oopgemaak moet word
        File dirPath = new File(dr +"/Datums/"+""+year+"/"+mnd+"/"+selectedLer+".txt");
        
        //Die reference een lyn op 'n slag
        String line = null;
        
        try
        {
            //FileReader wat uit die teksleer gaan lees
            FileReader fileReader = new FileReader(dirPath);
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //int y = yearSelectCombobox.getSelectedIndex() + 1900;
            //int m = monthSelectCombobox.getSelectedIndex() + 1;
            txtEvents.setText(year+"/"+mnd+"/"+selectedtxt2+"\r\n\r\n");
            while((line=bufferedReader.readLine())!=null)
            {
                System.out.println(line);
                //txtEvents.append(line);
                txtEvents.append(line + "\r\n");
            }
            txtEvents.append("\r\n");
            bufferedReader.close();
            //txtEvents.append(y+"/"+m+"/"+selectedtxt2);
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("unable to open the file");
        }
        catch(IOException ex)
        {
            System.out.println("Error reading file");
        }
   
       
       
       
       
       
       
   }
   
   public  void getFolderNames(int year , int month)
   {
        String dr = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
       
        String jr = Integer.toString(year);
        String mnd = Integer.toString(month+1);
        
         System.out.println("Die jaar geselekteer is :" + year );
         System.out.println("Die maand geselekteer is :" + mnd );
       
         if(mnd.length() < 2)
            mnd = "0" + mnd;
        
        File dirJaar = new File(dr +"/Datums/");
        File dirMaand = new File(dr +"/Datums/"+""+year);
        File dirDag = new File(dr +"/Datums/"+""+year+"/"+mnd);
        String[] namesY = dirJaar.list();
        String[] namesM = dirMaand.list();
        
        ArrayList<String> lerName = new ArrayList();
        
        File[] namesD = dirDag.listFiles();
       
        for(String nameY : namesY)
        {
            if (new File(dr +"/Datums/" + nameY).isDirectory())
            {
                //Kyk of die selectedYear gelyk is aan die Year in die folder... Indien dit is ...
                if(jr.equals(nameY))
                {
                    System.out.println(jr+"Is the same as "+ nameY);
                    //...Maak die jaar folder oop en kyk na die maande daar binne in
                    for(String nameM : namesM)
                    {
                        //Kyk of die selectedMaand gelyk is aan die maand in die folder
                        if(new File(dr +"/Datums/" + nameY+"/"+nameM).isDirectory())
                        {
                            if((mnd.equals(nameM)) || nameM.equals("0"+mnd) )
                            {
                                System.out.println(mnd+" Is the same as "+ nameM);
                                for(int i = 0; i<namesD.length;i++)
                                {
                                    String fname = namesD[i].getName();
                                    lerName.add(fname);
                                    System.out.println("Die leernaam is :"+ fname);
                                }
                                showCalendar(year, month,lerName);
                                
                            }
                            else
                            {
                                System.out.println("No match");
                            }
                            
                        }
                    }
                    
                }
                System.out.println("Die naam van die directory is" +nameY);
            }
        }
        //Sit method met die String array hierin
        showCalendar(year, month,lerName);
        
   }
    
    
    
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        yearSelectCombobox = new javax.swing.JComboBox<>();
        monthSelectCombobox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        yearSelectCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        yearSelectCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearSelectComboboxActionPerformed(evt);
            }
        });

        monthSelectCombobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Show");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(yearSelectCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(149, 149, 149)
                        .addComponent(monthSelectCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(363, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(yearSelectCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(monthSelectCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(46, 46, 46)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void yearSelectComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearSelectComboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearSelectComboboxActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        System.out.println("Lekker");
        int yearSelected =  Integer.parseInt(yearSelectCombobox.getSelectedItem().toString());
        int monthSelected = monthSelectCombobox.getSelectedIndex();// As jy maand name soek maak dit net selected items
        
        //showCalendar(yearSelected, monthSelected);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    
    public JTextField[] textFields = new JTextField[42];
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
      /* try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold> 
            

        /*Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainFrame().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
      
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> monthSelectCombobox;
    private javax.swing.JComboBox<String> yearSelectCombobox;
    // End of variables declaration//GEN-END:variables

    //1900-2100
    private String[] getYears() 
    {
        String[] str = new String[201];
        for(int i= 1900,j =0 ; i<2100;i++,j++)
        {
            str[j] = String.valueOf(i);
        }
        return str ;
            
    }

    private String[] getMonths() 
    {
        String[] str = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        return str;
    }

    protected void showCalendar(int yearSelected, int monthSelected,final ArrayList<String> lerName) 
    {
       int[] monthDays = {31,28,31,30,31,30,31,31,30,31,30,31};
       String tday = Integer.toString(todaysDay);
       String tmonth = Integer.toString(todaysMonth);
       String tyear = Integer.toString(todaysYear);
       
       int year =1900;
       int month = 0;
       int day =2;
       
       while(colbeKina(yearSelected,monthSelected,year,month))
       {
           if(month == 1 && leapYear(year))
           {
               day+= 29;
           }else
           {
               day+= monthDays[month];
           }
           
           month++;
           if(month == 12) 
           {
               month = 0;
               year++;
           }
           
           day = day % 7;
       }
       

       
       System.out.println(textFields.length + "");
       for(int i = 0; i <textFields.length; i++)
       {
           System.out.println(textFields[i].getText());
           textFields[i].setText("");
           textFields[i].setBackground(Color.white);
           
       }
       
       int lastValue = monthDays[monthSelected];
       if(monthSelected ==1 && leapYear(yearSelected))
       {
           lastValue ++;
       }
       
       for(int i=1,j=day;i<= lastValue;i++,j++)
       {
           String selectedtxt = textFields[j].getText();
           textFields[j].addMouseListener(new MouseAdapter()
                    {
                        public void mousePressed(MouseEvent e)
                            {    
                                
                                tf = (javax.swing.JTextField) e.getSource();
                                selectedtxt2 = tf.getText();
                                System.out.println(selectedtxt2);
                                 clearFile(selectedtxt);
                                                                
                            }
                       
                     });
       
       }
       
       for(int i=1,j=day;i<= lastValue;i++,j++)
       {
           
           
           textFields[j].setText(String.valueOf(i));
           //Kode om die goed te highlight
          // sukkel om deur die lys te gaan
          if(lerName != null)
          {
              //System.err.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + lerName.size());
               for(int count = 0; count<lerName.size();count++)
             {
                 
                 String leerNaam = lerName.get(count).substring(0,lerName.get(count).indexOf("."));
                 leerNaam = String.valueOf(Integer.parseInt(leerNaam));
                 final String leerNaam2 = leerNaam;
                 String selectedtxt = textFields[j].getText();
                 //System.err.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb " + leerNaam +" " + textFields[j].getText() + " " + leerNaam.length());
                 if(leerNaam.compareTo(textFields[j].getText())==0)
                {
                     textFields[j].setBackground(Color.GRAY);
                     
                     textFields[j].addMouseListener(new MouseAdapter()
                    {
                        public void mousePressed(MouseEvent e)
                            {    
                                
                                
                                 System.out.println("Batman");
                                 //selectedDay = selectedtxt;
                                 
                                 //txtEvents.setText(yearSelectCombobox.getSelectedItem()+""+m+"/"+selectedtxt2);
                                 readFile(leerNaam2 ,yearSelected, monthSelected);
                                 
                                
                            }
                       
                     });
                     
                    
                }
                 
                 
             }
          }
          
          
           System.out.println(i+ " " + tday);
           if(Integer.toString(i).equals(tday) && todaysMonth == monthSelected+1 && todaysYear == yearSelected)
           {
               System.out.println(" YearSelected :"+yearSelected+"todaysYear"+ tyear);
               textFields[j].setBackground(Color.MAGENTA);
           }
          
       }
       
    }

    private boolean colbeKina(int yearSelected, int monthSelected, int year, int month)
    {
        if(yearSelected==year && monthSelected == month)
        {
            return false;
        }
       return true;
    }

    private boolean leapYear(int year) 
    {
        boolean ans = false;
        
        if( year % 4 == 0)
        {
            ans = true;
        }
        if(year % 100 == 0)
        {
            ans = false;
        }
        if(year % 400 == 0)
        {
            ans = true;
        }
        return ans;
    }
    
    public void deleteAllFiles(File f) throws IOException
    {
         if(!f.exists())
        {
            System.out.println("Dir does not exist");

        }
        else
        {
            
            deleteDir(f);
        }
        
    }
   
      
    
   
   public void deleteDir(File file) throws IOException
   {
       if(file.isDirectory())
       {
           if(file.list().length ==0)
           {
               deleteEmptyDir(file);
           }
           else
           {
               File files[] = file.listFiles();
               for(File fileDelete : files)
               {
                   deleteDir(fileDelete);
               }
               
               if(file.list().length == 0)
               {
                   deleteEmptyDir(file);
               }
           }
       }
       else
       {
           file.delete();
           System.out.println("File is deleted "+ file.getAbsolutePath());
       }
   }
   
   public void deleteEmptyDir(File file)
   {
       file.delete();
       System.out.println("Directory is deletd :"+ file.getAbsolutePath());
   }

    
    
    
    
    
    
    
    private static Message[] inbox() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void sendRequest()
    {
        
        //client_gui.sendEmail(to, subject, msg, from, userName, password);
    }
    
    
   
    
}



    

