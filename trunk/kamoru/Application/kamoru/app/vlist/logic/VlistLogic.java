package kamoru.app.vlist.logic;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import kamoru.app.vlist.bean.Vfile;
import kamoru.app.vlist.form.VlistForm;

public interface VlistLogic {

	Vfile getVfile(URI uri);
	List getVlist(VlistForm vlistForm) throws IOException;
}
