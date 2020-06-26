package ahmad.egypt.myfilechooser;

import java.util.List; 
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; 
import android.widget.ArrayAdapter;
import android.widget.ImageView; 
import android.widget.TextView;


public class FileArrayAdapter extends ArrayAdapter<FileItem>{

	private Context c;
	private int id;
	private List<FileItem> fileItems;
	
	public FileArrayAdapter(Context context, int textViewResourceId,
			List<FileItem> objects) {
		super(context, textViewResourceId, objects);
		c = context;
		id = textViewResourceId;
		fileItems = objects;
	}
	public FileItem getItem(int i)
	 {
		 return fileItems.get(i);
	 }
	 @Override
       public View getView(int position, View convertView, ViewGroup parent) {
               View v = convertView;
               if (v == null) {
                   LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                   v = vi.inflate(id, null);
               }

       		
               final FileItem file_File_item = fileItems.get(position);
               if (file_File_item != null) {
                       TextView name = (TextView) v.findViewById(R.id.TextName);
                       TextView info = (TextView) v.findViewById(R.id.TextInfo);
                       TextView date = (TextView) v.findViewById(R.id.TextViewDate);


	               		ImageView fileIcon = (ImageView) v.findViewById(R.id.file_icon);
	               		/*String uri = "drawable/" + file_item.getImage();
	               	    int imageResource = c.getResources().getIdentifier(uri, null, c.getPackageName());
	               	    Drawable image = c.getResources().getDrawable(imageResource);
	               	    fileIcon.setImageDrawable(image);*/
	               		fileIcon.setImageResource(getDrawableResIdFromString(file_File_item.getImage()));
                       
                       if(name!=null)
                       	name.setText(file_File_item.getName());
                       if(info!=null)
                          	info.setText(file_File_item.getInfo());
                       if(date!=null)
                          	date.setText(file_File_item.getDate());
                       
               }
               return v;
       }

       private int getDrawableResIdFromString(String name){
		   String uri = "drawable/" + name;
		   return c.getResources().getIdentifier(uri, null, c.getPackageName());
	   }
}
