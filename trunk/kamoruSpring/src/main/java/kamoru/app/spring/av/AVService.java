package kamoru.app.spring.av;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

public interface AVService {
	List<AVOpus> getAV(Map<String, String> params);
	Map<String, Integer> getStudioMap();
	Map<String, Integer> getActressMap();
	File getImageFile(String opus, Model model);
	String action(String mode, String opus);
	String getOverview(String opus);
	void getOverviewSave(String opus, String overViewTxt);
}
