package hr.math.set.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardDeck {

	private static CardDeck instance;
	private Random rnd = new Random();

	private List<Card> unshuffledCards = new ArrayList<Card>();
	private List<Card> shuffeledCards = new ArrayList<Card>();

	private CardDeck() {
		initializeCardDeck();
	}

	public static CardDeck getInstance() {
		if (instance == null) {
			instance = new CardDeck();
		}
		return instance;
	}

	private void initializeCardDeck() {
		for (CardNumber number : CardNumber.values()) {
			for (CardColor color : CardColor.values()) {
				for (CardShape shape : CardShape.values()) {
					for (CardShading shading : CardShading.values()) {
						unshuffledCards.add(new Card(number, color, shape, shading));
					}
				}
			}
		}
	}

	public Card nextCard() {
		if (unshuffledCards.isEmpty()) {
			// TODO
			for (int i = 0; i < shuffeledCards.size(); i++) {
				unshuffledCards.add(shuffeledCards.get(i));
			}
			shuffeledCards.clear();
			for (int i = 0; i < Table.getInstance().size(); i++) {
				shuffeledCards.add(Table.getInstance().get(i));
				unshuffledCards.remove(Table.getInstance().get(i));
			}
		}
		int rndIndex = rnd.nextInt(unshuffledCards.size());
		Card rndCard = unshuffledCards.get(rndIndex);
		shuffeledCards.add(rndCard);
		unshuffledCards.remove(rndIndex);
		return rndCard;
	}	

}
