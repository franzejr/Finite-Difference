package method;

import util.Matrix;

public class FiniteDifference {

	public double dx, dy;
	public double Lx, Ly;
	int N,numPoints;

	public Matrix b;
	public Matrix points;

	private Matrix coefficients;
	public Matrix mask;

	/*
	 * Set Lx Ly
	 */
	public void setDomain(double Lx, double Ly) {
		this.Lx = Lx;
		this.Ly = Ly;
	}

	/*
	 * Set dx, dy
	 */
	public void setInterval(int N) {
		this.N = N;

		dx = Lx / N;
		dy = Ly / N;
		
		this.numPoints = (N-2)*(N-2);
	}

	public void setMask() {

		// Initializing
		this.mask = new Matrix(5, 1);

		// Element 0
		this.mask.set(0, 0, -2 / (dx * dx) - 2 / (dy * dy));
		// Element 1
		this.mask.set(1, 0, 1 / (dx * dx));
		// Element 2
		this.mask.set(2, 0, 1 / (dy * dy));
		// Element 3
		this.mask.set(3, 0, 1 / (dx * dx));
		// Element 4
		this.mask.set(4, 0, 1 / (dy * dy));
	}

	public void setCoefficients() {
		this.coefficients = new Matrix(numPoints, numPoints);
	}

	/*
	 * EDO
	 */
	double p(double x, double y) {
		return -2.0;
	}

	/*
	 * Result from the system
	 */
	public void setB() {
		this.b = new Matrix(numPoints, 1);

		for (int i = 0; i < (N - 2) * (N - 2); i++) {
			this.b.set(i, 0, p(0, 0));
		}

	}

	/*
	 * 
	 */
	public void applyMask() {
		  int k=0; //Linha da matriz de coefficients
		    int w = 0; //Coluna da matriz de coefficients
		    int num = N-2;
		    double value;

		    for (int i=0;i<(this.N)-2;i++){
		        for (int j=0;j<(this.N)-2;j++){

		            //Meio
		            value = this.mask.get(0,0);
		            this.coefficients.set(k,k,value);

		            //Borda esquerda
		            if (i == 0){
		                if (j == 0){

		                    //Direita
		                    w = num*i + (j+1);
		                    value = this.mask.get(3,0);
		                    //System.out.println(this.mask.get(3, 0));	
		                    this.coefficients.set(k,w, value);
		                    //System.out.println(this.coefficients.get(k, w));

		                    //Cima
		                    w = num*(i+1) + j;
		                    //System.out.println("k"+k+"w"+w);
		                    value = this.mask.get(4,0);
		                    //System.out.println(this.mask.get(4, 0));
		                    this.coefficients.set(k,w, value);
		                    //System.out.println(this.coefficients.get(k, w));

		                } else {
		                    if (j == (this.N)-3){

		                        //Cima
		                        w = num*(i+1) + j;
		                        value = this.mask.get(4,0);
		                        this.coefficients.set(k,w, value);

		                        //Esquerda
		                        w = num*i + (j-1);
		                        value = this.mask.get(1,0);
		                        this.coefficients.set(k,w, value);
		                    } else {

		                        //Cima
		                        w = num*(i+1) + j;
		                        //System.out.println("k"+k+"w"+w);
		                        value = this.mask.get(4,0);
		                        this.coefficients.set(k,w, value);

		                        //Direita
		                        w = num*i + (j+1);

		                        value = this.mask.get(3,0);
		                        this.coefficients.set(k, w, value);

		                        //Esquerda
		                        w = num*i + (j-1);
		                        value = this.mask.get(1,0);
		                        this.coefficients.set(k,w, value);

		                    }
		                }


		            } else {
		                //Borda direita
		                if (i == (this.N)-3){
		                    if (j == 0){

		                        //Direita
		                        w = num*i + (j+1);

		                        value = this.mask.get(3,0);
		                        this.coefficients.set(k, w, value);


		                        //Baixo
		                        w = num*(i-1) + j;

		                        value = this.mask.get(2,0);
		                        this.coefficients.set(k,w, value);
		                    } else {
		                        if (j == this.N-3){

		                            //Baixo
		                            w = num*(i-1) + j;

		                            value = this.mask.get(2,0);
		                            this.coefficients.set(k,w, value);

		                            //Esquerda
		                            w = num*i + (j-1);

		                            value = this.mask.get(1,0);
		                            this.coefficients.set(k,w,value);

		                        } else {

		                            //Baixo
		                            w = num*(i-1) + j;

		                            value = this.mask.get(2,0);
		                            this.coefficients.set(k,w, value);

		                            //Esquerda
		                            w = num*i + (j-1);

		                            value = this.mask.get(1,0);
		                            this.coefficients.set(k,w,value);

		                            //Direita
		                            w = num*i + (j+1);

		                            value = this.mask.get(3,0);
		                            this.coefficients.set(k, w, value);

		                        }
		                    }

		                } else {
		                    //O ponto est‡ no meio
		                    if (j == 0){

		                        //Baixo
		                        w = num*(i-1) + j;

		                        value = this.mask.get(2,0);
		                        this.coefficients.set(k,w, value);

		                        //Cima
		                        w = num*(i+1) + j;

		                        value = this.mask.get(4,0);
		                        this.coefficients.set(k,w, value);

		                        //Direita
		                        w = num*i + (j+1);

		                        value = this.mask.get(3,0);
		                        this.coefficients.set(k, w, value);

		                    } else {
		                        if (j == this.N - 3){

		                            //Baixo
		                            w = num*(i-1) + j;

		                            value = this.mask.get(2,0);
		                            this.coefficients.set(k,w, value);

		                            //Esquerda
		                            w = num*i + (j-1);

		                            value = this.mask.get(1,0);
		                            this.coefficients.set(k,w,value);

		                            //Cima
		                            w = num*(i+1) + j;

		                            value = this.mask.get(4,0);
		                            this.coefficients.set(k,w, value);

		                        } else {

		                            //Baixo
		                            w = num*(i-1) + j;

		                            value = this.mask.get(2,0);
		                            this.coefficients.set(k,w, value);

		                            //Esquerda
		                            w = num*i + (j-1);

		                            value = this.mask.get(1,0);
		                            this.coefficients.set(k,w,value);

		                            //Cima
		                            w = num*(i+1) + j;

		                            value = this.mask.get(4,0);
		                            this.coefficients.set(k,w, value);

		                            //Direita
		                            w = num*i + (j+1);

		                            value = this.mask.get(3,0);

		                            this.coefficients.set(k, w, value);

		                        }
		                }
		                    
		            }
		               
		            } k++;
		        }
		    }
		    
	}

	public Matrix getCoefficients() {
		return coefficients;
	}

	public void setCoefficients(Matrix coefficients) {
		this.coefficients = coefficients;
	}
}
