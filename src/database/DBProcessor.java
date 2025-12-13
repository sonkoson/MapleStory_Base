package database;

import database.callback.DBCallback;
import database.callback.DBGenerateKeyCallback;
import objects.utils.FileoutputUtil;
import objects.utils.Pair;

public class DBProcessor extends Thread {
   private DBSelector selector;
   private boolean error = false;
   private int index;

   public DBProcessor(String threadName, int ind) {
      super(threadName);
      this.selector = new DBSelector();
      this.index = ind;
   }

   public synchronized void process() {
      int select = this.selector.select();
      if (select > 0) {
         while (this.selector != null && !this.selector.isQueryEmpty()) {
            Pair<DBSelectionKey, Pair<String, Object>> query = this.selector.getAndRemove();
            if (query != null && query.left != null) {
               switch ((DBSelectionKey) query.left) {
                  case INSERT_OR_UPDATE:
                     DBConnection.insertOrUpdate((String) query.getRight().left, (DBCallback) query.getRight().right);
                     break;
                  case INSERT_OR_UPDATE_BATCH:
                     DBConnection.insertOrUpdateBatch((String) query.getRight().left,
                           (DBCallback) query.getRight().right);
                     break;
                  case INSERT_RETURN_GENERATED_KEYS:
                     DBConnection.insertOrUpdateGenerateKey((String) query.getRight().left,
                           (DBGenerateKeyCallback) query.getRight().right);
               }
            }
         }
      }
   }

   public void addQuery(DBSelectionKey key, String query, Object callback) {
      this.selector.addQuery(key, query, callback);
   }

   @Override
   public void run() {
      this.error = false;

      try {
         while (true) {
            this.process();
         }
      } catch (Exception var9) {
         Exception e = var9;

         try {
            FileoutputUtil.outputFileErrorReason("Log_DBProcessor_Except.rtf", "DBProcessor error occurred ", e);
         } catch (Exception var7) {
            System.out.println("DBProcessor error save failed : " + var7.toString());
            var7.printStackTrace();
         } finally {
            this.error = true;
            DBEventManager.restartDBProcessor(this, this.index);
         }
      }
   }

   public boolean isError() {
      return this.error;
   }

   public void saveRemaining() {
      try {
         while (!this.selector.isQueryEmpty()) {
            Pair<DBSelectionKey, Pair<String, Object>> query = this.selector.getAndRemove();
            FileoutputUtil.log("Log_NotInserted.rtf", (String) query.getRight().left + "\r\n");
         }
      } catch (Exception var4) {
         Exception e = var4;

         try {
            FileoutputUtil.outputFileErrorReason("Log_DBProcessor_Except.rtf", "Error saving unentered query ", e);
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }
   }
}
