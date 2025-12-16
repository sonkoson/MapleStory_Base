package objects.wz.provider;

import java.io.File;

public class MapleDataProviderFactory {
   private static final String wzPath = System.getProperty("net.sf.odinms.wzpath");

   private static MapleDataProvider getWZ(File in) {
      return new MapleDataProvider(in);
   }

   public static MapleDataProvider getDataProvider(File in) {
      return getWZ(in);
   }

   public static File fileInWZPath(String filename) {
      return new File(wzPath, filename);
   }
}
