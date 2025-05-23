package IndexServer;

import java.io.Serializable;
import java.util.Arrays;

public class File implements Serializable {
    private String fileName;
    private String[] keywords;
    private String contenuto;

    public File(String fileName, String[] keywords, String contenuto) {
        super();
        this.fileName = fileName;
        this.keywords = keywords;
        this.contenuto = contenuto;
    }

    public String getFileName() {
        return fileName;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public String getContenuto() {
        return contenuto;
    }

    public String toString(){
        return "File [fileName=" + fileName + ", keywords=" + Arrays.toString(keywords) +", contenuto=" + contenuto + "]";
    }

}//File
