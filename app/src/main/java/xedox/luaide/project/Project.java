package xedox.luaide.project;

import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import xedox.luaide.App;

public class Project {

    private String name;
    private ProjectType type;
    private File path;
    private List<File> files;

    public Project(ProjectType type, String name) {
        this.type = type;
        this.name = name;
        this.path = new File(App.projectsDir, name);
        this.files = new ArrayList<>();

        make();

        for (File file : path.listFiles()) {
            addFile(file);
        }
    }

    public void make() {
        if (!path.exists()) {
            path.mkdir();
        }
        File mainLuaFile = new File(path, "main.lua");
        if (!mainLuaFile.exists()) {
            try {
                mainLuaFile.createNewFile();
                files.add(mainLuaFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addFile(File file) {
        if (file.getAbsolutePath().startsWith(path.getAbsolutePath())) {
            files.add(file);
        } else {
            System.err.println("File not within project directory.");
        }
    }

    public void removeFile(String name) {
        File file = new File(path, name);
        if (file.exists()) {
            file.delete();
            files.remove(file);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        this.path = new File(App.projectsDir, name);
    }

    public File getPath() {
        return this.path;
    }

    public void setPath(File path) {
        this.path = path;
        this.name = path.getName();
    }

    public List<File> getFiles() {
        return this.files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public File getMain() {
        for (File file : files) {
            if (file.getName() == "main.lua") {
                return file;
            }
        }
        return null;
    }
}
