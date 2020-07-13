package ahmad.egypt.myfilechooser;


import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.text.DateFormat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import java.text.*;
import java.math.*;
import android.os.*;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import ahmad.egypt.myfilechooser.adapter.FileRecViewAdapter;
import ahmad.egypt.myfilechooser.model.FileInfo;
import ahmad.egypt.myfilechooser.model.FileItem;

public class FileChooserActivity extends AppCompatRecViewActivity implements FileRecViewAdapter.FileItemClickCallBack{

    private File currentDir;
    private String externalStorageRoot;
    private int REQUEST_CODE=0x000001;

    private static String FILE_INFO="FILE_INFO";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();
    }

    private void initUI(){
        currentDir = Environment.getExternalStorageDirectory();
        externalStorageRoot=currentDir.getAbsolutePath();
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

    private void fill(File parentDir)
    {
        File[]allFiles = parentDir.listFiles();
        this.setTitle("Current Dir: "+parentDir.getName());
        List<FileItem>directories = new ArrayList<FileItem>();
        List<FileItem>files = new ArrayList<FileItem>();
        try{
            for(File file: allFiles)
            {
                Date lastModDate = new Date(file.lastModified());
                DateFormat formatter = DateFormat.getDateTimeInstance();
                String date_modify = formatter.format(lastModDate);
                if(file.isDirectory()){
                    File[] subFiles = file.listFiles();
                    int subFilesCount = 0;
                    if(subFiles != null){
                        subFilesCount = subFiles.length;
                    }
                    else subFilesCount = 0;
                    String num_item = String.valueOf(subFilesCount);
                    if(subFilesCount == 0) num_item = num_item + getString(R.string._item);
                    else num_item = num_item + getString(R.string._items);
                    directories.add(new FileItem(file.getName(),num_item,date_modify,file.getAbsolutePath(),"directory_icon"));
                }
                else
                {
                    files.add(new FileItem(file.getName(),getFileSize(file.length()), date_modify, file.getAbsolutePath(),"file_icon"));
                }
            }
        }catch(Exception e)
        {

        }
        Collections.sort(directories);
        Collections.sort(files);
        directories.addAll(files);
        if(!isExternalStorageRoot(parentDir.getAbsolutePath())/*parentDir.getName().equalsIgnoreCase(sdCardDir)*/)
            directories.add(0,new FileItem("..","Parent Directory","",parentDir.getParent(),"directory_up"));
        FileRecViewAdapter adapter = new FileRecViewAdapter(FileChooserActivity.this, directories);
        this.setRecViewAdapter(adapter);
    }

    @Override
    public void onFileItemClick(FileItem item) {
        if(item.getImage().equalsIgnoreCase("directory_icon")||item.getImage().equalsIgnoreCase("directory_up")){
            currentDir = new File(item.getPath());
            fill(currentDir);
        }
        else
        {
            onFileClick(item);
        }
    }

    private void onFileClick(FileItem o)
    {
        Intent intent = new Intent();
        intent.putExtra(FILE_INFO,new FileInfo(o.getName(),o.getPath(),currentDir.toString()));
        setResult(RESULT_OK, intent);
        finish();
    }



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

    private boolean isExternalStorageRoot(String path){
        return path.equalsIgnoreCase(externalStorageRoot);
    }

}

