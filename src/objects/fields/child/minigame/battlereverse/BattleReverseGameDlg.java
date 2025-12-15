package objects.fields.child.minigame.battlereverse;

import constants.ServerConstants;
import java.util.List;
import objects.fields.Field;
import objects.users.MapleCharacter;
import objects.utils.FileoutputUtil;

public class BattleReverseGameDlg {
   private BattleReverseGameInfo gameInfo = new BattleReverseGameInfo();
   private Field_BattleReverse field = null;

   public BattleReverseGameDlg(Field field) {
      this.field = (Field_BattleReverse)field;
   }

   public void init(List<MapleCharacter> players) {
      if (players.size() >= 2) {
         int idx = 0;
         BattleReverseGameInfo gameInfo = new BattleReverseGameInfo();

         for (MapleCharacter player : this.field.getCharactersThreadsafe()) {
            if (idx >= 2) {
               player.setRegisterTransferField(15);
               player.setRegisterTransferFieldTime(System.currentTimeMillis() + 1000L);
            } else {
               gameInfo.setTeamByChr(player);
               player.setMiniGameTeam(gameInfo.getTeamByChr(player));
               this.gameInfo = gameInfo;
            }
         }
      } else {
         for (MapleCharacter playerx : this.field.getCharactersThreadsafe()) {
            playerx.dropMessage(5, "เธเธณเธเธงเธเธเธนเนเน€เธฅเนเธเนเธกเนเน€เธเธตเธขเธเธเธญเธชเธณเธซเธฃเธฑเธเน€เธเธก เธเธถเธเธ–เธนเธเธขเนเธฒเธขเธเธฅเธฑเธเน€เธกเธทเธญเธ");
            playerx.setRegisterTransferField(ServerConstants.TownMap);
            playerx.setRegisterTransferFieldTime(System.currentTimeMillis() + 1000L);
         }
      }
   }

   public void updateGameInfo() {
      if (this.gameInfo.getCharacter(0) != null && this.gameInfo.getCharacter(1) != null) {
         for (MapleCharacter chr : this.field.getCharacters()) {
            chr.send(BattleReversePacket.OpenBattleReverseUI(this.gameInfo, chr));
         }
      } else {
         FileoutputUtil.log("Log_MiniGame_Except.rtf", "์บ๋ฆญํฐ๊ฐ€ null์ธ ์ํ๋ก ๊ฒ์์์‘ ์๋");

         for (MapleCharacter chr : this.field.getCharacters()) {
            chr.dropMessage(5, "เน€เธเธดเธ”เธเนเธญเธเธดเธ”เธเธฅเธฒเธ” เน€เธเธกเนเธกเนเน€เธฃเธดเนเธก เธเธถเธเธ–เธนเธเธขเนเธฒเธขเธเธฅเธฑเธเน€เธกเธทเธญเธ");
            chr.setRegisterTransferField(ServerConstants.TownMap);
            chr.setRegisterTransferFieldTime(System.currentTimeMillis() + 1000L);
         }
      }
   }

   public BattleReverseGameInfo getGameInfo() {
      return this.gameInfo;
   }
}
