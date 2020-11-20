# AndroidFileChooser

AndroidFileChooser is a simple file chooser based on RecyclerView for android.

<img src="https://i.ibb.co/CVL54GH/screencast-Genymotion-2020-07-09.gif" alt="screencast-Genymotion-2020-07-09" border="0">

### Installation
Just download AndroidFileChooser directory and include it as a module to your project

### How To Use
Start activity for result:

Kotlin:
```kotlin
     var intent= Intent(this,FileChooserActivity::class.java)
            startActivityForResult(intent,BROWSE_REQUEST_CODE)
```
Java:
```java
     Intent intent= new Intent(this,FileChooserActivity.class);
            startActivityForResult(intent,BROWSE_REQUEST_CODE);
```


Then get your data from onActivityResult:

Kotlin:
```kotlin
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==BROWSE_REQUEST_CODE) {
            fileInfo = if (resultCode == Activity.RESULT_OK) {
                FileChooserActivity.getFileInfo(data)
            } else null
            var fileName=fileInfo?.name
            var fileFullPath=fileInfo?.fullPath
            var containingDirectory=fileInfo?.dirPath
        }
    }
```
Java:
```java
@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == BROWSE_REQUEST_CODE  && resultCode  == RESULT_OK) {
               FileInfo fileInfo=FileChooserActivity.getFileInfo(data);
               if(fileInfo!=null){
                    String fileName=fineInfo.getName();
                    String fileFullPath=fineInfo.getFullPath();
                    String containingDirectory=fineInfo.getDirPath();
               }
            }

    }
```


#Not yet compatible with android 11, but it will be soon..
