package hr.math.set.logic;

public class Card {

	private final CardNumber number;
	private final CardColor color;
	private final CardShape shape;
	private final CardShading shading;
	private final int rank;

	public Card(CardNumber number, CardColor color, CardShape shape, CardShading shading) {
		this.number = number;
		this.color = color;
		this.shape = shape;
		this.shading = shading;

		int tmpRank = number.id();
		tmpRank = 3 * tmpRank + color.id();
		tmpRank = 3 * tmpRank + shape.id();
		tmpRank = 3 * tmpRank + shading.id();
		rank = tmpRank;
	}
	
	public CardNumber getNumber() {
		return number;
	}

	public CardColor getColor() {
		return color;
	}

	public CardShape getShape() {
		return shape;
	}

	public CardShading getShading() {
		return shading;
	}

	public int getRank() {
		return rank;
	}

	public String getImageName() {
		StringBuilder sb = new StringBuilder();
		sb.append(number.id()).append(color.id()).append(shape.id()).append(shading.id());
		return sb.toString();
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rank;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		return true;
	}

	@Override
	public String toString() {
		 return "c" + String.valueOf(number.id()) + String.valueOf(color.id())
                 + String.valueOf(shape.id()) + String.valueOf(shading.id());
		/*
		return "C["+ rank + "]";
		*/
	}

}
