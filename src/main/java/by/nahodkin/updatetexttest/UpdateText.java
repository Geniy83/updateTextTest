package by.nahodkin.updatetexttest;

import jakarta.xml.bind.JAXBElement;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateText {

    private WordprocessingMLPackage getTemplate() throws Docx4JException, FileNotFoundException {
        return WordprocessingMLPackage.load(new FileInputStream("./src/main/resources/test.docx"));
    }

    private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<>();
        if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();
        if (obj.getClass().equals(toSearch))
            result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }

        }
        return result;
    }
    public void update() throws FileNotFoundException, Docx4JException {
        List<Object> texts = getAllElementFromObject(getTemplate().getMainDocumentPart(), Text.class);
        for (Object text : texts) {
            Text textElement = (Text) text;
            System.out.println(textElement.getValue());
        }
    }
}
