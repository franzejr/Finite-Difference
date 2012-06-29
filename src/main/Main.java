package main;

import util.LUDecomposition;
import util.Matrix;
import method.FiniteDifference;

public class Main {
	
	public static void main(String args[]){
		
		//Discrete Value
		int N = 32;
		
		FiniteDifference finiteDifference = new FiniteDifference();
		finiteDifference.setDomain(2, 2);
		finiteDifference.setInterval(N);
		finiteDifference.setMask();
		finiteDifference.setCoefficients();
		finiteDifference.setB();
		finiteDifference.applyMask();
		
		//Coefficients Matrix
		//finiteDifference.getCoefficients().print(4, 2);
		
		Matrix coefficients = finiteDifference.getCoefficients();
		
		//finiteDifference.b.print(N, 2);
		LUDecomposition luMatrix = Matrix.getLU(coefficients);
		Matrix lowerMatrix = luMatrix.getL();
		Matrix upperMatrix = luMatrix.getU();
		
		//L Matrix
		//lowerMatrix.print(4, 3);
		//U Matrix
		//upperMatrix.print(4,3);
		
		//Solving
		Matrix y = Matrix.solveSubstitution(lowerMatrix, finiteDifference.b);
		//System.out.println("ipsilon");
		//y.print(4, 3);
		Matrix points = Matrix.solveRetrosubstitution(upperMatrix, y);
		
		//points.print(4, 8);
		
		
		
		/* PLOTING for Mathematica */
		
		int k = 0;
		System.out.print("{{");
		for(int i = 0 ; i < N; i++){
			if(i == N-1){				
				System.out.print("0.0");
			}else{
				System.out.print("0.0,");
			}
		}
		
		//Middle
		System.out.print("},");
		for(int i = 0; i < N-2; i++){
			System.out.print("{0.0,");
			for(int j = 0; j < N-2; j++){
				System.out.print(points.get(k, 0)+ ",");
				k++;
			}
			System.out.print("0.0},");
		}
		
		//Ending
		System.out.print("{");
		for(int i = 0 ; i < N; i++){
			if(i == N-1){				
				System.out.print("0.0");
			}else{
				System.out.print("0.0,");
			}
		}
		System.out.print("}}");
		
		
	}

}
