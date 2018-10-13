package de.vontrostorff.giphy;

import de.vontrostorff.giphy.response.TenorGif;
import de.vontrostorff.giphy.response.TenorResponse;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TenorComService {
    private static TenorComService ourInstance = new TenorComService();
    private static final String TENOR_API_TOKEN =System.getenv("TENOR_API_KEY");
    private final RestTemplate restTemplate;
    private final Random random= new Random();

    public static TenorComService getInstance() {
        return ourInstance;
    }

    private TenorComService() {
        restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections.singletonList(new GsonHttpMessageConverter()));
    }

    public String getRandomGifUrlForWord(String word){
        TenorResponse forObject = restTemplate.getForObject(MessageFormat.format("https://api.tenor.com/v1/search?q={0}&key={1}&limit=50", word, TENOR_API_TOKEN), TenorResponse.class);
        List<TenorGif> results = forObject.getResults();
        int i = random.nextInt(results.size());
        return results.get(i).getMedia().get(0).getGif().getUrl();
    }

    public String getRandomGifUrlForWord(String word, String locale){
        TenorResponse forObject = restTemplate.getForObject(MessageFormat.format("https://api.tenor.com/v1/search?q={0}&key={1}&limit=50&local={2}", word, TENOR_API_TOKEN, locale), TenorResponse.class);
        List<TenorGif> results = forObject.getResults();
        if(results.isEmpty()){
            throw new RuntimeException("Invalid search word");
        }
        int i = random.nextInt(results.size());


        return results.get(i).getMedia().get(0).getGif().getUrl();
    }
}
