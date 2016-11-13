package com.chanhbc.filemanager.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanhbc.filemanager.R;

public class DialogOption extends Dialog implements View.OnClickListener {
    private OnReceiveDataListener onReceiveDataListener;
    private TextView tvAddFolder;
    private TextView tvSort;
    private TextView tvPaste;
    public static final String TXT_ADDFOLDER = "ADDFOLDER";
    public static final String TXT_SORT = "SORT";
    public static final String TXT_PASTE = "PASTE";

    public DialogOption(Context context) {
        super(context);
        setContentView(R.layout.item_option);
        initializeComponents();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void initializeComponents() {
        tvAddFolder = (TextView) findViewById(R.id.tvAddFolder);
        tvSort = (TextView) findViewById(R.id.tvSort);
        tvPaste = (TextView) findViewById(R.id.tvPaste2);
        tvPaste.setEnabled(false);
        tvPaste.setTextColor(0xff727272);
        tvAddFolder.setOnClickListener(this);
        tvSort.setOnClickListener(this);
        tvPaste.setOnClickListener(this);
    }

    public TextView getTvPaste() {
        return tvPaste;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAddFolder:
                onReceiveDataListener.onReceiveDataOption(TXT_ADDFOLDER);
                break;
            case R.id.tvSort:
                onReceiveDataListener.onReceiveDataOption(TXT_SORT);
                break;
            case R.id.tvPaste2:
                onReceiveDataListener.onReceiveDataOption(TXT_PASTE);
                break;
            default:
                break;
        }
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    public interface OnReceiveDataListener {
        void onReceiveDataOption(String receiveData);
    }
}
