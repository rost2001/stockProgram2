package stocks.selenium;

import java.util.List;

public class CBNews {

    private CBNews(){}
    
    
    public static List<String> getStocktwits(ChromeBot bot, String symbol) throws Exception{
	
	    bot.get("https://stocktwits.com/symbol/" + symbol);
	
	
	
	return null;
    }

    
    public static List<String> getSecFilings(ChromeBot bot, String symbol) throws Exception{
	
	    bot.get("https://stocktwits.com/symbol/" + symbol);
	    
	    

	
	return null;
    }


}
