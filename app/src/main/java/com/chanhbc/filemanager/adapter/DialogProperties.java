package com.chanhbc.filemanager.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanhbc.filemanager.R;
import com.chanhbc.filemanager.manager.FileManager;

import java.io.File;

public class DialogProperties extends Dialog implements View.OnClickListener {
    public static final String BTN_OK = "OK";
    private Button btnOk;
    private TextView tvName;
    private TextView tvType;
    private TextView tvLastModified;
    private TextView tvSize;
    private TextView tvPath;
    private FileManager fileManager;
    private File file;
    private OnReceiveDataListener onReceiveDataListener;

    public DialogProperties(Context context, File file) {
        super(context);
        this.file = file;
        setContentView(R.layout.item_properties);
        initializeComponents();
    }

    private void initializeComponents() {
        fileManager = new FileManager(file);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);

        tvName = (TextView) findViewById(R.id.tvTitle);
        tvType = (TextView) findViewById(R.id.tvType);
        tvLastModified = (TextView) findViewById(R.id.tvLastModified);
        tvSize = (TextView) findViewById(R.id.tvSize);
        tvPath = (TextView) findViewById(R.id.tvPath);
        tvName.setText(file.getName());
        if (file.isDirectory()) {
            tvType.setText("Loại: Thư mục");
        } else if (file.isFile()) {
            tvType.setText("Loại: Tập tin");
        }
        tvLastModified.setText("Sửa gần nhất: " + fileManager.getDateTimeFile(file));
        tvSize.setText("Kích thước: " + fileManager.getSize(file));
        tvPath.setText("Path: " + file.getParent());
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
            case R.id.btnOk:
                onReceiveDataListener.onReceiveDataProperties(BTN_OK);
                break;
            default:
                break;
        }
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    public interface OnReceiveDataListener {
        void onReceiveDataProperties(String receiveData);
    }
}
