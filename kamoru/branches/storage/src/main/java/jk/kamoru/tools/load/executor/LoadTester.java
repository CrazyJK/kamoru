package jk.kamoru.tools.load.executor;

import jk.kamoru.tools.load.factory.LoadFactoryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadTester extends LoadTestService {
	
	private int nLoads;
	private LoadFactoryService loadFactory;

	public void setNLoads(int nLoads) {
		this.nLoads = nLoads;
	}

	public void setLoadFactory(LoadFactoryService loadFactory) {
		this.loadFactory = loadFactory;
	}

	@Override
	int getNLoads() {
		return nLoads;
	}

	@Override
	LoadFactoryService getLoadFactory() {
		return loadFactory;
	}


}
