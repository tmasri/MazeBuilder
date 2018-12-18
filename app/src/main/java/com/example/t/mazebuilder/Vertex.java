package com.example.t.mazebuilder;

public class Vertex {

    private int xCoordinate;
    private int yCoordinate;
    private int position;

    public Vertex(int x, int y) {

        xCoordinate = x;
        yCoordinate = y;
        position = -1;

    }

    public int position(int s) {
        if (position == -1)
            position = (yCoordinate*s)+xCoordinate;

        return position;
    }

    public int getX(){
        return xCoordinate;
    }

    public int getY() {
        return yCoordinate;
    }

    // Check if the 2 vertices are equal
    public boolean equals(Vertex v) {
        return xCoordinate == v.getX() && yCoordinate == v.getY();
    }

}
