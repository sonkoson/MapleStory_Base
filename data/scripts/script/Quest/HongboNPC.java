package script.Quest;

import database.DBConfig;
import network.discordbot.DiscordBotHandler;
import scripting.newscripting.ScriptEngineNPC;

public class HongboNPC extends ScriptEngineNPC {

    public void hongbo_npc() {
        if (DBConfig.isGanglim) {
            return;
        }
        int v = self.askMenu(
                "สวัสดี! ฉันชื่อ Roa รับหน้าที่แนะนำการโปรโมทค่ะ!\r\n\r\n#b#L0#วิธีโปรโมท#l\r\n#L1#วิธียืนยันการโปรโมท#l\r\n#L2#รางวัลการโปรโมท#l\r\n#L3#ส่งจดหมายถึงทีมงาน#l");
        switch (v) {
            case 0: { // Promotion Methods
                int vv = self.askMenu(
                        "สงสัยเรื่องอะไรคะ!\r\n#b#L0#<วิธีโปรโมทผ่าน Blog>#l\r\n#L1#<วิธีโปรโมทผ่าน YouTube>#l\r\n#L2#<วิธีโปรโมทผ่าน GameZone, FreeZone, TodayServer, TodayFree, SundayServer, DamoaFree, GameDori>#l\r\n#L3## คำแนะนำแยกตามเว็บไซต์ GameZone, FreeZone, TodayServer, TodayFree, SundayServer, DamoaFree, GameDori#l");
                switch (vv) {
                    case 0: { // Blog Promotion
                        self.sayOk(
                                "  - กรุณาเขียนบทความ \"Blog\" ใน Naver, Daum หรือ Tistory\r\n  - ต้องแนบสกรีนช็อตอย่างน้อย 13 รูป และเขียนคำอธิบายสำหรับแต่ละรูปด้วย\r\n  - กรุณาตัดภาพเฉพาะส่วนที่สำคัญ ไม่ใช่ภาพเต็มหน้าจอ แล้วแนบมา\r\n\r\n# ข้อควรระวังในการโปรโมทผ่าน Blog\r\n  - ในหัวข้อและเนื้อหาต้องมีคีย์เวิร์ด Free Maple, Free Server, Jin Server, Jin:眞 อย่างใดอย่างหนึ่ง\r\n  - หากลงแต่รายละเอียดสกิล หรือออปชั่นไอเทม จะระงับการจ่ายคะแนน\r\n  - หากทีมงานพิจารณาว่าโพสต์นั้นไม่มีความตั้งใจ หรือใช้ภาพซ้ำๆ เพื่อสแปม จะระงับการจ่ายคะแนน\r\n  - กรณีโพสต์ใน Naver ห้ามระบุที่อยู่เซิร์ฟเวอร์โดยตรงในเนื้อหาโพสต์ ให้ระบุที่อยู่ผ่านการตอบกลับคอมเมนต์ลับเท่านั้น\r\n  - โปรดระวังว่า Blog อาจถูกระงับหากเนื้อหามีลักษณะเป็นการโฆษณา\r\n  - ต้องระบุข้อความ \"<โพสต์นี้เป็นรีวิวเกมส่วนตัว ไม่ได้มีจุดประสงค์เพื่อการโฆษณา>\" ไว้ที่ส่วนบนสุดของเนื้อหา\r\n  - กรุณาระบุเนื้อหาต่อไปนี้ใน Tag ด้วย\r\n\r\n  #프메 #프리메이플 #하자서버 #라라구현 #진서버 #고버전 #프메서버추천 #색변캐ช #해외แคช #메이플스토리\r\n\r\n  - หากโพสต์ถูกลบหรือตั้งเป็นส่วนตัว ณ เวลาที่จ่ายรางวัล จะไม่นับให้ ต้องสำรองโพสต์ไว้แบบส่วนตัวเผื่อกรณีถูกลบด้วย (สามารถใช้ Naver, Daum, Tistory สลับกันได้)\r\n\r\n# ข้อมูลรางวัลการโปรโมทผ่าน Blog\r\n  - โพสต์ Blog ทำได้วันละ 1 ครั้ง ได้รับ 2,000 Promotion Points\r\n  - หากโปรโมทครบ 15 วันขึ้นไป จะได้รับโบนัส 1,000 Promotion Points และเหรียญ Blogger Medal\r\n  - หากโปรโมทครบ 100 วันขึ้นไป จะได้รับเหรียญ Blogger+ Medal");
                        break;
                    }
                    case 1: { // YouTube
                        self.sayOk(
                                "  - กรุณาโปรโมทผ่านการไลฟ์สดบน YouTube\r\n  - ต้องใส่ชื่อ Free Maple Jin Server ในหัวข้อและเนื้อหา\r\n\r\n# ข้อควรระวังในการโปรโมทผ่าน YouTube\r\n  - หัวข้อไลฟ์สดต้องมีคำว่า \"Free Maple Jin Server\"\r\n  - ในรายละเอียดต้องระบุ Free Maple Jin Server และที่อยู่เซิร์ฟเวอร์ : https://maplejin.com\r\n  - ต้องใช้รูปปก (Thumbnail) YouTube ด้วย (รูปปกดูได้ที่ด้านล่างของประกาศนั้นๆ)\r\n  - อนุญาตให้เปิดจอกาทิ้งไว้ (AFK) ได้ แต่ต้องเห็นหน้าจอเกม Jin Server\r\n  - ต้องแคปภาพหน้า YouTube Studio > Content ให้เห็นเวลาที่ไลฟ์สดมาด้วย\r\n\r\n\r\n# ข้อมูลรางวัลการโปรโมทผ่าน YouTube\r\n  - ไลฟ์สด 10 ชั่วโมงต่อวัน ได้รับ 1,000 Promotion Points (ไม่นับส่วนที่เกิน 10 ชั่วโมง)\r\n  - หากโปรโมทครบ 15 วันขึ้นไป จะได้รับโบนัส 500 Promotion Points และเหรียญ YouTuber Medal\r\n  - หากโปรโมทครบ 100 วันขึ้นไป จะได้รับเหรียญ YouTuber+ Medal");
                        break;
                    }
                    case 2: { // GameZone Promotion
                        self.sayOk(
                                "  - กรุณาโพสต์โปรโมทในบอร์ด MapleStory ของ GameZone, FreeZone, TodayServer, TodayFree, SundayServer, DamoaFree, GameDori\r\n  - นับ 1 ครั้งต่อโพสต์ต่อเว็บไซต์ ได้รับ 200 Points\r\n  - นับสูงสุด 10 ครั้งต่อวันต่อประเภท (สามารถทำซ้ำในแต่ละประเภทได้)\r\n  - สามารถคัดลอกหัวข้อและเนื้อหาด้านล่างไปวางได้เลย\r\n  - ใส่ชื่อตัวละครผู้แนะนำในเนื้อหาโพสต์ได้\r\n  หัวข้อ : ■ https://maplejin.com ■ 1.2.1123 ■ Lara Implemented ■ Haja Server ■ Color Change Cash ■ Magnet Pet ■ Black Mage ■ Free Maple ■ Jin Server\r\n  เนื้อหา : https://maplejin.com 1.2.1123 MapleStory Jin Server\r\n\r\n\r\n GameZone (จำกัดวันละ 10 ครั้ง) - https://gamezone.live/index.php\r\n FreeZone (จำกัดวันละ 10 ครั้ง) - https://xn--oy2bi4yvoj.net/\r\n TodayServer (จำกัดวันละ 10 ครั้ง) - https://todayserver.net/\r\n TodayFree (จำกัดวันละ 10 ครั้ง) - https://oraksil.cc/\r\n SundayServer (จำกัดวันละ 10 ครั้ง) - https://www.linsunday.com/\r\n DamoaFree (จำกัดวันละ 10 ครั้ง) - https://damoafree.com/\r\n GameDori (จำกัดวันละ 10 ครั้ง) - http://gdori.live/\r\n\r\n# ข้อควรระวังในการโปรโมทผ่านเว็บไซต์ต่างๆ\r\n  - ต้องคัดลอก URL ของแต่ละโพสต์มาลงทะเบียนตามแบบฟอร์มด้านล่าง\r\n  - Link 1: https://xxx\r\n  - Link 2: https://xxx\r\n  - Link 3: https://xxx\r\n  # เมื่อตั้งกระทู้ กรุณาตั้งชื่อหัวข้อว่า Supporter วันที่ n (สะสมครบ 4 ครั้ง = 1 วัน)\r\n\r\n# เนื้อหาเพิ่มเติม 2021-07-02\r\n  - หากโปรโมทผ่านเว็บต่างๆ ตามเงื่อนไขด้านล่าง จะได้รับเหรียญเมื่อสะสมจำนวนครั้งครบ\r\n  - GameZone 2 โพสต์ = สะสม 1 ครั้ง\r\n  - FreeZone 4 โพสต์ = สะสม 1 ครั้ง\r\n  - TodayServer 2 โพสต์ = สะสม 1 ครั้ง\r\n  - TodayFree 2 โพสต์ = สะสม 1 ครั้ง\r\n  - SundayServer 8 โพสต์ = สะสม 2 ครั้ง (นับตั้งแต่ 8 ครั้งขึ้นไป)\r\n  - DamoaFree 2 โพสต์ = สะสม 1 ครั้ง\r\n  - GameDori 2 โพสต์ = สะสม 1 ครั้ง\r\n  # สะสมรวม 4 ครั้งต่อวัน = นับเป็น 1 วัน (ถ้าเกินกว่านั้นในวันเดียวจะไม่นับเพิ่ม ได้สูงสุดแค่วันละ 1 วัน)\r\n  # ต้องมีจำนวนครบตามเงื่อนไขภายใน 1 วันจึงจะนับเป็น 1 ครั้ง ไม่สามารถรวมข้ามวันได้ กรุณาสร้างหลายบัญชีในแต่ละเว็บเพื่อให้โพสต์ได้ครบตามจำนวน\r\n    => ตัวอย่าง: วันที่ 1 โพสต์ TodayServer 1 ครั้ง + วันที่ 2 โพสต์ TodayServer 1 ครั้ง = ไม่นับเป็น 1 ครั้ง\r\n  # เมื่อครบ 15 วัน ได้รับเหรียญ Supporter Medal (All Stat 250, ATT/MATT 200)\r\n  # เมื่อครบ 100 วัน ได้รับเหรียญ Supporter+ Medal (All Stat 400, ATT/MATT 350)");
                        break;
                    }
                    case 3: { // GameZone Individual Guide
                        self.sayOk(
                                "  # GameZone\r\n    - มีจำกัดคะแนนเมื่อตั้งกระทู้\r\n  # FreeZone\r\n    - ไม่จำกัดการตั้งกระทู้ (ถ้าถูกจำกัด ต้องใช้หลายบัญชี)\r\n    - ต้องยืนยันอีเมลเมื่อสมัคร\r\n    - หัก 5 Points เมื่อตั้งกระทู้, สมัครใหม่ได้ 50 Points\r\n    - หลังจากนั้นหา Point ได้จากการโดเนท หรือขยันตอบคอมเมนต์\r\n  # TodayServer\r\n    - ตั้งกระทู้ได้หลังสมัคร 1 ชั่วโมง\r\n    - ไม่ต้องยืนยันอีเมล/เบอร์โทร\r\n    - ใช้ 200P ต่อโพสต์\r\n    - เช็คชื่อรายวันได้ 150P\r\n  # TodayFree\r\n    - สมัครแล้วตั้งกระทู้ได้เลย\r\n    - ต้องยืนยันอีเมล\r\n    - โพสต์ได้วันละ 1 ครั้ง\r\n  # SundayServer\r\n    - สมัครแล้วตั้งกระทู้ได้เลย\r\n    - ไม่ต้องยืนยันอีเมล/เบอร์โทร\r\n    - โพสต์ได้วันละ 2 ครั้ง\r\n  # DamoaFree\r\n    - ตั้งกระทู้ได้หลังสมัคร 1 วัน\r\n    - ต้องยืนยันอีเมล\r\n    - โพสต์ได้วันละ 1 ครั้ง\r\n  # GameDori\r\n    - สมัครแล้วตั้งกระทู้ได้เลย\r\n   - ต้องยืนยันอีเมล\r\n    - ใช้ 20P ต่อโพสต์ (สมัครได้ 20P, เช็คชื่อได้ 10P)\r\n\r\nกรุณาสร้างหลายบัญชีในแต่ละเว็บไซต์เพื่อดำเนินการโปรโมท");
                        break;
                    }
                }
                break;
            }

            case 1: { // Promotion Verification
                self.sayOk(
                        "การยืนยันทั้งหมด กรุณาดูแบบฟอร์มด้านล่าง แล้วไปลงทะเบียนในบอร์ดโปรโมทที่เว็บไซต์หลักของ Jin:眞 (https://maplejin.com/) ให้ถูกต้องตามแบบฟอร์ม");
                break;
            }

            case 2: { // Promotion Rewards
                self.sayOk("กำลังเตรียมข้อมูลส่วนนี้");
                break;
            }
            case 3: {
                String heart = self.askText("กรุณาเขียนจดหมายที่ต้องการส่ง จดหมายจะถูกส่งถึงทีมงานแบบเรียลไทม์");
                if (!heart.equals("")) {
                    String letter = "ชื่อตัวละคร : " + getPlayer().getName() + "\r\n";
                    letter += "เลเวล : " + getPlayer().getLevel() + "\r\n";
                    letter += "อาชีพ : " + getPlayer().getJob() + "\r\n";
                    letter += heart;
                    if (DiscordBotHandler.requestSendTelegram(letter, -460314003)) {
                        self.sayOk("ได้รับข้อมูลเรียบร้อยแล้ว");
                    } else {
                        self.sayOk("ขณะนี้ไม่สามารถใช้งานได้");
                    }
                }
                break;
            }
        }
    }
}
