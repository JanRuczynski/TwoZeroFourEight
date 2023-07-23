package org.example;

public class Game {
    private int dimension;

    public Game(int dimension) {
        this.dimension = dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getDimension() {
        return dimension;
    }

    public void hmtlize() {
        String[] output = new String[getDimension()*4+1];
        output[0] = "+";
        for (int w = 0; w < getDimension(); w++) {
            output[0] = output[0].concat("-------+");
        }
        for (int h = 0; h < getDimension(); h++) {
            output[h*4+1] = "|";
            output[h*4+2] = "|";
            output[h*4+3] = "|";
            output[(h+1)*4] = "+";
            for (int w = 0; w < getDimension(); w++) {
                output[h*4+1] = output[h*4+1].concat("       |");
                output[h*4+2] = output[h*4+2].concat(insertNumber(h,w)).concat("|");
                output[h*4+3] = output[h*4+3].concat("       |");
                output[(h+1)*4] = output[(h+1)*4].concat("-------+");
            }
        }
        for (int h = 0; h < getDimension()*4+1; h++) {
            System.out.println(output[h]);
        }
    }

    private String insertNumber(int h, int w) {
        return " teest ";
    }
}
