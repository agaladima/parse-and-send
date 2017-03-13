import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;

public class TotalContacts {
	public static void main(String[] args) {
		String userInput = CompanyName();
		//getting primary email
		String pe = PrimaryEmail(userInput);
		System.out.println(pe);
		System.out.println("<-------------------------------------------------------------------------------------->");
		//getting owner email
		String oe = ownerEmail(userInput);
		System.out.println(oe);
		System.out.println("<-------------------------------------------------------------------------------------->");
		//getting list of contacts
		ArrayList<String> contacts = getList(userInput);
		for(String s : contacts){
			System.out.println(s);
		}
	}//end of main...
	
	public static String CompanyName(){
		//company to find
		Scanner userInputScanner = new Scanner(System.in);
		System.out.print("Which company's contacts do you need?");
		String userInput = userInputScanner.nextLine();
		userInputScanner.close();
		return userInput;
	}
	
	public static String PrimaryEmail(String company){
		//This is the email address of the primary point of contact for the company
		//Parsing/reading CSV file 
		String csvFile = "C:\\Users\\arum\\TotalContacts.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
	    
        //String null for email
		String primEmail = null;
        //String primEmail = new ArrayList<String>();

        try{
            br = new BufferedReader(new FileReader(csvFile));
            
            while((line = br.readLine()) != null) {
            // use comma as separator
            String[] entry = line.split(cvsSplitBy);
            //Search by company name
                if(entry[11].equals("Yes") && entry[1].toLowerCase().contains(company.toLowerCase())){
                	primEmail = entry[3].toString();
                }//end of if statement...                
            }//end of while loop...
        }//end of try...
        
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if(br != null){
                try{
                    br.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }//end of if statement...
        }//end of finally...
        return primEmail;
	}//end of PrimaryEmail method...
	
	public static String ownerEmail(String company){
		//This is the CCN employee that handles matters with the company
		//Parsing/reading CSV file 
		String csvFile = "C:\\Users\\arum\\TotalContacts.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
	    
        //String null for email
		String ownerEmail = null;

        try{
            br = new BufferedReader(new FileReader(csvFile));
            
            while((line = br.readLine()) != null){
            // use comma as separator
            String[] entry = line.split(cvsSplitBy);
            	//Search for owner name and provide appropriate email
                if(entry[12].contains("Shawn") && entry[1].toLowerCase().contains(company.toLowerCase())){
                	ownerEmail = "shawn@contractors.net";
                }//end of if statement...
                if(entry[12].contains("Sindy") && entry[1].toLowerCase().contains(company.toLowerCase())){
                	ownerEmail = "sindy@contractors.net";
                }//end of if statement...  
                if(entry[12].contains("Troy") && entry[1].toLowerCase().contains(company.toLowerCase())){
                	ownerEmail = "troy@contractors.net";
                }//end of if statement...
            }//end of while loop...
        }//end of try...
        
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }        
        finally{
            if(br != null){
                try{
                	br.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }//end of if statement...
        }//end of finally...
        return ownerEmail;
	}//end of ownerEmail method...
	
	public static ArrayList<String> getList(String company) {
		//Parsing/reading CSV file 
		String csvFile = "C:\\Users\\arum\\TotalContacts.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
	    
        //ArrayList holding csv lines
        ArrayList<String> fullList = new ArrayList<String>();

        try{
            br = new BufferedReader(new FileReader(csvFile));
            
            while((line = br.readLine()) != null){
            // use comma as separator
            String[] entry = line.split(cvsSplitBy);
            String wholeString = entry[0]+"| "+entry[1]+"| "+entry[2]+"| "+entry[3]+"| "+entry[4]+"| "+entry[5]+"| "+entry[6]+"| "+entry[7]+"| "+entry[8]+"| "+entry[9]+"| "+entry[10]+"| "+entry[11]+"| "+entry[12].toString();
            //Search by company name
                if(entry[1].toLowerCase().contains(company.toLowerCase())){
                	fullList.add(wholeString);
                	//System.out.println(fullList);
                }//end of if statement...

                
                //Save .txt file
                FileWriter writer = new FileWriter(company+"_output.txt");
                for(String str : fullList){
                	writer.write(str);
                }
                writer.close();
            }//end of while loop...
        }//end of try...
        
        
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if(br != null){
                try{
                    br.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }//end of if statement...
        }//end of finally...
        return fullList;
	 }//end of getList method...
}
