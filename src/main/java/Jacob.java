import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

class Jakob{
    int n;
    float eps=(float) 0.001;
    float [][] A;
    float [] b, x_tmp, x_prev;
    int k=0;
    Jakob() throws FileNotFoundException {
        Scanner fin = new Scanner(new FileReader("input.txt"));
        n = fin.nextInt();
        A = new float[n+1][n+1];
        b = new float[n+1];
        x_tmp = new float[n+1];
        x_prev = new float[n+1];
        for(int i=1; i<=n; i++){
            for (int j=1; j<=n; j++){
                A[i][j]= fin.nextFloat();
            }//for j
            b[i]=fin.nextFloat();
        }//for i
    }

    void printMatr(){
        for(int i=1; i<=n; i++){
            for (int j=1; j<=n; j++){
                System.out.print(A[i][j] + " ");
            }//for j
            System.out.print(b[i]);
            System.out.println();
        }//for i
    }//printMatr

    void searchResult(){
        float sum1, sum2;
        int max;
        for(int i=1; i<=n; i++){
            x_tmp[i]=0;
        }
        do{
            k++;
            for (int i=1; i<=n; i++){
                x_prev[i]=x_tmp[i];
            }//for i
            for (int i=1; i<=n; i++){
                sum1=0;
                for(int j=1; j<=i-1; j++){
                    sum1=sum1+ A[i][j]*x_prev[j];
                }//for j sum1
                sum2=0;
                for(int j=i+1; j<=n; j++){
                    sum2=sum2+ A[i][j]*x_prev[j];
                }//for j sum2
                x_tmp[i]=(b[i] - sum1 - sum2)/A[i][i];
            }//for i
            max=1;
            for(int i=2; i<=n; i++){
                if (Math.abs(x_tmp[max]-x_prev[max])<Math.abs(x_tmp[i]-x_prev[i])){
                    max=i+1;
                }//if
            }//for i max
        }while(Math.abs(x_tmp[max]-x_prev[max])<eps);
    }//searchResult

    void printResult(){
        System.out.println("Решение системы:");
        for (int i=1; i<=n; i++){
            System.out.println(x_tmp[i]);
        }
        System.out.println("Кол-во итераций: " + k);
        System.out.println("С точностью: " + eps);
    }//printResult
}