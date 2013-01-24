package kamoru.app.video.logic;

import java.util.List;
import java.util.Map;

import kamoru.app.video.av.AVOpus;
import kamoru.app.video.form.AVForm;

public interface AVLogic {

	List<AVOpus> getList(AVForm avForm);

	Map<String, Integer> getStudioMap();

	Map<String, Integer> getActressMap();

}
