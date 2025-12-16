package script.Util;

import database.DBConnection;
import network.center.Center;
import network.models.CWvsContext;
import objects.fields.gameobject.lifes.MapleLifeFactory;
import objects.utils.Pair;
import scripting.newscripting.ScriptEngineNPC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Recommend extends ScriptEngineNPC {

    // 담에 추천인 구조 꼭 바꾸자.. 제발..
    // Next time, I must change the recommender structure... Please...

    Pair<Integer, Integer> etcReward = new Pair<>(4001715, 10);

    List<Pair<Integer, Integer>> selfReward = List.of(
            new Pair<>(4001715, 10),
            new Pair<>(4310266, 300));

    Map<Integer, List<Pair<Integer, Integer>>> reward = Map.of(
            1000, List.of(new Pair<>(5068301, 5)),
            5000, List.of(new Pair<>(5068301, 20)),
            7000, List.of(new Pair<>(5068301, 40)));

    public int getAccIdFromDB(String name) {
        int ret = -1;
        try (var c = DBConnection.getConnection()) {
            try (var con = c.prepareStatement("SELECT * FROM characters WHERE name LIKE '" + name + "%'")
                    .executeQuery()) {
                if (con.next()) {
                    ret = con.getInt("accountid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void join_recom(int name, String name2, String recom) {
        try (var con = DBConnection.getConnection()) {
            try (var insert = con
                    .prepareStatement("INSERT INTO recom_log(name, recom, state, date) VALUES(?,?,?,now())")) {
                insert.setString(1, name + "%" + name2);
                insert.setString(2, recom);
                insert.setString(3, "0");
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existChar(String name) {
        boolean overlab = true;
        try (var c = DBConnection.getConnection()) {
            try (var con = c.prepareStatement("SELECT * FROM characters WHERE name LIKE '" + name + "%'")
                    .executeQuery()) {
                if (!con.next())
                    overlab = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return overlab;
    }

    public boolean overlab_recom(int name, String name2) {
        boolean overlab = true;
        try (Connection c = DBConnection.getConnection()) {
            try (var con = c.prepareStatement("SELECT * FROM recom_log WHERE `name` LIKE '" + name + "%'")
                    .executeQuery()) {
                overlab = con.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return overlab;
    }

    public String recom_log() {
        var txt = new StringBuilder();
        try (var c = DBConnection.getConnection()) {
            try (var con = c.prepareStatement(
                    "SELECT id, recom, COUNT(recom) as player FROM recom_log GROUP BY recom HAVING COUNT(recom) > 0 order by COUNT(recom) desc")
                    .executeQuery()) {
                var rank = 0;
                while (con.next()) {
                    txt.append("#L").append(con.getInt("id")).append("#")
                            .append(rank == 0 ? "#r "// #fUI/UIWindow2.img/ProductionSkill/productPage/meister#
                                    : rank == 1 ? "#b "// #fUI/UIWindow2.img/ProductionSkill/productPage/craftman#
                                            : "#k ")// #fUI/UIWindow2.img/ProductionSkill/productPage/hidden#

                            .append("รหัสผู้แนะนำ #k: ").append(con.getString("recom")).append(" | ")
                            .append("จำนวนการแนะนำ #k: #e").append(con.getString("player")).append("#n\r\n");
                    rank++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return txt.toString();
    }

    public int recoms_count(String name) {
        var a = 0;
        try (var c = DBConnection.getConnection()) {
            try (var con = c.prepareStatement("SELECT * FROM recom_log WHERE recom LIKE '" + name + "'")
                    .executeQuery()) {
                while (con.next()) {
                    if (con.getString("recom").equals(name)) {
                        a++;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return a;
    }

    public int recom_num(String name) {
        int recoms_num = 0;
        try (var c = DBConnection.getConnection()) {
            try (var con = c
                    .prepareStatement(
                            "SELECT COUNT(*) AS player FROM recom_log WHERE recom = '" + name + "' and state = 0")
                    .executeQuery()) {
                if (con.next()) {
                    recoms_num = con.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recoms_num;
    }

    public String recom_person(String name) {
        var txt = new StringBuilder();
        try (var c = DBConnection.getConnection()) {
            try (var con = c.prepareStatement("SELECT * FROM recom_log WHERE recom = '" + name + "' and state = 0")
                    .executeQuery()) {
                while (con.next()) {
                    var con_name = con.getString("name").split("%");
                    txt.append("#b[").append(con_name[1]).append("] ");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return txt.toString();
    }

    public String recom_list(int id) {
        var txt = new StringBuilder();
        try (Connection c = DBConnection.getConnection()) {
            try (var idcon = c.prepareStatement("SELECT * FROM recom_log WHERE id = '" + id + "'").executeQuery()) {
                if (idcon.next()) {
                    String recom_per = idcon.getString("recom");

                    var con = c.prepareStatement("SELECT * FROM recom_log WHERE recom = '" + recom_per + "'")
                            .executeQuery();
                    txt.append("ผู้เล่นที่แนะนำคุณ ").append(recom_per).append(" :\r\n\r\n");
                    while (con.next()) {
                        var con_name = con.getString("name").split("%");
                        txt.append("ชื่อ : #e").append(con_name[1]).append("#n | ")
                                .append("วันที่ : ").append(con.getDate("date") + " " + con.getTimestamp("date"))
                                .append("\r\n");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return txt.toString();
    }

    public void recommend() {
        initNPC(MapleLifeFactory.getNPC(9062454));
        StringBuilder str = new StringBuilder(
                "#fs11##b #h 0##fc0xFF000000#, ยินดีต้อนรับสู่ #fc0xFF990033#[Ganglim World]#fc0xFF000000# \r\n\r\n")
                .append("#r(สามารถแนะนำได้ 1 คนต่อ 1 บัญชี และต้องมีเลเวล 200 ขึ้นไป)#k\r\n")
                .append("#fUI/UIWindow.img/UtilDlgEx/list1#\r\n")
                .append("#L0##bลงทะเบียน#fc0xFF000000# ผู้แนะนำ\r\n")
                .append("#L1##bดูอันดับ#fc0xFF000000# ผู้แนะนำ#l\r\n\r\n")
                .append("#fUI/UIWindow.img/UtilDlgEx/list0#\r\n")
                .append("#L2##bยืนยัน#fc0xFF000000# ผู้แนะนำ\r\n");

        int menu = self.askMenu(str.toString());
        switch (menu) {
            case 0: {
                if (!overlab_recom(getPlayer().getClient().getAccID(), getPlayer().getName())) {
                    if (getPlayer().getLevel() >= 200) {
                        str = new StringBuilder(
                                "#b#fs11# #h 0##fc0xFF000000#, กรุณาระบุ #bชื่อตัวละคร#fc0xFF000000# ของผู้ที่แนะนำคุณมายัง #fc0xFF990033#[Ganglim World]#fc0xFF000000# ด้วยค่ะ\r\n");
                        str.append(
                                "แต่ #rเมื่อลงทะเบียนแล้วจะไม่สามารถแก้ไขได้#fc0xFF000000# กรุณาตรวจสอบให้แน่ใจก่อนลงทะเบียนนะคะ");
                        String text = self.askText(str.toString());

                        if (!existChar(text)) {
                            self.sayOk("ไม่พบผู้เล่นดังกล่าว");
                            return;
                        }
                        if (text.equals("") || text.equals(getPlayer().getName())
                                || getAccIdFromDB(text) == getAccIdFromDB(getPlayer().getName())) {
                            self.sayOk(text.equals("") ? "ป้อนข้อมูลผิดพลาด" : "ไม่สามารถลงทะเบียนชื่อตัวเองได้");
                        } else {
                            join_recom(getClient().getAccID(), getPlayer().getName(), text);
                            getPlayer().gainItem(etcReward.left, etcReward.right);

                            str = new StringBuilder(
                                    "#fs11#นี่คือ #b#h 0##kของขวัญเล็กน้อยสำหรับคุณ มันจะเป็นประโยชน์ต่อการเดินทางของคุณในอนาคต#b\r\n");

                            for (Pair<Integer, Integer> reward : selfReward) {
                                getPlayer().gainItem(reward.left, reward.right);
                                str.append(String.format("#i%d##z%d# %d ชิ้น\r\n", reward.left, reward.left,
                                        reward.right));
                            }

                            self.sayOk(str.toString());
                            Center.Broadcast.broadcastMessage(
                                    CWvsContext.serverNotice(11, getClient().getChannel(), "[Recommender] ผู้เล่น "
                                            + getPlayer().getName() + " ได้ลงทะเบียน " + text + " เป็นผู้แนะนำ"));
                        }
                    } else {
                        self.sayOk("#fs11##rเลเวลต่ำกว่า 200#k ไม่สามารถลงทะเบียนผู้แนะนำได้ค่ะ");
                    }
                } else {
                    self.sayOk("#fs11##fc0xFF000000#สามารถลงทะเบียนผู้แนะนำได้เพียงครั้งเดียว");
                }
                break;
            }
            case 1: {
                str = new StringBuilder("#fs11##fc0xFF000000#นี่คือรายชื่อผู้ที่ได้รับการแนะนำมากที่สุด\r\n");
                str.append("#b#h 0##fc0xFF000000#ก็สามารถขึ้นมาอยู่ที่นี่ได้หากพยายามอีกนิด\r\n");
                str.append(recom_log());
                self.sayOk(recom_list(self.askMenu(str.toString())));
                break;
            }
            case 2: {
                int recoms_num = recom_num(getPlayer().getName());
                if (recoms_num == 0) {
                    str = new StringBuilder("#fs11##fc0xFF000000#ยังไม่มีใครแนะนำคุณ #b#h 0##fc0xFF000000# เลย\r\n");
                    str.append(
                            "หากช่วยประชาสัมพันธ์ #fc0xFF990033#[Ganglim World]#fc0xFF000000# อย่างเต็มที่ รางวัลจะตามมาแน่นอน");
                    self.sayOk(str.toString());
                } else {
                    self.sayOk("#b #h 0##fc0xFF000000#ได้รับคำแนะนำทั้งหมด " + recoms_num + " คน "
                            + recom_person(getPlayer().getName()) + "#fc0xFF000000#");
                    getPlayer().gainHPoint(100000 * recoms_num);
                    getPlayer().dropMessage(1, "ได้รับ " + (100000 * recoms_num) + " Promotion Point");
                    try (var c = DBConnection.getConnection()) {
                        var ps = c.prepareStatement(
                                "UPDATE recom_log SET state = 1 WHERE recom = '" + getPlayer().getName() + "'");
                        ps.executeUpdate();
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case 3: {
                int a = recoms_count(getPlayer().getName());

                StringBuilder msg = new StringBuilder(
                        "#fs11#กรุณาเลือกรางวัลที่จะรับ\r\nปัจจุบันคุณ #b#h ##k ได้รับคำแนะนำ " + a + " คน#fs11#\r\n");

                int i = 0;
                for (Integer entry : reward.keySet()) {
                    if (entry <= a && getClient().getKeyValue("recom_" + entry) == null) {
                        msg.append("#L").append(entry).append("##b").append(entry).append(" คน (สามารถรับได้)\r\n");
                    } else {
                        msg.append("#L").append(entry).append("##r").append(entry).append(" คน (ไม่สามารถรับได้)\r\n");
                    }
                    i++;
                }

                int select = self.askMenu(msg.toString());

                msg = new StringBuilder("ของรางวัลสำหรับผู้แนะนำครบ " + select + " คน:#b#fs11#\r\n");

                for (Map.Entry<Integer, List<Pair<Integer, Integer>>> entry : reward.entrySet()) {
                    var itemList = reward.get(select);
                    for (Pair<Integer, Integer> item : itemList) {
                        msg.append("#i").append(item.left).append("##z").append(item.left).append("# ")
                                .append(item.right).append(" ชิ้น\r\n");
                    }
                }

                if (select <= a && getClient().getKeyValue("recom_" + select) == null) {
                    getClient().setKeyValue("recom_" + select, "1");
                    for (Map.Entry<Integer, List<Pair<Integer, Integer>>> entry : reward.entrySet()) {
                        var itemList = reward.get(select);
                        for (Pair<Integer, Integer> item : itemList) {
                            getPlayer().gainItem(item.left, item.right);
                        }
                    }
                }
                self.sayOk(msg.toString());
                break;
            }
        }
    }
}
