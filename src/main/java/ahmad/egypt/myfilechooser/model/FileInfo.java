package ahmad.egypt.myfilechooser.model;

import android.os.Parcelable;

import java.io.Serializable;

public class FileInfo implements Serializable {
    private String name,fullPath,dirPath;

    public FileInfo(String name, String fullPath, String dirPath) {
        this.name = name;
        this.fullPath = fullPath;
        this.dirPath = dirPath;
    }

    public String getName() {
        return name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public String getDirPath() {
        return dirPath;
    }
}
