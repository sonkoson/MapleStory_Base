
import os

replacements_map = {
    'child/etc/Field_MMRace.java': [
        ('"멍멍이 ๋ ˆ이싱이 시작되었습니다."', '"Doggy Race has started."'),
        ('"버기"', '"Buggy"'),
        ('"얼루"', '"Alloo"'),
        ('"그로돈"', '"Grodon"'),
        ('"쿤두라"', '"Koondura"'),
        ('"게임 시작까지 " + this.startTimer + "초 남았습니다. 참여하지 않은 분들은 빨리 참여해 주세요!"', '"Game starts in " + this.startTimer + " seconds. Please join quickly!"'),
        ('"์ œ " + this.gameRound + "회 운동회의 우승자는 \'#" + mob.getEventName() + "\' 입니다."', '"The winner of round " + this.gameRound + " is \'#" + mob.getEventName() + "\'."'),
        ('" 슬로우에 걸๋ ธ습니다!"', '" has been slowed!"')
    ],
    'child/fritto/Field_CourtshipDance.java': [
         ('"달걀을 훔치๋ ค면 먼์ € 닭들은 속여야 해! 자, 나를 따라 구์• 의 춤을 춰!"', '"To steal eggs, you must trick the chickens first! Come, dance the courtship dance with me!"')
    ],
    'child/fritto/Field_ReceivingTreasure.java': [
         ('"์  프키와 방향키를 이용해 내가 떨어트리는 보물을 최대한 많이 받아줘!"', '"Use Jump and Arrow keys to catch as many treasures as possible!"')
    ],
    'child/fritto/Field_StealDragonsEgg.java': [
         ('"쉿! 이 둥지의 꼭대기에는 드래곤의 알이 숨겨์ ธ 있어. 꼭대기로 가는 길을 잘 찾아보라구!"', '"Shh! Dragon eggs are hidden at the top of this nest. Find your way to the top!"')
    ],
    'child/jinhillah/Field_JinHillah.java': [
         ('"진 힐라 격파로 얻은 아이템"', '"Item obtained from defeating Jin Hilla"'),
         ('"보스 하드 진 힐라 격파"', '"Boss Hard Jin Hilla Defeated"'),
         ('"ก่อนที่ Hilla จะเข้ามาและหายไป ต้องรัวปุ่ม Harvest ที่แท่นบูชาเพื่อกู้คืนวิญญҳ"', '"ก่อนที่ Hilla จะเข้ามาและหายไป ต้องรัวปุ่ม Harvest ที่แท่นบูชาเพื่อกู้คืนวิญญาณ"') 
    ],
    'child/karing/Field_BossGoongiPhase.java': [
         ('"เพื่อไล่ตาม Karing ต้องกำจѴ 4 สѵว์ร้า·ี่บุกรุกแต่ละĴูกาลของ Shangri-La"', '"เพื่อไล่ตาม Karing ต้องกำจัด 4 สัตว์ร้ายที่บุกรุกแต่ละฤดูกาลของ Shangri-La"'),
         ('"사흉을 모시깽이 합니다."', '"Summoning the Four Perils."'),
         ('"타입 : "', '"Type : "'),
         ('"ส่งแพ็กเก็ต Type 3 แล้ว"', '"Sent Packet Type 3: "')
    ],
    'child/karing/Field_BossKaringMatch.java': [
         ('"Type แรก : "', '"First Type : "'),
         ('" ครั้งที่สอง : "', '" Second Type : "')
    ],
    'child/karing/Field_BossKaringPhase2.java': [
         ('"วิญญҳแค้นของสѵว์ประหลҴที่ Karing ดٴซับกำลังดิ้นรน ต้องหลบหลีกเพื่อไม่ให้ถูกกลืนกิน"', '"วิญญาณแค้นของสัตว์ประหลาดที่ Karing ดูดซับกำลังดิ้นรน ต้องหลบหลีกเพื่อไม่ให้ถูกกลืนกิน"'),
         ('"Karing ที่ดٴซับ 4 สѵว์ร้ҁดูเหมือนจะอาละวҴได้ทุกเมื่อ"', '"Karing ที่ดูดซับ 4 สัตว์ร้ามา ดูเหมือนจะอาละวาดได้ทุกเมื่อ"') 
    ],
    'child/karing/Field_BossKaringPhase3.java': [
         ('"ดูเหมือนว่าเครื่องดนตรีของสѵว์ประหลҴจะล้นทะลักและอาละวҴ ต้องหยشยั้งไม่ให้ Shangri-La พังทลาย"', '"ดูเหมือนว่าเครื่องดนตรีของสัตว์ประหลาดจะล้นทะลักและอาละวาด ต้องหยุดยั้งไม่ให้ Shangri-La พังทลาย"')
    ],
    'child/karrotte/Field_BossKalos.java': [
         ('"T-boy의 간섭으로 구속의 눈이 ์ž 에서 깨어납니다."', '"Due to T-boy\'s interference, Eye of Restraint awakens from sleep."'),
         ('"T-boy의 간섭으로 포격 ์ „투기가 활성화됩니다."', '"Due to T-boy\'s interference, Bombardment fighter activated."'),
         ('"T-boy의 간섭으로 오디움의 구체가 ์  을 감지합니다."', '"Due to T-boy\'s interference, Odium\'s Sphere detects enemy."'),
         ('"T-boy의 간섭으로 심연의 눈이 ์ž 에서 깨어납니다."', '"Due to T-boy\'s interference, Eye of Abyss awakens from sleep."'),
         ('"자격을 확인 받지 못해 다음 관문으로 넘어가지 못하๊ณ  추방당했습니다."', '"Failed to verify qualification, unable to proceed to next gate and expelled."'),
         ('"자격이 없는 자에게 문은 열리지 않는다..."', '"The door does not open for the unqualified..."')
    ],
    'child/karrotte/Field_BossKalosPhase1.java': [
         ('"아직 심판은 끝나지 않았다."', '"The judgment is not over yet."')
    ],
    'child/karrotte/Field_BossKalosPhase2.java': [
         ('"여기까진가... 죄송합니다. 아버지..."', '"Is this it... I am sorry. Father..."'),
         ('"폭주에 휘말리지 않도록 수호 지대를 찾아라..."', '"Find the sanctuary to avoid being swept by the rampage..."'),
         ('"이지"', '"Easy"'), ('"노말"', '"Normal"'), ('"카오스"', '"Chaos"'), ('"익스트림"', '"Extreme"'),
         ('" 칼로스]를 격파하였습니다."', '" Kalos] Defeated."')
    ],
    'child/lucid/Field_LucidBattle.java': [
         ('"ความฝันกำลังรุนแรงขึ้น ระวังตัǴ้วย!"', '"ความฝันกำลังรุนแรงขึ้น ระวังตัวด้วย!"')
    ],
    'child/lucid/Field_LucidBattlePhase1.java': [
         ('"ดูเหมือน Lucid จะโกรธจѴ!"', '"ดูเหมือน Lucid จะโกรธจัด!"'),
         ('"Lucid จะแʴงพลังที่แข็งแกร่งยิ่งขึ้น!"', '"Lucid จะแสดงพลังที่แข็งแกร่งยิ่งขึ้น!"'),
         ('"Lucid กำลังดึงพลังออกมา!"', '"Lucid กำลังดึงพลังออกมา!"')
    ],
    'child/lucid/Field_LucidBattlePhase2.java': [
         ('"ไอเทมที่ได้รับจากการกำจѴ Lucid"', '"Item obtained from defeating Lucid"'),
         ('"กำจѴ Boss Normal Lucid"', '"Boss Normal Lucid Defeated"')
    ],
    'child/magnus/Field_Magnus.java': [
         ('"매그너스가 사망하여 방출된 에너지로 인해 더이상 구와르의 힘에 영향을 받지 않습니다."', '"With Magnus dead, the released energy no longer affects Guwaru\'s power."'),
         ('"매그너스가 구와르를 ์ œ어하는 힘이 약화 되었습니다. 구와르의 기운이 더욱 강해집니다."', '"Magnus\'s control over Guwaru has weakened. Guwaru\'s aura becomes stronger."')
    ],
    'child/minigame/onecard/Field_OneCard.java': [
         ('"당์‹ 의 턴입니다."', '"It\'s your turn."'),
         ('"คسชนะ! จบเกม"', '"คุณชนะ! จบเกม"'),
         ('"마법 : 색 바꾸기!"', '"Magic : Change Color!"'),
         ('"마법 : 한 번 더!"', '"Magic : One More Time!"'),
         ('"마법 : ์  프!"', '"Magic : Jump!"'),
         ('"마법 : 거꾸로!"', '"Magic : Reverse!"')
    ],
    'child/minigame/soccer/Field_MultiSoccer.java': [
         ('"경기~~ 시작합니다~~~!!!"', '"Match~~ Start~~~!!!"')
    ],
    'child/minigame/train/Field_TrainMaster.java': [
         ('"#b#e크리에이터들의\r\n기차 운행 도์ „!#n#k\r\n#r지금 바로 시작하๊ฒ 네!#k"', '"#b#eCreators\'\r\nTrain Operation Challenge!#n#k\r\n#rStarting Right Now!#k"')
    ],
    'child/minigame/yutgame/Field_MultiYutGame.java': [
         ('"무효"', '"Invalid"'),
         ('" 5회 이상 시 퇴장 처리됩니다."', '" 5 or more will result in expulsion."')
    ],
    'child/moonbridge/Field_FerociousBattlefield.java': [
         ('"더스크 격파로 얻은 아이템"', '"Item obtained from defeating Dusk"'),
         ('"보스 카오스 더스크 격파"', '"Boss Chaos Dusk Defeated"'),
         ('"보스 노말 더스크 격파"', '"Boss Normal Dusk Defeated"'),
         ('"หน้าที่ป้องกันอยู่จะโจมตีอย่างรุนแรง! ถ้านได้ จะสามารถโจมตีวงตาแห่งความว่างเปล่ҷี่เปิดออกได้!"', '"หน้าที่ป้องกันอยู่จะโจมตีอย่างรุนแรง! ถ้ากันได้ จะสามารถโจมตีดวงตาแห่งความว่างเปล่าที่เปิดออกได้!"')
    ],
    'child/papulatus/Field_Papulatus.java': [
         ('"파풀라투스가 차원 이동을 통해서 HP를 회복하๋ ค๊ณ  시도합니다."', '"Papulatus attempts to heal HP through dimensional shift."'),
         ('"파풀라투스가 시간 이동을 ํ•  수 없도록 차원의 ๊ท 열을 봉인해야 합니다."', '"You must seal the dimensional rift so Papulatus cannot time travel."'),
         ('"차원의 ๊ท 열을 봉인했습니다."', '"Sealed the dimensional rift."'),
         ('"파풀라투스의 시계가 움직입니다. 차원의 포탈을 통해 시간을 봉인하세요."', '"Papulatus\'s clock is moving. Seal time through the dimensional portal."'),
         ('"기다리๊ณ  있었다! 용기가 남았다면 들어와 보시지!"', '"I\'ve been waiting! If you have courage left, come in!"')
    ],
    'child/pollo/Field_BountyHunting.java': [
         ('"놈들이 사방에서 몰๋ ค오는군! 녀석들을 처치하면 막대한 경험치를 얻을 수 있다!"', '"They are swarming from everywhere! Defeat them for huge EXP!"')
    ],
    'child/pollo/Field_FlameWolf.java': [
         ('"불꽃늑대를 처치ํ•  용사가 늘었군. 어서 녀석을 공격해! 머무를 수 있는 시간은 30초 뿐이야!"', '"More heroes to defeat the Flame Wolf. Attack him! You only have 30 seconds!"')
    ],
    'child/pollo/Field_MidnightMonsterHunting.java': [
          ('"npc/채집 키로 공격을 ํ•  수 있다. 몰๋ ค오는 놈들을 나와 함께 다 쓸어버리자๊ณ ."', '"You can attack with NPC/Harvest key. Let\'s sweep away the swarming enemies!"')
    ],
    'child/pollo/Field_StormwingArea.java': [
          ('"재๋น 른 황금빛 녀석이 스톰윙일세. 그 녀석을 잡으면 머물 수 있는 시간이 늘어나지!"', '"That quick golden one is Stormwing. Catching him extends your stay!"')
    ],
    'child/pollo/Field_TownDefense.java': [
          ('"WAVE를 막아냈습니다. 다음 WAVE를 준비해주세요."', '"Wave Defended. Please prepare for the next Wave."'),
          ('"놈들이 겁도 없이 성벽 안의 마을을 습격하는군! 모조리 해치워라!"', '"They dare attack the town inside the walls! Wipe them all out!"'),
          ('", 너의 사냥 실๋ ฅ이 이์ •도밖에 안됐나?"', '", Is this all your hunting skill amounts to?"')
    ],
    'child/rimen/Field_RimenNearTheEnd.java': [
          ('"듄켈 격파로 얻은 아이템"', '"Item obtained from defeating Dunkel"'),
          ('"보스 하드 듄켈 격파"', '"Boss Hard Dunkel Defeated"'),
          ('"보스 노말 듄켈 격파"', '"Boss Normal Dunkel Defeated"')
    ],
    'child/rootabyss/Field_CrimsonQueen.java': [
          ('"내가 상대해주๊ฒ 어요."', '"I will deal with you."'),
          ('"킥킥, 다 없์• 주지"', '"Kikik, I\'ll destroy everything."'),
          ('"모두 불태워주마!"', '"I\'ll burn everything!"'),
          ('"내 ๊ณ 통을 느끼게 해줄게요."', '"I\'ll let you feel my pain."')
    ],
    'child/sernium/Field_SerenPhase1.java': [
          ('"태양의 불꽃은 복수를 잊지 않는다."', '"The Flame of the Sun never forgets revenge."')
    ],
    'child/sernium/Field_SerenPhase2.java': [
          ('"태양의 빛으로 가득찬 ์ •오가 시작됩니다."', '"Noon filled with sunlight begins."'),
          ('"머지않아 태양은 다시 ๋– 오를 것이다…"', '"Soon the sun will rise again..."'),
          ('"보스 노말 세๋ Œ 격파"', '"Boss Normal Seren Defeated"'),
           ('"보스 하드 세๋ Œ 격파"', '"Boss Hard Seren Defeated"'),
          ('"황혼의 불타는 듯한 석양이 회복 효율을 낮추๊ณ  지속์  으로 피해를 입힙니다."', '"The burning twilight reduces recovery efficiency and deals continuous damage."'),
          ('"태양이 ์ €물어 빛을 잃๊ณ  자์ •이 시작됩니다."', '"The sun sets, losing light, and midnight begins."'),
          ('"태양이 서서히 ๋– 올라 빛과 희망이 시작되는 여명이 다가옵니다."', '"The sun slowly rises, dawn of light and hope approaches."'),
          ('"์ •오가 시작됨과 동시에 남아있는 여명의 기운이 세๋ Œ을 회복시킵니다."', '"As noon begins, the remaining aura of dawn heals Seren."'),
          ('"태양이 지지 않는다면 누구도 나에게 대항ํ•  수 없다."', '"As long as the sun does not set, no one can oppose me."')
    ],
    'child/slime/Field_GuardianAngelSlime.java': [
          ('"왕관의 수호로 ์ œ대로 된 피해를 입힐 수 없๊ฒ 군. 마스코트 슬라임에게 도움을 받아 마그마 슬라임을 ๋‚ ๋ ค버리자."', '"Guardian of the Crown prevents proper damage. Get help from Mascot Slime to block Magma Slime."'),
          ('"카오스 가디언 엔์ ค 슬라임"', '"Chaos Guardian Angel Slime"'),
          ('"노말 가디언 엔์ ค 슬라임"', '"Normal Guardian Angel Slime"'),
          ('"สำเร็จแล้ว!! รีบขึ้นไปข้างบนเธ ายใน 10 วินҷีก่อนที่พื้นที่ด้านล่างจะปԴลง!!"', '"สำเร็จแล้ว!! รีบขึ้นไปข้างบนภายใน 10 วินาทีก่อนที่พื้นที่ด้านล่างจะปิดลง!!"'),
          ('"Guardian Wave กำลังจеกลงมา!! ตԴตั้ง Crystal Droplet เพื่อควบคุมการไหลของคลื่น ฉันจะช่วยเธอเอง!"', '"Guardian Wave กำลังจะตกลงมา!! ติดตั้ง Crystal Droplet เพื่อควบคุมการไหลของคลื่น ฉันจะช่วยเธอเอง!"')
    ],
    'child/union/Field_Union.java': [
          ('"โหŴข้อมูŁUnion ล้มเหลว"', '"Start Loading Union Failed"') # Corrected assumption
    ],
    'child/union/MapleUnionData.java': [
        ('"노비스 ์œ 니온"', '"Novice Union"'),
        ('" 단계"', '" Stage"')
    ],
    'child/will/Field_WillBattle.java': [
          ('"지금이 기회야! 거울이 갈라져서 ์ €쪽 세계의 윌에게도 피해를 입힐 수 있어!"', '"Now is the chance! The mirror is cracked, damage can be dealt to Will on the other side!"'),
          ('"ตอนนี้แหละ! ต้องโจมตյอนที่ Will ไร้การป้องกัน!"', '"ตอนนี้แหละ! ต้องโจมตีตอนที่ Will ไร้การป้องกัน!"'),
          ('"Mirror of Lies สз้อนการโจมตีกลับ หากรอยแยกปรากฏขึ้น ให้เผชิญหน้ากับการโจมตี"', '"Mirror of Lies สะท้อนการโจมตีกลับ หากรอยแยกปรากฏขึ้น ให้เผชิญหน้ากับการโจมตี"')
    ],
    'child/will/Field_WillBattlePhase1.java': [
          ('"Will เริ่มจริงจังแล้ǁความจริงใจของ Will อาจสз้อนในส่วนลึกของกระจก"', '"Will เริ่มจริงจังแล้ว ความจริงใจของ Will อาจสะท้อนในส่วนลึกของกระจก"')
    ],
    'child/will/Field_WillBattlePhase2.java': [
          ('"Will หมดความʹทนแล้ǁส่วนที่ลึกที่สشของโลกกระจกกำลังจะเปԴเผย"', '"Will หมดความอดทนแล้ว ส่วนที่ลึกที่สุดของโลกกระจกกำลังจะเปิดเผย"')
    ],
    'child/will/Field_WillBattleReward.java': [
          ('"윌 격파로 얻은 아이템"', '"Item obtained from defeating Will"'),
          ('"보스 노말 윌 격파"', '"Boss Normal Will Defeated"'),
          ('"보스 하드 윌 격파"', '"Boss Hard Will Defeated"'),
          ('"보스 헬 윌 격파"', '"Boss Hell Will Defeated"')
    ]
}

def process_recursive(root_dir, replacements_map):
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith(".java"):
                file_path = os.path.join(root, file)
                # match relative path suffix
                relative_path = os.path.relpath(file_path, root_dir).replace('\\', '/')
                
                matched = False
                for key in replacements_map:
                    # Logic: if file_path ends with key (handling separators)
                    # Use standard string check
                    normalized_ft = file_path.replace('\\', '/')
                    if normalized_ft.endswith(key):
                        matched = True
                        try:
                            with open(file_path, 'r', encoding='utf-8') as f:
                                content = f.read()
                            
                            modified = False
                            for old, new in replacements_map[key]:
                                if old in content:
                                    content = content.replace(old, new)
                                    modified = True
                            
                            if modified:
                                with open(file_path, 'w', encoding='utf-8') as f:
                                    f.write(content)
                                print(f"Processed {file_path}")
                        except Exception as e:
                            print(f"Error processing {file_path}: {e}")

process_recursive('src/objects/fields/child', replacements_map)
