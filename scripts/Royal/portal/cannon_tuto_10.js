
function enter(pi) {
	pi.EnableUI(0);
	pi.DisableUI(false);
	if (pi.isQuestFinished(2568) && pi.getQuestStatus(2570) == 0) {
		pi.showInstruction("ที่นี่ที่ไหนเนี่ย? ปวดหัวชะมัด...", 150, 5);
	}
}  