package moons.moepicture;

import android.provider.DocumentsContract;
import android.provider.Settings;
import android.util.Log;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import moons.moepicture.Models.PictureItem;
import moons.moepicture.Services.Spider;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void PictureItem_Construct_Test() throws Exception {
        final  String sampleFileName = "\\app\\src\\test\\java\\moons\\moepicture\\" + "SamplePost.xml";
        File sampleFile = new File(System.getProperty("user.dir") + sampleFileName);

        assert(sampleFile.exists());

        FileInputStream in =  new FileInputStream(sampleFile);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder= dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(sampleFile);

        NodeList nList = doc.getElementsByTagName("post");
        assertEquals(nList.getLength(), 40);
        for (int i = 0; i < 2; i++) {
            Element postNode = (Element) nList.item(i);
            PictureItem newItem = PictureItem.getItemFromDomElement(postNode);
            assertEquals(newItem.id, postNode.getAttribute("id"));
        }
    }

    @Test
    public void Spider_FileName_Test() {
        final String url = "https://files.yande.re/sample/19799cc35390b1aa7cd1ebb332a84a97/yande.re%20405388%20sample%20feet%20nyum%20seifuku%20wet.jpg";
        final String fileName = "yande.re 405388 sample feet nyum seifuku wet.jpg".replaceAll("[^a-zA-Z0-9.-]", "_");
        assertEquals(Spider.getFileNameFromUrl(url), fileName);
    }


}