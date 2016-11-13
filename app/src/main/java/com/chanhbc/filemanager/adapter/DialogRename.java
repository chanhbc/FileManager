package com.chanhbc.filemanager.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanhbc.filemanager.R;

public class DialogRename extends Dialog implements View.OnClickListener {
    private EditText etRename;
    private Button btnCancel;
    private Button btnOk;
    private TextView tvTitleFile;
    public static final String BTN_CANCEL = "CANCEL";
    public static final String BTN_OK = "OK";
    private String fileName;
    private OnReceiveDataListener onReceiveDataListener;

    public DialogRename(Context context, String fileName) {
        super(context);
        setContentView(R.layout.item_rename_file);
        this.fileName = fileName;
        initializeComponents();
    }

    private void initializeComponents() {
        etRename = (EditText) findViewById(R.id.etRename);
        etRename.setText(fileName);
        try {
            etRename.setSelection(0, fileName.indexOf("."));
        } catch (IndexOutOfBoundsException e) {
            etRename.setSelection(0, fileName.length());
        }
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        tvTitleFile = (TextView) findViewById(R.id.tvTitleFile);
    }

    public EditText getEtRename() {
        return etRename;
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    public interface OnReceiveDataListener {
        void onReceiveDataRename(String receiveData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                onReceiveDataListener.onReceiveDataRename(BTN_CANCEL);
                break;
            case R.id.btnOk:
                onReceiveDataListener.onReceiveDataRename(BTN_OK);
                break;
            default:
                break;
        }
    }
}
