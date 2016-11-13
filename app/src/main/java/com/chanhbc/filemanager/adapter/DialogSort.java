package com.chanhbc.filemanager.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.chanhbc.filemanager.R;

public class DialogSort extends Dialog implements View.OnClickListener {
    private RadioButton rdSortName;
    private RadioButton rdSortSize;
    private RadioButton rdSortDate;
    private RadioButton rdSortType;
    public static final String RD_SORTNAME = "SORTNAME";
    public static final String RD_SORTSIZE = "SORTSIZE";
    public static final String RD_SORTDATE = "SORTDATE";
    public static final String RD_SORTTYPE = "SORTTYPE";
    private OnReceiveDataListener onReceiveDataListener;

    public DialogSort(Context context) {
        super(context);
        setContentView(R.layout.item_sort);
        initializeComponents();
    }

    private void initializeComponents() {
        rdSortName = (RadioButton) findViewById(R.id.rbSortName);
        rdSortSize = (RadioButton) findViewById(R.id.rbSortSize);
        rdSortDate = (RadioButton) findViewById(R.id.rbSortDate);
        rdSortType = (RadioButton) findViewById(R.id.rbSortType);
        rdSortName.setOnClickListener(this);
        rdSortSize.setOnClickListener(this);
        rdSortDate.setOnClickListener(this);
        rdSortType.setOnClickListener(this);
        rdSortName.setChecked(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbSortName:
                onReceiveDataListener.onReceiveDataSort(RD_SORTNAME);
                rdSortName.setChecked(true);
                break;
            case R.id.rbSortSize:
                onReceiveDataListener.onReceiveDataSort(RD_SORTSIZE);
                rdSortSize.setChecked(true);
                break;
            case R.id.rbSortDate:
                onReceiveDataListener.onReceiveDataSort(RD_SORTDATE);
                rdSortDate.setChecked(true);
                break;
            case R.id.rbSortType:
                onReceiveDataListener.onReceiveDataSort(RD_SORTTYPE);
                rdSortType.setChecked(true);
                break;
            default:
                break;
        }
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    public interface OnReceiveDataListener {
        void onReceiveDataSort(String receiveData);
    }
}
