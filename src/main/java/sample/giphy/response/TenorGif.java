package sample.giphy.response;

import lombok.Data;

import java.util.List;

@Data
public class TenorGif {
    private String url;
    private List<TenorMedia> media;
}
