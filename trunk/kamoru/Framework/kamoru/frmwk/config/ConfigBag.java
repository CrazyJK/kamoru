package kamoru.frmwk.config;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.opensymphony.module.propertyset.xml.XMLPropertySet;

/**
 * @author  kamoru
 */
public class ConfigBag {

	protected static final Log logger = LogFactory.getLog(ConfigBag.class);

    /**
	 * @uml.property  name="instance"
	 * @uml.associationEnd  
	 */
    private static ConfigBag instance;
    /**
	 * @uml.property  name="config"
	 */
    private static XMLPropertySet config;

	private ConfigBag() {
        init();
    }

    private void init() {
        java.io.InputStream is = getClass().getResourceAsStream("/resources/propertyset.xml");
        logger.info("Loading propertySet ....");
        if(is == null)
            is = getClass().getResourceAsStream("/resources/propertyset.xml");
        config = new XMLPropertySet();
        try {
            config.init(null, null);
            config.load(is);
            logger.info("Successfully loaded to propertySet.");
        }
        catch(ParserConfigurationException e) {
            logger.error("ParserConfigurationException", e);
        }
        catch(SAXException e) {
            logger.error("SAXException", e);
        }
        catch(IOException e) {
            logger.error("IOException", e);
        }
    }

    /**
	 * @return
	 * @uml.property  name="instance"
	 */
    public static ConfigBag getInstance() {
        if(instance == null)
            instance = new ConfigBag();
        return instance;
    }

    /**
	 * @return
	 * @uml.property  name="config"
	 */
    public XMLPropertySet getConfig() {
        return config;
    }

    public void reload() {
        init();
    }


}
