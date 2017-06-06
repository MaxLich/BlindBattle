package maxlich.game.activeobjects;

import maxlich.game.activeobjects.ActiveObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Максим on 23.03.2017.
 */
public class Player extends ActiveObject { //игрок (человек)


    @Override
    public String toString() {
        return "Игрок: \n" + super.toString();
    }

    public boolean attackOrMove() { //true - атаковать, false - двигаться
        System.out.println("Что будете делать: атаковать (а) или передвигаться (п)? ");
        String answerOfUser = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) { //цикл нужен для фильтра ввода пользователем ответа с клавиатуры
                answerOfUser = reader.readLine().toLowerCase();
                if ("а".equals(answerOfUser)) return true;
                else if ("п".equals(answerOfUser)) return false;
            }

        } catch (IOException e) {
            System.out.println(e);
            return true;
        }
    }

    public int attack(ActiveObject enmy) {
        System.out.print("Введите предположительное местоположение противника (от 0 до " + ActiveObject.MAXLOCATION + "): ");
        boolean hittingTheEnemy = enmy.checkLocation(this.getInputLocationOfEnemy());
        System.out.println();

        if (hittingTheEnemy) {
            System.out.print("Вы попали по врагу");
            int curDamage = this.damage(enmy);
            System.out.println(" и нанесли ему урон " + curDamage  + " ед.");
            System.out.print("Очки здоровья противника после Вашей атаки: ");
            enmy.printHealth();
            return curDamage;
        } else {
            System.out.println("Промах!");
            return 0;
        }
    }

    public int getInputLocationOfEnemy() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean isCorrectInput = false;
        int locInput = -1;
        while (!isCorrectInput) {
            try {
                locInput = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                isCorrectInput = false;
                continue;
            } catch (Exception e) {
                isCorrectInput = false;
                continue;
            }
            //locInput = Integer.parseInt(reader.readLine());
            if (locInput >= 0 && locInput <= 5)
                isCorrectInput = true;
        }
        return locInput;

    }

    public int move() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean isCorrectInput = false;
        String delta = "";
        while (!isCorrectInput) {
            delta = reader.readLine();
            if (delta.equals("+") || delta.equals("-"))
                isCorrectInput = true;
        }
        return motion(delta);
    }
}
