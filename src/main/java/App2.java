import org.ejml.alg.dense.linsol.qr.AdjLinearSolverQr_D64;
import org.ejml.data.ComplexMatrix64F;
import org.ejml.data.DenseMatrix64F;
import org.ejml.data.Matrix;
import org.ejml.interfaces.linsol.LinearSolver;

/**
 * Created by medhigh on 9/8/15.
 */
public class App2 {
    public static void main(String[] args) {
        LinearSolver<DenseMatrix64F> solver = new AdjLinearSolverQr_D64();
        //solver.
    }
}
