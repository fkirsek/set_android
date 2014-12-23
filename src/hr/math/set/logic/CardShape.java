package hr.math.set.logic;

public enum CardShape {

	OVAL(0), WAVE(1), DIAMOND(2);

	private final int id;

	CardShape(int id) {
		this.id = id;
	}

	public int id() {
		return this.id;
	}

}
