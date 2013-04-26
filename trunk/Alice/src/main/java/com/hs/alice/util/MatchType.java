package com.hs.alice.util;

public enum MatchType {
	ANYWHERE(0), START(1), END(2), EXACT(3);
	
	@SuppressWarnings("unused")
	private int value;
	MatchType(int value) {
		this.value = value;
	}
}
