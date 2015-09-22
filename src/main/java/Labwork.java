import Jama.Matrix;

public class Labwork {
    static int filesNum = 3;
    static int[] L = {100, 0, 0, 125, 0, 0, 150, 0, 175, 0, 200};
    static int[] k = {0, 25, 50, 0, 100, 125, 0, 150, 0, 70, 0};
    static int[] n = {1, 0, 0, 1, 0, 0, 2, 0, 3, 0, 3};
    static double[] rootsR = {-1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D};
    static double[][] rootsL = {
            {0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D},
            {0.0D, 0.0D, 0.857D, 0.0D, 0.054D, 0.018D, 0.0D, 0.071D, 0.0D, 0.0D, 0.0D},
            {0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D},
            {0.0D, 0.0D, 0.857D, 0.0D, 0.054D, 0.018D, 0.0D, 0.071D, 0.0D, 0.0D, 0.0D},
            {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D},
            {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D},
            {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D},
            {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.6D, 0.4D},
            {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D},
            {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 1.0D},
            {0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D}
    };
    static double[] roots = new double[rootsR.length];

    public static void main(String[] args) {
        solveSLAU();
        calcEvgOpNum(true);
        calcEvgFileReq();
        calcEvgFileDataReq();
        calcEvgRefToOp(true);
        calcMiddle();
    }

    private static void solveSLAU() {
        System.out.println("\n1. Êîðíè ÑËÀÐ");

        Matrix A = new Matrix(rootsL);
        for (int i = 0; i < 11; i++) {
            for (int j = i + 1; j < 11; j++) {
                double temp = A.get(i, j);
                A.set(i, j, A.get(j, i));
                A.set(j, i, temp);
            }
        }
        for (int i = 0; i < 11; i++) {
            A.set(i, i, A.get(i, i) - 1.0D);
        }
        Matrix B = new Matrix(rootsR, 11);

        Matrix result = A.solve(B);
        for (int i = 0; i < 11; i++) {
            roots[i] = result.get(i, 0);
            System.out.println(String.format(">\tn[%d] = %f", i + 1, roots[i]));
        }
    }

    private static double calcEvgOpNum(boolean showOutput) {
        double Q = 0;
        for (int i = 0; i < roots.length; i++) {
            Q += roots[i] * k[i];
        }
        if (showOutput) {
            System.out.println("\n2. The average number of processing operations");
            System.out.println(">\t" + Q);
        }
        return Q;
    }

    private static void calcEvgFileReq() {
        double Q;
        System.out.println("\n3. The average number of requests to file");
        for (int i = 1; i < filesNum + 1; i++) {
            Q = 0;
            for (int j = 0; j < n.length; j++) {
                if (n[j] == i) {
                    Q = Q + roots[j];
                    if (Q > 1)
                        System.out.println(">\tQ[" + (i) + "] = " + Q);
                }
            }
        }
    }

    private static void calcEvgFileDataReq() {
        double Q;
        double data;
        System.out.println("\n4. The average amount of data transmitted at one referring to files");
        for (int i = 1; i < filesNum + 1; i++) {
            Q = 0;
            data = 0;
            for (int j = 0; j < n.length; j++) {
                if (n[j] == i) {
                    Q += roots[j];
                    data += (L[j] + roots[j]);
                }

            }
            data /= Q;
            System.out.println(">\tQ[" + (i) + "] = " + data);
        }
    }

    private static double calcEvgRefToOp(boolean showOutput) {
        double Q = 0;
        for (int i = 0; i < roots.length; i++) {
            if (k[i] != 0) {
                Q = (Q + roots[i]);
            }
        }
        if (showOutput) {
            System.out.println("\n5. The amount of the average number of references to the main operators");
            System.out.println(">\t" + Q);
        }
        return Q;
    }

    public static void calcMiddle() {
        double result = calcEvgOpNum(false);
        result /= calcEvgRefToOp(false);
        System.out.println("\n6. Calculation of average labor input of stage");
        System.out.println(">\t" + result);
    }
}
