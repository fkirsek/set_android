package hr.math.set.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardDeck {

	private static CardDeck instance;
	private Random rnd = new Random();

	private List<Card> unshuffledCards = new ArrayList<Card>();
	private List<Card> suffeledCards = new ArrayList<Card>();
	private List<Card> selectedCards = new ArrayList<Card>();

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
			return null;
		}
		int rndIndex = rnd.nextInt(unshuffledCards.size());
		Card rndCard = unshuffledCards.get(rndIndex);
		suffeledCards.add(rndCard);
		unshuffledCards.remove(rndIndex);
		return rndCard;
	}

	public List<Card> initializeTable() {
		List<Card> table = new ArrayList<Card>();
		for (int i = 0; i < 12; i++) {
			table.add(nextCard());
		}
		return table;
	}
	
	public SetStatus selectCard(Card card) {
		if (selectedCards.size() >= 3) {
			throw new RuntimeException("Number of selected cards must be < 3.");
		}
		selectedCards.add(card);
		if (selectedCards.size() == 3) {
			if (isSet()) {
				return SetStatus.SET_OK;
			} else {
				return SetStatus.SET_FAIL;
			}
		} else {
			return SetStatus.CARD_ADDED;
		}
	}

	private boolean isSet() {
		return numberOk() && colorOk() && shapeOk() && shadingOk();
	}

	private boolean numberOk() {
		int sum = 0;
		for (Card card : selectedCards) {
			sum += card.getNumber().id();
		}
		return sum % 3 == 0;
	}

	private boolean colorOk() {
		int sum = 0;
		for (Card card : selectedCards) {
			sum += card.getColor().id();
		}
		return sum % 3 == 0;
	}

	private boolean shapeOk() {
		int sum = 0;
		for (Card card : selectedCards) {
			sum += card.getShape().id();
		}
		return sum % 3 == 0;
	}

	private boolean shadingOk() {
		int sum = 0;
		for (Card card : selectedCards) {
			sum += card.getShading().id();
		}
		return sum % 3 == 0;
	}

}
