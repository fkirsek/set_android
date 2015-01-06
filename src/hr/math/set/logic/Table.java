package hr.math.set.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.util.Log;

public class Table {

	private CardDeck deck;

	private List<Card> cards = new ArrayList<Card>();
	private List<Card> selection = new ArrayList<Card>();
	Random r = new Random();

	public final int GAME_NOT_DONE = 0;
	public final int GAME_DONE = 1;

	//constructor if the reshuffle setting WAS provided
	public Table(boolean reshuf){
		deck = new CardDeck(reshuf);
		initializeTable(); // initialize the field
	}
	
	//if the reshuffle setting wasn't provided
	public Table() {
		deck = new CardDeck();
		initializeTable(); // initialize the field
	}

	public Table getInstance() {
		return this;
	}

	public void initializeTable() {
		reset();
		for (int i = 0; i < 4; i++) {
			drawNext3();
		}
		// ensureSet(); //this is most probably unneeded since a set is
		// guaranteed in first 12 cards
	}

	public void reset() {
		cards.clear();
		selection.clear();
		deck.reset();
	}

	public boolean ensureSet() {
		while (!existsSet()) {
			if (!drawNext3()) {
				return false;
			}
		}
		return true;
	}

	public void removeSelected() {
		for (int i = 0; i < 3; i++) {
			cards.remove(selection.get(i));
		}
	}

	public boolean drawNext3() {
		for (int i = 0; i < 3; i++) {
			Card c = deck.nextCard(this);
			if (c == null) {
				return false;
			}
			cards.add(c);
		}
		return true;
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

	public void hint() {
		clearSelection();
		List<Card> set = getSet();
		if (set != null) {
			if (cards.size() > 12) {
				selectCard(cards.indexOf(set.get(2)));
			} else {
				selectCard(cards.indexOf(set.get(r.nextInt(3))));
			}
		}
	}

	private List<Card> getSet() {
		int n = cards.size();
		for (int i = 0; i < n - 2; i++) {
			for (int j = i + 1; j < n - 1; j++) {
				for (int k = j + 1; k < n; k++) {
					List<Card> set = Arrays.asList(cards.get(i), cards.get(j),
							cards.get(k));
					if (isSet(set)) {
						return set;
					}
				}
			}
		}
		return null;
	}

	// takes an integer indicating what card was selected within the list of
	// cards currently on the table
	// a function that selects the card and checks if the cards selected make a
	// set, need to be replaced, etc.
	// most of the internal logic is here
	public SetStatus selectCardAndCheck(int position) {
		// TODO provjeri ako sam nesto zabrljao
		SetStatus status = selectCard(position);
		if (status == SetStatus.SET_OK) {
			removeSelected();
			if (size() < 12) {
				drawNext3();
			}
			if (!ensureSet()) {
				reset();
				return SetStatus.GAME_DONE;
			}
			clearSelection();
		} else if (status == SetStatus.SET_FAIL) {
			clearSelection();
		}
		return status;
	}

	// Test only - removes set from table
	public void set() {
		clearSelection();
		if (getSet() != null) {
			for (Card c : getSet()) {
				selectCard(cards.indexOf(c));
			}
			removeSelected();
			if (size() < 12) {
				drawNext3();
			}
			if (!ensureSet()) {
				Log.d("TAG", "Kraj partije");
			}
			clearSelection();
		}
	}

	private boolean existsSet() {
		return getSet() != null;
	}

	public SetStatus selectCard(int index) {
		Card card = cards.get(index);
		if (selection.contains(card)) {
			selection.remove(card);
			return SetStatus.CARD_REMOVED;
		} else
			selection.add(card);

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
