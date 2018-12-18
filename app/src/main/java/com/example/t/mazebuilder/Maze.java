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

        ArrayList<int[]> startPoints = findStart(); // get all possible starting points
        ArrayList<int[]> endPoints = findEnd(); // get all possible ending points

        assignStartEnd(startPoints, endPoints);

    }

    public int[] getStart() {
        return start;
    }

    public int[] getEnd() {
        return end;
    }

    /*
     * Builds a list of elements with exactly 3 walls
     * from the first 2 rows
     *
     * @return the list of possible starting points
     */
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

            // if at least one element found with 3 walls break out of loop
            if (startPoints.size() != 0) break;
        }

        return startPoints;
    }

    /*
     * Unlike findStart, findEnd will build a list of elements
     * with exactly 3 walls stating from the second row and ending
     * at the last row
     *
     * @return the list of possible ending points
     */
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

    /*
     * Calls findBest for every combination of starting and
     * ending point to get the 2 points with the longest
     * path
     *
     * @param s the list of starting points
     * @param e the list of ending points
     */
    private void assignStartEnd(ArrayList<int[]> s, ArrayList<int[]> e) {

        int start, end;
        int startI, startJ, endI, endJ;

        // new int[]{start, end, length of path}
        int[] best = new int[]{0,0,0};
        for (int i = 0; i < s.size(); i++) {
            startI = s.get(i)[0]; // i value for starting point
            startJ = s.get(i)[1]; // j value for starting point
            for (int j = 0; j < e.size(); j++) {
                start = (s.get(i)[0]*size)+s.get(i)[1]; // get starting position in array
                end = (e.get(j)[0]*size)+e.get(j)[1]; // get ending position in array
                path = new ArrayList<>(); // reset path

                endI = e.get(j)[0]; // i value for ending point
                endJ = e.get(j)[1]; // j value for ending point

                findBest( startI, startJ, endI, endJ);
                if (path.size() > best[2]) {
                    // if longer path found make it new best
                    best = new int[]{start, end, path.size()};
                }
            }
        }

        this.start = Path.getIJ(best[0], size); // assign start
        this.end = Path.getIJ(best[1], size); // assign end

    }


    /*
     * Recursively finds its way from starting point to
     * ending point given while building the path from
     * both points
     *
     * @param i starting i coordinate
     * @param j starting j coordinate
     * @param i2 ending i coordinate
     * @param j2 ending j coordinate
     * @return true for path found, or false for dead end or path not found
     */
    private boolean findBest(int i, int j, int i2, int j2) {

        if (i == i2 && j == j2) {
            return true;
        } else {

            int nextPos, pos;
            // get possible paths with no walls
            boolean[] walls = maze[i][j].getWalls();

            pos = (i*size)+j;
            path.add(pos);

            // go up
            nextPos = ((i-1)*size)+j;
            if (!walls[0] && !path.contains(nextPos)) {
                // if top is not a wall
                if (findBest(i-1, j, i2, j2)) {
                    return true;
                }
            }


            // go down
            nextPos = ((i+1)*size)+j;
            if (!walls[1] && !path.contains(nextPos)) {
                // if bottom is not wall
                if (findBest(i+1, j, i2, j2)) {
                    return true;
                }
            }

            // go left
            nextPos = (i*size)+(j-1);
            if (!walls[2] && !path.contains(nextPos)) {
                // if left is not wall
                if (findBest(i, j-1, i2, j2)) {
                    return true;
                }
            }

            // go right
            nextPos = (i*size)+(j+1);
            if (!walls[3] && !path.contains(nextPos)) {
                // if right is not wall
                if (findBest(i, j+1, i2, j2)) {
                    return true;
                }
            }

            path.remove( path.size()-1 );

        }

        return false;

    }

    public Path getVertex(int i, int j) {
        return maze[i][j];
    }
}
