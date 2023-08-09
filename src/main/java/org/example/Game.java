package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Game {
    private int dimension;
    private int[][] gameField;
    private boolean gameOver;
    int filledSquares = 0;
    int score = 0;
    final int WINNING_NUMBER = 2048;
    public Game() throws IOException {
        play();
    }

    public void play() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("###   Please enter game dimension (between 2 and 8)   ###");
        dimension = Integer.parseInt(reader.readLine());
        initialize();
        addNewNumber();
        while (!gameOver) {
            if (filledSquares == dimension * dimension) {
                gameOver();
            }
            hmtlize();
            char direction = 0;
            String input;
            do {
                System.out.println("###   Please enter direction (w, s, a or d)   ###");
                input = reader.readLine();

                if (!input.isEmpty()) {
                    direction = input.charAt(0);
                    if (direction == 'w' || direction == 's' || direction == 'a' || direction == 'd') {
                        break;
                    } else {
                        System.out.println("###   wrong character entered   ###");
                    }
                } else {
                    System.out.println("###   please enter w, s, a or d   ###");
                }
            } while (true);
            move(direction);
            addNewNumber();
        }
    }

    private void gameOver() {
        gameOver = true;
        System.out.println("\n###   GAME OVER!   ###\n");
    }

    private void move(char direction) {
        int[][] rotatedField;
        char invertedDirection = 0;
        rotatedField = rotate(gameField, direction);
        for (int i = 0; i <= 1; i++) {
            for (int h = 0; h < gameField.length - 1; h++) {
                for (int w = 0; w < gameField.length; w++) {
                    if (rotatedField[h][w] == rotatedField[h+1][w] && i == 0 && rotatedField[h][w] != 0) {
                        rotatedField[h][w] = 0;
                        rotatedField[h+1][w]*=2;
                        score += rotatedField[h+1][w];
                        filledSquares--;
                        if (rotatedField[h+1][w] == WINNING_NUMBER) {
                            win();
                        }
                    } else if (rotatedField[h+1][w] == 0) {
                        rotatedField[h+1][w] = rotatedField[h][w];
                        rotatedField[h][w] = 0;
                    }
                }
            }
        }
        switch (direction) {
            case 'w' -> invertedDirection = 'w';
            case 's' -> invertedDirection = 's';
            case 'a' -> invertedDirection = 'd';
            case 'd' -> invertedDirection = 'a';
        }

        gameField = rotate(rotatedField, invertedDirection);
    }

    private void win() {
        System.out.println("\n###   YOU WON!   ###\n");
    }

    private void addNewNumber() {
        Random random = new Random();
        int addedNumber = 2;
        boolean isSet = false;
        if (random.nextInt(8) == 4) {
            addedNumber = 4;
        }
        while (!isSet) {
            int randX = random.nextInt(1, dimension + 1) - 1;
            int randY = random.nextInt(1, dimension + 1) - 1;
            if (gameField[randY][randX] == 0) {
                gameField[randY][randX] = addedNumber;
                filledSquares++;
                isSet = true;
            }
        }
    }

    private void initialize() {
        gameField = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                gameField[i][j] = 0;
            }
        }
        gameOver = false;
    }

    private void hmtlize() {
        System.out.printf("---   score: %d   ---%n", score);
        String[] output = new String[dimension*4+1];
        output[0] = "+";
        for (int w = 0; w < dimension; w++) {
            output[0] = output[0].concat("-------+");
        }
        for (int h = 0; h < dimension; h++) {
            output[h*4+1] = "|";
            output[h*4+2] = "|";
            output[h*4+3] = "|";
            output[(h+1)*4] = "+";
            for (int w = 0; w < dimension; w++) {
                output[h*4+1] = output[h*4+1].concat("       |");
                output[h*4+2] = output[h*4+2].concat(insertNumber(h,w)).concat("|");
                output[h*4+3] = output[h*4+3].concat("       |");
                output[(h+1)*4] = output[(h+1)*4].concat("-------+");
            }
        }
        for (int h = 0; h < dimension*4+1; h++) {
            System.out.println(output[h]);
        }
    }

    private String insertNumber(int h, int w) {
        int number = gameField[h][w];
        int numberLength = String.valueOf(number).length();
        return switch (numberLength) {
            case 1 -> number == 0 ? "       " : String.format("   %d   ", number);
            case 2 -> String.format("  %d   ", number);
            case 3 -> String.format("  %d  ", number);
            case 4 -> String.format(" %d  ", number);
            default -> "";
        };
    }

    private int[][] rotate(int[][] initialArray, char direction) {
        int[][] rotatedField = new int[initialArray.length][initialArray.length];
        switch (direction) {
            case 'w' :
                for (int h = 0; h < initialArray.length; h++) {
                    for (int w = 0; w < initialArray.length; w++) {
                        rotatedField[initialArray.length - h - 1][initialArray.length - w - 1] = initialArray[h][w];
                    }
                }
                break;
            case 'd' :
                for (int h = 0; h < initialArray.length; h++) {
                    for (int w = 0; w < initialArray.length; w++) {
                        rotatedField[w][initialArray.length-h-1] = initialArray[h][w];
                    }
                }
                break;
            case 's' :
                for (int h = 0; h < initialArray.length; h++) {
                    System.arraycopy(initialArray[h], 0, rotatedField[h], 0, initialArray.length);
                }
                break;
            case 'a' :
                for (int h = 0; h < initialArray.length; h++) {
                    for (int w = 0; w < initialArray.length; w++) {
                        rotatedField[initialArray.length-w-1][h] = initialArray[h][w];
                    }
                }
                break;
        }
        return rotatedField;
    }
}
