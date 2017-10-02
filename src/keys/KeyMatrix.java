package keys;

import math.SquareMatrix2;

public class KeyMatrix implements Key {

	SquareMatrix2 keyMatrix;
	
	public KeyMatrix(SquareMatrix2 keyMatrix) {
		this.keyMatrix = keyMatrix;
	}
	
	@Override
	public Object getValue() {
		return keyMatrix;
	}
	
}
