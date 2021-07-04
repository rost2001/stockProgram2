package stocks.model.yahoo;

  
  import java.util.ArrayList; 
  import java.util.Calendar; 
  import java.util.List;
  import java.util.Map; 
  import java.util.Map.Entry;
  
  import org.openqa.selenium.JavascriptExecutor;
  
  import yahoofinance.Stock; 
  import yahoofinance.YahooFinance; 
  import yahoofinance.histquotes.HistoricalQuote; 
  import yahoofinance.histquotes.Interval;
  
  public class YahooHistory {
  
  
  
  
  public static void main(String[] args) {
  
      
      
      Calendar from = Calendar.getInstance(); Calendar to = Calendar.getInstance();
      from.add(Calendar.MONTH, -5);
      
      
      Stock google = YahooFinance.get("AAPL"); 
      
      List<HistoricalQuote>
      googleHistQuotes = google.getHistory(from, to, Interval.DAILY);
      
      
      
      String history = "";
      
      for(int i = 0; i < googleHistQuotes.size() ;i++) {
      
      history += (googleHistQuotes.get(i).getDate().getTime()); history += "<br/>";
      
      //history += ("Day " + i + " | "); history += (" Gap: " +
      
      String.format("%.0f", (googleHistQuotes.get(i).getHigh().doubleValue() /
      googleHistQuotes.get(i).getLow().doubleValue() - 1)*100) + "%"); history +=
      (" Low: " + String.format("%.0f",
      (googleHistQuotes.get(i).getLow().doubleValue() /
      googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%"); history +=
      (" High: " + String.format("%.0f",
      (googleHistQuotes.get(i).getHigh().doubleValue() /
      googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%"); history +=
      (" Close: " + String.format("%.0f",
      (googleHistQuotes.get(i).getClose().doubleValue() /
      googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%"); history +=
      (" Vol: " + googleHistQuotes.get(i).getVolume() ); history += (" Close: " +
      String.format("%.0f", (googleHistQuotes.get(i).getClose().doubleValue() /
      googleHistQuotes.get(i).getOpen().doubleValue() - 1)*100) + "%"); history +=
      "<br/>--------------------------------------"; history += "<br/>"; }
      

      
      Thread.sleep(500);

      
      //Thread.sleep(10000); //bot.close(); }
      
      
      
      
      
      String[] symbols = new String[] {"AMD", "zom", "izea", "bngo", "OCGN",
      "AAPL", "GOOGL", "ACOR", "CEI", "ZOOM"};
      
      
      
      from = Calendar.getInstance(); Calendar to = Calendar.getInstance();
      from.add(Calendar.MONTH, -10);
      
      
      Map<String, Stock> stocks = null; long startTime =
      System.currentTimeMillis(); stocks = YahooFinance.get(symbols); long
      estimatedTime = System.currentTimeMillis() - startTime;
      System.out.println("ms: **" + estimatedTime);
      
      
      
      ArrayList<Stock> stored = new ArrayList<Stock>(); ArrayList<Stock> active =
      new ArrayList<Stock>();
      
      ArrayList<Stock> temp = new ArrayList<Stock>(); Stock intel =
      stocks.get("OCGN"); for (Entry<String, Stock> entry : stocks.entrySet()) {
      System.out.println(entry.getKey() + "/" + entry.getValue());
      
      for(int i = 0; i < stored.size();i++) {
      
          
      if(entry.getKey().equalsIgnoreCase(stored.get(i).getSymbol()) )
      temp.add(entry.getValue()); 
      
      else if(i < stored.size()-1) { 
          
      }
      
      } } stored = temp;
      
      
      
      
      List<HistoricalQuote> googleHistQuotes = intel.getHistory();
      
      
      
      
      
      for(int i = 0; i < googleHistQuotes.size(); i++) {
      System.out.println(googleHistQuotes.get(i).getDate().getTime());
      System.out.println(googleHistQuotes.get(i).getLow());
      System.out.println("-----------------------------"); }
      
      }
      
  }
  
  
  

 