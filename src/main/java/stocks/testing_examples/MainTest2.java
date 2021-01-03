package stocks.testing_examples;

import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import stocks.system.RobotAwt;

public class MainTest2 {

    

    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) throws AWTException, InterruptedException {
	
	String str = "Symbol:JAGX Shares:80";
	
	System.out.println(str.split("Symbol:")[0].split(" ")[0]);
	
	
	customOrderAndFile();
	

    }
    
    
    
    public static void customOrderAndFile() {
   	try {
   	
   	  boolean fileExist = false;
   	  File f;
   	    do {
   	        System.out.println("Please enter the file Path:");
   	        f = new File(sc.nextLine());
   	        if(f.isFile())
   	            fileExist = true;
   	        else {
   	            System.out.println("Couldn't find file.");
   	        }
   	    } while(!fileExist);
   	    

   	    Files.lines(Path.of((f.getAbsolutePath()))).forEach(str -> {
   		
   		System.out.println(str);
   		
   		String symbol = str.split("Symbol:")[1].split(" ")[0];
   		int shares = Integer.parseInt(str.split("Shares:")[1].split(" ")[0]);
   		double capital = Double.parseDouble(str.split("Capital:")[1].split(" ")[0]);
   		double price = Double.parseDouble(str.split("Price:")[1].split(" ")[0]);
   		int account = Integer.parseInt(str.split("Account:")[1].split(" ")[0]);
   		
   	        System.out.println(symbol + shares +  capital +  price +  account);
   	    });
   	} catch (IOException e) {
   	    // TODO Auto-generated catch block
   	    e.printStackTrace();
   	}
       }

}
