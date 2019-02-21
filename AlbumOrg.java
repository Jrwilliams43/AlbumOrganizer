import java.util.ArrayList;

public class AlbumOrg
{
	private ArrayList<Album> albums;
	StringBuilder result;
	public AlbumOrg()
	{
		albums = new ArrayList<Album>();
	}
	public void addAlbum(Album al)
	{
		albums.add(al);
	}
	public void removeAlbum(int index)
	{
		albums.remove(index);
	}
	public String toString()
	{
		result = new StringBuilder();
		for(int i = 0; i < albums.size(); i++)
		{
			result.append((i+1) + " " + albums.get(i).toString() + "\n");
		}
		return result.toString();
	}
	
}