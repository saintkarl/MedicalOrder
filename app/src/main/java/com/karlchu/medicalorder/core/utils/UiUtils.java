package com.karlchu.medicalorder.core.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by hieu on 6/27/2016.
 */
public class UiUtils {
    public static void setListViewHeightBasedOnChildren(ListView listView, int maxHeight) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        totalHeight = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

//        if (totalHeight < maxHeight) {
//            ViewGroup.LayoutParams params = listView.getLayoutParams();
//            params.height = totalHeight;
//            listView.setLayoutParams(params);
//            listView.requestLayout();
//        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static int getHeightParamInPixel(ListView listView) {
        return listView.getLayoutParams().height;
    }
}
