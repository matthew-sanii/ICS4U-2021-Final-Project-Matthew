import java.util.Scanner;
import java.util.InputMismatchException;

public class Player extends Character {

  /**
  * The starting hp value.
  */
  private int hp;

  /**
  * The starting mp value.
  */
  private int mp;

  /**
  * The starting str (strength) value.
  */
  private int str;

  /**
  * The starting intel (intelligence) value.
  */
  private int intel;

  /**
  * The starting def (defence) value.
  */
  private int def;

  /**
  * The starting mdf (magic defence) value.
  */
  private int mdf;

  /**
  * The starting level value.
  */
  private int lvl;

  /**
  * The base hp value.
  */
  private final int startingHp = 30;

  /**
  * The base mp value.
  */
  private final int startingMp = 6;

  /**
  * The base str value.
  */
  private final int startingStr = 5;

  /**
  * The base intel value.
  */
  private final int startingIntel = 4;

  /**
  * The mp value used for skills.
  */
  private int currentMp;

  /**
  * The no arguements player constructor.
  */
  public Player() {
    lvl = 1;
    mdf = 2;
    def = 2;
    intel = startingIntel;
    str = startingStr;
    mp = startingMp;
    currentMp = startingMp;
    hp = startingHp;
  }

  /**
  * The value used every time a 1 is needed.
  */
  private final int choiceA = 1;

  /**
  * The value used every time a 2 is needed.
  */
  private final int choiceB = 2;

  /**
  * The value used every time a 3 is needed.
  */
  private final int choiceC = 3;

  /**
  * The value used every time a 4 is needed.
  */
  private final int choiceD = 4;

  /**
  * The cost to use skills.
  */
  private final int spellCost = 2;

  /**
  * The temporary defence value.
  */
  private int tempDef = 0;

  /**
  * The value that hp increases by.
  */
  private final int hpUp = 5;

  /**
  * The actions method, used to show the basic actions.
  */
  public void actions() {
    System.out.println("Attack(1)");
    System.out.println("Skills(2)");
    System.out.println("Defend(3)");
  }

  /**
  * The skills menu, showing the starting class' skills.
  */
  public void skills() {
    System.out.println("\nSkills:");
    System.out.println("Fireball(1): 2Mp");
    System.out.println("Zap(2): 2Mp");
    System.out.println("Frostblast(3): 2Mp");
    System.out.println("Back(4)");
  }

  /**
  * The playerAttack method, used for when it's.
  * your turn using the basic class.
  *
  * @param eDef the enemy defence value.
  * @param eMdf the enemy magic defence value.
  * @param type the enemy type.
  *
  * @return damage.
  */
  public int playerAttack(final int eDef, final int eMdf,
    final String type) {
    tempDef = 0;
    int action = 0;
    int skillAction = 0;
    int damage = 0;
    int act = 0;
    final Scanner userInput = new Scanner(System.in);
    while (act == 0) {
      try {
        actions();
        action = userInput.nextInt();
        if (action == choiceA) {
          damage = attack(eDef);
          act += 1;
          attackDamage(damage);
        } else if (action == choiceB) {
          skills();
          skillAction = userInput.nextInt();
          if (currentMp >= spellCost) {
            if (skillAction == choiceA) {
              damage = fireball(eMdf, type);
              act += 1;
              currentMp -= spellCost;
              attackDamage(damage);
            } else if (skillAction == choiceB) {
              damage = zap(eMdf, type);
              act += 1;
              currentMp -= spellCost;
              attackDamage(damage);
            } else if (skillAction == choiceC) {
              damage = frostblast(eMdf, type);
              act += 1;
              currentMp -= spellCost;
              attackDamage(damage);
            } else if (skillAction == choiceD) {
              damage = playerAttack(eDef, eMdf, type);
            }
          } else {
            invalidMp();
          }
        } else if (action == choiceC) {
          tempDef += choiceC;
          act += 1;
        } else {
          System.out.println("That isn't a viable input.");
        }
      } catch (InputMismatchException errorCode) {
        System.out.println("That is not a viable input.");
      }
    }
    return damage;
  }

  /**
  * The showHp method.
  *
  * @param enemyName the enemy name
  * @param enemyHp the enemy hp value.
  * @param playerHp the player's hp
  */
  public void showHp(final String enemyName, final int enemyHp,
    final int playerHp) {
    final int showMp = currentMp;
    System.out.println(enemyName + " hp: " + enemyHp);
    System.out.println("\nPlayer hp: " + playerHp);
    System.out.println("Player mp: " + showMp);
  }

  /**
  * The attackDamage method tells you how much damage you dealt.
  *
  * @param damage the damage amount.
  */
  public void attackDamage(final int damage) {
    System.out.println("You attacked for " + damage + " damage!");
  }

  /**
  * The invalidMp method tells you when you don't have enough Mp.
  */
  public void invalidMp() {
    System.out.println("Not enough Mp!");
  }

  /**
  * The attack method.
  *
  * @param eDef the enemy defence value.
  *
  * @return damage the damage dealt.
  */
  public int attack(final int eDef) {
    final int damage = super.attack(str, eDef) + lvl;
    return damage;
  }

  /**
  * The fireball method.
  *
  * @param eMdf the enemy magic defence value.
  * @param type the enemy's elemental type.
  *
  * @return fireDmg the damage dealt.
  */
  public int fireball(final int eMdf, final String type) {
    int fireDmg = super.fireball(intel, eMdf);
    if (type.equals("ice")) {
      fireDmg += choiceC;
    } else if (type.equals("lightning")) {
      fireDmg -= 2;
    }
    return fireDmg;
  }

  /**
  * The zap method.
  *
  * @param eMdf the enemy magic defence value.
  * @param type the enemy's elemental type.
  *
  * @return zapDmg the damage dealt.
  */
  public int zap(final int eMdf, final String type) {
    int zapDmg = super.zap(intel, eMdf);
    if (type.equals("fire")) {
      zapDmg = zapDmg * choiceC;
    } else if (type.equals("ice")) {
      zapDmg -= 2;
    }
    return zapDmg;
  }

  /**
  * The frostblast method.
  *
  * @param eMdf the enemy magic defence value.
  * @param type the enemy's elemental type.
  *
  * @return frostDmg the damage dealt.
  */
  public int frostblast(final int eMdf, final String type) {
    int frostDmg = super.frostblast(intel, eMdf);
    if (type.equals("lightning")) {
      frostDmg += choiceC;
    } else if (type.equals("fire")) {
      frostDmg -= 2;
    }
    return frostDmg;
  }

  /**
  * The getDef method.
  *
  * @return def
  */
  public int getDef() {
    return (def + tempDef);
  }

  /**
  * The getMdf method.
  *
  * @return mdf
  */
  public int getMdf() {
    return mdf;
  }

  /**
  * The getHp method.
  *
  * @return hp
  */
  public int getHp() {
    return hp;
  }

  /**
  * The getMp method.
  *
  * @return currentMp
  */
  public int getMp() {
    return currentMp;
  }

  /**
  * The levelup method.
  */
  public void levelUp() {
    lvl += 1;
    mp += 1;
    str += 2;
    intel += 1;
    def += 1;
    mdf += 1;
    hp += hpUp;
    currentMp = mp;
  }
}
