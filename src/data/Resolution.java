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
		int h=Integer.parseInt(toParse.substring(toParse.indexOf('x')+1, toParse.length()-1));
		this.width=w;
		this.height=h;
	}
	
	public String print() {
		return new String(Integer.toString(width)+"x"+Integer.toString(height));
	}
	public static Resolution get_360p() {
		return new Resolution(640,360);
	}
	public static Resolution get_480p() {
		return new Resolution(854,480);
	}
	public static Resolution get_720p() {
		return new Resolution(1280,720);
	}
	public static Resolution get_1080p() {
		return new Resolution(1920,1080);
	}
	public static Resolution get_custom() {
		return new Resolution(0,0);
	}
	public boolean is_16_9() {
		if (1.777<(float)width/(float)height && (float)width/(float)height <1.78) {
			return true;
		}else return false;
	}
	public Resolution get_360p_ratio() {
		float ratio=(float)width/(float)height;
		int h =360;
		int w =(int)((float)ratio*360);
		if (w%2!=0) {
			w++;
		}
		if (w<64) {
			w=64;
		}
		return new Resolution(w,h);
	}
	public Resolution get_480p_ratio() {
		float ratio=(float)width/(float)height;
		int h =480;
		int w =(int)((float)ratio*480);
		if (w%2!=0) {
			w++;
		}
		if (w<64) {
			w=64;
		}
		return new Resolution(w,h);
	}
	public Resolution get_720p_ratio() {
		float ratio=(float)width/(float)height;
		int h =720;
		int w =(int)((float)ratio*720);
		if (w%2!=0) {
			w++;
		}
		if (w<64) {
			w=64;
		}
		return new Resolution(w,h);
	}
	public Resolution get_1080p_ratio() {
		float ratio=(float)width/(float)height;
		int h =1080;
		int w =(int)((float)ratio*1080);
		if (w%2!=0) {
			w++;
		}
		if (w<64) {
			w=64;
		}
		return new Resolution(w,h);
	}

}


