package script.FieldSet;

import scripting.newscripting.Script;
import scripting.newscripting.ScriptEngineNPC;

public class YutaFarm extends ScriptEngineNPC {

    @Script
    public void out_yFarm_field() {
        getPlayer().dropMessage(5, "กรุณาใช้คำสั่ง @광장 เพื่อย้าย");
    }
}
