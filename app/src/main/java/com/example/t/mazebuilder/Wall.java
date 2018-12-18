package com.example.t.mazebuilder;

public class Wall {

    private Vertex v1;
    private Vertex v2;

    public Wall(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }

    // Check if the 2 walls are equal
    public boolean equals(Wall w) {

        return (v1.equals(w.getV1()) && v2.equals(w.getV2())) ||
                (v1.equals(w.getV2()) && v2.equals(w.getV1()));
    }

}
