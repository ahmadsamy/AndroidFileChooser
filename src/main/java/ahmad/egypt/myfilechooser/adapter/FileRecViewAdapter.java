package ahmad.egypt.myfilechooser.adapter;

import java.util.List; 
 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ahmad.egypt.myfilechooser.R;
import ahmad.egypt.myfilechooser.model.FileItem;

public class FileRecViewAdapter extends RecyclerView.Adapter<FileRecViewAdapter.FileViewHolder>
{

	List<FileItem> fileList;
	FileItemClickCallBack callBack;

	public FileRecViewAdapter(FileItemClickCallBack callBack,List<FileItem> list){
		this.fileList=list;
		this.callBack=callBack;
	}
	public FileRecViewAdapter(List<FileItem> list){
		this(null,list);
	}

	@NonNull
	@Override
	public FileRecViewAdapter.FileViewHolder onCreateViewHolder(ViewGroup parent, int pos)
	{
		View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item,parent,false);
		return new FileViewHolder(v);
	}

	@Override
	public void onBindViewHolder(FileRecViewAdapter.FileViewHolder p1, int p2)
	{
		p1.populate(fileList.get(p2));
	}

	@Override
	public int getItemCount()
	{
		return fileList!=null?fileList.size():0;
	}

	public FileItem getItem(int i)
	{
		return i<fileList.size()?fileList.get(i):null;
	}

	public class FileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		TextView name,info,date;
		ImageView fileIcon;

		public FileViewHolder(View v){
			super(v);
			name =  v.findViewById(R.id.TextName);
			info =  v.findViewById(R.id.TextInfo);
			date =  v.findViewById(R.id.TextViewDate);
			fileIcon =  v.findViewById(R.id.file_icon);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View p1)
		{
			if(callBack!=null)callBack.onFileItemClick(fileList.get(getAdapterPosition()));
		}


		public void populate(FileItem file){
			name.setText(file.getName());
			info.setText(file.getInfo());
			date.setText(file.getDate());
			fileIcon.setImageResource(getResource(file.getImage()));
		}

		private int getResource(String s){
			if(s.equalsIgnoreCase("directory_icon"))return R.drawable.directory_icon;
			else if(s.equals("directory_up"))return R.drawable.directory_up;
			else return R.drawable.file_icon;
		}
	}


	public interface FileItemClickCallBack{
		void onFileItemClick(FileItem item);
	}
}
