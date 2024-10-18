package xedox.luaide;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class App extends Application {

    private static Context context;
    public static final int REQUEST_CODE_MANAGE = 1;
    public static final String projectsPath = "L-IDE/projects";
    public static final File projectsDir =
            new File(Environment.getExternalStorageDirectory(), projectsPath);

    static {
        if (!projectsDir.exists()) {
            projectsDir.mkdirs();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }

    public static boolean requestManagePermission(AppCompatActivity act) {
        if (Environment.isExternalStorageManager()) {
            return true;
        } else {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                act.startActivityForResult(intent, REQUEST_CODE_MANAGE);
                return false;
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                act.startActivityForResult(intent, REQUEST_CODE_MANAGE);
                return false;
            }
        }
    }
}
