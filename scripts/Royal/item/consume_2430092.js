/*
제작자 : 백란(vmfhvlfqhwlak@nate.com);
*/


var status = -1;

function start() {
 action(1, 0, 0);
}

function action(mode, type, selection) {
 if (mode == 1) {
  status++;
 } else {
  status--;
  cm.dispose();
 }
 if (status == 0) {
  cm.sendYesNo("#b#z2430092##k 를 정말로 사용하시겠습니까?");
 } else if (status == 1) {
cm.gainItem(2430092,-1);//아이템 사라지게
cm.teachSkill(80001025,1,1); // 스킬 주기
cm.sendOk("สกิล Riding ถูกใช้งานเรียบร้อยแล้ว");
cm.dispose();
}
}