package api.telegram;

import database.DBConfig;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

public class TelegramSender {
   static String Token;
   static String ChatIDCCU;
   static String ChatIDDonation;

   public static void sendMessage(String chatid, String msg) throws Exception {
      String sToken = toURLEncode(Token);
      String chat_id = toURLEncode(chatid);
      String msgs = toURLEncode(msg);
      if (chatid == "CCU") {
         chat_id = toURLEncode(ChatIDCCU);
      }

      if (chatid == "Donation") {
         chat_id = toURLEncode(ChatIDDonation);
      }

      String sendURL = "https://api.telegram.org/bot" + sToken + "/sendmessage?chat_id=" + chat_id + "&text=" + msgs;
      URL obj = new URL(sendURL);
      HttpURLConnection con = (HttpURLConnection)obj.openConnection();
      con.setRequestMethod("GET");

      try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
         while (in.readLine() != null) {
         }
      } catch (Exception var13) {
      }
   }

   private static String toURLEncode(String text) {
      try {
         return URLEncoder.encode(text, "UTF-8");
      } catch (UnsupportedEncodingException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   static Properties getDefaultProperties() throws FileNotFoundException, IOException {
      Properties props = new Properties();
      FileInputStream fileInputStream = new FileInputStream(DBConfig.isGanglim ? "settings_ganglim.properties" : "settings_jin.properties");
      props.load(fileInputStream);
      fileInputStream.close();
      return props;
   }

   static {
      try {
         Properties props = getDefaultProperties();
         Token = props.getProperty("TelegramToken", "6196551651:AAGHIGQkEkEcmTCKkgS4nl0v_T7nhe-YaVM");
         ChatIDCCU = props.getProperty("TelegramChatIDCCU", "-911115797");
         ChatIDDonation = props.getProperty("TelegramChatIDDonation", "-958875616");
      } catch (Exception var1) {
         var1.printStackTrace();
      }
   }
}
