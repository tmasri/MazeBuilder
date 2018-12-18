package com.example.t.mazebuilder;

public class Path {

    private boolean topWall, bottomWall, leftWall, rightWall;
    private int position;

    public Path(int p) {

        position = p;

    }

    public void setWalls(boolean t, boolean b, boolean l, boolean r) {

        topWall = t;
        bottomWall = b;
        leftWall = l;
        rightWall = r;

    }

    public boolean[] getWalls() {
        return new boolean[]{
                topWall, bottomWall, leftWall, rightWall
        };
    }

    public int getPosition() {
        return position;
    }

    public boolean hasRightWall() {
        return rightWall;
    }

    public boolean hasLeftWall() {
        return leftWall;
    }

    public boolean hasTopWall() {
        return topWall;
    }

    public boolean hasBottomWall() {
        return bottomWall;
    }

    public static int[] getIJ(int x, int mazeSize) {
        int i,j;

        j = x % mazeSize;
        i = (x - j)/mazeSize;

        return new int[]{i,j};
    }

}
