package kamoru.app.sample.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * @author  kamoru
 */
public class SampleForm extends ActionForm {

	/**
	 * @uml.property  name="sampleList"
	 */
	private List sampleList;

	/**
	 * @return
	 * @uml.property  name="sampleList"
	 */
	public List getSampleList() {
		return sampleList;
	}

	/**
	 * @param sampleList
	 * @uml.property  name="sampleList"
	 */
	public void setSampleList(List sampleList) {
		this.sampleList = sampleList;
	}
	

}
