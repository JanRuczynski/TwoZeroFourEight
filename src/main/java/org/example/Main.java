package org.example;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
//        Game game = new Game(3);
//        game.gameField = new int[][]{{1,22,333}, {22,22,22}, {333,333,333}};
//        game.hmtlize();
        int[][] array = new int[][]{{2,2,0,0}, {2,2,0,0}, {2,2,0,0}, {2,2,0,0}};
        System.out.println(Arrays.deepToString(move(array, 's')));
    }

    public static int[][] rotate(int[][] initialArray, char direction) {
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

    public static int[][] move(int[][] array, char direction) {
        int[][] rotatedField;
        rotatedField = rotate(array, direction);
        for (int i = 0; i <= 1; i++) {
            for (int h = 0; h < array.length - 1; h++) {
                for (int w = 0; w < array.length; w++) {
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
        return rotatedField;
    }
}