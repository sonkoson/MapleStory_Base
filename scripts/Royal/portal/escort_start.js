/*

ep1.White Mage
Creator :: usf - (uuff1234)
		nate - uuff1236@nate.com

*/

function enter(pi) {
	if (pi.isQuestActive(300)) {
		if (pi.getQuestStatus(300) == 1) {
			pi.warp(302010100, 0);
			pi.getPlayer().dropMessage(-1, "[Notice] Move to the right portal.");
			pi.getPlayer().dropMessage(5, "[Notice] Move to the right portal.");
		} else {
			pi.getPlayer().dropMessage(-1, "[Notice] Talk to Hatsar on the left.");
			pi.getPlayer().dropMessage(5, "[Notice] Talk to Hatsar on the left.");
			//pi.forceCompleteQuest(3164);
			//pi.playerMessage("Quest Complete");
		}
	}
}