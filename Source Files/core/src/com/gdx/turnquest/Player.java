package com.gdx.turnquest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Player {

    private static String characterClass = "";
    private static int gold;
    private static int exp;
    private static int level;

    public Player(String fileName) {
        try {
            Scanner file = new Scanner(new FileReader("../" + fileName + ".txt"));
            file.nextLine();
            file.nextLine();
            characterClass = file.nextLine();
            gold = Integer.parseInt(file.nextLine());
            exp = Integer.parseInt(file.nextLine());
            level = Integer.parseInt(file.nextLine());
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File not found.");
        }
    }

    public static String getCharacterClass() {
        return characterClass;
    }

    public static void addGold(int g) {
        gold =+ g;
    }

    public static int removeGold(int g) {
        if (gold < g) {
            return -1;
        }
        gold -= g;
        return 0;
        // 0 means success, -1 means not enough gold.
    }

    public static int getGold() {
        return gold;
    }

    public static void addExp(int e) {
        exp += e;
    }

    public static int getExp() {
        return exp;
    }

    public static void increaseLevel(int l) {level += l;}

    public static int getLevel() {return level;}
}
