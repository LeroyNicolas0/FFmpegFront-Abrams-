package data;

// Class to store SubRip Entry. 
public class SubEntry {
	
	public int number; // number of subtitle (see SubRip file description)
	public String start; // starting time
	public String end; // Ending time
	public String text; // text
	public int seconds; //starting time in seconds for comparison (Could be removed with use of Main.stringToTime)
	
	public SubEntry(int number, int start, int end, String text)
	{
		this.number=number;
		this.text=text;
		this.seconds=start;
		this.start=String.format("%02d:%02d",start/60,start%60);
		this.end=String.format("%02d:%02d",end/60,end%60); 
	}
		
	public String send ()
	{
		return String.format("%d\r\n00:%s,000 --> 00:%s,000\r\n%s\r\n",number,start,end,text);	
	}
}
