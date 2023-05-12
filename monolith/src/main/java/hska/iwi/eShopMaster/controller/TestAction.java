package hska.iwi.eShopMaster.controller;
import com.opensymphony.xwork2.ActionSupport;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import org.codehaus.jackson.map.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestAction extends ActionSupport {

    /**
     *
     */
    private static final long serialVersionUID = 3657805620703279195L;
    public Product product = new Product();
    
    @Override
    public String execute() throws Exception {
        
        // Return string:
        String result = "success";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://products-service:4000/api/products/one")) // Replace with your .NET controller endpoint URL
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();

        String responseBody = response.body();

        // Deserialize the response body into a Java object using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(responseBody, Product.class);

        this.product = product;

        return result;
    }
    
	@Override
	public void validate() {
	}
}
