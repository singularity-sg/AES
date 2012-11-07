package models;

public enum Fibonacci {
	
	ZERO(0), ONE(1), TWO(2), THREE(3), FIVE(5), EIGHT(8), THIRTEEN(13), TWENTYONE(21), FORTY(40), HUNDRED(100);
	
	private final int number;
	
	Fibonacci(Integer number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

}
