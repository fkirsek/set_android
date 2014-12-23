package hr.math.set.logic;

public enum CardShading {

	SOLID(0), STRIPED(1), OUTLINED(2);

	private final int id;

	CardShading(int id) {
		this.id = id;
	}

	public int id() {
		return this.id;
	}

}
