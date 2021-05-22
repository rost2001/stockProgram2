package stocks.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UFilings {


    /* Reading a file and parses it.
     * Returns a list of strings ordered like the fields
     * -To use- make a file and format it like this one per each line ex: 
     * tradingviewUsername:fj222hn@student.lnu.se
     * tradingviewPassword:...
     * avanzaPersonnummer:...
     * */
    /* UFiles.readFile(System.getProperty("user.dir") + "\\src\\main\\resources\\FileStorage\\" + "Credentials.txt"
	, "tradingviewUsername", "tradingviewPassword").get(1)
     * */
    public static List<String> readFile(String path, String ...fields) throws IOException{


	List<String> result = new ArrayList<String>();
	Files.lines(Path.of(new File(path).getAbsolutePath())).forEach((String str) -> 
	{
	    for(String field : fields) {
		if(str.toLowerCase().contains(field.toLowerCase()))
		    result.add(str.split(field+":")[1]);
	    }
	}
	);

	return result;
    }


}
