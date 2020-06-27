package ahmad.egypt.myfilechooser;


import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.text.DateFormat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import java.text.*;
import java.math.*;
import android.os.*;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import ahmad.egypt.myfilechooser.adapter.FileArrayAdapter;
import ahmad.egypt.myfilechooser.model.FileInfo;
import ahmad.egypt.myfilechooser.model.FileItem;

public class FileChooserActivity extends AppCompatListActivity {

    private File currentDir;
    private FileArrayAdapter adapter;
    private String sdCardDir;
    private int REQUEST_CODE=0x000001;

    private static String DIR_PATH="DIR_PATH";
    private static String FILE_FULL_PATH="FILE_FULL_PATH";
    private static String FILE_NAME="FILE_NAME";
    private static String FILE_INFO="FILE_INFO";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();
    }

    private void initUI(){
        currentDir = Environment.getExternalStorageDirectory();
        sdCardDir=currentDir.getName();
        fill(currentDir);
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            initUI();
        } else {
            // You can directly ask for the permission.
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE){
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initUI();
                return;
            }
        }
        finish();
    }

    private void fill(File f)
    {
        File[]dirs = f.listFiles();
        this.setTitle("Current Dir: "+f.getName());
        List<FileItem>dir = new ArrayList<FileItem>();
        List<FileItem>fls = new ArrayList<FileItem>();
        try{
            for(File ff: dirs)
            {
                Date lastModDate = new Date(ff.lastModified());
                DateFormat formatter = DateFormat.getDateTimeInstance();
                String date_modify = formatter.format(lastModDate);
                if(ff.isDirectory()){

                    File[] fbuf = ff.listFiles();
                    int buf = 0;
                    if(fbuf != null){
                        buf = fbuf.length;
                    }
                    else buf = 0;
                    String num_item = String.valueOf(buf);
                    if(buf == 0) num_item = num_item + getString(R.string._item);
                    else num_item = num_item + getString(R.string._items);


                    dir.add(new FileItem(ff.getName(),num_item,date_modify,ff.getAbsolutePath(),"directory_icon"));
                }
                else
                {
                    fls.add(new FileItem(ff.getName(),getFileSize(ff.length()), date_modify, ff.getAbsolutePath(),"file_icon"));
                }
            }
        }catch(Exception e)
        {

        }
        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
//		 if(!f.getName().equalsIgnoreCase("sdcard"))
//			 dir.add(0,new Item("..","Parent Directory","",f.getParent(),"directory_up"));
        if(!f.getName().equalsIgnoreCase(sdCardDir))
            dir.add(0,new FileItem("..","Parent Directory","",f.getParent(),"directory_up"));
        adapter = new FileArrayAdapter(FileChooserActivity.this,R.layout.file_item,dir);
        this.setListAdapter(adapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        FileItem o = adapter.getItem(position);
        if(o.getImage().equalsIgnoreCase("directory_icon")||o.getImage().equalsIgnoreCase("directory_up")){
            currentDir = new File(o.getPath());
            fill(currentDir);
        }
        else
        {
            onFileClick(o);
        }
    }

    private void onFileClick(FileItem o)
    {
        Intent intent = new Intent();
        /*intent.putExtra(DIR_PATH,currentDir.toString());
        intent.putExtra(FILE_FULL_PATH,o.getPath());
        intent.putExtra(FILE_NAME,o.getName());*/
        intent.putExtra(FILE_INFO,new FileInfo(o.getName(),o.getPath(),currentDir.toString()));
        setResult(RESULT_OK, intent);
        finish();
    }


    /*public static String getDirPath(Intent data){
       return getData(data,DIR_PATH);
    }
    public static String getFileFullPath(Intent data){
        return getData(data,FILE_FULL_PATH);
    }
    public static String getFileName(Intent data){
        return getData(data,FILE_NAME);
    }*/

    public static FileInfo getFileInfo(Intent data){
        if(data!=null){
            try {
                return (FileInfo) data.getSerializableExtra(FILE_INFO);
            }catch (Exception e){return null;}
        }
        return null;
    }


    private static String getData(Intent intent,String key){
        if(intent!=null){
            return intent.getStringExtra(key);
        }
        return null;
    }

    private String getFileSize(long bytes){
        float gig=1024*1024*1024;
        float meg = 1024*1024;
        float kil = 1024;
        if(bytes >= gig){return optimize(bytes/gig) +" GB";}
        else if(bytes >= meg){return optimize(bytes/meg) +" MB";}
        else if(bytes >= kil){return optimize(bytes/meg) +" KB";}
        else {return bytes + " Byte";}
    }
    private String optimize(float f){
        DecimalFormat df = new DecimalFormat("##.###");
        df.setRoundingMode(RoundingMode.UP);
        return df.format(f);
    }

}

