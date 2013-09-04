package kamoru.app.spring.test;

import java.util.Date;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class TestDaoImpl implements TestDao {

	@Override
	@Cacheable("testCache")
	public Object getNow(int i) {
		return new Date();
	}

	@Override
	@Cacheable("testCache")
	public Object getNow() {
		return new Date();
	}

	@Override
	public Object getDummy() {
		return new Object();
	}

}
