package zoominfo.hw.model;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    private String fileName;
    private File file;
    private FileReader fileReader;
    private FileWriter fileWriter;
    private JsonFactory jfactory;
    private JsonParser jParser;

    public FileUtils(String fileName) {
        try {
            this.fileName = fileName;
        } catch (Exception e) {
            System.err.println("Error while creating JSON parser for file: " + this.fileName);
        }
    }

    public Document readDocument() {
        try {
            if (jParser == null) initReader();

            // Check the token
            if (jParser.nextToken() != JsonToken.START_OBJECT) {
                return null;
            }
            Document document = new Document();
            while (jParser.nextToken() != JsonToken.END_OBJECT) {
                String property = jParser.getCurrentName();
                jParser.nextToken();
                switch (property) {
                    case "id":
                        document.setId(jParser.getText());
                        break;
                    case "firstName":
                        document.setFirstName(jParser.getText());
                        break;
                    case "lastName":
                        document.setLastName(jParser.getText());
                        break;
                    case "age":
                        document.setAge(jParser.getIntValue());
                        break;
                    // Unknown properties are ignored
                }
            }
            return document;
        } catch (Exception e) {
            System.err.println("Error while reading document in file: " + fileName);
            return null;
        }
    }

    public void writeId(String id) {
        try {
            if (fileWriter == null) initWriter();
            fileWriter.append(id);
            fileWriter.append('\n');
        } catch (Exception e) {
            System.err.println("Error while writing id to result file");
        }
    }

    private void initReader() throws Exception {
        if (file == null) file = new File(fileName);
        if (fileReader == null) fileReader = new FileReader(file);
        if (jfactory == null) jfactory = new JsonFactory();
        if (jParser == null) jParser = jfactory.createParser(fileReader);
        if (jParser.nextToken() != JsonToken.START_ARRAY) {
            throw new IllegalStateException("Expected content to be an array");
        }
    }

    private void initWriter() throws Exception {
        if (file == null) {
            file = new File(fileName);
            if (file.exists() && file.isFile()) {
                file.delete();
                file.createNewFile();
            }
        }
        if (fileWriter == null) fileWriter = new FileWriter(file, true);
    }

    public void closeWriter() {
        try {
            if (fileWriter != null) fileWriter.close();
        } catch (IOException e) {
            System.err.println("Error while closing writer.");
        }
    }

    public void closeReader() {
        try {
            fileReader.close();
            jParser.close();
        } catch (IOException e) {
            System.err.println("Error while closing reader.");
        }
    }

}
