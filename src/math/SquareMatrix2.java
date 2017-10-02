package math;

import gui.Main;

public final class SquareMatrix2 {

	// Number of letters
	private static final int modulo = Main.getAlphabetInUse().size();
	
	// Elements of the matrix
	private final int a;
	private final int b;
	private final int c;
	private final int d;
	
	// Determinant and its modular multiplicative inverse
	private final int determinant;
	private final int inverse;
	
	public SquareMatrix2(int a, int b, int c, int d) {
		this.a = mod(a);
		this.b = mod(b);
		this.c = mod(c);
		this.d = mod(d);
		
		this.determinant = mod(computeDeterminant());
		this.inverse = computeModularMultiplicativeInverse();
	}
	
	private static int mod(int x) {
		return ((x % modulo) + modulo) % modulo;
	}
	
	private int computeDeterminant() {
		return (a * d) - (b * c);
	}
	
	private int computeModularMultiplicativeInverse() {
		int inverse = -1;
		
		for (int i = 0; i < modulo; i++) {
			
			// Assume determinant to be positive
			if ((determinant * i) % modulo == 1) {
				inverse = i;
				
				break;
			}

		}
		
		return inverse;
	}
	
	public SquareMatrix2 invert() {
		int aa = mod(inverse * d);
		int bb = mod(inverse * -b);
		int cc = mod(inverse * -c);
		int dd = mod(inverse * a);
		
		return new SquareMatrix2(aa, bb, cc, dd);
	}
	
	public boolean isInvertible() {
		if (determinant == 0 || inverse == -1) {
			return false;
		}
		
		return true;
	}
	
	public Vector2 times(Vector2 vector) {
		int aa = mod(this.a * vector.a + this.b * vector.b);
		int bb = mod(this.c * vector.a + this.d * vector.b);
		
		return new Vector2(aa, bb);
	}
	
	public SquareMatrix2 times(SquareMatrix2 other) {
		int aa = mod(this.a * other.a + this.b * other.c);
		int bb = mod(this.a * other.b + this.b * other.d);
		int cc = mod(this.c * other.a + this.d * other.c);
		int dd = mod(this.c * other.b + this.d * other.d);
		
		return new SquareMatrix2(aa, bb, cc, dd);
	}
	
	public int getOrder() {
		return 2;
	}
	
}
