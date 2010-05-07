package ol.rss.linked;

public interface Link<Datatype> {
	
	public String getLink();
	
	public boolean hasPrevious();
	public Datatype getPrevious();
	
	public boolean hasNext();
	public Datatype getNext();

}
