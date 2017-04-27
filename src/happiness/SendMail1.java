package happiness;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.*;  
import javax.mail.internet.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SendMail1 {  
	public static void main(String[] args) throws IOException { 
		String msg,msg1,msg2,msg3,msg4;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateobj = new Date();
		String date=df.format(dateobj);
		System.out.println(date);
		Emp_Info e=new Emp_Info();
		ArrayList<String> st2=new ArrayList<String>();
		//getting email addresses from viewdetails method
		st2=e.viewdetails();
		System.out.println(st2);
		ArrayList<String> st3=new ArrayList<String>();
		//getting urls from getUrls method
		st3=e.geturls();
		String st5=st3.get(0);
		String host="smtp.gmail.com";  //smtp host
		final String user="cmit.coe@gmail.com";//username
		final String password="coMakeIT";//password 

		//Get the session object  
		Properties props = new Properties();  
		props.put("mail.smtp.host",host);  
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getDefaultInstance(props,  
				new javax.mail.Authenticator() {  
			protected PasswordAuthentication getPasswordAuthentication() {  
				return new PasswordAuthentication(user,password);  
			}  
		});  

		//Compose the message  
		try { 
			//InternetAddress[] iAdressArray = new InternetAddress[st2.size()];
			for (int i = 0; i < st2.size(); i++) {
				StringTokenizer st = new StringTokenizer(st2.get(i).toString(),"[/,");
				//System.out.println(st);
				while(st.hasMoreTokens()){
					String usern=st.nextToken();
					String team=st.nextToken();
					msg1 = st5+ "?username=" + usern+"&status="+"veryhappy" +"&date="+date+"&team="+team;
					msg2= st5+ "?username=" + usern+"&status="+"quitehappy" +"&date="+date+"&team="+team;
					msg3 = st5+ "?username=" + usern+"&status="+"nothappy" +"&date="+date+"&team="+team;
					msg4= st5+ "?username=" + usern+"&status="+"verysad" +"&date="+date+"&team="+team;
					String veryhappy= "<a href="+msg1+">" +"Very Happy &#128515;"+"</a><br>";
					String quitehappy= "<a href="+msg2+">" +"Quite Happy &#128516;"+"</a><br>";
					String nothappy= "<a href="+msg3+">" +"Not Happy &#128530;"+"</a><br>";
					String notatallhappy= "<a href="+msg4+">" +"Not at all Happy &#128542;"+"</a>";
					//System.out.println(st5+ "&"+st2.get(i));
					//	iAdressArray[i] = new InternetAddress(st2.get(i));
					MimeMessage message = new MimeMessage(session);  
					message.setFrom(new InternetAddress(user)); 
					//adding recipient addresses
					message.setRecipients(Message.RecipientType.TO, usern);
					message.setSubject("Happiness-Index"); 
					msg = "<i>Greetings!</i><br>";
					msg += "<b>How was the day?</b><br>";
					String msgbody=msg+veryhappy+quitehappy+nothappy+notatallhappy;
					message.setContent(msgbody, "text/html");
					//sending the message  
					Transport.send(message);  
					System.out.println("message sent successfully..."); 
				}
			}
		} catch (MessagingException ee) {ee.printStackTrace();}  


	} 
}










