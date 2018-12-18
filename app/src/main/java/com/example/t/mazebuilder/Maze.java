package com.example.t.mazebuilder;

import java.util.ArrayList;
import java.util.Random;

public class Maze {

    public Path[][] maze;
    private int size;
    private Random r;
    private MazeBuilder builder;
    private int[] start;
    private int[] end;
    private ArrayList<Integer> path;

    public Maze(int s) {

        r = new Random();
        size = s;
        builder = new MazeBuilder(size);

        maze = builder.getMaze();

        ArrayList<int[]> startPoints = findStart();
        ArrayList<int[]> endPoints = findEnd();

        assignStartEnd(startPoints, endPoints);

    }

    public int[] getStart() {
        return start;
    }

    public int[] getEnd() {
        return end;
    }

    private void assignStartEnd(ArrayList<int[]> s, ArrayList<int[]> e) {

        int start, end;
        int startI, startJ, endI, endJ;
        int[] best = new int[]{0,0,0};
        for (int i = 0; i < s.size(); i++) {
            startI = s.get(i)[0];
            startJ = s.get(i)[1];
            for (int j = 0; j < e.size(); j++) {
                start = (s.get(i)[0]*size)+s.get(i)[1];
                end = (e.get(j)[0]*size)+e.get(j)[1];
                path = new ArrayList<>();

                endI = e.get(j)[0];
                endJ = e.get(j)[1];

                findBest( startI, startJ, endI, endJ);
                if (path.size() > best[2]) {
                    best = new int[]{start, end, path.size()};
                }
            }
        }

        this.start = getIJ(best[0]);
        this.end = getIJ(best[1]);

    }

    private int[] getIJ(int x) {

        int i,j;

        j = x % size;
        i = (x - j)/size;

        return new int[]{i,j};

    }


    private boolean findBest(int i, int j, int i2, int j2) {

        if (i == i2 && j == j2) {
            return true;
        } else {

            int nextPos, pos;
            // get possible paths with no walls
            boolean[] walls = maze[i][j].getWalls();
            // go up
            pos = (i*size)+j;
            nextPos = ((i-1)*size)+j;
            if (!walls[0] && !path.contains(nextPos)) {
                // if top is not a wall
                path.add(pos);
                if (findBest(i-1, j, i2, j2)) {
                    return true;
                }
                path.remove( path.size()-1 );
            }


            // go down
            nextPos = ((i+1)*size)+j;
            if (!walls[1] && !path.contains(nextPos)) {
                // if bottom is not wall
                path.add(pos);
                if (findBest(i+1, j, i2, j2)) {
                    return true;
                }
                path.remove( path.size()-1 );
            }

            // go left
            nextPos = (i*size)+(j-1);
            if (!walls[2] && !path.contains(nextPos)) {
                // if left is not wall
                path.add(pos);
                if (findBest(i, j-1, i2, j2)) {
                    return true;
                }
                path.remove( path.size()-1 );
            }

            // go right
            nextPos = (i*size)+(j+1);
            if (!walls[3] && !path.contains(nextPos)) {
                // if right is not wall
                path.add(pos);
                if (findBest(i, j+1, i2, j2)) {
                    return true;
                }
                path.remove( path.size()-1 );
            }
        }

        return false;

    }

    private ArrayList<int[]> findStart() {
        ArrayList<int[]> startPoints = new ArrayList<>();
        boolean[] walls;
        int counter;
        for (int k = 0; k < 2; k++) {
            for (int i = 0; i < size; i++) {
                walls = maze[k][i].getWalls();
                counter = 0;
                for (int j = 0; j < walls.length; j++) {
                    if (walls[j]) counter++;
                }

                if (counter == 3) startPoints.add(new int[]{k, i});
            }
            if (startPoints.size() != 0) break;
        }

        return startPoints;
    }

    private ArrayList<int[]> findEnd() {

        ArrayList<int[]> endPoints = new ArrayList<>();
        boolean[] walls;
        int counter;

        for (int i = 1; i < size; i++) {
            for (int j = 0; j < size; j++) {

                walls = maze[i][j].getWalls();
                counter = 0;
                for (int k = 0; k < walls.length; k++) {
                    if (walls[k]) counter++;
                }

                if (counter == 3) endPoints.add(new int[]{i, j});

            }
        }

        return endPoints;

    }

    public Path getVertex(int i, int j) {
        return maze[i][j];
    }
}
