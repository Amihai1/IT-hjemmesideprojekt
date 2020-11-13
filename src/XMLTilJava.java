import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;

public class XMLTilJava {

    public static void main(String[] args) {
        try{
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(DeserializationFeature.valueOf("xmlout.xml"));
            Person value = xmlMapper.writeValue("xmlout.xml", Person.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
