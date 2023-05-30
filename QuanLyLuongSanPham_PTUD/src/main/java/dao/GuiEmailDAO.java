package dao;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class GuiEmailDAO {

	
	public boolean sendEmail(String nguoiNhan, String chuDe, String noiDung, List<File> tepDinhKem ) throws IOException {
	    // Replace with your Mailgun API key and domain
	    String apiKey = "0361f091422458eba0c72e2ebb2602b6-6b161b0a-ac2f1be3";
	    String domain = "sandbox855a4f27aebe475691688b6c4c2b9dd7.mailgun.org";

	    HttpClient client = HttpClientBuilder.create().build();
	    HttpPost post = new HttpPost("https://api.mailgun.net/v3/" + domain + "/messages");

	    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	    builder.addTextBody("from", "lantuonglearning@gmail.com");
	    builder.addTextBody("to", nguoiNhan);
	    builder.addTextBody("subject", chuDe, ContentType.create("text/plain", StandardCharsets.UTF_8));
	    builder.addTextBody("text", noiDung,  ContentType.create("text/plain", StandardCharsets.UTF_8));
	
	    for (File tep : tepDinhKem) {
	        builder.addPart("attachment", new FileBody(tep));
	    }

	    HttpEntity entity = builder.build();
	    post.setEntity(entity);

	    post.addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(("api:" + apiKey).getBytes()));
	    HttpResponse response = client.execute(post);
	    System.out.println(EntityUtils.toString(response.getEntity()));
		
	    return true;
	    
	    
	    
	    
	}
	
	
}
