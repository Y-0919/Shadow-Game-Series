import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class IOUtils {
    public static ArrayList<String[]> readCsv(String csvFile) {
        ArrayList<String[]> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvFile));
            String textRead;
            while ((textRead = reader.readLine()) != null) {
                String[] splitText = textRead.split(",");
                lines.add(splitText);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        return lines;
    }

    public static Properties readPropertiesFile(String configFile) {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(configFile));
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        return appProps;
    }
}
