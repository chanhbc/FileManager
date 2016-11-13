package com.chanhbc.filemanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanhbc.filemanager.App;
import com.chanhbc.filemanager.R;
import com.chanhbc.filemanager.manager.FileManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<File> files;
    private FileManager fileManager;
    private LayoutInflater inflater;
    private File file;

    public ListViewAdapter(FileManager fileManager, ArrayList<File> files) {
        inflater = LayoutInflater.from(App.getContext());
        this.fileManager = fileManager;
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public File getItem(int i) {
        return files.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_file, viewGroup, false);
            holder = new Holder();
            holder.ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
            holder.tvNameFile = (TextView) view.findViewById(R.id.tvNameFile);
            holder.tvTimeFile = (TextView) view.findViewById(R.id.tvTimeFile);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        file = files.get(i);
        fileManager.setFile(file);
        //file name
        fileManager.cutFileName(fileManager.getFileName());
        if (fileManager.isDirectory(file)) {
            holder.tvNameFile.setText(fileManager.getFileName() + " (" + fileManager.getNumberFileChild() + ")");
        } else {
            holder.tvNameFile.setText(fileManager.getFileName());
        }
        //file icon
        if (fileManager.isDirectory(file)) {
            holder.ivIcon.setImageBitmap(null);
            holder.ivIcon.setImageResource(R.drawable.bm_folder);
        } else if (fileManager.isFileMusic(file)) {
            holder.ivIcon.setImageBitmap(null);
            holder.ivIcon.setImageResource(R.drawable.bm_music);
        } else if (fileManager.isFileImage(file)) {
            holder.ivIcon.setImageResource(0);
            Picasso.with(App.getContext()).load(fileManager.getFile()).into(holder.ivIcon);
        } else if (fileManager.isFileVideo(file)) {
            holder.ivIcon.setImageBitmap(null);
            holder.ivIcon.setImageResource(R.drawable.ic_mp4);
        } else if (fileManager.isFileDocument(file)) {
            if (file.getName().toString().endsWith(".doc") || file.getName().toString().endsWith(".docx")) {
                holder.ivIcon.setImageBitmap(null);
                holder.ivIcon.setImageResource(R.drawable.ic_doc);
            } else if (file.getName().toString().endsWith(".pdf")) {
                holder.ivIcon.setImageBitmap(null);
                holder.ivIcon.setImageResource(R.drawable.ic_pdf);
            } else if (file.getName().toString().endsWith(".ppt") || file.getName().toString().endsWith(".pptx")) {
                holder.ivIcon.setImageBitmap(null);
                holder.ivIcon.setImageResource(R.drawable.ic_ppt);
            } else if (file.getName().toString().endsWith(".xls") || file.getName().toString().endsWith(".xlsx")) {
                holder.ivIcon.setImageBitmap(null);
                holder.ivIcon.setImageResource(R.drawable.ic_xls);
            } else if (file.getName().toString().endsWith(".txt")) {
                holder.ivIcon.setImageBitmap(null);
                holder.ivIcon.setImageResource(R.drawable.ic_txt);
            }
        } else if (file.getName().toString().endsWith(".zip")) {
            holder.ivIcon.setImageBitmap(null);
            holder.ivIcon.setImageResource(R.drawable.ic_zip);
        } else if (file.getName().toString().endsWith(".rar")) {
            holder.ivIcon.setImageBitmap(null);
            holder.ivIcon.setImageResource(R.drawable.ic_rar);
        } else if (fileManager.isFileApp(file)) {
            holder.ivIcon.setImageBitmap(null);
            holder.ivIcon.setImageResource(R.drawable.ic_apk);
        } else {
            holder.ivIcon.setImageBitmap(null);
            holder.ivIcon.setImageResource(R.drawable.ic_file);
        }
        //file time
        if (file.isFile()) {
            holder.tvTimeFile.setText(fileManager.getDateTimeFile(file) + " | " + fileManager.getSize(file));
        } else {
            holder.tvTimeFile.setText(fileManager.getDateTimeFile(file));
        }
        return view;
    }

    private class Holder {
        private ImageView ivIcon;
        private TextView tvNameFile;
        private TextView tvTimeFile;
    }
}
