package com.chanhbc.filemanager.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chanhbc.filemanager.R;

public class DialogAdapter extends Dialog implements View.OnClickListener {
    private OnReceiveDataListener onReceiveDataListener;
    private TextView tvShare;
    private TextView tvDelete;
    private TextView tvRename;
    private TextView tvTitle;
    private TextView tvCut;
    private TextView tvCopy;
    private TextView tvPaste;
    private TextView tvFavorites;
    private TextView tvProperties;
    private String title;
    public static final String TXT_SHARE = "SHARE";
    public static final String TXT_RENAME = "RENAME";
    public static final String TXT_DELETE = "DELETE";
    public static final String TXT_CUT = "CUT";
    public static final String TXT_COPY = "COPY";
    public static final String TXT_PASTE = "PASTE";
    public static final String TXT_FAVORITES = "FAVORITES";
    public static final String TXT_PROPERTIES = "PROPERTIES";

    public DialogAdapter(Context context, String title) {
        super(context);
        setContentView(R.layout.item_dialog);
        this.title = title;
        initializeComponents();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("abc");
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void initializeComponents() {
        tvShare = (TextView) findViewById(R.id.tvShare);
        tvDelete = (TextView) findViewById(R.id.tvDelete);
        tvRename = (TextView) findViewById(R.id.tvRename);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvCopy = (TextView) findViewById(R.id.tvCopy);
        tvCut = (TextView) findViewById(R.id.tvCut);
        tvPaste = (TextView) findViewById(R.id.tvPaste);
        tvFavorites = (TextView) findViewById(R.id.tvFavorites);
        tvProperties = (TextView) findViewById(R.id.tvProperties);
        tvPaste.setTextColor(0xff727272);
        tvPaste.setEnabled(false);
        tvTitle.setText(title);
        tvShare.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        tvRename.setOnClickListener(this);
        tvCopy.setOnClickListener(this);
        tvCut.setOnClickListener(this);
        tvPaste.setOnClickListener(this);
        tvFavorites.setOnClickListener(this);
        tvProperties.setOnClickListener(this);
    }

    public TextView getTvPaste() {
        return tvPaste;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvFavorites() {
        return tvFavorites;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvShare:
                onReceiveDataListener.onReceiveData(TXT_SHARE);
                break;
            case R.id.tvDelete:
                onReceiveDataListener.onReceiveData(TXT_DELETE);
                break;
            case R.id.tvRename:
                onReceiveDataListener.onReceiveData(TXT_RENAME);
                break;
            case R.id.tvCut:
                onReceiveDataListener.onReceiveData(TXT_CUT);
                break;
            case R.id.tvCopy:
                onReceiveDataListener.onReceiveData(TXT_COPY);
                break;
            case R.id.tvPaste:
                onReceiveDataListener.onReceiveData(TXT_PASTE);
                break;
            case R.id.tvFavorites:
                onReceiveDataListener.onReceiveData(TXT_FAVORITES);
                break;
            case R.id.tvProperties:
                onReceiveDataListener.onReceiveData(TXT_PROPERTIES);
                break;
            default:
                break;
        }
    }

    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    public interface OnReceiveDataListener {
        void onReceiveData(String receiveData);
    }
}
