package script.item;

import objects.context.SpecialSunday;
import objects.utils.Randomizer;
import scripting.newscripting.ScriptEngineNPC;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class SoulPiece extends ScriptEngineNPC {

    public void consume_2431655() { // Rock Spirit Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591010, 15.0d); // Beefy Rock Spirit Soul
        percentageInfo.put(2591011, 15.0d); // Swift Rock Spirit Soul
        percentageInfo.put(2591012, 15.0d); // Clever Rock Spirit Soul
        percentageInfo.put(2591013, 15.0d); // Fortuitous Rock Spirit Soul
        percentageInfo.put(2591014, 15.0d); // Hearty Rock Spirit Soul
        percentageInfo.put(2591015, 15.0d); // Flashy Rock Spirit Soul
        percentageInfo.put(2591016, 10.0d); // Potent Rock Spirit Soul
        change_soul_piece(percentageInfo);
    }

    public void consume_2431656() { // Prison Guard Ani Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591017, 15.0d); // Beefy Prison Guard Ani Soul
        percentageInfo.put(2591018, 15.0d); // Swift Prison Guard Ani Soul
        percentageInfo.put(2591019, 15.0d); // Clever Prison Guard Ani Soul
        percentageInfo.put(2591020, 15.0d); // Fortuitous Prison Guard Ani Soul
        percentageInfo.put(2591021, 15.0d); // Hearty Prison Guard Ani Soul
        percentageInfo.put(2591022, 15.0d); // Flashy Prison Guard Ani Soul
        percentageInfo.put(2591023, 10.0d); // Potent Prison Guard Ani Soul
        change_soul_piece(percentageInfo);
    }

    public void consume_2431657() { // Dragon Rider Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591024, 15.0d); // Beefy Dragon Rider Soul
        percentageInfo.put(2591025, 15.0d); // Swift Dragon Rider Soul
        percentageInfo.put(2591026, 15.0d); // Clever Dragon Rider Soul
        percentageInfo.put(2591027, 15.0d); // Fortuitous Dragon Rider Soul
        percentageInfo.put(2591028, 15.0d); // Hearty Dragon Rider Soul
        percentageInfo.put(2591029, 15.0d); // Flashy Dragon Rider Soul
        percentageInfo.put(2591030, 10.0d); // Potent Dragon Rider Soul
        change_soul_piece(percentageInfo);
    }

    public void consume_2431658() { // Rex Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591031, 15.0d); // Beefy Rex Soul
        percentageInfo.put(2591032, 15.0d); // Swift Rex Soul
        percentageInfo.put(2591033, 15.0d); // Clever Rex Soul
        percentageInfo.put(2591034, 15.0d); // Fortuitous Rex Soul
        percentageInfo.put(2591035, 15.0d); // Hearty Rex Soul
        percentageInfo.put(2591036, 15.0d); // Flashy Rex Soul
        percentageInfo.put(2591037, 10.0d); // Potent Rex Soul
        change_soul_piece(percentageInfo);
    }

    public void consume_2431659() { // Mu Gong Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591038, 15.0d); // Beefy Mu Gong Soul
        percentageInfo.put(2591039, 15.0d); // Swift Mu Gong Soul
        percentageInfo.put(2591040, 15.0d); // Clever Mu Gong Soul
        percentageInfo.put(2591041, 15.0d); // Fortuitous Mu Gong Soul
        percentageInfo.put(2591042, 15.0d); // Hearty Mu Gong Soul
        percentageInfo.put(2591043, 15.0d); // Flashy Mu Gong Soul
        percentageInfo.put(2591044, 10.0d); // Potent Mu Gong Soul
        change_soul_piece(percentageInfo);
    }

    public void consume_2431660() { // Balrog Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591045, 15.0d); // Beefy Balrog Soul
        percentageInfo.put(2591046, 15.0d); // Swift Balrog Soul
        percentageInfo.put(2591047, 15.0d); // Clever Balrog Soul
        percentageInfo.put(2591048, 15.0d); // Fortuitous Balrog Soul
        percentageInfo.put(2591049, 15.0d); // Flashy Balrog Soul
        percentageInfo.put(2591050, 8.0d); // Potent Balrog Soul
        percentageInfo.put(2591051, 8.0d); // Radiant Balrog Soul
        percentageInfo.put(2591052, 8.0d); // Hearty Balrog Soul
        // percentageInfo.put(2591053, 1.5d); //Sharp Balrog Soul
        // percentageInfo.put(2591054, 1.5d); //Destructive Balrog Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591085, 5.0d); // Magnificent Balrog Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591085, 1.0d); // Magnificent Balrog Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2431661() { // Pink Bean Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591055, 15.0d); // Beefy Pink Bean Soul
        percentageInfo.put(2591056, 15.0d); // Swift Pink Bean Soul
        percentageInfo.put(2591057, 15.0d); // Clever Pink Bean Soul
        percentageInfo.put(2591058, 15.0d); // Fortuitous Pink Bean Soul
        percentageInfo.put(2591059, 15.0d); // Flashy Pink Bean Soul
        percentageInfo.put(2591060, 8.0d); // Potent Pink Bean Soul
        percentageInfo.put(2591061, 8.0d); // Radiant Pink Bean Soul
        percentageInfo.put(2591062, 8.0d); // Hearty Pink Bean Soul
        // percentageInfo.put(2591063, 1.5d); //Sharp Pink Bean Soul
        // percentageInfo.put(2591064, 1.5d); //Destructive Pink Bean Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591087, 5.0d); // Magnificent Pink Bean Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591087, 1.0d); // Magnificent Pink Bean Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2431662() { // Von Leon Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591065, 15.0d); // Beefy Von Leon Soul
        percentageInfo.put(2591066, 15.0d); // Swift Von Leon Soul
        percentageInfo.put(2591067, 15.0d); // Clever Von Leon Soul
        percentageInfo.put(2591068, 15.0d); // Fortuitous Von Leon Soul
        percentageInfo.put(2591069, 15.0d); // Flashy Von Leon Soul
        percentageInfo.put(2591070, 8.0d); // Potent Von Leon Soul
        percentageInfo.put(2591071, 8.0d); // Radiant Von Leon Soul
        percentageInfo.put(2591072, 8.0d); // Hearty Von Leon Soul
        // percentageInfo.put(2591073, 1.5d); //Sharp Von Leon Soul
        // percentageInfo.put(2591074, 1.5d); //Destructive Von Leon Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591086, 5.0d); // Magnificent Von Leon Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591086, 1.0d); // Magnificent Von Leon Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2431709() { // Xerxes Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591148, 15.0d); // Beefy Xerxes Soul
        percentageInfo.put(2591149, 15.0d); // Swift Xerxes Soul
        percentageInfo.put(2591150, 15.0d); // Clever Xerxes Soul
        percentageInfo.put(2591151, 15.0d); // Fortuitous Xerxes Soul
        percentageInfo.put(2591152, 15.0d); // Hearty Xerxes Soul
        percentageInfo.put(2591153, 15.0d); // Flashy Xerxes Soul
        percentageInfo.put(2591154, 10.0d); // Potent Xerxes Soul
        change_soul_piece(percentageInfo);
    }

    public void consume_2431710() { // Zakum Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591155, 15.0d); // Beefy Zakum Soul
        percentageInfo.put(2591156, 15.0d); // Swift Zakum Soul
        percentageInfo.put(2591157, 15.0d); // Clever Zakum Soul
        percentageInfo.put(2591158, 15.0d); // Fortuitous Zakum Soul
        percentageInfo.put(2591159, 15.0d); // Flashy Zakum Soul
        percentageInfo.put(2591160, 8.0d); // Potent Zakum Soul
        percentageInfo.put(2591161, 8.0d); // Radiant Zakum Soul
        percentageInfo.put(2591162, 8.0d); // Hearty Zakum Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591163, 5.0d); // Magnificent Zakum Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591163, 1.0d); // Magnificent Zakum Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2431711() { // Cygnus Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591075, 15.0d); // Beefy Cygnus Soul
        percentageInfo.put(2591076, 15.0d); // Swift Cygnus Soul
        percentageInfo.put(2591077, 15.0d); // Clever Cygnus Soul
        percentageInfo.put(2591078, 15.0d); // Fortuitous Cygnus Soul
        percentageInfo.put(2591079, 15.0d); // Flashy Cygnus Soul
        percentageInfo.put(2591080, 8.0d); // Potent Cygnus Soul
        percentageInfo.put(2591081, 8.0d); // Radiant Cygnus Soul
        percentageInfo.put(2591082, 8.0d); // Hearty Cygnus Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591088, 5.0d); // Magnificent Cygnus Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591088, 1.0d); // Magnificent Cygnus Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2431752() { // Ephenia Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591187, 15.0d); // Beefy Ephenia Soul
        percentageInfo.put(2591188, 15.0d); // Swift Ephenia Soul
        percentageInfo.put(2591189, 15.0d); // Clever Ephenia Soul
        percentageInfo.put(2591190, 15.0d); // Fortuitous Ephenia Soul
        percentageInfo.put(2591191, 15.0d); // Hearty Ephenia Soul
        percentageInfo.put(2591192, 15.0d); // Flashy Ephenia Soul
        percentageInfo.put(2591193, 10.0d); // Potent Ephenia Soul
        change_soul_piece(percentageInfo);
    }

    public void consume_2431753() { // Arkarium Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591194, 15.0d); // Beefy Arkarium Soul
        percentageInfo.put(2591195, 15.0d); // Swift Arkarium Soul
        percentageInfo.put(2591196, 15.0d); // Clever Arkarium Soul
        percentageInfo.put(2591197, 15.0d); // Fortuitous Arkarium Soul
        percentageInfo.put(2591198, 15.0d); // Flashy Arkarium Soul
        percentageInfo.put(2591199, 8.0d); // Potent Arkarium Soul
        percentageInfo.put(2591200, 8.0d); // Radiant Arkarium Soul
        percentageInfo.put(2591201, 8.0d); // Hearty Arkarium Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591202, 5.0d); // Magnificent Arkarium Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591202, 1.0d); // Magnificent Arkarium Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2431895() { // Pianus Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591218, 15.0d); // Beefy Pianus Soul
        percentageInfo.put(2591219, 15.0d); // Swift Pianus Soul
        percentageInfo.put(2591220, 15.0d); // Clever Pianus Soul
        percentageInfo.put(2591221, 15.0d); // Fortuitous Pianus Soul
        percentageInfo.put(2591222, 15.0d); // Hearty Pianus Soul
        percentageInfo.put(2591223, 15.0d); // Flashy Pianus Soul
        percentageInfo.put(2591224, 10.0d); // Potent Pianus Soul
        change_soul_piece(percentageInfo);
    }

    public void consume_2431896() { // Hilla Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591225, 15.0d); // Beefy Hilla Soul
        percentageInfo.put(2591226, 15.0d); // Swift Hilla Soul
        percentageInfo.put(2591227, 15.0d); // Clever Hilla Soul
        percentageInfo.put(2591228, 15.0d); // Fortuitous Hilla Soul
        percentageInfo.put(2591229, 15.0d); // Flashy Hilla Soul
        percentageInfo.put(2591230, 8.0d); // Potent Hilla Soul
        percentageInfo.put(2591231, 8.0d); // Radiant Hilla Soul
        percentageInfo.put(2591232, 8.0d); // Hearty Hilla Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591233, 5.0d); // Magnificent Hilla Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591233, 1.0d); // Magnificent Hilla Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2431963() { // Black Slime Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591249, 15.0d); // Beefy Black Slime Soul
        percentageInfo.put(2591250, 15.0d); // Swift Black Slime Soul
        percentageInfo.put(2591251, 15.0d); // Clever Black Slime Soul
        percentageInfo.put(2591252, 15.0d); // Fortuitous Black Slime Soul
        percentageInfo.put(2591253, 15.0d); // Hearty Black Slime Soul
        percentageInfo.put(2591254, 15.0d); // Abundant Black Slime Soul
        percentageInfo.put(2591255, 10.0d); // Flashy Black Slime Soul
        change_soul_piece(percentageInfo);
    }

    public void consume_2431964() { // Magnus Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591256, 15.0d); // Beefy Magnus Soul
        percentageInfo.put(2591257, 15.0d); // Swift Magnus Soul
        percentageInfo.put(2591258, 15.0d); // Clever Magnus Soul
        percentageInfo.put(2591259, 15.0d); // Fortuitous Magnus Soul
        percentageInfo.put(2591260, 15.0d); // Flashy Magnus Soul
        percentageInfo.put(2591261, 8.0d); // Potent Magnus Soul
        percentageInfo.put(2591262, 8.0d); // Radiant Magnus Soul
        percentageInfo.put(2591263, 8.0d); // Hearty Magnus Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591264, 5.0d); // Magnificent Magnus Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591264, 1.0d); // Magnificent Magnus Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2432138() { // Murmur Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591288, 15.0d); // Beefy Murmur Soul
        percentageInfo.put(2591289, 15.0d); // Swift Murmur Soul
        percentageInfo.put(2591290, 15.0d); // Clever Murmur Soul
        percentageInfo.put(2591291, 15.0d); // Fortuitous Murmur Soul
        percentageInfo.put(2591292, 15.0d); // Flashy Murmur Soul
        percentageInfo.put(2591293, 8.0d); // Potent Murmur Soul
        percentageInfo.put(2591294, 8.0d); // Radiant Murmur Soul
        percentageInfo.put(2591295, 8.0d); // Hearty Murmur Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591296, 5.0d); // Magnificent Murmur Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591296, 1.0d); // Magnificent Murmur Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2432575() { // Mochadin Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591297, 15.0d); // Beefy Mochadin Soul
        percentageInfo.put(2591298, 15.0d); // Swift Mochadin Soul
        percentageInfo.put(2591299, 15.0d); // Clever Mochadin Soul
        percentageInfo.put(2591300, 15.0d); // Fortuitous Mochadin Soul
        percentageInfo.put(2591301, 15.0d); // Flashy Mochadin Soul
        percentageInfo.put(2591302, 8.0d); // Potent Mochadin Soul
        percentageInfo.put(2591303, 8.0d); // Radiant Mochadin Soul
        percentageInfo.put(2591304, 8.0d); // Hearty Mochadin Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591305, 5.0d); // Magnificent Mochadin Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591305, 1.0d); // Magnificent Mochadin Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2432576() { // Karianne Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591306, 15.0d); // Beefy Karianne Soul
        percentageInfo.put(2591307, 15.0d); // Swift Karianne Soul
        percentageInfo.put(2591308, 15.0d); // Clever Karianne Soul
        percentageInfo.put(2591309, 15.0d); // Fortuitous Karianne Soul
        percentageInfo.put(2591310, 15.0d); // Flashy Karianne Soul
        percentageInfo.put(2591311, 8.0d); // Potent Karianne Soul
        percentageInfo.put(2591312, 8.0d); // Radiant Karianne Soul
        percentageInfo.put(2591313, 8.0d); // Hearty Karianne Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591314, 5.0d); // Magnificent Karianne Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591314, 1.0d); // Magnificent Karianne Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2432577() { // CQ57 Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591315, 15.0d); // Beefy CQ57 Soul
        percentageInfo.put(2591316, 15.0d); // Swift CQ57 Soul
        percentageInfo.put(2591317, 15.0d); // Clever CQ57 Soul
        percentageInfo.put(2591318, 15.0d); // Fortuitous CQ57 Soul
        percentageInfo.put(2591319, 15.0d); // Flashy CQ57 Soul
        percentageInfo.put(2591320, 8.0d); // Potent CQ57 Soul
        percentageInfo.put(2591321, 8.0d); // Radiant CQ57 Soul
        percentageInfo.put(2591322, 8.0d); // Hearty CQ57 Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591323, 5.0d); // Magnificent CQ57 Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591323, 1.0d); // Magnificent CQ57 Soul
            change_soul_piece(percentageInfo);
        }

    }

    public void consume_2432578() { // July Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591324, 15.0d); // Beefy July Soul
        percentageInfo.put(2591325, 15.0d); // Swift July Soul
        percentageInfo.put(2591326, 15.0d); // Clever July Soul
        percentageInfo.put(2591327, 15.0d); // Fortuitous July Soul
        percentageInfo.put(2591328, 15.0d); // Flashy July Soul
        percentageInfo.put(2591329, 8.0d); // Potent July Soul
        percentageInfo.put(2591330, 8.0d); // Radiant July Soul
        percentageInfo.put(2591331, 8.0d); // Hearty July Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591332, 5.0d); // Magnificent July Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591332, 1.0d); // Magnificent July Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2432579() { // Pladd Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591333, 15.0d); // Beefy Pladd Soul
        percentageInfo.put(2591334, 15.0d); // Swift Pladd Soul
        percentageInfo.put(2591335, 15.0d); // Clever Pladd Soul
        percentageInfo.put(2591336, 15.0d); // Fortuitous Pladd Soul
        percentageInfo.put(2591337, 15.0d); // Flashy Pladd Soul
        percentageInfo.put(2591338, 8.0d); // Potent Pladd Soul
        percentageInfo.put(2591339, 8.0d); // Radiant Pladd Soul
        percentageInfo.put(2591340, 8.0d); // Hearty Pladd Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591341, 5.0d); // Magnificent Pladd Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591341, 1.0d); // Magnificent Pladd Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2433446() { // Pierre Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591383, 15.0d); // Beefy Pierre Soul
        percentageInfo.put(2591384, 15.0d); // Swift Pierre Soul
        percentageInfo.put(2591385, 15.0d); // Clever Pierre Soul
        percentageInfo.put(2591386, 15.0d); // Fortuitous Pierre Soul
        percentageInfo.put(2591387, 15.0d); // Flashy Pierre Soul
        percentageInfo.put(2591388, 8.0d); // Potent Pierre Soul
        percentageInfo.put(2591389, 8.0d); // Radiant Pierre Soul
        percentageInfo.put(2591390, 8.0d); // Hearty Pierre Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591391, 5.0d); // Magnificent Pierre Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591391, 1.0d); // Magnificent Pierre Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2433515() { // Von Bon Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591392, 15.0d); // Beefy Von Bon Soul
        percentageInfo.put(2591393, 15.0d); // Swift Von Bon Soul
        percentageInfo.put(2591394, 15.0d); // Clever Von Bon Soul
        percentageInfo.put(2591395, 15.0d); // Fortuitous Von Bon Soul
        percentageInfo.put(2591396, 15.0d); // Flashy Von Bon Soul
        percentageInfo.put(2591397, 8.0d); // Potent Von Bon Soul
        percentageInfo.put(2591398, 8.0d); // Radiant Von Bon Soul
        percentageInfo.put(2591399, 8.0d); // Hearty Von Bon Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591400, 5.0d); // Magnificent Von Bon Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591400, 1.0d); // Magnificent Von Bon Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2433591() { // Bloody Queen Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591401, 15.0d); // Beefy Bloody Queen Soul
        percentageInfo.put(2591402, 15.0d); // Swift Bloody Queen Soul
        percentageInfo.put(2591403, 15.0d); // Clever Bloody Queen Soul
        percentageInfo.put(2591404, 15.0d); // Fortuitous Bloody Queen Soul
        percentageInfo.put(2591405, 15.0d); // Flashy Bloody Queen Soul
        percentageInfo.put(2591406, 8.0d); // Potent Bloody Queen Soul
        percentageInfo.put(2591407, 8.0d); // Radiant Bloody Queen Soul
        percentageInfo.put(2591408, 8.0d); // Hearty Bloody Queen Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591409, 5.0d); // Magnificent Bloody Queen Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591409, 1.0d); // Magnificent Bloody Queen Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2433592() { // Vellum Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591410, 15.0d); // Beefy Vellum Soul
        percentageInfo.put(2591411, 15.0d); // Swift Vellum Soul
        percentageInfo.put(2591412, 15.0d); // Clever Vellum Soul
        percentageInfo.put(2591413, 15.0d); // Fortuitous Vellum Soul
        percentageInfo.put(2591414, 15.0d); // Flashy Vellum Soul
        percentageInfo.put(2591415, 8.0d); // Potent Vellum Soul
        percentageInfo.put(2591416, 8.0d); // Radiant Vellum Soul
        percentageInfo.put(2591417, 8.0d); // Hearty Vellum Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591418, 5.0d); // Magnificent Vellum Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591418, 1.0d); // Magnificent Vellum Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2433593() { // Lotus Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591419, 15.0d); // Beefy Lotus Soul
        percentageInfo.put(2591420, 15.0d); // Swift Lotus Soul
        percentageInfo.put(2591421, 15.0d); // Clever Lotus Soul
        percentageInfo.put(2591422, 15.0d); // Fortuitous Lotus Soul
        percentageInfo.put(2591423, 15.0d); // Flashy Lotus Soul
        percentageInfo.put(2591424, 8.0d); // Potent Lotus Soul
        percentageInfo.put(2591425, 8.0d); // Radiant Lotus Soul
        percentageInfo.put(2591426, 8.0d); // Hearty Lotus Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591427, 5.0d); // Magnificent Lotus Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591427, 1.0d); // Magnificent Lotus Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2434035() { // Pig Bar Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591487, 15.0d); // Beefy Pig Bar Soul
        percentageInfo.put(2591488, 15.0d); // Swift Pig Bar Soul
        percentageInfo.put(2591489, 15.0d); // Clever Pig Bar Soul
        percentageInfo.put(2591490, 15.0d); // Fortuitous Pig Bar Soul
        percentageInfo.put(2591491, 15.0d); // Flashy Pig Bar Soul
        percentageInfo.put(2591492, 8.0d); // Potent Pig Bar Soul
        percentageInfo.put(2591493, 8.0d); // Radiant Pig Bar Soul
        percentageInfo.put(2591494, 8.0d); // Hearty Pig Bar Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591495, 5.0d); // Magnificent Pig Bar Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591495, 1.0d); // Magnificent Pig Bar Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2434210() { // Premium PC Room Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591500, 12.5d); // Beefy Premium PC Room Soul
        percentageInfo.put(2591501, 12.5d); // Swift Premium PC Room Soul
        percentageInfo.put(2591502, 12.5d); // Clever Premium PC Room Soul
        percentageInfo.put(2591503, 12.5d); // Fortuitous Premium PC Room Soul
        percentageInfo.put(2591504, 12.5d); // Flashy Premium PC Room Soul
        percentageInfo.put(2591505, 12.5d); // Potent Premium PC Room Soul
        percentageInfo.put(2591506, 12.5d); // Radiant Premium PC Room Soul
        percentageInfo.put(2591507, 12.5d); // Hearty Premium PC Room Soul
        change_soul_piece(percentageInfo);
    }

    public void consume_2434470() { // Ursus Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591509, 15.0d); // Beefy Ursus Soul
        percentageInfo.put(2591510, 15.0d); // Swift Ursus Soul
        percentageInfo.put(2591511, 15.0d); // Clever Ursus Soul
        percentageInfo.put(2591512, 15.0d); // Fortuitous Ursus Soul
        percentageInfo.put(2591513, 15.0d); // Flashy Ursus Soul
        percentageInfo.put(2591514, 8.0d); // Potent Ursus Soul
        percentageInfo.put(2591515, 8.0d); // Radiant Ursus Soul
        percentageInfo.put(2591516, 8.0d); // Hearty Ursus Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591517, 5.0d); // Magnificent Ursus Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591517, 1.0d); // Magnificent Ursus Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2435031() { // Pink Bean Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591528, 14.0d); // Beefy Pink Bean Soul
        percentageInfo.put(2591529, 14.0d); // Swift Pink Bean Soul
        percentageInfo.put(2591530, 14.0d); // Clever Pink Bean Soul
        percentageInfo.put(2591531, 14.0d); // Fortuitous Pink Bean Soul
        percentageInfo.put(2591532, 14.0d); // Hearty Pink Bean Soul
        percentageInfo.put(2591533, 14.0d); // Abundant Pink Bean Soul
        percentageInfo.put(2591534, 14.0d); // Flashy Pink Bean Soul
        change_soul_piece(percentageInfo);
    }

    public void consume_2435369() { // Damien Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591564, 15.0d); // Beefy Damien Soul
        percentageInfo.put(2591565, 15.0d); // Swift Damien Soul
        percentageInfo.put(2591566, 15.0d); // Clever Damien Soul
        percentageInfo.put(2591567, 15.0d); // Fortuitous Damien Soul
        percentageInfo.put(2591568, 15.0d); // Flashy Damien Soul
        percentageInfo.put(2591569, 8.0d); // Potent Damien Soul
        percentageInfo.put(2591570, 8.0d); // Radiant Damien Soul
        percentageInfo.put(2591571, 8.0d); // Hearty Damien Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591572, 5.0d); // Magnificent Damien Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591572, 1.0d); // Magnificent Damien Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2436039() { // Lucid Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591582, 15.0d); // Beefy Lucid Soul
        percentageInfo.put(2591583, 15.0d); // Swift Lucid Soul
        percentageInfo.put(2591584, 15.0d); // Clever Lucid Soul
        percentageInfo.put(2591585, 15.0d); // Fortuitous Lucid Soul
        percentageInfo.put(2591586, 15.0d); // Flashy Lucid Soul
        percentageInfo.put(2591587, 8.0d); // Potent Lucid Soul
        percentageInfo.put(2591588, 8.0d); // Radiant Lucid Soul
        percentageInfo.put(2591589, 8.0d); // Hearty Lucid Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591590, 5.0d); // Magnificent Lucid Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591590, 1.0d); // Magnificent Lucid Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2437478() { // Papulatus Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591611, 15.0d); // Beefy Papulatus Soul
        percentageInfo.put(2591612, 15.0d); // Swift Papulatus Soul
        percentageInfo.put(2591613, 15.0d); // Clever Papulatus Soul
        percentageInfo.put(2591614, 15.0d); // Fortuitous Papulatus Soul
        percentageInfo.put(2591615, 15.0d); // Flashy Papulatus Soul
        percentageInfo.put(2591616, 8.0d); // Potent Papulatus Soul
        percentageInfo.put(2591617, 8.0d); // Radiant Papulatus Soul
        percentageInfo.put(2591618, 8.0d); // Hearty Papulatus Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591619, 5.0d); // Magnificent Papulatus Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591619, 1.0d); // Magnificent Papulatus Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2438396() { // Will Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591632, 15.0d); // Beefy Will Soul
        percentageInfo.put(2591633, 15.0d); // Swift Will Soul
        percentageInfo.put(2591634, 15.0d); // Clever Will Soul
        percentageInfo.put(2591635, 15.0d); // Fortuitous Will Soul
        percentageInfo.put(2591636, 15.0d); // Flashy Will Soul
        percentageInfo.put(2591637, 8.0d); // Potent Will Soul
        percentageInfo.put(2591638, 8.0d); // Radiant Will Soul
        percentageInfo.put(2591639, 8.0d); // Hearty Will Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591640, 5.0d); // Magnificent Will Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591640, 1.0d); // Magnificent Will Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2439567() { // Verus Hilla Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591651, 15.0d); // Beefy Verus Hilla Soul
        percentageInfo.put(2591652, 15.0d); // Swift Verus Hilla Soul
        percentageInfo.put(2591653, 15.0d); // Clever Verus Hilla Soul
        percentageInfo.put(2591654, 15.0d); // Fortuitous Verus Hilla Soul
        percentageInfo.put(2591655, 15.0d); // Flashy Verus Hilla Soul
        percentageInfo.put(2591656, 8.0d); // Potent Verus Hilla Soul
        percentageInfo.put(2591657, 8.0d); // Radiant Verus Hilla Soul
        percentageInfo.put(2591658, 8.0d); // Hearty Verus Hilla Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591659, 5.0d); // Magnificent Verus Hilla Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591659, 1.0d); // Magnificent Verus Hilla Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void consume_2439568() { // Dunkel Soul Piece
        LinkedHashMap<Integer, Double> percentageInfo = new LinkedHashMap<>();
        percentageInfo.put(2591668, 15.0d); // Beefy Dunkel Soul
        percentageInfo.put(2591669, 15.0d); // Swift Dunkel Soul
        percentageInfo.put(2591670, 15.0d); // Clever Dunkel Soul
        percentageInfo.put(2591671, 15.0d); // Fortuitous Dunkel Soul
        percentageInfo.put(2591672, 15.0d); // Flashy Dunkel Soul
        percentageInfo.put(2591673, 8.0d); // Potent Dunkel Soul
        percentageInfo.put(2591674, 8.0d); // Radiant Dunkel Soul
        percentageInfo.put(2591675, 8.0d); // Hearty Dunkel Soul
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY && SpecialSunday.isActive
                && SpecialSunday.activeSoulGacha) {
            percentageInfo.put(2591676, 5.0d); // Magnificent Dunkel Soul
            change_soul_piece_sunday(percentageInfo);
        } else {
            percentageInfo.put(2591676, 1.0d); // Magnificent Dunkel Soul
            change_soul_piece(percentageInfo);
        }
    }

    public void change_soul_piece(HashMap<Integer, Double> pInfo) {
        StringBuilder sb = new StringBuilder();
        for (Integer pKey : pInfo.keySet()) {
            sb.append("\r\n#i").append(pKey).append(":# #t").append(pKey).append("#(")
                    .append(Math.round(pInfo.get(pKey))).append("%)");
        }
        if (getPlayer().getItemQuantity(itemID, false) < 10) {
            self.say("#b#t" + itemID
                    + "##k \r\nหากรวบรวม #r10 ชิ้น#k, จะสามารถแลกเปลี่ยนเป็นไอเทมตารางด้านล่างได้แบบสุ่ม\r\n\r\n#b#e<โอกาสได้รับ Soul แต่ละชนิด>#n#k#b"
                    + sb.toString());
            return;
        }
        if (1 == self.askAccept("ต้องการใช้ #b#t" + itemID
                + "##k 10 ชิ้น เพื่อแลกเปลี่ยนเป็นไอเทม Soul หรือไม่?\r\n\r\n#b#e<โอกาสได้รับ Soul แต่ละชนิด>#n#k#b"
                + sb.toString())) {
            List<Integer> rKeys = new ArrayList<>(pInfo.keySet());
            Collections.shuffle(rKeys);
            double percent = 0.0;
            for (Integer rKey : rKeys) {
                percent += pInfo.get(rKey);
            }
            double random = percent - (Randomizer.nextDouble() * 100);
            double stack = 0.0d;
            int vou = 0;
            Iterator<Integer> ite = rKeys.iterator();
            while (ite.hasNext() && vou == 0) {
                int a = ite.next();
                Double p = pInfo.get(a);
                stack += p;
                if (stack >= random) {
                    vou = a;
                }
            }
            if (vou != 0) {
                if (target.exchange(itemID, -10, vou, 1) > 0) {
                    self.sayOk("โอ้โห~ ได้ #b#t" + vou + "##k สินะ\r\nจงใช้มันให้เป็นประโยชน์ล่ะ คึคึคึ...");
                } else {
                    self.sayOk("ช่องเก็บของ Use ไม่เพียงพอ กรุณาลองใหม่อีกครั้งหลังจากเคลียร์ช่องว่าง");
                }
            } else {
                self.sayOk("เกิดข้อผิดพลาดชั่วคราว กรุณาลองใหม่อีกครั้งในภายหลัง...");
            }
        } else {
            self.sayOk("ความรอบคอบก็ไม่ใช่เรื่องแย่...\r\nแต่ Soul เป็นไอเทมที่ทรงพลัง ลองไตร่ตรองดูดีๆ ล่ะ...");
        }
    }

    public void change_soul_piece_sunday(HashMap<Integer, Double> pInfo) {
        if (getPlayer().getItemQuantity(itemID, false) < 10) {
            self.say("#b#t" + itemID
                    + "##k \r\nหากรวบรวม #r10 ชิ้น#k, จะสามารถแลกเปลี่ยนเป็นไอเทมตารางด้านล่างได้แบบสุ่ม");
            return;
        }
        if (1 == self.askAccept("#r<Sunday Maple> โอกาสได้รับ Magnificent Soul เพิ่มขึ้น 5 เท่า!#k\r\nต้องการใช้ #b#t"
                + itemID + "##k 10 ชิ้น เพื่อแลกเปลี่ยนเป็นไอเทม Soul หรือไม่?")) {
            List<Integer> rKeys = new ArrayList<>(pInfo.keySet());
            Collections.shuffle(rKeys);
            double percent = 0.0;
            for (Integer rKey : rKeys) {
                percent += pInfo.get(rKey);
            }
            double random = percent - (Randomizer.nextDouble() * 100);
            double stack = 0.0d;
            int vou = 0;
            Iterator<Integer> ite = rKeys.iterator();
            while (ite.hasNext() && vou == 0) {
                int a = ite.next();
                Double p = pInfo.get(a);
                stack += p;
                if (stack >= random) {
                    vou = a;
                }
            }
            if (vou != 0) {
                if (target.exchange(itemID, -10, vou, 1) > 0) {
                    self.sayOk("โอ้โห~ ได้ #b#t" + vou + "##k สินะ\r\nจงใช้มันให้เป็นประโยชน์ล่ะ คึคึคึ...");
                } else {
                    self.sayOk("ช่องเก็บของ Use ไม่เพียงพอ กรุณาลองใหม่อีกครั้งหลังจากเคลียร์ช่องว่าง");
                }
            } else {
                self.sayOk("เกิดข้อผิดพลาดชั่วคราว กรุณาลองใหม่อีกครั้งในภายหลัง...");
            }
        } else {
            self.sayOk("ความรอบคอบก็ไม่ใช่เรื่องแย่...\r\nแต่ Soul เป็นไอเทมที่ทรงพลัง ลองไตร่ตรองดูดีๆ ล่ะ...");
        }
    }

}
