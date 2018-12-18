package com.example.t.mazebuilder;

/*
 * DisjointSet initially starts with n sets, everytime
 * a find is performed on 2 random numbers, the find will
 * return the set the element is in, performing a union
 * on the 2 sets will give you one set.
 *
 * If this process is done repeatedly, you will end with
 * 1 set where each element of the set is connected to at
 * least one other element from the set
 */
public class DisjointSet {

    int[] set;

    /*
     * Build the set of sets
     *
     * @param size the initial size of the set
     */
    public DisjointSet(int n) {

        set = new int[n];
        for (int i = 0; i < n; i++) {
            set[i] = -1;
        }

    }

    /*
     * Find the set that the element is in
     *
     * @param x the element that is being searched for
     * @return the set that the element is in
     */
    public int find(int x) {

        if (set[x] < 0) return x;
        else return set[x] = find(set[x]);

    }


    /*
     * Combining the 2 sets together assuming they are
     * not of the same set
     *
     * @param x set 1
     * @param y set 2
     */
    public void union(int x, int y) {

        if (set[y] < set[x])
            set[x] = y;
        else {
            if (set[x] == set[y])
                set[x]--;
            set[y] = x;
        }

    }

    /*
     * Checks the number of sets left
     * Should return 1 if theres only one set left
     *
     * @return the number of sets contained in set
     */
    public int count() {

        int count = 0;
        for (int i = 0; i < set.length; i++) {
            if (set[i] < 0) count++;
        }

        return count;

    }

}