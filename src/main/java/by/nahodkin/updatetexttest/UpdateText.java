package by.nahodkin.updatetexttest;

import jakarta.xml.bind.JAXBElement;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateText {

    private WordprocessingMLPackage getTemplate() throws Docx4JException, FileNotFoundException {
        return WordprocessingMLPackage.load(new FileInputStream("./src/main/resources/test.docx"));
    }
    private WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException {
        return WordprocessingMLPackage.load(new FileInputStream(name));
    }

    private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<>();
        if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();
        if (obj.getClass().equals(toSearch)) result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }
        }
        return result;
    }
    private void replacePlaceholder(WordprocessingMLPackage template, String name, String placeholder ) throws Docx4JException {
        List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);

        for (Object text : texts) {
            Text textElement = (Text) text;
            if (textElement.getValue().equals(placeholder)) {
                textElement.setValue(name);
            }
        }
        writeDocxToStream(template, "./src/main/resources/new.docx");
    }

    private void writeDocxToStream(WordprocessingMLPackage template, String target) throws Docx4JException {
        File f = new File(target);
        template.save(f);
    }

    public void update() throws FileNotFoundException, Docx4JException {
        replacePlaceholder(getTemplate(), "ПАПА+ДАША+МАМА= СЕМЬЯ ", "QQQ");
    }
}
