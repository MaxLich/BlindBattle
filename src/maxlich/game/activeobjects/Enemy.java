package maxlich.game.activeobjects;

import maxlich.game.activeobjects.ActiveObject;

/**
 * Created by Максим on 23.03.2017.
 */
public class Enemy extends ActiveObject { //враг (компьютер)
    private int[] locationsOfPlayer = {-1, -1}; //список позиций игрока, который угадал враг: 0-ой элемент - предпоследнаяя угаданная позиция, 1-ый элемент - последняя угаданная
    private boolean playerWasMoving = false; //отметка о том, двигался ли игрок
    private int numOfMisses = 0;
    private int level = 1;

    public Enemy() {
        super();
    }

    public Enemy(int hp, int minDamage, int maxDamage, int lvl) {
        super(hp, minDamage, maxDamage);
        this.level = lvl;
    }

    @Override
    public String toString() {
        return "Враг:\n" + super.toString();
    }

    public void addLocationOfPlayer(int locationOfPlayer) {
        this.locationsOfPlayer[0] = this.locationsOfPlayer[1];
        this.locationsOfPlayer[1] = locationOfPlayer;
    }

    public int getLastLocationOfPlayer() {
        return locationsOfPlayer[1];
    }

    public int getPenaultLocationOfPlayer() {
        return locationsOfPlayer[0];
    }


    public void setPlayerWasMoving(boolean playerWasMoving) {
        this.playerWasMoving = playerWasMoving;
    }

    public boolean isPlayerWasMoving() {
        return playerWasMoving;
    }


    public void setNumOfMisses(int numOfMisses) {
        this.numOfMisses = numOfMisses;
    }

    public int incrNumOfMisses() {
        this.numOfMisses++;
        return this.numOfMisses;
    }

    public int getNumOfMisses() {
        return numOfMisses;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean attackOrMove() {
        //добавить: если игрок атаковал, то бежать
        //если было удачное попадание по игроку, то атаковать
        switch (level) {

            case 1:
                int choice = (int) (Math.random() * 2);
                if (choice == 1) return true;
                else return false;
            case 2:
                return true;
            case 3:
                return true;

            default:
                return true;
        }
    }

    public int guessLocationOfPlayer() { //попытка угадать позицию игрока, возращает предполагаемую позицию
        //используется предыдущая угаданная позиция
        //int mult = MAXLOCATION; //множителя для случайных чисел
        int loc = 3; //предполагаемая позиция игрока
        switch (level) {
            case 1:
                if (locationsOfPlayer[1] >= 0 && numOfMisses == 0) loc = locationsOfPlayer[1];
                else loc = (int) (Math.random() * (MAXLOCATION + 1));
                return loc;
            case 2:
                if (locationsOfPlayer[1] >= 0) { //если угадана позиция игрока
                    if (playerWasMoving) {  //и если игрок двигался после этого
                        if (locationsOfPlayer[1] == 0) { //если позиция была нулевая
                            loc = locationsOfPlayer[1] + 1; //то игрок мог передвинуться только на первую
                        } else if (locationsOfPlayer[1] == MAXLOCATION) { //если позиция конечная
                            loc = locationsOfPlayer[1] - 1; //то игрок мог передвинуться только влево
                        } else { //если была угадана позиция игрока, он передвинулся и находился ни у одного из краёв линии передвижения
                            loc = (((int) (Math.random() * 2)) == 1) ? (locationsOfPlayer[1] + 1) : (locationsOfPlayer[1] - 1); //две позиции: позиция слева и позиция справа от текущей
                        }
                    } else { // если игрок не двигался
                        if (locationsOfPlayer[1] >= 0 && numOfMisses == 1) {
                            //if ()

                        } else {
                            loc = locationsOfPlayer[1];
                        }
                    }
                } else { //если позиция не была угадана, то ищём на линии передвижения случайным образом
                    loc = (int) (Math.random() * (MAXLOCATION + 1));
                }
                System.out.println(loc);
                return loc;
            case 3:
                return loc;
            default:
                return loc;
        }

    }

    public int attack(ActiveObject plr) {
        System.out.print("Противник думает, что Ваше местоположение: ");
        int locPlayer = this.guessLocationOfPlayer();
        System.out.println(locPlayer);
        boolean hittingThePlayer = plr.checkLocation(locPlayer);
        System.out.println();
        if (hittingThePlayer) { //если враг попал по игроку (угадал позицию игрока)
            this.addLocationOfPlayer(locPlayer);
            this.setNumOfMisses(0);
            System.out.print("Враг угадал Ваше местоположение, атаковал Вас");
            int curDamage = this.damage(plr);
            System.out.println(" и нанёс Вам урон " + curDamage  + " ед.");
            System.out.print("Ваши очки здоровья после атаки врага: ");
            plr.printHealth();
            return curDamage;
        } else { //если враг проманулся
            System.out.println("Враг промахнулся!");
            this.incrNumOfMisses(); //увеличиваем количество промахов на 1

            if (this.getNumOfMisses() >= 2) this.addLocationOfPlayer(-1);
            //if (enemy.getLocationOfPlayer() >= 0) enemy.setLocationOfPlayer();
            return 0;
        }
    }

    public int move() {
        switch (level) {
            case 1:
                int deltaNum = (int) (Math.random() * 2);
                String delta = ((deltaNum == 1) ? "+" : "-");
                return motion(delta);
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                return 3;
        }

    }
}
