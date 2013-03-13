package kamoru.app.spring.schedule;

import kamoru.app.spring.picture.service.ImageService;
import kamoru.app.spring.picture.source.ImageSource;
import kamoru.app.spring.video.service.VideoService;
import kamoru.app.spring.video.source.VideoSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchExecutor {

	private static final Logger logger = LoggerFactory.getLogger(BatchExecutor.class);

	@Autowired VideoSource videoSource;
	@Autowired ImageService imageService;
	
	@Scheduled(cron="0 */5 * * * *")
	public void batchVideoSource() {
		logger.info("execute batch");
		videoSource.reload();
	}
	
	@Scheduled(cron="0 */5 * * * *")
	public void batchImageSource() {
		logger.info("execute batch");
		imageService.reload();
	}
	
}
