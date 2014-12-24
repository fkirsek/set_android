package hr.math.set.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Table {

	private static Table instance;

	private List<Card> cards = new ArrayList<Card>();
	private List<Card> selection = new ArrayList<Card>();

	private Table() {
		initializeTable();
	}

	public static Table getInstance() {
		if (instance == null) {
			instance = new Table();
		}
		return instance;
	}

	private void initializeTable() {
		for (int i = 0; i < 4; i++) {
			drawNext3();
		}
		ensureSet();
	}

	public void ensureSet() {
		while (!existsSet()) {
			drawNext3();
		}
	}

	private void drawNext3() {
		for (int i = 0; i < 3; i++) {
			cards.add(CardDeck.getInstance().nextCard());
		}
	}
	
	public List<Card> cards() {
		return new ArrayList<Card>(cards);
	}
	
	public int size() {
		return cards.size();
	}

	public Card get(int index) {
		return cards.get(index);
	}

	private boolean existsSet() {
		int n = cards.size();
		for (int i = 0; i < n - 2; i++) {
			for (int j = i + 1; j < n - 1; j++) {
				for (int k = j + 1; k < n; k++) {
					if (isSet(Arrays.asList(cards.get(i), cards.get(j), cards.get(k)))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public SetStatus selectCard(int index) {
		selection.add(cards.get(index));
		if (selection.size() == 3) {
			if (isSet(selection)) {
				return SetStatus.SET_OK;
			} else {
				return SetStatus.SET_FAIL;
			}
		} else {
			return SetStatus.CARD_ADDED;
		}
	}

	public void clearSelection() {
		selection.clear();
	}

	public List<Card> getSelection() {
		return Collections.unmodifiableList(selection);
	}

	private boolean isSet(List<Card> set) {
		return numberOk(set) && colorOk(set) && shapeOk(set) && shadingOk(set);
	}

	private boolean numberOk(List<Card> set) {
		int sum = 0;
		for (Card card : set) {
			sum += card.getNumber().id();
		}
		return sum % 3 == 0;
	}

	private boolean colorOk(List<Card> set) {
		int sum = 0;
		for (Card card : set) {
			sum += card.getColor().id();
		}
		return sum % 3 == 0;
	}

	private boolean shapeOk(List<Card> set) {
		int sum = 0;
		for (Card card : set) {
			sum += card.getShape().id();
		}
		return sum % 3 == 0;
	}

	private boolean shadingOk(List<Card> set) {
		int sum = 0;
		for (Card card : set) {
			sum += card.getShading().id();
		}
		return sum % 3 == 0;
	}

}
