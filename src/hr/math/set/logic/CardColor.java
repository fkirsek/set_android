package hr.math.set.logic;

public enum CardColor {

	GREEN(0), BLUE(1), MAGENTA(2);

	private final int id;

	CardColor(int id) {
		this.id = id;
	}

	public int id() {
		return this.id;
	}
}
