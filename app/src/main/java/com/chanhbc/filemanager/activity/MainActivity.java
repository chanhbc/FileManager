package com.chanhbc.filemanager.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chanhbc.filemanager.R;
import com.chanhbc.filemanager.adapter.DialogAdapter;
import com.chanhbc.filemanager.adapter.DialogNewFolder;
import com.chanhbc.filemanager.adapter.DialogOption;
import com.chanhbc.filemanager.adapter.DialogProperties;
import com.chanhbc.filemanager.adapter.DialogRename;
import com.chanhbc.filemanager.adapter.DialogSort;
import com.chanhbc.filemanager.adapter.ListViewAdapter;
import com.chanhbc.filemanager.adapter.ListViewNavMenu;
import com.chanhbc.filemanager.manager.FileManager;
import com.chanhbc.filemanager.manager.MenuItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, DialogAdapter.OnReceiveDataListener, DialogRename.OnReceiveDataListener, DialogProperties.OnReceiveDataListener, View.OnClickListener, DialogOption.OnReceiveDataListener, DialogNewFolder.OnReceiveDataListener, DialogSort.OnReceiveDataListener, TextWatcher {
    private ListView lvFileManager;
    private ListView lvNavMenu;
    private ListViewAdapter listViewAdapter;
    private FileManager fileManager;
    private DialogAdapter dialogAdapter;
    private DialogRename dialogRename;
    private DialogNewFolder dialogNewFolder;
    private DialogProperties dialogProperties;
    private DialogSort dialogSort;
    private DialogOption dialogOption;
    private ListViewNavMenu navMenu;
    private String path;
    private String pathCurrent;
    private File file;
    private File fileCurrent;
    private File fileCopy;
    private int indexPosition;
    private static int countPermission;
    private ImageView ivOption;
    private ImageView ivSearch;
    private ImageView ivMenu;
    private RelativeLayout rlMenu;
    private RelativeLayout rlSearch;
    private ImageView ivDel;
    private ImageView ivBack;
    private EditText etSearch;
    private String textSearchCurrent;
    private ArrayList<File> files;
    private ArrayList<File> listFileSearch;
    private ArrayList<File> listFileFavorites;
    private ArrayList<MenuItem> items;
    private DrawerLayout dlMenu;
    private ProgressBar prbSize;
    private TextView tvSize;
    private TextView tvTitle;
    private LinearLayout llNav;
    private CircleImageView civHome;
    private boolean isCut = false;
    private int sort = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
        ivOption = (ImageView) findViewById(R.id.ivOption);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        ivDel = (ImageView) findViewById(R.id.ivDel);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        etSearch = (EditText) findViewById(R.id.etSearch);
        dlMenu = (DrawerLayout) findViewById(R.id.drawerLayout);
        prbSize = (ProgressBar) findViewById(R.id.prbSize);
        tvSize = (TextView) findViewById(R.id.tvSize);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivOption.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivMenu.setOnClickListener(this);
        ivDel.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        etSearch.addTextChangedListener(this);
        rlMenu = (RelativeLayout) findViewById(R.id.rlMenu);
        rlSearch = (RelativeLayout) findViewById(R.id.rlSearch);
        rlSearch.setVisibility(View.GONE);
        civHome = (CircleImageView) findViewById(R.id.civHome);
        civHome.setOnClickListener(this);
        lvFileManager = (ListView) findViewById(R.id.lvFileManager);
        lvNavMenu = (ListView) findViewById(R.id.lvMenu);
        llNav = (LinearLayout) findViewById(R.id.llNav);
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileManager = new FileManager(path);

        items = new ArrayList<>();
        items.add(new MenuItem("Hình ảnh", R.drawable.ic_images));
        items.add(new MenuItem("Video", R.drawable.ic_video));
        items.add(new MenuItem("Nhạc", R.drawable.ic_music1));
        items.add(new MenuItem("Ứng dụng", R.drawable.ic_android));
        items.add(new MenuItem("Tài liệu", R.drawable.ic_document));
        items.add(new MenuItem("Yêu thích", R.drawable.ic_star));
        navMenu = new ListViewNavMenu(items);
        lvNavMenu.setAdapter(navMenu);
        lvNavMenu.setOnItemClickListener(this);
        llNav.setOnClickListener(this);
        countPermission = 0;
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissionStorage();
        } else {
            fileManager.allFile(Environment.getExternalStorageDirectory().getAbsolutePath());
            files = fileManager.getListFile();
            loadListFile(files);
        }
        dialogSort = new DialogSort(MainActivity.this);
        dialogSort.setOnReceiveDataListener(this);
        dialogAdapter = new DialogAdapter(MainActivity.this, "");
        dialogAdapter.setOnReceiveDataListener(this);
        dialogOption = new DialogOption(MainActivity.this);
        dialogOption.setOnReceiveDataListener(this);
        listFileFavorites = new ArrayList<>();
    }

    private boolean checkPermissionStorage() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            fileManager.allFile(Environment.getExternalStorageDirectory().getAbsolutePath());
            files = fileManager.getListFile();
            loadListFile(files);
        }
        return true;
    }

    private void loadListFile(ArrayList<File> files) {
        sortFiles(sort);
        listViewAdapter = new ListViewAdapter(fileManager, files);
        if (file != null)
            pathCurrent = file.getPath();
        indexPosition = files.indexOf(fileCurrent);
        lvFileManager.setAdapter(listViewAdapter);
        lvFileManager.setSelection(indexPosition);
        lvFileManager.setOnItemClickListener(this);
        lvFileManager.setOnItemLongClickListener(this);
        File path1 = new File("/sdcard/");
        long free = (100 * path1.getFreeSpace()) / (1024 * 1024 * 1024);
        long total = (100 * path1.getTotalSpace()) / (1024 * 1024 * 1024);
        prbSize.setMax((int) total);
        prbSize.setProgress((int) (total - free));
        double a = (double) free / 100;
        double b = (double) total / 100;
        tvSize.setText(a + " GB free of " + b + " GB");
    }

    private void openImage(File file) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "image/*");
        startActivity(intent);
    }

    private void openMusic(File file) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "audio/*");
        startActivity(intent);
    }

    private void openVideo(File file) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "video/*");
        startActivity(intent);
    }

    private void openDocument(File file) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        startActivity(intent);
    }

    private void renameFile(final String fileRename) {
        if (fileRename.startsWith(".") || fileRename.length() == 0) {
            Toast.makeText(this, "Tên tập tin không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (file.isFile()) {
            if (!fileRename.substring(fileRename.indexOf("."), fileRename.length()).
                    equals(file.getName().substring(file.getName().indexOf("."), file.getName().length()))) {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("Xác nhận đổi tên")
                        .setMessage("Bạn đang thay đổi đuôi mở rộng tập tin, có thể khiến tập tin không thể nhận dạng hoặc đọc được. Bạn có muốn thay đổi không?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //đổi tên
                                File f = new File(file.getParent().toString() + "/" + fileRename);
                                ArrayList<File> files1 = fileManager.getAllFile();
                                files1.remove(file);
                                files1.add(f);
                                fileManager.setAllFile(files1);
                                file.renameTo(f);
                                loadListFile(files);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //no to do
                            }
                        })
                        .create();
                alertDialog.show();
            } else {
                File f = new File(file.getParent().toString() + "/" + fileRename);
                ArrayList<File> files1 = fileManager.getAllFile();
                files1.remove(file);
                files1.add(f);
                fileManager.setAllFile(files1);
                files.remove(file);
                file.renameTo(f);
                files.add(file);
                loadListFile(files);
            }
        } else {
            File f = new File(file.getParent().toString() + "/" + fileRename);
            ArrayList<File> files1 = fileManager.getAllFile();
            files1.remove(file);
            files1.add(f);
            fileManager.setAllFile(files1);
            files.remove(file);
            file.renameTo(f);
            files.add(file);
            loadListFile(files);
        }
        dialogRename.dismiss();
    }

    private void sortFiles(int sort) {
        if (sort == 1) {
            files = fileManager.sortNameFile(files);
            files = fileManager.sortNameFile(files);
        }
        if (sort == 2) {
            files = fileManager.sortSizeFile(files);
            files = fileManager.sortSizeFile(files);
        }
        if (sort == 3) {
            files = fileManager.sortDateFile(files);
            files = fileManager.sortDateFile(files);
        }
        if (sort == 4) {
            files = fileManager.sortTypeFile(files);
            files = fileManager.sortTypeFile(files);
        }
    }

    private void deleteFile() {
        dialogAdapter.dismiss();
        AlertDialog alertDialogDel = new AlertDialog.Builder(this)
                .setTitle("Xóa")
                .setMessage("Bạn chắc chắn muốn xóa?\n\n" + file.getName())
                .setCancelable(true)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (file.exists()) {
                            ArrayList<File> files1 = fileManager.getAllFile();
                            files1.remove(file);
                            fileManager.setAllFile(files1);
                            files.remove(file);
                            deleteFileDirectory(file);
                            loadListFile(files);
                        }
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // no to do
                    }
                })
                .create();
        alertDialogDel.show();
    }

    private void deleteFileDirectory(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteFileDirectory(files[i]);
                } else {
                    if (files[i].exists()) {
                        files[i].delete();
                    }
                }
            }
        }
        if (file.exists()) {
            file.delete();
        }
    }

    private void shareFile() {
        Intent intent = new Intent();
        Uri uri = Uri.parse("file://" + file.getPath());
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Chia sẻ \"" + file.getName() + "\" với"));
    }

    private void pasteFile(String path, File fCopy) {
        try {
            File filePaste = new File(path + "/" + fCopy.getName());
            if (!filePaste.exists()) {
                if (fCopy.isFile()) {
                    filePaste.createNewFile();
                } else if (fCopy.isDirectory()) {
                    filePaste.mkdir();
                    File[] listFileCopy = fCopy.listFiles();
                    for (int i = 0; i < listFileCopy.length; i++) {
                        File f = listFileCopy[i];
                        pasteFile(filePaste.getAbsolutePath(), f);
                    }
                }
            }
            InputStream is = new FileInputStream(fCopy);
            OutputStream os = new FileOutputStream(filePaste);
            byte[] bytes;
            if (fileManager.getSize(fileCopy).contains("MB")) {
                bytes = new byte[1024 * 1024];
            } else {
                bytes = new byte[1024];
            }
            int length;
            while ((length = is.read(bytes)) > 0) {
                os.write(bytes, 0, length);
            }
            ArrayList<File> files1 = fileManager.getAllFile();
            files1.add(filePaste);
            fileManager.setAllFile(files1);
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isCut == true) {
            ArrayList<File> files1 = fileManager.getAllFile();
            files1.remove(file);
            fileManager.setAllFile(files1);
            deleteFileDirectory(fileCopy);
            isCut = false;
            TextView tvPaste = dialogAdapter.getTvPaste();
            tvPaste.setTextColor(0xff727272);
            tvPaste.setEnabled(false);
            TextView tvPaste2 = dialogOption.getTvPaste();
            tvPaste2.setTextColor(0xff727272);
            tvPaste2.setEnabled(false);
        }
        files = fileManager.getListFile();
        loadListFile(files);
    }

    private void addFileFavorites(File file) {
        listFileFavorites.add(file);
        fileManager.addFileListFavorites(file);
    }

    private void removeFileFavorites(File file) {
        listFileFavorites.remove(file);
        fileManager.removeFileListFavorites(file);
    }

    private void loadItemFileFromMenu(String key) {
        ArrayList<File> files1 = fileManager.getAllFile();
        ArrayList<File> files2 = new ArrayList<>();
        tvTitle.setText(key);
        if (key.equals("Hình ảnh")) {
            for (int i = 0; i < files1.size(); i++) {
                if (fileManager.isFileImage(files1.get(i))) {
                    files2.add(files1.get(i));
                }
            }
        } else if (key.equals("Video")) {
            for (int i = 0; i < files1.size(); i++) {
                if (fileManager.isFileVideo(files1.get(i))) {
                    files2.add(files1.get(i));
                }
            }
        } else if (key.equals("Tài liệu")) {
            for (int i = 0; i < files1.size(); i++) {
                if (fileManager.isFileDocument(files1.get(i))) {
                    files2.add(files1.get(i));
                }
            }
        } else if (key.equals("Ứng dụng")) {
            for (int i = 0; i < files1.size(); i++) {
                if (fileManager.isFileApp(files1.get(i))) {
                    files2.add(files1.get(i));
                }
            }
        } else if (key.equals("Nhạc")) {
            for (int i = 0; i < files1.size(); i++) {
                if (fileManager.isFileMusic(files1.get(i))) {
                    files2.add(files1.get(i));
                }
            }
        } else if (key.equals("Yêu thích")) {
            listFileFavorites = fileManager.getFileListFavorites("ListFavorites");
            files = listFileFavorites;
            loadListFile(files);
            return;
        }
        files = files2;
        loadListFile(files);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == 0) {
                    fileManager.allFile(Environment.getExternalStorageDirectory().getAbsolutePath());
                    files = fileManager.getListFile();
                    loadListFile(files);
                    return;
                } else if (grantResults[0] == -1) {
                    if (countPermission == 2) {
                        finish();
                    }
                    countPermission++;
                    checkPermissionStorage();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.lvFileManager:
                path = files.get(position).getAbsolutePath();
                File file = new File(path);
                if (file.isDirectory()) {
                    fileManager.setPath(path);
                    files = fileManager.getListFile();
                    loadListFile(files);
                } else if (file.isFile()) {
                    if (fileManager.isFileImage(file)) {
                        openImage(file);
                    } else if (fileManager.isFileMusic(file)) {
                        openMusic(file);
                    } else if (fileManager.isFileVideo(file)) {
                        openVideo(file);
                    } else if (fileManager.isFileDocument(file)) {
                        openDocument(file);
                    } else {
                        Toast.makeText(MainActivity.this, "Không có chương trình thích hợp để mở tập tin này", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.lvMenu:
                MenuItem item = items.get(position);
                dlMenu.closeDrawers();
                loadItemFileFromMenu(item.getName());
                break;
            default:
                Toast.makeText(this, "default", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        file = files.get(position);
        TextView tvTitle = dialogAdapter.getTvTitle();
        tvTitle.setText(file.getName());
        TextView tv = dialogAdapter.getTvFavorites();
        if (fileManager.getFileListFavorites("ListFavorites").contains(file)) {
            tv.setText("Xóa khỏi d.s yêu thích");
        } else {
            tv.setText("Thêm vào d.s yêu thích");
        }
        dialogAdapter.show();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (dlMenu.isDrawerOpen(GravityCompat.START)) {
            dlMenu.closeDrawers();
            return;
        }
        if (!tvTitle.getText().equals("Quản lí tập tin")) {
            files = fileManager.getListFile();
            loadListFile(files);
            tvTitle.setText("Quản lí tập tin");
            return;
        }
        if (rlSearch.getVisibility() == View.VISIBLE) {
            rlSearch.setVisibility(View.GONE);
            rlMenu.setVisibility(View.VISIBLE);
            files = fileManager.getListFile();
            loadListFile(files);
            return;
        }
        if (fileManager.getPathParent().equals(new File(Environment.getExternalStorageDirectory().getAbsolutePath()).getParent())) {
            super.onBackPressed();
        } else {
            fileCurrent = new File(fileManager.getPath());
            fileManager.setPath(fileManager.getPathParent());
            files = fileManager.getListFile();
            loadListFile(files);
        }
    }

    @Override
    public void onReceiveData(String receiveData) {
        switch (receiveData) {
            case DialogAdapter.TXT_SHARE:
                shareFile();
                dialogAdapter.dismiss();
                break;
            case DialogAdapter.TXT_DELETE:
                deleteFile();
                break;
            case DialogAdapter.TXT_RENAME:
                dialogAdapter.dismiss();
                dialogRename = new DialogRename(MainActivity.this, file.getName());
                dialogRename.show();
                dialogRename.setOnReceiveDataListener(this);
                break;
            case DialogAdapter.TXT_CUT:
                dialogAdapter.dismiss();
                TextView tvPaste1 = dialogAdapter.getTvPaste();
                tvPaste1.setTextColor(0xff000000);
                tvPaste1.setEnabled(true);
                TextView tvPaste3 = dialogAdapter.getTvPaste();
                tvPaste3.setTextColor(0xff000000);
                tvPaste3.setEnabled(true);
                isCut = true;
                break;
            case DialogAdapter.TXT_COPY:
                dialogAdapter.dismiss();
                TextView tvPaste = dialogAdapter.getTvPaste();
                TextView tvPaste2 = dialogOption.getTvPaste();
                tvPaste.setTextColor(0xff000000);
                tvPaste.setEnabled(true);
                tvPaste2.setTextColor(0xff000000);
                tvPaste2.setEnabled(true);
                isCut = false;
                fileCopy = file;
                break;
            case DialogAdapter.TXT_PASTE:
                dialogAdapter.dismiss();
                pasteFile(file.getPath(), fileCopy);
                break;
            case DialogAdapter.TXT_FAVORITES:
                dialogAdapter.dismiss();
                TextView tv = dialogAdapter.getTvFavorites();
                if (tv.getText().equals("Thêm vào d.s yêu thích")) {
                    addFileFavorites(file);
                } else {
                    removeFileFavorites(file);
                }
                break;
            case DialogAdapter.TXT_PROPERTIES:
                dialogAdapter.dismiss();
                dialogProperties = new DialogProperties(MainActivity.this, file);
                dialogProperties.show();
                dialogProperties.setOnReceiveDataListener(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onReceiveDataRename(String receiveData) {
        switch (receiveData) {
            case DialogRename.BTN_CANCEL:
                dialogRename.dismiss();
                break;
            case DialogRename.BTN_OK:
                final String fileRename = dialogRename.getEtRename().getText().toString();
                renameFile(fileRename);
                break;
            default:
                break;
        }
    }

    @Override
    public void onReceiveDataProperties(String receiveData) {
        switch (receiveData) {
            case DialogProperties.BTN_OK:
                dialogProperties.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivOption:
                WindowManager.LayoutParams wml = dialogOption.getWindow().getAttributes();
                wml.gravity = Gravity.RIGHT | Gravity.TOP;
                wml.x = 20;
                wml.y = 100;
                dialogOption.show();
                break;
            case R.id.ivSearch:
                rlMenu.setVisibility(View.GONE);
                rlSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.ivMenu:
                dlMenu.openDrawer(GravityCompat.START);
                break;
            case R.id.ivBack:
                etSearch.setText("");
                rlMenu.setVisibility(View.VISIBLE);
                rlSearch.setVisibility(View.GONE);
                if (tvTitle.getText().equals("Quản lí tập tin")) {
                    files = fileManager.getListFile();
                    loadListFile(files);
                } else {
                    loadItemFileFromMenu(tvTitle.getText().toString());
                }
                break;
            case R.id.ivDel:
                etSearch.setText("");
                break;
            case R.id.civHome:
                dlMenu.closeDrawers();
                fileManager.setPath(Environment.getExternalStorageDirectory().getAbsolutePath());
                files = fileManager.getListFile();
                loadListFile(files);
                tvTitle.setText("Quản lí tập tin");
            default:
                break;
        }
    }

    @Override
    public void onReceiveDataOption(String receiveData) {
        switch (receiveData) {
            case DialogOption.TXT_ADDFOLDER:
                dialogOption.dismiss();
                dialogNewFolder = new DialogNewFolder(MainActivity.this, "New Folder");
                dialogNewFolder.setOnReceiveDataListener(this);
                dialogNewFolder.show();
                break;
            case DialogOption.TXT_SORT:
                dialogOption.dismiss();
                dialogSort.show();
                break;
            case DialogOption.TXT_PASTE:
                dialogOption.dismiss();
                Toast.makeText(MainActivity.this, pathCurrent, Toast.LENGTH_SHORT).show();
                pasteFile(path, fileCopy);
                break;
            default:
                break;
        }
    }

    @Override
    public void onReceiveDataNewFile(String receiveData) {
        switch (receiveData) {
            case DialogNewFolder.BTN_OK:
                TextView txt = dialogNewFolder.getEtFileName();
                File f = new File(fileManager.getPath() + "/" + txt.getText().toString());
                if (!f.exists()) {
                    f.mkdir();
                    ArrayList<File> files1 = fileManager.getAllFile();
                    files1.add(f);
                    fileManager.setAllFile(files1);
                    files = fileManager.getListFile();
                    loadListFile(files);
                }
                dialogNewFolder.dismiss();
                break;
            case DialogNewFolder.BTN_CANCEL:
                dialogNewFolder.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onReceiveDataSort(String receiveData) {
        switch (receiveData) {
            case DialogSort.RD_SORTNAME:
                sort = 1;
                dialogSort.dismiss();
                files = fileManager.sortNameFile(files);
                loadListFile(files);
                break;
            case DialogSort.RD_SORTSIZE:
                sort = 2;
                dialogSort.dismiss();
                files = fileManager.sortSizeFile(files);
                loadListFile(files);
                break;
            case DialogSort.RD_SORTDATE:
                sort = 3;
                dialogSort.dismiss();
                files = fileManager.sortDateFile(files);
                loadListFile(files);
                break;
            case DialogSort.RD_SORTTYPE:
                sort = 4;
                dialogSort.dismiss();
                files = fileManager.sortTypeFile(files);
                loadListFile(files);
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        textSearchCurrent = etSearch.getText().toString();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (textSearchCurrent != etSearch.getText().toString()) {
            String s = etSearch.getText().toString();
            listFileSearch = fileManager.searchFile(s);
            files = listFileSearch;
            loadListFile(files);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
