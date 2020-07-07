# AndroidFileChooser

AndroidFileChooser is a simple file chooser based on RecyclerView for android.

### Installation
Just download AndroidFileChooser directory and include it as a module to your project

### How To Use
Start activity for result:
```kotlin
     var intent= Intent(this,FileChooserActivity::class.java)
            startActivityForResult(intent,BROWSE_REQUEST_CODE)
```

Then get your data from onActivityResult
```kotlin
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==BROWSE_REQUEST_CODE) {
            fileInfo = if (resultCode == Activity.RESULT_OK) {
                FileChooserActivity.getFileInfo(data)
            } else null
            var fileName=fileInfo?.name
            var fileFullPath=fileInfo?.fullPath
            var containigDirecory=fileInfo?.dirPath
        }
    }
```