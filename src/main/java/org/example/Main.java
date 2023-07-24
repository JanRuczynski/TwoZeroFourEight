package org.example;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
//        Game game = new Game(3);
//        game.gameField = new int[][]{{1,22,333}, {22,22,22}, {333,333,333}};
//        game.hmtlize();
        rotate(new int[][]{{1,2,3}, {4,5,6}, {7,8,9}});

    }

    public static void rotate(int[][] array) {
        int dimension = array.length;
        int[][] rotatedField = new int[dimension][dimension];
        for (int h = 0; h < dimension; h++) {
            for (int w = 0; w < dimension; w++) {
                rotatedField[dimension-w-1][h] = array[h][w];
            }
        }
        System.out.println(Arrays.deepToString(rotatedField));
    }
}