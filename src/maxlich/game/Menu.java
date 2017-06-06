package maxlich.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Максим on 23.03.2017.
 */
public class Menu { //меню игры
    private ArrayList<String> menu;

    public Menu() {
        this.menu = new ArrayList<>();
    }

    public Menu(String... menuItem) {
        this.menu = new ArrayList<>();
        for (String mi : menuItem) {
            this.menu.add(mi);
        }
    }

    public void setMenu(String... menuItem) {
        for (String mi : menuItem) {
            this.menu.add(mi);
        }
    }

    public void printMenu() {
        int menuSize = menu.size();
        for (int i = 0; i < menuSize; i++) {
            System.out.println((i + 1) + ") " + this.menu.get(i));
        }

    }

    public int getUserInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //Scanner sc = new Scanner(System.in);

        System.out.print("\nВыберете пункт меню: ");

        boolean isCorrectInput = false;
        int menuItemInput = -1;
        int menuSize = menu.size();
        while (!isCorrectInput) {
            try {
                menuItemInput = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                isCorrectInput = false;
                continue;
            } catch (Exception e) {
                isCorrectInput = false;
                continue;
            }
            //if (sc.hasNextInt()) menuItemInput = sc.nextInt();
            if (menuItemInput >= 1 && menuItemInput <= menuSize)
                isCorrectInput = true;
        }

        return menuItemInput;
    }
}