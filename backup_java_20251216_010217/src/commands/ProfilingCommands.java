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
            new CommandDefinition("!startprofiling", "", "เน€เธฃเธดเนเธกเธเธฒเธฃเธงเธดเน€เธเธฃเธฒเธฐเธซเน CPU เธเธญเธเน€เธเธดเธฃเนเธเน€เธงเธญเธฃเน", 6),
            new CommandDefinition("!stopprofiling", "<filename>",
                  "เธซเธขเธธเธ”เธเธฒเธฃเธงเธดเน€เธเธฃเธฒเธฐเธซเน CPU เนเธฅเธฐเธเธฑเธเธ—เธถเธเธฅเธเนเธเธฅเนเธ—เธตเนเธเธณเธซเธเธ”", 6)
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
         c.getPlayer().dropMessage(6, "เน€เธฃเธดเนเธกเธเธฒเธฃเธงเธดเน€เธเธฃเธฒเธฐเธซเนเนเธฅเนเธง");
      } else if (splitted[0].equals("!stopprofiling")) {
         CPUSampler sampler = CPUSampler.getInstance();

         try {
            String filename = "odinprofile.txt";
            if (splitted.length > 1) {
               filename = splitted[1];
            }

            File file = new File(filename);
            if (file.exists()) {
               c.getPlayer().dropMessage(6, "เนเธเธฅเนเธเธตเนเธกเธตเธญเธขเธนเนเนเธฅเนเธง เนเธเธฃเธ”เน€เธฅเธทเธญเธเธเธทเนเธญเนเธเธฅเนเธญเธทเนเธ");
               return;
            }

            sampler.stop();
            FileWriter fw = new FileWriter(file);
            sampler.save(fw, 1, 10);
            fw.close();
            c.getPlayer().dropMessage(6, "เธซเธขเธธเธ”เธเธฒเธฃเธงเธดเน€เธเธฃเธฒเธฐเธซเนเนเธฅเธฐเธเธฑเธเธ—เธถเธเนเธเธขเธฑเธ " + filename);
         } catch (IOException var7) {
            System.err.println("Error saving profile" + var7);
            c.getPlayer().dropMessage(6, "เน€เธเธดเธ”เธเนเธญเธเธดเธ”เธเธฅเธฒเธ”เนเธเธเธฒเธฃเธเธฑเธเธ—เธถเธเนเธเธฃเนเธเธฅเน: " + var7.getMessage());
         }

         sampler.reset();
      }
   }
}
