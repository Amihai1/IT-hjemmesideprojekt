import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;


public class JavaTilXML {
    public static void main(String[] args) {
        try{
            XmlMapper xmlMapper = new XmlMapper(); //import that shit
            xmlMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            Person person = new Person();
            person.setCpr("1212121212");
            person.setPassword("kalaha");
            person.setPatientid(2);

            String xmlstring = xmlMapper.writeValueAsString(person);
            System.out.println(xmlstring);
            //write this shit down into an XML file:
            File xmlout = new File("xmlout.xml");
            FileWriter fileWriter = new FileWriter(xmlout);
            fileWriter.write(xmlstring);
            fileWriter.close();
        }catch (Exception e){
            //https://www.baeldung.com/jackson-jsonmappingexception use this to locate problems with things not being where
            //they should be
            e.printStackTrace();
        }

    }



}