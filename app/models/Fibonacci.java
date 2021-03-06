package models;

import java.util.ArrayList;
import java.util.List;

public enum Fibonacci {

	ZERO(0), ONE(1), TWO(2), THREE(3), FIVE(5), EIGHT(8), THIRTEEN(13), TWENTYONE(21), FORTY(40), HUNDRED(100);

	private final int number;

	Fibonacci(Integer number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public static List<Fibonacci> list() {
		List<Fibonacci> list = new ArrayList<Fibonacci>();
		list.add(ZERO);
		list.add(ONE);
		list.add(TWO);
		list.add(THREE);
		list.add(FIVE);
		list.add(EIGHT);
		list.add(THIRTEEN);
		list.add(TWENTYONE);
		list.add(FORTY);
		list.add(HUNDRED);

		return list;
	}

	public static Fibonacci valueOf(int number) {
		switch(number) {
			case 0 : return Fibonacci.ZERO;
			case 1 : return Fibonacci.ONE;
			case 2 : return Fibonacci.TWO;
			case 3 : return Fibonacci.THREE;
			case 5 : return Fibonacci.FIVE;
			case 8 : return Fibonacci.EIGHT;
			case 13 : return Fibonacci.THIRTEEN;
			case 21 : return Fibonacci.TWENTYONE;
			case 40 : return Fibonacci.FORTY;
			case 100 : return Fibonacci.HUNDRED;
			default : return Fibonacci.ZERO;
		}
	}


    @Override
    public String toString() {
        return String.valueOf(getNumber());
    }
}
