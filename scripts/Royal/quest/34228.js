var status = -1;

function start(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else {
		status--;
	}
	if (status == 0) {
		qm.sendNext("ยินดีต้อนรับค่ะ #ho#! มาช่วยทำอาหารเหรอคะ?");
	} else if (status == 1) {
		qm.sendNextPrev("ช่วงนี้รวบรวมวัตถุดิบได้พอสมควรแล้ว งานที่จะขอความช่วยเหลือจาก #ho# จึงลดลงนิดหน่อยค่ะ\r\n#e#r*จำนวนครั้งที่สามารถทำ <มูโตะผู้หิวโหย> เสร็จทันทีเพิ่มขึ้น 1 ครั้ง#k#n");
	} else if (status == 2) {
		qm.forceCompleteQuest();
		qm.dispose();
	}
}
