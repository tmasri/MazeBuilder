package com.example.t.mazebuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MazeBuilder {

    private int size;
    private ArrayList<Wall> walls;
    private Path[][] maze;

    private Random r;

    public MazeBuilder(int s) {

        r = new Random();
        size = s;

        maze = new Path[s][s];
        buildWalls();
        buildMazeWalls();

        buildMaze();

    }

    public Path[][] getMaze() {
        return maze;
    }

    private void buildMaze() {

        boolean top, bottom, left, right;
        int pos;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                top = false;
                bottom = false;
                right = false;
                left = false;

                pos = (y*size)+x;
                // check top
                if (pos - size >= 0) {
                    // check if vertex above has bottom wall
                    top = isWall(pos - size, pos, false);
                } else {
                    // if out of bounds then it hit wall
                    top = true;
                }

                // check bottom
                if (pos + size < size*size) {
                    bottom = isWall(pos, pos + size, false);
                } else {
                    bottom = true;
                }

                // check left
                if (x - 1 >= 0) {
                    left = isWall(pos - 1, pos, false);
                } else {
                    left = true;
                }

                // check right
                if (x + 1 < size) {
                    right = isWall(pos, pos + 1, false);
                } else {
                    right = true;
                }

                maze[y][x] = new Path((y*size)+x);
                maze[y][x].setWalls(top, bottom, left, right);

            }
        }

    }

    private boolean isWall(int x, int y, boolean breaker) {

        Vertex v1, v2;
        v1 = getIJ(x);
        v2 = getIJ(y);
        Wall w;

        for (int i = 0; i < walls.size(); i++) {
            w = new Wall(v1, v2);
            if (walls.get(i).equals(w)) {
                if (breaker) walls.remove(i);
                return true;
            }
        }

        return false;

    }

    private void buildMazeWalls() {

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
                isWall(x, y, true);
                ds.union(u, v);
            }

        }

    }

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

    private Vertex getIJ(int x) {

        int i,j;

        j = x % size;
        i = (x - j)/size;

        return new Vertex(j,i);

    }

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