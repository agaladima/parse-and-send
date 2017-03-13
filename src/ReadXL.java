import java.util.ArrayList;
import java.io.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class ReadXL {

	public static void main(String[] args){
		//----------------------------------------------GET THE DATE AND TIME--------------------------------------------
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

        Calendar cal = Calendar.getInstance();
        //System.out.println(sdf.format(cal.getTime()));
		//------------------------------------------END OF GET DATE AND TIME---------------------------------------------
        XSSFWorkbook workbook = new XSSFWorkbook(); 

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Current Contact Info");
        
        //Call TotalContacts class to get company name, arraylist, primary contact email and CCN owner email
        TotalContacts tc = new TotalContacts();
        String compName = tc.CompanyName();
		ArrayList<String> comp = tc.getList(compName);
		String primary = tc.PrimaryEmail(compName);
		String owner = tc.ownerEmail(compName);
		
		String fname = compName+"_"+sdf.format(cal.getTime())+".xlsx";
		String filename = "C:\\Users\\arum\\ToSend\\VIPSent\\"+fname;
		
        String str = "Name| Company| Phone| Email| Title| Address| City| State| PostalCode| Type| Website| PrimaryContact| Owner";
        comp.add(0,str);
        int rowCount = 0;

        for (Object aBook : comp) {
            String[] entry =comp.get(rowCount).toString().split("\\|");
            //System.out.println(entry[rowCount]);
            // rowCount++;
            System.out.println(++rowCount);

            Row row = sheet.createRow(rowCount);
            int columnCount = 0;
            for (Object field : entry) {
                columnCount++;
                Cell cell = row.createCell(columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }
        
       try{
			//Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(filename));
			workbook.write(out);
			out.close();
			System.out.println("The file has been written and saved successfully.");
			
			//THE SENDING OF THE EMAIL
			// Recipient's email ID needs to be mentioned.
			String to = primary;
			//String to = "arum.galadima@gmail.com";
			
			// Sender's email ID needs to be mentioned
			String from = "arum@contractors.net";
		      
			//CC email address
			String cc = owner;
			//String cc = "arum.galadima@gmail.com";
			
			final String username = "maggioroofing\\arum";//change accordingly
	      	final String password = "luvrhater12";//change accordingly
		
	      	// Email host
	      	String host = "mail.maggioroofing.com";
			
	      	Properties props = new Properties();
      		props.put("mail.smtp.auth", "false");
      		props.put("mail.smtp.starttls.enable", "true");
      		props.put("mail.smtp.host", host);
      		props.put("mail.smtp.port", "25");
			
      		// Get the Session object.
      		Session session = Session.getInstance(props,
  				new javax.mail.Authenticator() {
			        protected PasswordAuthentication getPasswordAuthentication() {
			           return new PasswordAuthentication(username, password);
			        }
      			});
		
		      try {
		         // Create a default MimeMessage object.
		         Message message = new MimeMessage(session);
		
		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(from));
		
		         // Set To: header field of the header.
		         message.setRecipients(Message.RecipientType.TO,
		            InternetAddress.parse(to));
		         
		         //Set cc email address
		         message.setRecipients(Message.RecipientType.CC,
		 	            InternetAddress.parse(cc));
		
		         // Set Subject: header field
		         message.setSubject("CCN Information Check-In");
		
		         // Create the message part
		         BodyPart messageBodyPart = new MimeBodyPart();
		         
		         //Create line separator
		         String nLine = System.getProperty("line.separator");
		         
		         // Now set the actual message
		         messageBodyPart.setText("Greetings,"+nLine+nLine+ "I’m Arum from Certified Contractors Network, and I have been working on cleaning up the contact info in our system,"+nLine
		         		+ "just so our communications (newsletters, etc.) will reach the right people. Could you review the attached information,"+nLine
		         		+ "and verify its accuracy? **Please highlight all changes/updates in yellow.** Let me know if there is anything you would like me to change (add, delete, etc) like:"+nLine+nLine
		         		+ ">> Current employee info"+nLine+ ">> Phone numbers"+nLine+ ">> Correct email addresses"+nLine+ ">> Correct company/mailing address" +nLine+ ">> Correct website"+nLine+nLine
		         		+ "If there is someone else in charge of updating this on your side, I will greatly appreciate your assistance in forwarding/copying them on this email."+nLine+nLine
		         		+ "Thanks so much!"+nLine+nLine
		   
	  					+ "Arum Galadima"+nLine
						+ "Membership Database Specialist"+nLine
						+ "Certified Contractors Network (CCN)"+nLine
						+ "301.891.0999 x102");
		
		         // Create a multipar message
		         Multipart multipart = new MimeMultipart();
		
		         // Set text message part
		         multipart.addBodyPart(messageBodyPart);
		
		         // Part two is attachment
		         messageBodyPart = new MimeBodyPart();
		         //String filename = LaFilename();
		         DataSource source = new FileDataSource(filename);
		         messageBodyPart.setDataHandler(new DataHandler(source));
		         messageBodyPart.setFileName(fname);
		         multipart.addBodyPart(messageBodyPart);
		
		         // Send the complete message parts
		         message.setContent(multipart);
		
		         // Send message
		         Transport.send(message);
		
		         System.out.println("Sent message successfully....");
		         
		      }
  	    catch (MessagingException e) {
	         throw new RuntimeException(e);
	      }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
