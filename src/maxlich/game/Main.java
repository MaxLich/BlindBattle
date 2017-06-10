package maxlich.game;

import maxlich.game.activeobjects.Enemy;
import maxlich.game.activeobjects.Player;
import maxlich.game.score.Score;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    public static final String[] COMPLEXITY = {"легко", "средне", "сложно"}; //сложность игры
    public static int curComplexity = 0; //текущая сложность игры

    public static void main(String[] args) throws IOException {
        //Score.createTopListFile();
        //Score.printTopList();
        //Score scr1 = new Score();
        //scr1.isPlrEnteredInTopList(1);

        while (true) { // "проигрывание" ("toPlay()") игры
            System.out.println("Игра \"Слепой бой\"");
            Menu menuGame = new Menu("Начать игру","Топ-лист игроков", "Выход");
            menuGame.printMenu();

            int menuItem = menuGame.getUserInput();
            if (menuItem == 3) System.exit(0);
            else if (menuItem == 2) {
                //Score.printTopList();
                continue;
            }
            else if (menuItem == 1) { // если выбран пункт "начать игру"

                printRules(); //показать правила игры

                Score scr = new Score();
                Player player = new Player();

                //int HPEnemy = 10;
                //double multHPEnemy = 1.5;
                for (int iLevel = 1; iLevel <= 10; iLevel++) {
                    System.out.println("Этап 1, уровень " + iLevel);
                    System.out.println();

                    scr.clearAllIndicators();

                    if (iLevel < 6) {
                        player.setCurrentHealth(30);
                    }
                    else if (iLevel == 6) {
                        player.setMaxHealth(40);
                        player.setCurrentHealth(40);
                        player.setMinDamage(3);
                        player.setMaxDamage(8);
                    }
                    else if (iLevel > 6) player.setCurrentHealth(40);

                    Enemy enemy = new Enemy(10 * iLevel, 1 * iLevel, 3 + 2 * (iLevel - 1), 1);
                    player.printCharacteristics();
                    enemy.printCharacteristics();
                    System.out.println();

                    System.out.println("Всё, в бой!");

                    System.out.println("Начальное местоположение игрока: " + player.getRandomLocation());
                    enemy.getRandomLocation();

                    boolean isWin = false;

                    while (true) { //цикл боя с противником
                        System.out.println("\nВаш ход.");

                        //System.out.println("loc enemy:" + enemy.getCurrentLocation());

                        boolean isAttackOrMovePlayer = player.attackOrMove(); //выбор игрока: атаковать или передвигаться

                        if (isAttackOrMovePlayer) { //если игрок выбирает атаковать

                            enemy.setPlayerWasMoving(false); //враг запоминает информацию о том, что игрок не передвигался
                            scr.incrNumOfPlrAttcks(); //счётчик атак игрока для подсчёта очков
                            int curPlDmg = player.attack(enemy); //атака игрока по врагу
                            if (curPlDmg > 0)  {
                                scr.incrNumOfHittingTheEnemy();
                                scr.increaseTotalDamagePlrForLvl(curPlDmg);
                            }
                            if (enemy.getCurrentHeath() <= 0) {
                                System.out.println("Вы убили врага!");
                                System.out.println("Вы победили!");
                                isWin = true;
                                scr.incrNumfTurns();
                                break;
                            }

                        } else { //игрок выбрал передвижение
                            scr.incrNumOfPlrMovements();
                            System.out.println("Выберете, куда будете двигаться: \"-\" - влево, \"+\" - вправо.");
                            int nextLocPlayer = player.move();
                            enemy.setPlayerWasMoving(true);
                            System.out.println("Теперь Ваше текущее местоположение: " + nextLocPlayer);
                        }

                        System.out.println("\nХод противника");

                        boolean isAttackOrMoveEnemy = enemy.attackOrMove();

                        if (isAttackOrMoveEnemy) {
                            System.out.println("Противник решил атаковать Вас.");

                            int curEnmyDmg = enemy.attack(player);
                            if (curEnmyDmg > 0)  {
                                scr.increaseLostHPPlrForLvl(curEnmyDmg);
                                scr.incrNumOfHittingThePlayer();
                            }


                            if (player.getCurrentHeath() <= 0) {
                                System.out.println("Вы убиты своим врагом!");
                                System.out.println("Вы проиграли!");
                                isWin = false;
                                scr.incrNumfTurns();
                                break;
                            }


                        } else {
                            System.out.println("Противник решил передвинуться.");
                            enemy.move();
                            System.out.println("Враг передвинулся на энную позицию.");
                        }
                        scr.incrNumfTurns();
                    }
                    if (isWin) { //если игрок выиграл уровень
                        System.out.println("Уровень " + iLevel + " пройден.");
                        scr.calculateTotalScore(iLevel);
                        scr.printAllIndicators();
                        scr.printTotalScore();
                        System.out.println();
                        System.out.print("Перейти на следующий уровень? (д/н): ");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        //boolean isCorrectInput = false;
                        String answerOfUser = "";
                        while (true) { //запрашивать у пользователя ввод, пока он не введёт нужный ответ
                            answerOfUser = reader.readLine().toLowerCase();
                            if ("д".equals(answerOfUser) || "н".equals(answerOfUser)) //если корректный ввод, то...
                                break; //то выходим из цикла
                        }
                        if ("н".equals(answerOfUser)) break;
                    }
                    else { //иначе, если игрок проиграл
                        System.out.println("Игра окончена.");
                        scr.calculateTotalScore(iLevel);
                        scr.printAllIndicators();
                        scr.printTotalScore();
                        System.out.println();
                        System.out.print("Хотите попробовать сыграть ещё? (д/н): ");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        //boolean isCorrectInput = false;
                        String answerOfUser = "";
                        while (true) { //цикл ввода пользователем ответа, ожидается нужный ответ (тогда выход из цикла)
                            answerOfUser = reader.readLine().toLowerCase();
                            if ("д".equals(answerOfUser) || "н".equals(answerOfUser)) //если пользователь ввёл то, что нужно
                                break; //выходим из цикла
                        }
                        if ("д".equals(answerOfUser)) break; //выход из цикла боя с противником, начало нового уровня
                        else if ("н".equals(answerOfUser)) System.exit(0); //выход из программы
                    }
                    System.out.println("\n");
                }
            }
        }

    }

    private static void printRules() {
        System.out.println("Правила игры:");
        System.out.println("На поле боя есть игрок и компьютер. Поле боя - линейно. ");
        System.out.println("Каждый из бойцов может двигаться вдоль одной линии: влево или вправо. ");
        System.out.println("Сами линии, по которым двигаются бойцы - параллельны друг другу.");
        System.out.println("Ход игрока или компьютера начинается с выбора: атаковать или двигаться.");
        System.out.println("Если игрок или компьютер выбирает атаковать, то дальше");
        System.out.println("происходит угадывание местоположения противника и последующее нанесение ему урону");
        System.out.println("(в случае попадания). Урон наносится случайный (из некоторого диапозона).");
        System.out.println("Если игрок или компьютер выбирает движение, то дальше происходит движение влево или вправо.");
        System.out.println("Первым ходит игрок. Игра состоит из нескольких этапов и нескольких уровней. В каждом этапе 10 уровней.");
        System.out.println("Каждый этап игры отличается интеллектом противника. Каждый уровень этапа отличается очками здоровья и наносимым уровном противника.");
        System.out.println("Игра продолжается до тех пор, пока у одного из бойцов не кончатся очки здоровья.");
        System.out.println();
    }
}


