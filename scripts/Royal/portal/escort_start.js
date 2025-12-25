/*

ep1.White Mage
Creator :: usf - (uuff1234)
		nate - uuff1236@nate.com

*/

function enter(pi) {
	if (pi.isQuestActive(300)) {
		if (pi.getQuestStatus(300) == 1) {
			pi.warp(302010100, 0);
			pi.getPlayer().dropMessage(-1, "[Notice] ย้ายไปที่พอร์ทัลทางขวา");
			pi.getPlayer().dropMessage(5, "[Notice] ย้ายไปที่พอร์ทัลทางขวา");
		} else {
			pi.getPlayer().dropMessage(-1, "[Notice] คุยกับ Hatsar ทางซ้าย");
			pi.getPlayer().dropMessage(5, "[Notice] คุยกับ Hatsar ทางซ้าย");
			//pi.forceCompleteQuest(3164);
			//pi.playerMessage("Quest Complete");
		}
	}
}