package sample.telegram.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TelegramUpdateResponse{
    private boolean ok;
    private List<TelegramUpdate> result;
}
