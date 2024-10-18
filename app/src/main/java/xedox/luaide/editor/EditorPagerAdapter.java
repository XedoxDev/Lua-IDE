package xedox.luaide.editor;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditorPagerAdapter extends FragmentStateAdapter {

    private List<EditorFragment> editors;

    public EditorPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
        editors = new ArrayList<>();
    }

    @Override
    public Fragment createFragment(int position) {
        return editors.get(position);
    }

    @Override
    public int getItemCount() {
        return editors.size();
    }

    public void newEditor(File file) {
        editors.add(new EditorFragment(file));
    }

    public List<EditorFragment> getEditors() {
        return this.editors;
    }
    
    public void removeEditor(int id) {
        editors.remove(id);
    }
}
