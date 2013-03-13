package kamoru.app.spring.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	TestDao testDao;
	
	@Override
	public Object getDummy() {
		return testDao.getDummy();
	}

	@Override
	public Object getNow() {
		return testDao.getNow();
	}

	@Override
	public Object getNow(int i) {
		return testDao.getNow(i);
	}

}
