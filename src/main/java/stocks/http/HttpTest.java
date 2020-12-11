package stocks.http;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class HttpTest {
	
	
	
	class Two2 {
		
	}
	
	

	public static void main(String[] args) throws IOException, InterruptedException {
		
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://finance.yahoo.com/quote/TOPS"))
				.build();
		
		
		HttpClient client = HttpClient.newHttpClient();
		/* async version
		
		client.sendAsync(request, BodyHandlers.ofString())
		
	      .thenApply(response -> { System.out.println(response.statusCode()); return response; } )
	      
	      
		      .thenApply(HttpResponse::body)
		      
		      .thenApply(response -> {System.out.println(response); return response;} )
		      
		      .join();

		*/
		long startTime = System.nanoTime();
		
		HttpResponse<String> response =
			      client.send(request, BodyHandlers.ofString());
		long elapsedTime = System.nanoTime() - startTime;
		
        
			System.out.println(response.statusCode());
			System.out.println(response.body());
		
			System.out.println("***** " + elapsedTime/1000000 + " ms");
	}

	

	
	
	
	
	
}
