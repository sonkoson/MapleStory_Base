package commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import objects.users.MapleClient;
import objects.utils.CPUSampler;

public class ProfilingCommands implements Command {
   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!startprofiling", "", "เริ่มการวิเคราะห์ CPU ของเซิร์ฟเวอร์", 6),
            new CommandDefinition("!stopprofiling", "<filename>",
                  "หยุดการวิเคราะห์ CPU และบันทึกลงไฟล์ที่กำหนด", 6)
      };
   }

   @Override
   public void execute(MapleClient c, String[] splitted) {
      if (splitted[0].equals("!startprofiling")) {
         CPUSampler sampler = CPUSampler.getInstance();
         sampler.addIncluded("api");
         sampler.addIncluded("commands");
         sampler.addIncluded("constants");
         sampler.addIncluded("logging");
         sampler.addIncluded("network");
         sampler.addIncluded("objects");
         sampler.addIncluded("scripting");
         sampler.addIncluded("security");
         sampler.start();
         c.getPlayer().dropMessage(6, "เริ่มการวิเคราะห์แล้ว");
      } else if (splitted[0].equals("!stopprofiling")) {
         CPUSampler sampler = CPUSampler.getInstance();

         try {
            String filename = "odinprofile.txt";
            if (splitted.length > 1) {
               filename = splitted[1];
            }

            File file = new File(filename);
            if (file.exists()) {
               c.getPlayer().dropMessage(6, "ไฟล์นี้มีอยู่แล้วโปรดเลือกชื่อไฟล์อื่น");
               return;
            }

            sampler.stop();
            FileWriter fw = new FileWriter(file);
            sampler.save(fw, 1, 10);
            fw.close();
            c.getPlayer().dropMessage(6, "หยุดการวิเคราะห์และบันทึกไปยัง " + filename);
         } catch (IOException var7) {
            System.err.println("Error saving profile" + var7);
            c.getPlayer().dropMessage(6, "เกิดข้อผิดพลาดในการบันทึกโปรไฟล์: " + var7.getMessage());
         }

         sampler.reset();
      }
   }
}
