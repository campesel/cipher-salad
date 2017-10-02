package keys;

public class Shift implements Key {

	private int shift;
	
	public Shift(int shift) {
		this.shift = shift;
	}
	
	@Override
	public Object getValue() {
		return new Integer(shift);
	}

}
