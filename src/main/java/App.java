import org.apache.commons.math.linear.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import Jama.Matrix;
/**
 * Created by medhigh on 9/8/15.
 */
public class App {
    public static void main(String[] args) {
        RealMatrix coefficients2 =
                new Array2DRowRealMatrix(new double[][] {
                        { -1, 1,  0, 0, 0, 0, 0, 0, 0, 0, 0},
                        { 0, -1, 6d/7d, 0, 3d/56d, 1d/56d, 0, 1d/14d, 0, 0, 0},
                        { 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0},
                        { 0, 0, 6d/7d, -1,3d/56d,1d/56d,0,1d/14d,0,0,0},
                        { 0, 0, 0, 0,-1,0,1,0,0,0,0},
                        { 0, 0, 0, 0,0,-1,1,0,0,0,0},
                        { 0, 0, 0, 0,0,0,-1,0,1,0,0},
                        { 0, 0, 0, 0,0,0,0,-1,1,0,0},
                        { 0, 0, 0, 0,0,0,0,0,-1,5d/9d,4d/9d},
                        { 0, 0, 0, 0,0,0,0,0,0,-1,1},
                        { 0, 0, 0, 0,0,0,0,0,0,0,-1}
                },false);
        RealMatrix copy = coefficients2.copy();
        coefficients2= coefficients2.transpose();
        RealMatrix coefficientsImported =
                new Array2DRowRealMatrix(new double[][] {
                        {-1.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d},
                        {1.0d,-1.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d},
                        {0.0d,0.4287d,-1.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d},
                        {0.0d,0.0d,1.0d,-1.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d},
                        {0.0d,0.4897d,0.0d,0.8571d,-1.0d,0.8571d,0.0d,0.0d,0.0d,0.0d},
                        {0.0d,0.0816d,0.0d,0.0d,1.0d,-1.0d,0.0d,0.0d,0.0d,0.0d},
                        {0.0d,0.0d,0.0d,0.1429d,0.0d,0.1429d,-1.0d,0.0d,0.0d,0.0d},
                        {0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.4546d,-1.0d,0.0d,0.0d},
                        {0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,1.0d,-1.0d,0.0d},
                        {0.0d,0.0d,0.0d,0.0d,0.0d,0.0d,0.5454d,0.0d,1.0d,-1.0d}
                },false);
        //coefficientsImported.transpose();

        DecompositionSolver solver = new LUDecompositionImpl(coefficients2).getSolver();
        DecompositionSolver solverImported = new LUDecompositionImpl(coefficientsImported).getSolver();
        System.out.println("1 method my Value :");
        RealVector constants = new ArrayRealVector(new double[] {0, 0, 0, 0,0,0,0,0,0,0,1}, false);
        RealVector constantsImported = new ArrayRealVector(new double[] {-1, 0, 0, 0, 0, 0, 0, 0,0,0}, false);
        RealVector solution = solver.solve(constants);
        RealVector solutionImported = solverImported.solve(constantsImported);
        double[] data = solution.getData();
        double[] dataImported = solutionImported.getData();
        DecimalFormat df = new DecimalFormat();
        df.setRoundingMode(RoundingMode.DOWN);
        for (double d:data){
            System.out.print(df.format(d)+" ");
        }
        print(coefficients2.getData());
        Matrix A = new Matrix(coefficients2.getData());
        Matrix B = new Matrix(constants.getData(), 11);
        Matrix result = A.solve(B);
        print(result.getArray());
        /*System.out.println();
        System.out.println("1 method Imported Value :");
        for (double d:dataImported){
            System.out.print(df.format(d)+" ");
        }
        System.out.println();
        System.out.println("_________________________________________________-"+"\n"+"Imported value each line sum:");
        double sum=0;
        for (double d[]:coefficientsImported.getData()){
            for (double varD: d){
                //System.out.print(df.format(varD)+" ");
                sum+=varD;
            }
            System.out.print(df.format(sum)+" ");
        }
        System.out.println();
        sum=0;
        System.out.println("_________________________________________________"+"\n"+"Our value each line sum:");
        copy.transpose();
        print(copy.getData()); */
    }
    public static void print(double[][] mass){
        DecimalFormat df = new DecimalFormat();
        df.setRoundingMode(RoundingMode.DOWN);
        double sum = 0;
        for (double d[]:mass){
            for (double varD: d){
                System.out.print(df.format(varD)+" ");
                sum+=varD;
            }
            System.out.println("   sum: "+df.format(sum)+" ");
        }

    }
}
