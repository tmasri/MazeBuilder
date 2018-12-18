package com.example.t.mazebuilder;

public class Vertex {

    private int xCoordinate;
    private int yCoordinate;

    public Vertex(int x, int y) {

        xCoordinate = x;
        yCoordinate = y;

    }

    public int position(int s) {
        return (yCoordinate*s)+xCoordinate;
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
