package com.springinaction.springidol;

public class Sonnet29 implements Poem {

	private static String[] LINES = {
		"Everything that has a beginning has an end",
		"Hope, It is the quintessential human delusion",
		"You see there is only one constant. One universal. It is the only real truth. Causality Action, reaction. Cause and effect",
		"Have you ever had a dream, Neo, that you were so sure was real? What if you were unable to wake from that dream? How would you know the difference between world and the real world?"
	};
	@Override
	public void recite() {
		for(String line : LINES) {
			System.out.println(line);
		}

	}

}
