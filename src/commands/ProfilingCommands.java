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
            new CommandDefinition("!startprofiling", "", "Starts CPU profiling for the server.", 6),
            new CommandDefinition("!stopprofiling", "<filename>",
                  "Stops CPU profiling and saves it to the specified file.", 6)
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
         c.getPlayer().dropMessage(6, "Profiling started.");
      } else if (splitted[0].equals("!stopprofiling")) {
         CPUSampler sampler = CPUSampler.getInstance();

         try {
            String filename = "odinprofile.txt";
            if (splitted.length > 1) {
               filename = splitted[1];
            }

            File file = new File(filename);
            if (file.exists()) {
               c.getPlayer().dropMessage(6, "The file already exists. Please choose a different filename.");
               return;
            }

            sampler.stop();
            FileWriter fw = new FileWriter(file);
            sampler.save(fw, 1, 10);
            fw.close();
            c.getPlayer().dropMessage(6, "Profiling stopped and saved to " + filename);
         } catch (IOException var7) {
            System.err.println("Error saving profile" + var7);
            c.getPlayer().dropMessage(6, "Error saving profile: " + var7.getMessage());
         }

         sampler.reset();
      }
   }
}
