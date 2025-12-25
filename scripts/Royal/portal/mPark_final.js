var data;
var day;
var item = -1;
function enter(pi) {
	if (pi.getClient().getChannelServer().getMapFactory().getMap(pi.getPlayer().getMapId()).getNumMonsters() > 0) {
		pi.getPlayer().dropMessage(5, "เจ้าต้องกำจัดมอนสเตอร์ทั้งหมดจึงจะผ่านด่านได้");
	} else {
		if (pi.getPlayer().getQuestStatus(100573) == 1) {
			pi.getPlayer().updateOneInfo(100573, "monsterPark", String(pi.getPlayer().getOneInfoQuestInteger(100573, "monsterPark") + 1));
		}
		pi.openNpc(9071000);
	}
}