package network.auction;

import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class AuctionHistoryIDManager {
   private static AtomicInteger idx = new AtomicInteger(1);

   public static void init() {
      DBConnection db = new DBConnection();

      try {
         label58: {
            try (Connection con = DBConnection.getConnection()) {
               PreparedStatement ps = con
                     .prepareStatement("select historyID from `auctionitems` order by historyID DESC");
               ResultSet rs = ps.executeQuery();
               if (rs.next()) {
                  idx.set(rs.getInt("historyID") + 1);
                  System.out.println("Auction historyID reset to " + idx.get() + ".");
                  rs.close();
                  ps.close();
                  break label58;
               }

               rs.close();
               ps.close();
            }

            return;
         }
      } catch (SQLException var6) {
      }

      System.out.println("Auction completed item indexing load finished.");
   }

   public static int getAndIncrement() {
      return idx.getAndIncrement();
   }
}
