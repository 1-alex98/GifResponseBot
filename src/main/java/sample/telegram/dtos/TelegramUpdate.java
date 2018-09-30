package sample.telegram.dtos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TelegramUpdate {
  @SerializedName("update_id")
  private Integer updateId;
  private TelegramMessage message;
}
