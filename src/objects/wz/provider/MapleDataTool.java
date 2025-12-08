package objects.wz.provider;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class MapleDataTool {
   public static String getString(MapleData data) {
      return data.getType() == MapleDataType.INT ? String.valueOf(getInt(data)) : (String)data.getData();
   }

   public static String getString(MapleData data, String def) {
      if (data != null && data.getData() != null) {
         if (data.getType() != MapleDataType.STRING && !(data.getData() instanceof String)) {
            return data.getType() == MapleDataType.VECTOR ? String.valueOf(getPoint(data)) : String.valueOf(getInt(data));
         } else {
            return (String)data.getData();
         }
      } else {
         return def;
      }
   }

   public static String getString(String path, MapleData data) {
      return getString(data.getChildByPath(path));
   }

   public static String getString(String path, MapleData data, String def) {
      return getString(data != null && data.getChildByPath(path) != null ? data.getChildByPath(path) : null, def);
   }

   public static double getDouble(MapleData data) {
      return (Double)data.getData();
   }

   public static float getFloat(MapleData data) {
      return (Float)data.getData();
   }

   public static float getFloat(MapleData data, float def) {
      return data != null && data.getData() != null ? (Float)data.getData() : def;
   }

   public static int getInt(MapleData data) {
      return (Integer)data.getData();
   }

   public static int getInt(MapleData data, int def) {
      if (data != null && data.getData() != null) {
         if (data.getType() == MapleDataType.STRING) {
            try {
               return Integer.parseInt(getString(data));
            } catch (NumberFormatException var4) {
               return def;
            }
         } else if (data.getType() == MapleDataType.SHORT) {
            return Integer.valueOf((Short)data.getData());
         } else if (data.getType() == MapleDataType.LONG) {
            return Integer.valueOf(((Long)data.getData()).intValue());
         } else {
            try {
               int buffer = (Integer)data.getData();
               return (Integer)data.getData();
            } catch (NumberFormatException var5) {
               return def;
            }
         }
      } else {
         return def;
      }
   }

   public static long getLong(MapleData data, int def) {
      if (data != null && data.getData() != null) {
         if (data.getType() == MapleDataType.STRING) {
            return Long.parseLong(getString(data));
         } else if (data.getType() == MapleDataType.SHORT) {
            return ((Short)data.getData()).shortValue();
         } else if (data.getType() == MapleDataType.INT) {
            try {
               return ((Integer)data.getData()).intValue();
            } catch (ClassCastException var3) {
               return (Long)data.getData();
            }
         } else {
            Long buffer = (Long)data.getData();
            return buffer;
         }
      } else {
         return def;
      }
   }

   public static int getInt(String path, MapleData data) {
      return getInt(data.getChildByPath(path));
   }

   public static int getIntConvert(MapleData data) {
      return data.getType() == MapleDataType.STRING ? Integer.parseInt(getString(data)) : getInt(data);
   }

   public static int getIntConvert(String path, MapleData data) {
      MapleData d = data.getChildByPath(path);
      return d.getType() == MapleDataType.STRING ? Integer.parseInt(getString(d)) : getInt(d);
   }

   public static int getInt(String path, MapleData data, int def) {
      return data == null ? def : getInt(data.getChildByPath(path), def);
   }

   public static int getIntConvert(String path, MapleData data, int def) {
      return data == null ? def : getIntConvert(data.getChildByPath(path), def);
   }

   public static long getLongConvert(String path, MapleData data, int def) {
      return data == null ? def : getLongConvert(data.getChildByPath(path), def);
   }

   public static int getIntConvert(MapleData d, int def) {
      if (d == null) {
         return def;
      } else if (d.getType() == MapleDataType.STRING) {
         String dd = getString(d);
         if (dd != null && !dd.isEmpty()) {
            if (dd.endsWith("%")) {
               dd = dd.substring(0, dd.length() - 1);
            }

            try {
               return Integer.parseInt(dd);
            } catch (NumberFormatException var4) {
               return def;
            }
         } else {
            return def;
         }
      } else {
         return d.getType() == MapleDataType.CANVAS ? 0 : getInt(d, def);
      }
   }

   public static long getLongConvert(MapleData d, int def) {
      if (d == null) {
         return def;
      } else if (d.getType() == MapleDataType.STRING) {
         String dd = getString(d);
         if (dd.endsWith("%")) {
            dd = dd.substring(0, dd.length() - 1);
         }

         try {
            return Long.parseLong(dd);
         } catch (NumberFormatException var4) {
            return def;
         }
      } else if (d.getType() == MapleDataType.INT) {
         long value = getLong(d, def);
         return value > 2147483647L ? value : getInt(d, def);
      } else {
         return getLong(d, def);
      }
   }

   public static double getDouble(MapleData data, double def) {
      if (data != null && data.getData() != null) {
         if (data.getType() == MapleDataType.STRING) {
            return Double.parseDouble(getString(data));
         } else if (data.getType() == MapleDataType.SHORT) {
            return Double.valueOf((double)((Short)data.getData()).shortValue());
         } else {
            return data.getType() == MapleDataType.LONG ? ((Long)data.getData()).longValue() : (Double)data.getData();
         }
      } else {
         return def;
      }
   }

   public static double getDouble(String path, MapleData data, double def) {
      return data == null ? def : getDouble(data.getChildByPath(path), def);
   }

   public static BufferedImage getImage(MapleData data) {
      return ((MapleCanvas)data.getData()).getImage();
   }

   public static Point getPoint(MapleData data) {
      return (Point)data.getData();
   }

   public static Point getPoint(String path, MapleData data) {
      return getPoint(data.getChildByPath(path));
   }

   public static Point getPoint(String path, MapleData data, Point def) {
      MapleData pointData = data.getChildByPath(path);
      return pointData == null ? def : getPoint(pointData);
   }

   public static String getFullDataPath(MapleData data) {
      String path = "";

      for (MapleDataEntity myData = data; myData != null; myData = myData.getParent()) {
         path = myData.getName() + "/" + path;
      }

      return path.substring(0, path.length() - 1);
   }
}
