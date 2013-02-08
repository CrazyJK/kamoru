package com.springinaction.springidol;

import javax.inject.Inject;

public class PoeticJuggler extends Juggler {

	@Inject
	private Poem poem;

	public PoeticJuggler(Poem poem) {
		super();
		this.poem = poem;
	}

	public PoeticJuggler(int beanBags, Poem poem) {
		super(beanBags);
		this.poem = poem;
	}

	@Override
	public void perform() throws PerformanceException {
		// TODO Auto-generated method stub
		super.perform();
		System.out.println("WHILE RECITING ...");
		poem.recite();
	}
	
	
	
}
