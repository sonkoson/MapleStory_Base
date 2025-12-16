package scripting.newscripting;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject.Kind;
import objects.fields.Portal;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.fields.gameobject.lifes.MapleNPC;
import objects.quest.MapleQuest;
import objects.users.MapleCharacter;
import objects.users.MapleClient;

public class ScriptManager {
   private static final ReentrantLock lock = new ReentrantLock();
   private static ScriptHolder holder = new ScriptHolder(null);
   private static Boolean safe = Boolean.FALSE;

   public static ScriptHolder get() {
      lock.lock();

      ScriptHolder var0;
      try {
         var0 = holder;
      } finally {
         lock.unlock();
      }

      return var0;
   }

   public static void runScriptThread(MapleClient c, MapleNPC npc, Portal portal, MapleQuest quest, int itemID,
         Method method, NewScriptEngine engine) {
      ScriptEngineNPC eInstance = (ScriptEngineNPC) engine;
      c.getPlayer().setScriptThread(eInstance);
      eInstance.initEngine(c);
      if (npc != null) {
         eInstance.initNPC(npc);
      }

      if (portal != null) {
         eInstance.initPortal(portal);
      }

      if (quest != null) {
         eInstance.initQuest(quest);
         if (npc == null) {
            eInstance.initNPC(MapleLifeFactory.getNPC(2003));
         }
      }

      if (itemID != -1) {
         eInstance.initItemID(itemID);
      }

      eInstance.init(method);
   }

   public static void runScript(MapleClient c, String script, MapleNPC npc) {
      runScript(c, script, npc, null, null, -1);
   }

   public static void runScript(MapleClient c, String script, MapleNPC npc, Portal portal) {
      runScript(c, script, npc, portal, null, -1);
   }

   public static void runScript(MapleClient c, String script, MapleNPC npc, Portal portal, MapleQuest quest) {
      runScript(c, script, npc, portal, quest, -1);
   }

   public static void runScript(final MapleClient c, String script, final MapleNPC npc, final Portal portal,
         final MapleQuest quest, final int itemID) {
      Class<? extends NewScriptEngine> scriptClass = get()._scripts.get(script);

      try {
         String from = "";
         if (npc != null) {
            from = "NPC (" + npc.getId() + ")";
         }

         if (portal != null) {
            from = "Portal (" + portal.getId() + ")";
         }

         if (quest != null) {
            from = "Quest (" + quest.getId() + ")";
         }

         if (itemID != -1) {
            from = "Item (" + itemID + ")";
         }

         c.setLastUsedNewScriptName("Java : " + script + " From " + from);
         final Method method = scriptClass.getMethod(script);
         final NewScriptEngine engineInstance = scriptClass.getConstructor().newInstance();
         if (!method.isAnnotationPresent(Script.class)) {
            Lock lock = c.getNPCLock();
            lock.lock();

            try {
               ScriptEngineNPC scriptThread = c.getPlayer().getScriptThread();
               if (scriptThread != null) {
                  scriptThread.getSc().setAfterEnd(new Runnable() {
                     @Override
                     public void run() {
                        ScriptManager.runScriptThread(c, npc, portal, quest, itemID, method, engineInstance);
                     }
                  });
               } else {
                  runScriptThread(c, npc, portal, quest, itemID, method, engineInstance);
               }
            } finally {
               lock.unlock();
            }
         } else {
            engineInstance.initEngine(c);
            ScriptEngineNPC eInstance = (ScriptEngineNPC) engineInstance;
            if (npc != null) {
               eInstance.initNPC(npc);
            }

            if (portal != null) {
               eInstance.initPortal(portal);
            }

            if (quest != null) {
               eInstance.initQuest(quest);
            }

            if (itemID != -1) {
               eInstance.initItemID(itemID);
            }

            try {
               method.invoke(eInstance);
            } catch (InvocationTargetException var17) {
            }
         }
      } catch (Exception var19) {
      }
   }

   public static int parseScripts() throws IOException {
      lock.lock();

      try {
         ScriptHolder oHolder = holder;
         ScriptHolder nHolder = new ScriptHolder(oHolder);
         String binaryPath = "outscripts/scripts";
         new File("outscripts/scripts").mkdirs();
         List<JavaFileObject> scripts = new LinkedList<>();
         List<String> scriptURL = new LinkedList<>();
         loadJavaFiles(Paths.get("data/scripts"), scripts, scriptURL);
         JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
         if (compiler == null) {
            throw new NullPointerException(
                  "JavaComplier wasn't found. check your installed java development kit(equals or above than java api 6). ");
         }

         if (!scripts.isEmpty()) {
            try (StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(),
                  null)) {
               String[] compileOptions = new String[] { "-d", "outscripts/scripts" };
               Iterable<String> compilationOptionss = Arrays.asList(compileOptions);
               DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
               CompilationTask compilerTask = compiler.getTask(null, stdFileManager, diagnostics, compilationOptionss,
                     null, scripts);
               boolean status = compilerTask.call();
               if (!status) {
                  for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                     System.out
                           .println(String.format("Error on line %d in %s", diagnostic.getLineNumber(), diagnostic));
                  }

                  safe = false;
                  throw new RuntimeException("Compilation failed.");
               }

               ClassLoader cloader = new ScriptManager.ScriptClassLoader(Paths.get("outscripts/scripts"));

               for (String clas : scriptURL) {
                  Class<?> cl = cloader.loadClass(clas);
                  if (cl != null) {
                     for (Method te : cl.getDeclaredMethods()) {
                        nHolder._scripts.put(te.getName(), (Class<? extends ScriptEngineNPC>) cl);
                     }
                  }
               }
            } catch (ClassNotFoundException var28) {
               throw new RuntimeException("Compilation failed.");
            }
         }

         oHolder.clear();
         holder = nHolder;
      } finally {
         lock.unlock();
      }

      System.out.println("[Script] เธฃเธงเธก " + holder._scripts.size() + " scripts have been parsed.");
      return holder._scripts.size();
   }

   public static void resetScript(MapleCharacter chr) {
      synchronized (safe) {
         if (safe) {
            if (chr != null) {
               chr.dropMessage(5, "Script reset is already in progress.");
            }

            System.out.println("Script reset is already in progress.");
         } else {
            safe = true;
            if (chr != null) {
               chr.dropMessage(5, "Starting script parsing.");
            }

            System.out.println("Starting script parsing.");
            Runnable scriptRunnable = () -> {
               try {
                  parseScripts();
                  if (chr != null) {
                     chr.dropMessage(5, "[Script] เธฃเธงเธก " + holder._scripts.size() + " scripts have been parsed.");
                  }
               } catch (IOException var2x) {
                  new RuntimeException(var2x);
               }

               safe = false;
            };
            ScriptThreadManager.getInstance().execute(scriptRunnable);
         }
      }
   }

   public static boolean resetScriptBool() {
      synchronized (safe) {
         if (safe) {
            System.out.println("Script reset is already in progress.");
            return false;
         } else {
            safe = true;
            System.out.println("Starting script parsing.");
            Runnable scriptRunnable = () -> {
               try {
                  parseScripts();
               } catch (IOException var1x) {
                  new RuntimeException(var1x);
               }

               safe = false;
            };
            ScriptThreadManager.getInstance().execute(scriptRunnable);
            return true;
         }
      }
   }

   private static void loadJavaFiles(final Path path, final List<JavaFileObject> scripts, final List<String> fileURl)
         throws IOException {
      Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (!Files.isHidden(file) && (!attrs.isRegularFile() || file.getFileName().toString().endsWith(".java"))) {
               scripts.add(new ScriptManager.CompileTestfileObject(file));
               fileURl.add(path.relativize(file).toString().split("\\.")[0].replace(File.separator, "."));
            }

            return FileVisitResult.CONTINUE;
         }
      });
   }

   public static class CompileTestfileObject extends SimpleJavaFileObject {
      private final Path path;

      protected CompileTestfileObject(Path file) {
         super(file.toUri(), Kind.SOURCE);
         this.path = file;
      }

      public Path getPath() {
         return this.path;
      }

      @Override
      public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
         StringBuilder sb = new StringBuilder();

         for (String str : Files.readAllLines(this.path, Charset.forName("euc-kr"))) {
            sb.append(str).append("\r\n");
         }

         return sb.toString();
      }
   }

   public static class ScriptClassLoader extends URLClassLoader {
      private final Path binPath;

      public ScriptClassLoader(Path path) throws MalformedURLException {
         super(new URL[] { path.toUri().toURL() });
         this.binPath = path;
      }

      @Override
      public Class<?> loadClass(String name) throws ClassNotFoundException {
         if (!name.startsWith("scripts.") && !name.startsWith("processor.")) {
            return super.loadClass(name);
         } else {
            Path path = this.binPath.resolve(name.replace(".", "/") + ".class");

            try {
               Class var6;
               try (SeekableByteChannel sbc = Files.newByteChannel(path)) {
                  ByteBuffer bb = ByteBuffer.allocate((int) sbc.size());
                  bb.clear();
                  sbc.read(bb);
                  bb.flip();
                  byte[] hb = bb.array();
                  var6 = this.defineClass(name, hb, 0, hb.length);
               }

               return var6;
            } catch (IOException | LinkageError var9) {
               return null;
            }
         }
      }
   }
}
