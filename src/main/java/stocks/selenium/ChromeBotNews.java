package stocks.selenium;

import java.util.List;

public class ChromeBotNews {

    private ChromeBotNews(){}
    
    
    public static List<String> getStocktwits(ChromeBot bot, String symbol) throws Exception{
	
	    bot.get("https://stocktwits.com/symbol/" + symbol);
	
	
	
	return null;
    }

    
    public static List<String> getSecFilings(ChromeBot bot, String symbol) throws Exception{
	
	    bot.get("https://stocktwits.com/symbol/" + symbol);
	    
	    

	
	return null;
    }


}
