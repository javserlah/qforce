package Mocking;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class MockResponses {
    private static ClassLoader classLoader = MockResponses.class.getClassLoader();
    private static final String Ext = ".json";

    private static HashMap<String, String> mockResponses = new HashMap<>();

    public static String Get(String responseName) {
        if (!mockResponses.containsKey(responseName)) {
            read(responseName);
        }
        return mockResponses.get(responseName);
    }

    private static void read(String responseName) {
        String fileName = responseName;
        try {
            fileName = classLoader.getResource(  "swapapiresponses/" + responseName + Ext).getFile();
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            mockResponses.put(responseName, new String(data, "UTF-8"));
        } catch (NullPointerException e0) {
            mockResponses.put(responseName, "Couldn't find response " + fileName);
        } catch (java.io.FileNotFoundException e1) {
            mockResponses.put(responseName, "Couldn't find response " + fileName);
        } catch (java.io.IOException e2) {
            mockResponses.put(responseName, "Error reading file " + fileName);
        }
    }
}
