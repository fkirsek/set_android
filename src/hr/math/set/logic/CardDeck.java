package hr.math.set.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardDeck {

	private Random rnd = new Random();
	boolean reshuffle;

	private List<Card> unshuffledCards = new ArrayList<Card>();
	private List<Card> shuffledCards = new ArrayList<Card>();

	// a constructor if the reshuffle setting value wasn't provided
	CardDeck() {
		reshuffle = false;
		initializeCardDeck();
	}

	// and a constructor if it was
	CardDeck(boolean reshuf) {
		reshuffle = reshuf;
		initializeCardDeck();
	}

	// this method is unneeded and should be deleted in the next commit
	public CardDeck getInstance() {
		return this;
	}

	public void setReshuffle(boolean reshuffle) {
		this.reshuffle = reshuffle;
	}

	private void initializeCardDeck() {
		for (CardNumber number : CardNumber.values()) {
			for (CardColor color : CardColor.values()) {
				for (CardShape shape : CardShape.values()) {
					for (CardShading shading : CardShading.values()) {
						unshuffledCards.add(new Card(number, color, shape,
								shading));
					}
				}
			}
		}
	}

	public void reset() {
		unshuffledCards.addAll(shuffledCards);
		shuffledCards.clear();
	}

	public Card nextCard(Table table) {
		if (unshuffledCards.isEmpty()) {
			// TODO
			if (reshuffle == false) {
				return null;
			}
			reset();
			for (int i = 0; i < table.size(); i++) {
				shuffledCards.add(table.get(i));
				unshuffledCards.remove(table.get(i));
			}
		}
		int rndIndex = rnd.nextInt(unshuffledCards.size());
		Card rndCard = unshuffledCards.get(rndIndex);
		shuffledCards.add(rndCard);
		unshuffledCards.remove(rndIndex);
		return rndCard;
	}

}
