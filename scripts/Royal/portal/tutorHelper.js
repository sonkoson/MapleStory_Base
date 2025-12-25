function enter(pi) {
	if (pi.getQuestStatus(20021) == 0) {
		pi.playerSummonHint(true);
		pi.summonMsg("ยินดีต้อนรับสู่โลกแห่ง Maple! ฉันชื่อ Koo และฉันจะเป็นไกด์ของเจ้า! ฉันจะอยู่ที่นี่เพื่อตอบคำถามและแนะนำเจ้าจนกว่าเจ้าจะถึงเลเวล 10 และกลายเป็น Knight-in-Training หากเจ้ามีคำถามใดๆ ดับเบิ้ลคลิกที่ฉันได้เลย!");
		//	pi.forceCompleteQuest(20100);
		pi.forceCompleteQuest(20021);
	}
}