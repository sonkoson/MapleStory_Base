package objects.wz.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapleDataProvider {
   private List<File> rootFiles = new ArrayList<>();
   private Map<String, MapleDataDirectoryEntry> rootForNavigation = new HashMap<>();

   public MapleDataProvider(File fileIn) {
      this.addFile(fileIn);

      for (File root : this.rootFiles) {
         this.rootForNavigation.put(root.getName(), new MapleDataDirectoryEntry(root.getName(), 0, 0, null));
         this.fillMapleDataEntitys(root, this.rootForNavigation.get(root.getName()));
      }
   }

   public void addFile(File fileIn) {
      for (File file : fileIn.listFiles()) {
         if (file.isDirectory() && file.getName().contains(".wz") && file.getName().contains("_")) {
            this.rootFiles.add(file);
         }
      }
   }

   private void fillMapleDataEntitys(File lroot, MapleDataDirectoryEntry wzdir) {
      for (File file : lroot.listFiles()) {
         String fileName = file.getName();
         if (file.isDirectory() && !fileName.endsWith(".img")) {
            MapleDataDirectoryEntry newDir = new MapleDataDirectoryEntry(fileName, 0, 0, wzdir);
            wzdir.addDirectory(newDir);
            this.fillMapleDataEntitys(file, newDir);
         } else if (fileName.endsWith(".xml")) {
            wzdir.addFile(new MapleDataFileEntry(fileName.substring(0, fileName.length() - 4), 0, 0, wzdir));
         }
      }
   }

   public MapleData getData(String root, String path) {
      File dataFile = new File(root, path + ".xml");
      File imageDataDir = new File(root, path);

      FileInputStream fis;
      try {
         fis = new FileInputStream(dataFile);
      } catch (FileNotFoundException var16) {
         throw new RuntimeException("Datafile " + path + " does not exist in " + root);
      }

      MapleData domMapleData;
      try {
         domMapleData = new MapleData(fis, imageDataDir.getParentFile());
      } finally {
         try {
            fis.close();
         } catch (IOException var14) {
            throw new RuntimeException(var14);
         }
      }

      return domMapleData;
   }

   public MapleData getData(String path) {
      for (File root : this.rootFiles) {
         File dataFile = new File(root, path + ".xml");
         File imageDataDir = new File(root, path);

         FileInputStream fis;
         try {
            fis = new FileInputStream(dataFile);
         } catch (FileNotFoundException var17) {
            continue;
         }

         MapleData domMapleData;
         try {
            domMapleData = new MapleData(fis, imageDataDir.getParentFile());
         } finally {
            try {
               fis.close();
            } catch (IOException var15) {
               throw new RuntimeException(var15);
            }
         }

         return domMapleData;
      }

      throw new RuntimeException("Datafile " + path + " does not exist in " + this.rootFiles);
   }

   public List<MapleDataDirectoryEntry> getRoot() {
      return new ArrayList<>(this.rootForNavigation.values());
   }

   public MapleDataDirectoryEntry getRoot(String name) {
      return this.rootForNavigation.get(name);
   }
}
