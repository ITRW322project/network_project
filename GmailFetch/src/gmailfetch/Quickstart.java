package gmailfetch;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;


public class Quickstart {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Calendar API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/calendar-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        //InputStream in =
        //    Quickstart.class.getResourceAsStream("/client_secret.json");
	InputStream in = new FileInputStream("C:\\Users\\Johannnnnn\\Documents\\NetBeansProjects\\Gmailfetch\\src\\client_secret.json");
			
			
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
        getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] args) throws IOException {
        getEvent();
    }
    
    
    
    public static void addEvent(String[] input, String sender) throws IOException
    {
        com.google.api.services.calendar.Calendar service =
            getCalendarService();

        
        Event event = new Event()
        .setSummary(input[0])
        .setLocation(input[1])
        .setDescription(input[2]);

        
        
        
        DateTime startDateTime = new DateTime(input[3]);
        EventDateTime start = new EventDateTime()
            .setDateTime(startDateTime)
            .setTimeZone("Africa/Johannesburg");
        event.setStart(start);

        DateTime endDateTime = new DateTime(input[4]);
        EventDateTime end = new EventDateTime()
            .setDateTime(endDateTime)
            .setTimeZone("Africa/Johannesburg");
        event.setEnd(end);
        
        //jaco quickstart
        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
        event.setRecurrence(Arrays.asList(recurrence));

        if(!sender.equals(""))
        {
        EventAttendee[] attendees = new EventAttendee[] {
            new EventAttendee().setEmail(sender),
        };
        event.setAttendees(Arrays.asList(attendees));
        }
        //Jaco quickstart
        EventReminder[] reminderOverrides = new EventReminder[] {
            new EventReminder().setMethod("email").setMinutes(24 * 60),
            new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
            .setUseDefault(false)
            .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        System.out.println(event.toString());
        System.out.printf("Event created: %s\n", event.getHtmlLink());
        getEvent();
        
    }
    
    public static List<Event> getEvent() throws IOException
    {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
            getCalendarService();

       
        
        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime("2000-01-01T00:00:00.000+02:00");
        Events events = service.events().list("primary")
            .setMaxResults(1000)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute();
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
                String eventSum = event.getSummary();
                
                //Hier word die method geroep om die date te substring om die korrekte folders te maak
                dateTimeCalc(start,eventSum);
            }
        }
        return items;
    }
    
    public static boolean testAppointmentAvailability(Date appDate, Date appSTime, Date appETime) throws IOException, ParseException
    {
        List<Event> items = getEvent();
        
        for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                DateTime end = event.getEnd().getDateTime();
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date sDate = new Date(start.getValue());

                sDate = new SimpleDateFormat("yyyy-MM-dd").parse(sdf.format(sDate));
                
                Date eDate = new Date(end.getValue());
                
                eDate = new SimpleDateFormat("yyyy-MM-dd").parse(sdf.format(eDate));
                
                Date sTime = new Date(start.getValue());
                Date eTime = new Date(end.getValue());
                
                System.out.println(".....................................................................");
                System.out.println(sDate + " " + appDate);
                
                if((sDate.compareTo(appDate))==0)
                {
                    System.out.println("===================================================================");
                    System.out.println(appSTime + " " +eTime);
                    System.out.println(appSTime.before(eTime));
                    System.out.println(appETime+ " " +sTime);
                    System.out.println(appSTime.before(eTime));
                    if((appSTime.before(eTime))&&(appETime.after(sTime)))
                        return false;
                }
                
            }
        
        return true;
    }
    
    //Jaco se code 
    
    //Creating the directory of the event
    public static void createFolder (String year, String month, String day,String time, String eventSum) throws IOException
    {
       
       String dr = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();

      // File dirJaar = new File(dr +"/Datums/"+""+year+"");
        File dirJaar = new File(dr +"/Datums/"+""+year+"/"+month+"\\"+day+".txt");
        File dirMaand = new File(dr +"/Datums/"+""+year+"/"+month+"");
        File dirDag = new File(dr +"/Datums/"+""+year+"/"+month+"\\"+day+".txt");
       //+"\\mytxt.txt"
       dirJaar.getParentFile().mkdirs();
       dirMaand.getParentFile().mkdirs();
       dirDag.getParentFile().mkdirs();
      
       
            
       //As die jaar folder nie bestaan nie
       if(!dirJaar.exists())
       {
          
           dirJaar.createNewFile();
           writeToFile(eventSum,time,dirDag);
           System.out.println("Dir "+dirJaar.getName()+" was printed");
           
       }else
        {
            //As die jaar folder bestaan...
            if(dirJaar.exists())
            {
                //...kyk dan of die maand folder bestaan
                if(!dirMaand.exists())
                {
                    dirMaand.mkdir();
                    dirDag.createNewFile();
                    writeToFile(eventSum,time,dirDag);
                    
                }
                //...As die maand folder bestaan kyk of die dag file in die maand folder bestaan
                else
                {
                    if(dirMaand.exists())
                    {
                        //Kyk of die dag file bestaan
                        if(!dirDag.exists())
                        {
                            dirDag.createNewFile();
                            //Append event teks
                            writeToFile(eventSum,time,dirDag);
                        }else
                        {
                            if(dirDag.exists())
                                    {
                                        //Append event teks
                                        writeToFile(eventSum,time,dirDag);
                                    }
                            
                        }
                    }
                }
            }
            
        }
        
            
      
    }
    
    public static void dateTimeCalc (DateTime date,String eventSum ) throws IOException
    {
        
        String dateString = date.toString();
        String completeDate = date.toString().substring(0,10);
        
        //Breuk die DateTime van die event op in jaar,maand,dag en tyd
        String [] dateParts = completeDate.split("-");
        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];
        String time = date.toString().substring(11,dateString.length()-10);
        

        System.out.println(day + month+year);
        
        //createFolder
        createFolder(year,month,day,time, eventSum);
        
    }
    
    public static void writeToFile (String eventSum, String time, File dirDag) throws IOException
    {
        //Moontlike probleem is dat elke keer as die stuff gerun word dan append dit twee keer. Oplossing is om elke keer die leers skoon te maak voor nuwe stuff geadd word
       String lyn ="\n\r" + time +"\n\r"+eventSum+"\n\r";
        String line="";
        boolean valid = true;
        
     
        
        try
        {
            //FileReader wat uit die teksleer gaan lees
            FileReader fileReader = new FileReader(dirDag);
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while((line=bufferedReader.readLine())!=null)
            {
                if(line.equals(lyn))
                {
                    System.out.println("Die teks bestan klaar");
                    valid= false;
                    bufferedReader.close();
                }
                else
                {
                    bufferedReader.close();
                }
            }
            
                if(valid == true)
                {
                     FileWriter fw = new FileWriter(dirDag,true);
                     BufferedWriter writer = new BufferedWriter(fw);
                     writer.append(' ');
                     writer.append(lyn);
                     writer.close();
                }
            
            
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

  
    
    
    

}