/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.javaanpr.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;
import static net.bytebuddy.matcher.ElementMatchers.is;
import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.matches;
import org.xml.sax.SAXException;

/**
 *
 * @author yoyo
 */
@RunWith(Parameterized.class)
public class RecognitionAllIT {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws ParserConfigurationException, SAXException, IOException {


    }

    private File file;
    private String plate;
    //private String realPlate;
    
    public RecognitionAllIT(File file, String plate) 
    {
        this.file = file;
        this.plate = plate;
 
    }

    @After
    public void tearDown() {
    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
        Intelligence intel = new Intelligence();
        String snapshotDirPath = "src/test/resources/snapshots";
        File snapshotDir = new File(snapshotDirPath);
        File[] snapshots = snapshotDir.listFiles();
        Properties properties = new Properties();
        String resultsPath = "src/test/resources/results.properties";
        InputStream resultsStream = new FileInputStream(new File(resultsPath));
        properties.load(resultsStream);
        resultsStream.close();

        Collection<Object[]> dataForOneImage = new ArrayList<>();
 
        for (File file : snapshots) 
        {
            String name = file.getName();
            String plateCorrect = properties.getProperty(name);
            dataForOneImage.add(new Object[]{file, plateCorrect});
        }
          return dataForOneImage;

    }

    @Test
    public void testAllSnapshots() throws ParserConfigurationException, FileNotFoundException, IOException, SAXException 
    {
           assertThat(getNumber(file), equalTo(plate));
        
    }
    
    public String getNumber(File f) throws ParserConfigurationException, FileNotFoundException, IOException, SAXException
    {
          Intelligence intel = new Intelligence();
          CarSnapshot carSnap = new CarSnapshot(new FileInputStream(f));
          String plateCorrect = intel.recognize(carSnap, false);  
          return plateCorrect;
      
    }

}
