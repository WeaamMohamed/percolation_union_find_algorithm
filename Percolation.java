import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // Sites are in same component if connected by open sites.
    // Percolates iff any site on bottom row is connected to site on top row.

    // rows and columns are 1 indexed [1, n]

    private final int size;  // size of open & close array
    private final WeightedQuickUnionUF quickFind; // instance of weighted quick union
    private boolean[][] isSiteOpen;  // open & closed array for sites
    private int openSitesCounter;   // count number of open sites
    private final int topSite;  // index for extra site to connect top sites
    private final int bottomSite; // index for extra site to connect bottom sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0)
            throw new IllegalArgumentException();

        size = n;
        topSite = size * size;
        bottomSite = topSite + 1;
        quickFind = new WeightedQuickUnionUF(size * size + 2);
        isSiteOpen = new boolean[size][size];
        openSitesCounter = 0;

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        validateSite(row, col);

        if (!isOpen(row, col)) {

            int currentSiteIndex = getSiteIndex(row, col);

            if (row == 1) {
                quickFind.union(currentSiteIndex, topSite);
            }
            else if (row == size) {
                quickFind.union(currentSiteIndex, bottomSite);
            }


            // check the upper cell
            if (row != 1 && isOpen(row - 1, col)) {
                int upperSiteIndex = getSiteIndex(row - 1, col);
                quickFind.union(currentSiteIndex, upperSiteIndex);
            }

            // check the down cell
            if (row != size && isOpen(row + 1, col)) {
                int downSiteIndex = getSiteIndex(row + 1, col);
                quickFind.union(currentSiteIndex, downSiteIndex);
            }

            // check the left cell
            if (col > 1 && isOpen(row, col - 1)) {
                int leftSiteIndex = getSiteIndex(row, col - 1);
                quickFind.union(currentSiteIndex, leftSiteIndex);
            }

            // check the right cell
            if (col < size && isOpen(row, col + 1)) {
                int rightSiteIndex = getSiteIndex(row, col + 1);
                quickFind.union(currentSiteIndex, rightSiteIndex);
            }


            openSitesCounter++;
            isSiteOpen[row - 1][col - 1] = true;
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        return isSiteOpen[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        // check if it's in the same component with top.. then It's full
        return quickFind.find(topSite) == quickFind.find(getSiteIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCounter;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickFind.find(topSite) == quickFind.find(bottomSite);
    }

    // TODO test this method.
    private int getSiteIndex(int row, int col) {
        return size * (row - 1) + (col - 1);
    }

    // check if parameters are valid
    private void validateSite(int row, int col) {

        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException();


    }


}


