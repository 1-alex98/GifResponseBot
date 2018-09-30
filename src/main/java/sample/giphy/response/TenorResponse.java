package sample.giphy.response;

import lombok.Data;

import java.util.List;

@Data
public class TenorResponse {
    private String weburl;
    private List<TenorGif> results;
}
