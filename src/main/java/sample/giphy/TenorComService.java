package sample.giphy;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import sample.giphy.response.TenorGif;
import sample.giphy.response.TenorMedia;
import sample.giphy.response.TenorResponse;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TenorComService {
    private static TenorComService ourInstance = new TenorComService();
    private static final String TENOR_API_TOKEN ="-";
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
        int i = random.nextInt(results.size());

        List<TenorMedia> media = results.get(i).getMedia();
        if(media.isEmpty()){
            throw new RuntimeException("Invalid search word");
        }
        return media.get(0).getGif().getUrl();
    }
}
