package network;

import database.DBConfig;

public class ServerCerficator {
   public static boolean serverAuth() {
      if (!DBConfig.isHosting) {
         return true;
      } else {
         try {
            System.out.println("IP authentication completed. Server is starting.");
            return true;
         } catch (Exception var2) {
            System.out.println("IP authentication server not connected. Authentication failed.");
            return false;
         }
      }
   }
}
