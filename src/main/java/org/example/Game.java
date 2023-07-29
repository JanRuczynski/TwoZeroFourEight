package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Game {
    private int dimension;
    private int[][] gameField;
    private boolean gameOver;

    public Game(int dimension) throws IOException {
        this.dimension = dimension;
        play();
    }

    public void play() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("###   Please enter game dimension (between 2 and 8)   ###");
        dimension = Integer.parseInt(reader.readLine());
        initialize();
        while (!gameOver) {
            addNewNumber();
            hmtlize();
            System.out.println("###   Please enter direction   ###");
            char direction = reader.readLine().charAt(0);
            move(direction);
        }
    }

    private void move(char direction) {
        int[][] rotatedField;
        char invertedDirection = 0;
        rotatedField = rotate(gameField, direction);
        for (int i = 0; i <= 1; i++) {
            for (int h = 0; h < gameField.length - 1; h++) {
                for (int w = 0; w < gameField.length; w++) {
                    if (rotatedField[h][w] == rotatedField[h+1][w] && i == 0) {
                        rotatedField[h][w] = 0;
                        rotatedField[h+1][w]*=2;
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

    private void addNewNumber() {
        Random random = new Random();
        int addedNumber = 2;
        boolean isSet = false;
        if (random.nextInt(8) == 4) {
            addedNumber = 4;
        }
        while (!isSet) {
            int randX = random.nextInt(1, dimension) - 1;
            int randY = random.nextInt(1, dimension) - 1;
            if (gameField[randY][randX] == 0) {
                gameField[randY][randX] = addedNumber;
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
            case 1 -> String.format("   %d   ", number);
            case 2 -> String.format("  %d   ", number);
            case 3 -> String.format("  %d  ", number);
            case 4 -> String.format(" %d  ", number);
            default -> "";
        };
    }

    private int[][] rotate(int[][] initialArray, char direction) {
        int[][] rotatedField = new int[dimension][dimension];
        switch (direction) {
            case 'w' :
                for (int h = 0; h < dimension; h++) {
                    for (int w = 0; w < dimension; w++) {
                        rotatedField[dimension - h - 1][dimension - w - 1] = gameField[h][w];
                    }
                }
                break;
            case 'd' :
                for (int h = 0; h < dimension; h++) {
                    for (int w = 0; w < dimension; w++) {
                        rotatedField[w][dimension-h-1] = gameField[h][w];
                    }
                }
                break;
            case 's' :
                for (int h = 0; h < dimension; h++) {
                    System.arraycopy(gameField[h], 0, rotatedField[h], 0, dimension);
                }
                break;
            case 'a' :
                for (int h = 0; h < dimension; h++) {
                    for (int w = 0; w < dimension; w++) {
                        rotatedField[dimension-w-1][h] = gameField[h][w];
                    }
                }
                break;
        }
        return rotatedField;
    }
}
