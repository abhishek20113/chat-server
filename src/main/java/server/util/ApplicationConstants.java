package server.util;

import com.google.gson.Gson;

public class ApplicationConstants {

  public static final Gson GSON = new Gson();

  public interface FieldConstants {
    String APP_SECRET = "qwedsa";
    String USER_ID = "userId";

    int PERSISTED_MESSAGE_COUNT = 10;
    int MAX_MESSAGE_SIZE = 4 * 1024;

    String USER_KEY_FORMAT = "u:%s";
    String MESSAGE_KEY_FORMAT = "m:%s";
    String MESSAGE_TIME_KEY_FORMAT = "mt:%s";
  }

}
