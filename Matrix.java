
public class Matrix {

    // helper function to multiply two 2D arrays
    public static double[][] multiplicate (double[][] a, double[][] b) {
        int aRows = a.length;
        int aCols = a[0].length;
        int bRows = b.length;
        int bCols = b[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException("Columns in A: " + aCols
            + " did not match rows in B " + bRows + ".");
        }

        double[][] C = new double[aRows][bCols];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                C[i][j] = 0.0;
            }
        }

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                for (int k = 0; k < aCols; k++) {
                    C[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return C;

    }
}
