package Utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by korgua on 2017. 12. 06..
 */

public class ViewReplacer {
    private static ViewGroup getParent(View view) {
        return (ViewGroup)view.getParent();
    }

    private static void removeView(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }

    public static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null) {
            return;
        }
        final int index = parent.indexOfChild(currentView);
        removeView(currentView);
        removeView(newView);
        parent.addView(newView, index);
    }
}