package data;

public class Resolution {
	public int width;
	public int height;
	
	public Resolution (int w, int h) {
		this.width=w;
		this.height=h;
	}
	public Resolution(String toParse) {
		int w=Integer.parseInt(toParse.substring(0, toParse.indexOf('x')));
		int h=Integer.parseInt(toParse.substring(toParse.indexOf('x')+1, toParse.length()));
		this.width=w;
		this.height=h;
	}
}


