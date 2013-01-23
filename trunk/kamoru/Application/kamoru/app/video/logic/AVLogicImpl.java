package kamoru.app.video.logic;

import java.util.List;

import kamoru.app.video.av.AVOpus;
import kamoru.app.video.dao.AVDao;
import kamoru.app.video.form.AVForm;

public class AVLogicImpl implements AVLogic {

	private AVDao avDao;

	public void setAvDao(AVDao avDao) {
		this.avDao = avDao;
	}

	@Override
	public List<AVOpus> getList(AVForm avForm) {
		List<AVOpus> list = avDao.getList(avForm.getStudio(), avForm.getOpus(), avForm.getTitle(), avForm.getActress(),
				avForm.isAddCond(), avForm.isExistVideo(), avForm.isExistSubtitles(),
				avForm.getListViewType(), avForm.getSortMethod(), avForm.isSortReverse(), avForm.isUseCacheData());
		return list;
	}
	
	
}
