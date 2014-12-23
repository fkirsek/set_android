package hr.math.set.logic;

public enum CardNumber {
	
	ONE(0), TWO(1), THREE(2);
	
	private final int id;

	CardNumber(int id) {
		this.id = id;
	}

	public int id() {
		return this.id;
	}

}
