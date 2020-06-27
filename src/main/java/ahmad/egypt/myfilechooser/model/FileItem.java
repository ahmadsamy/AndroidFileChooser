package ahmad.egypt.myfilechooser.model;

public class FileItem implements Comparable<FileItem>{
	private String name;
	private String info;
	private String date;
	private String path;
	private String image;
	
	public FileItem(String name, String info, String date, String path, String img)
	{
		this.name = name;
		this.info = info;
		this.date = date;
		this.path = path;
		this.image = img;
		
	}
	public String getName()
	{
		return name;
	}
	public String getInfo()
	{
		return info;
	}
	public String getDate()
	{
		return date;
	}
	public String getPath()
	{
		return path;
	}
	public String getImage() {
		return image;
	}
	
	public int compareTo(FileItem o) {
		if(this.name != null)
			return this.name.toLowerCase().compareTo(o.getName().toLowerCase()); 
		else 
			throw new IllegalArgumentException();
	}
}
