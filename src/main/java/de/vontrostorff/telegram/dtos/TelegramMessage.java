package de.vontrostorff.telegram.dtos;

import lombok.Data;

@Data
public class TelegramMessage {
  private Integer message_id;
  private TelegramUser from;
  private TelegramChat chat;
  private Integer date;
  private String text;
}
