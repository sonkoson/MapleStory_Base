package commands;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import objects.item.MapleItemInformationProvider;
import objects.users.MapleClient;
import objects.users.skills.Skill;
import objects.users.skills.SkillFactory;
import objects.utils.Pair;
import objects.utils.StringUtil;
import objects.wz.provider.MapleData;
import objects.wz.provider.MapleDataProvider;
import objects.wz.provider.MapleDataProviderFactory;
import objects.wz.provider.MapleDataTool;

public class SearchCommands implements Command {
   @Override
   public void execute(MapleClient c, String[] splitted) throws Exception, IllegalCommandSyntaxException {
      if (splitted.length == 1) {
         c.getPlayer().dropMessage(6, splitted[0] + ": <npc> <mob> <item> <map> <skill> <quest>");
      } else {
         String type = splitted[1];
         String search = StringUtil.joinStringFrom(splitted, 2);
         MapleData data = null;
         MapleDataProvider dataProvider = MapleDataProviderFactory
               .getDataProvider(new File(System.getProperty("net.sf.odinms.wzpath") + "/String.wz"));
         c.getPlayer().dropMessage(6, "<<Type: " + type + " | Search: " + search + ">>");
         if (type.equalsIgnoreCase("npc")) {
            List<String> retNpcs = new ArrayList<>();
            data = dataProvider.getData("Npc.img");
            List<Pair<Integer, String>> npcPairList = new LinkedList<>();

            for (MapleData npcIdData : data.getChildren()) {
               npcPairList.add(new Pair<>(Integer.parseInt(npcIdData.getName()),
                     MapleDataTool.getString(npcIdData.getChildByPath("name"), "NO-NAME")));
            }

            for (Pair<Integer, String> npcPair : npcPairList) {
               if (npcPair.getRight().toLowerCase().contains(search.toLowerCase())) {
                  retNpcs.add(npcPair.getLeft() + " - " + npcPair.getRight());
               }
            }

            if (retNpcs != null && retNpcs.size() > 0) {
               for (String singleRetNpc : retNpcs) {
                  c.getPlayer().dropMessage(6, singleRetNpc);
               }
            } else {
               c.getPlayer().dropMessage(6, "No NPC found.");
            }
         } else if (type.equalsIgnoreCase("map")) {
            List<String> retMaps = new ArrayList<>();
            data = dataProvider.getData("Map.img");
            List<Pair<Integer, String>> mapPairList = new LinkedList<>();

            for (MapleData mapAreaData : data.getChildren()) {
               for (MapleData mapIdData : mapAreaData.getChildren()) {
                  mapPairList.add(
                        new Pair<>(
                              Integer.parseInt(mapIdData.getName()),
                              MapleDataTool.getString(mapIdData.getChildByPath("streetName"), "NO-NAME")
                                    + " - "
                                    + MapleDataTool.getString(mapIdData.getChildByPath("mapName"), "NO-NAME")));
               }
            }

            for (Pair<Integer, String> mapPair : mapPairList) {
               if (mapPair.getRight().toLowerCase().contains(search.toLowerCase())) {
                  retMaps.add(mapPair.getLeft() + " - " + mapPair.getRight());
               }
            }

            if (retMaps != null && retMaps.size() > 0) {
               for (String singleRetMap : retMaps) {
                  c.getPlayer().dropMessage(6, singleRetMap);
               }
            } else {
               c.getPlayer().dropMessage(6, "No Map found.");
            }
         } else if (type.equalsIgnoreCase("mob")) {
            List<String> retMobs = new ArrayList<>();
            data = dataProvider.getData("Mob.img");
            List<Pair<Integer, String>> mobPairList = new LinkedList<>();

            for (MapleData mobIdData : data.getChildren()) {
               mobPairList.add(new Pair<>(Integer.parseInt(mobIdData.getName()),
                     MapleDataTool.getString(mobIdData.getChildByPath("name"), "NO-NAME")));
            }

            for (Pair<Integer, String> mobPair : mobPairList) {
               if (mobPair.getRight().toLowerCase().contains(search.toLowerCase())) {
                  retMobs.add(mobPair.getLeft() + " - " + mobPair.getRight());
               }
            }

            if (retMobs != null && retMobs.size() > 0) {
               for (String singleRetMob : retMobs) {
                  c.getPlayer().dropMessage(6, singleRetMob);
               }
            } else {
               c.getPlayer().dropMessage(6, "No Mob found.");
            }
         } else if (type.equalsIgnoreCase("REACTOR")) {
            c.getPlayer().dropMessage(6, "NOT ADDED YET");
         } else if (type.equalsIgnoreCase("item")) {
            List<String> retItems = new ArrayList<>();

            for (Pair<Integer, String> itemPair : MapleItemInformationProvider.getInstance().getAllItems()) {
               if (itemPair.getRight().toLowerCase().contains(search.toLowerCase())) {
                  retItems.add(itemPair.getLeft() + " - " + itemPair.getRight());
               }
            }

            if (retItems != null && retItems.size() > 0) {
               for (String singleRetItem : retItems) {
                  System.out.println(singleRetItem);
                  c.getPlayer().dropMessage(6, singleRetItem);
               }
            } else {
               c.getPlayer().dropMessage(6, "No Item found.");
            }
         } else if (type.equalsIgnoreCase("skill")) {
            List<String> retSkills = new ArrayList<>();
            data = dataProvider.getData("Skill.img");
            List<Pair<Integer, String>> skillPairList = new LinkedList<>();

            for (MapleData skillIdData : data.getChildren()) {
               try {
                  int skillID = Integer.parseInt(skillIdData.getName());
                  String name = MapleDataTool.getString(skillIdData.getChildByPath("name"), "NO-NAME");
                  Skill skill = SkillFactory.getSkill(skillID);
                  if (skill != null && skill.isInvisible()) {
                     name = name + " (invisible)";
                  }

                  skillPairList.add(new Pair<>(skillID, name));
               } catch (NumberFormatException var14) {
               }
            }

            for (Pair<Integer, String> skillPair : skillPairList) {
               if (skillPair.getRight().toLowerCase().contains(search.toLowerCase())) {
                  retSkills.add(skillPair.getLeft() + " - " + skillPair.getRight());
               }
            }

            if (retSkills != null && retSkills.size() > 0) {
               for (String singleRetSkill : retSkills) {
                  c.getPlayer().dropMessage(6, singleRetSkill);
               }
            } else {
               c.getPlayer().dropMessage(6, "No Skill found.");
            }
         } else if (type.equalsIgnoreCase("quest")) {
            List<String> retQuests = new ArrayList<>();
            dataProvider = MapleDataProviderFactory
                  .getDataProvider(new File(System.getProperty("net.sf.odinms.wzpath") + "/Quest.wz"));
            data = dataProvider.getData("QuestInfo.img");
            List<Pair<Integer, String>> questPairList = new LinkedList<>();

            for (MapleData questIdData : data.getChildren()) {
               questPairList.add(new Pair<>(Integer.parseInt(questIdData.getName()),
                     MapleDataTool.getString(questIdData.getChildByPath("name"), "NO-NAME")));
            }

            for (Pair<Integer, String> questPair : questPairList) {
               if (questPair.getRight().toLowerCase().contains(search.toLowerCase())) {
                  retQuests.add(questPair.getLeft() + " - " + questPair.getRight());
               }
            }

            if (retQuests != null && retQuests.size() > 0) {
               for (String singleRetQuest : retQuests) {
                  System.out.println(singleRetQuest);
                  c.getPlayer().dropMessage(6, singleRetQuest);
               }
            } else {
               c.getPlayer().dropMessage(6, "No Quest found.");
            }
         } else {
            c.getPlayer().dropMessage(6, "ไม่พบ Type");
         }
      }
   }

   @Override
   public CommandDefinition[] getDefinition() {
      return new CommandDefinition[] {
            new CommandDefinition("!search", "<type> <search>", "Searches for an ID with the given type and keyword.",
                  2),
            new CommandDefinition("!find", "<type> <search>", "Searches for an ID with the given type and keyword.", 2)
      };
   }
}
