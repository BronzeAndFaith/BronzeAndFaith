package BronzeAndFaith.Map;

public enum PatternElement {
	
	EMPTY(' '),
	WALL('X'),
	DOOR('O'),
	FLOOR('.'),
	MODULE_NODE('I');

	private char c;
	
	PatternElement(char c){
		this.c = c;
	}
}
