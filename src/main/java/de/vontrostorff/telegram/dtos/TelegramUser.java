package de.vontrostorff.telegram.dtos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TelegramUser {
  private Integer id;
  @SerializedName("is_bot")
  private Boolean isBot;
  @SerializedName("language_code")
  private String languageCode;
}
