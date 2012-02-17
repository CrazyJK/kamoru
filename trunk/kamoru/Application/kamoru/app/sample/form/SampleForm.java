package kamoru.app.sample.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class SampleForm extends ActionForm {

	private List sampleList;

	public List getSampleList() {
		return sampleList;
	}

	public void setSampleList(List sampleList) {
		this.sampleList = sampleList;
	}
	

}
