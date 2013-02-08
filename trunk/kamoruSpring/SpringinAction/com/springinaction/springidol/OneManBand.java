package com.springinaction.springidol;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

public class OneManBand implements Performer {

	private Properties instruments;

	public Properties getInstruments() {
		return instruments;
	}

	public void setInstruments(Properties instruments) {
		this.instruments = instruments;
	}

	@Override
	public void perform() throws PerformanceException {
		for(Object key : instruments.keySet()) {
			System.out.print(key + " : ");
			String instrument = instruments.getProperty((String) key);
			System.out.println(instrument);
		}

	}

}
