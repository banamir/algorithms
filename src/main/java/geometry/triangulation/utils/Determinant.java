package geometry.triangulation.utils;

/**
 * Created by banamir on 05.03.17.
 */
public class Determinant {

    public static double det(double A[][], int N){

        if(N == 1) return A[0][0];

        if(N == 2) return A[0][0]*A[1][1]-A[1][0]*A[0][1];

        double det = 0;

        double sign = 1;
        for(int k = 0; k < N; k++){

            double Mk[][] = new double[N-1][N-1];

            for(int i = 0; i < N - 1; i++){
                for(int j = 0; j < N - 1; j++){
                    Mk[i][j] = A[i + 1][j < k ? j : j + 1];
                }
            }

            det += sign*A[0][k]* det(Mk, N - 1);
            sign *= -1;
        }

        return det;
    }

    public static void main(String[] args){

        double A[][] = {{1.}};
        System.out.println(A.toString());
        System.out.println("Matrix 1x1");
        System.out.println(String.format("Det A = %5.2f  Must be %5.2f", det(A, 1),1.));

        double B[][] = {
                {5, 2},
                {1, 3}};
        System.out.println("Matrix 2x2");
        System.out.println(String.format("Det B = %5.2f  Must be %5.2f", det(B, 2),13.0));

        double C[][] = {
                {1, 0.5, 6},
                {1, 8, 7},
                {2, 4, 3}};
        System.out.println("Matrix 3x3");
        System.out.println(String.format("Det C = %5.2f  Must be %5.2f", det(C, 3),-70.5));

        double D[][] = {
                {5, 6, 7, -4},
                {0, 1.1, 4, 15},
                {1.2, 3, 2.8, 1.5},
                {1, -2, 2, -0.5}};
        System.out.println("Matrix 4x4");
        System.out.println(String.format("Det D = %5.2f  Must be %5.2f", det(D, 4),165.93));
    }


}
