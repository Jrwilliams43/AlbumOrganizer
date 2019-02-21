import java.math.BigDecimal;
public class Album
{
	private String artist;
	private String title;
	private BigDecimal price;
	
	
	public Album(String artist, String title, BigDecimal price)
	{
		this.artist = artist;
		this.title = title;
		this.price = price;
	}
	public Album()
	{
		this.artist = "_";
		this.title = "_";
		this.price = new BigDecimal(0);
	}
	public String getArtist()
	{
		return this.artist;
	}
	public String getTitle()
	{
		return this.title;
	}
	public BigDecimal getPrice()
	{
		return this.price;
	}
	public void setArtist( String newArtist)
	{
		this.artist = newArtist;
	}
	public void setTitle(String newTitle)
	{
		this.title = newTitle;
	}
	public void setPrice(BigDecimal newPrice)
	{
		this.price = newPrice;
	}
	
}