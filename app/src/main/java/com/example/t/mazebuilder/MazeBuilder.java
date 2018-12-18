package com.example.t.mazebuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MazeBuilder {

    private int size;
    private ArrayList<Wall> walls;

    private Random r;

    /*
     * MazeBuilder will handle all the necessary parts
     * needed to build a maze, will call buildWalls and
     * buildMaze to build the maze
     *
     * @param s the dimentions of the maze
     */
    public MazeBuilder(int s) {

        r = new Random();
        size = s;

        buildWalls();
        buildMaze();

    }

    /*
     * Uses DisjointSet class to for help, everytime ds
     * performes a union, buildMaze will break the wall
     * between the numbers randomly chosen from random
     * generator
     */
    private void buildMaze() {

        DisjointSet ds = new DisjointSet(size*size);

        int bound = size*size;
        int x, y, u, v;
        while (ds.count() != 1) {

            x = r.nextInt(bound);
            y = getYPossible(x, bound);

            // find the set number of the values chosen
            u = ds.find(x);
            v = ds.find(y);

            // if they are not of the same set, break wall
            // and unionize sets
            if (u != v) {
                breakWall(x, y);
                ds.union(u, v);
            }

        }

    }

    /*
     * Gets a random y value around x
     *
     * @param x the random x value chosen
     * @param bound the boundary value of the matrix
     * @return randomly chosen value from possible directions around x
     */
    private int getYPossible(int x, int bound) {

        ArrayList<Integer> yPossible = new ArrayList<>();
        // up
        if (x-size >= 0) yPossible.add(x-size);
        // down
        if (x+size < bound) yPossible.add(x+size);
        // left
        if (x-1 >= 0) {
            if (x % size > 0) yPossible.add(x-1);
        }
        // right
        if (x+1 < bound) {
            if (x % size < size-1) yPossible.add(x+1);
        }

        Collections.shuffle(yPossible);
        return yPossible.get(r.nextInt(yPossible.size()));

    }

    /*
     * Removes wall from list of walls in the maze
     *
     * @param x value 1
     * @param y value 2
     */
    private void breakWall(int x, int y) {

        Vertex v1, v2;
        v1 = getIJ(x);
        v2 = getIJ(y);

        Wall wall;
        for (int i = 0; i < walls.size(); i++) {
            wall = new Wall(v1, v2);
            if (walls.get(i).equals(wall)) {
                walls.remove(i);
                break;
            }
        }

    }

    /*
     * Finds the row and column value from the position
     * in the matrix
     *
     * @param x value from matrix
     * @return the vertex in the matrix
     */
    private Vertex getIJ(int x) {

        int i,j;

        j = x % size;
        i = (x - j)/size;

        return new Vertex(j,i);

    }

    /*
     * Builds a right and bottom wall for every element in
     * the matrix, except the bottom row
     */
    private void buildWalls() {

        walls = new ArrayList<>();
        Vertex v1, v2;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                // add right wall, dont add border
                if (x+1 < size) {
                    v1 = new Vertex(x, y);
                    v2 = new Vertex(x+1, y);
                    walls.add(new Wall(v1, v2));
                }

                // add bottom wall, dont add border
                if (y+1 < size) {
                    v1 = new Vertex(x, y);
                    v2 = new Vertex(x, y+1);
                    walls.add(new Wall(v1, v2));
                }
            }
        }

    }

}