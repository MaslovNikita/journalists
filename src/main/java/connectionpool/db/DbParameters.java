package connectionpool.db;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.net.URL;

/**
 * Stores parameters of database.
 *
 * @author homie
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DbParameters", propOrder = {"driver", "url", "user", "password", "pullSize"})
public class DbParameters {

    private static final Logger log = LogManager.getRootLogger();

    @XmlElement(required = true)
    private String driver;
    @XmlElement(required = true)
    private String url;
    @XmlElement(required = true)
    private String user;
    @XmlElement(required = true)
    private String password;
    @XmlElement(required = true)
    private String pullSize;

    public DbParameters() {
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(final String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPullSize() {
        return pullSize;
    }

    public void setPullSize(final String pullSize) {
        this.pullSize = pullSize;
    }

    /**
     * Obtain parameters database from xml file.
     *
     * @return Object of DbParameters class, which stores database parameters
     */
    public static DbParameters getDbParameters() {
        try {
            JAXBContext context = JAXBContext.newInstance(DbParameters.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            URL url = Class.forName(DbParameters.class.getCanonicalName()).getResource("../../parameters.xml");

            File file = new File(url.getPath());

            DbParameters parameters = (DbParameters) unmarshaller.unmarshal(file);
            return parameters;
        } catch (JAXBException e) {
            log.log(Level.FATAL, "Something wrong in DbParamters: " + e.getLocalizedMessage());
            return new DbParameters();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new DbParameters();
        }
    }
}
