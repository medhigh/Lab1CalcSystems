import org.apache.commons.math.linear.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import Jama.Matrix;
/**
 * Created by medhigh on 9/8/15.
 */
public class App {
        static double[][] arr =  {
                {100, 0, 0, 125, 0, 0, 150, 0, 175, 0, 200}, //operations by L operator
                {0, 25, 50, 0, 100, 125, 0, 150, 0, 70, 0},//bit per file operator(B)
                {1, 0, 0, 1, 0, 0, 2, 0, 3, 0, 3}}; // file number
    public static void main(String[] args) {
        RealMatrix coefficients2 =
                new Array2DRowRealMatrix(new double[][] {
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
                },false);
        for (int i = 0; i <11 ; i++) {
            coefficients2.setEntry(i,i,-1d);
        }
        coefficients2= coefficients2.transpose();
        DecompositionSolver solver = new LUDecompositionImpl(coefficients2).getSolver();
        System.out.println("1 method my Value :");
        RealVector constants = new ArrayRealVector(new double[] {-1, 0, 0, 0,0,0,0,0,0,0,0}, false);
        RealVector solution = solver.solve(constants);
        double[] data = solution.getData();
        DecimalFormat df = new DecimalFormat();
        df.setRoundingMode(RoundingMode.DOWN);
        System.out.println("Корни уравнения:");
        for (double dd : data){
            System.out.print(df.format(dd)+" ");
        }
        System.out.println();
        System.out.println("Среднее число процессорных операций, выполняемых при одном прогоне алгоритма: " +
                operationsByProcess(data, arr));
        System.out.println("Среднее число обращений к файлам:");
        for (int i = 1; i< 4; i++)
        {
            System.out.println("  Файл " + i + " : "+ fileMiddleRequest(data, arr, i));
        }
        System.out.println("Среднее количество информации передаваемой при одном обращении к файлам:");
        for (int i = 1; i< 4; i++)
        {
            System.out.println("  Файл " + i + " : "+ bitsPerFileTransfer(data, arr, i));
        }
        System.out.println("Сумма среднего числа обращений к основным операторам: " + operatorExecute(data, arr));
        System.out.println("Средняя трудоемкость этапа: " + middleWork(data, arr));
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
    // Подсчет среднего числа обращений к файлам
    public static double fileMiddleRequest(double[] returns, double[][] data, int fileNumber){
        double result = 0;
        for (int i = 0; i< returns.length; i++){
            if (data[2][i] == fileNumber)
                result = result + returns[i];
        }
        return result;

    }
    // Операций за Оператор
    public static double operationsByProcess(double[] answer, double[][] arr){
        double result = 0;
        for (int i=0; i< answer.length; i++){
            result = result + answer[i]*arr[0][i];
        }
        return result;
    }
    // Подсчет средней трудоемкости этапа
    public static double middleWork(double[] returns, double[][] data){
        double result = operationsByProcess(returns, data);
        return result/ operatorExecute(returns, data);
    }
    // Подсчет суммы среднего числа обращений к основным операторам
    public static double operatorExecute(double[] returns, double[][] data){
        double result = 0;
        for (int i = 0; i< returns.length; i++){
            if (data[0][i] !=0) result = result + returns[i];
        }
        return result;
    }
    // Подсчет среднего количества информации передаваемой при одном обращении к файлам
    public static double bitsPerFileTransfer(double[] answer1, double [][]somedata1, int fileNumber){
        double result = 0;
        double information =0;
        for (int i = 0; i< answer1.length; i++){
            if (somedata1[2][i] == fileNumber) {
                result = result + answer1[i];
                information = information + somedata1[1][i]*answer1[i];
            }
        }
        information =information/result;
        return information;
    }
}
