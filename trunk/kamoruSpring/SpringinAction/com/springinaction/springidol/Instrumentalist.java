package com.springinaction.springidol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.springinaction.springidol.qualifiers.StringedInstrument;
import com.springinaction.springidol.qualifiers.Strummed;

public class Instrumentalist implements Performer {

	public Instrumentalist() {
		
	}
	
	@Override
	public void perform() throws PerformanceException {
		System.out.println("Playing " + song + " : ");
		instrument.play();
	}

	private String song;

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public String screamSong() {
		return song;
	}
	
	private Instrument instrument;

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
	
}
