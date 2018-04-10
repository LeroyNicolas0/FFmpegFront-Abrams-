package Enums;

public enum VCodec {
	LIBX264("libx264","x264 H.264/MPEG-4 AVC Codec"),
	LIBX265("libx265","x265 H.265/HEVC Codec"),
	LIBXVID("libxvid","Xvid MPEG-4 Part 2 Codec"),
	MPEG2("mpeg2","MPEG-2 video encoder"),
	THEORA("libtheora","Theora Video Codec"),
	ALL(null,"Marker for all supported Video codecs");
	
	private String library;
	private String desc;
	
	VCodec(String lib, String desc){
		this.library=lib;
		this.desc=desc;
	}
	
	public String get_lib() {
		return this.library;
	}
	public String get_description() {
		return this.desc;
	}
	public static VCodec[] Get_All() {
		return new VCodec[] {LIBX264,LIBX265,LIBXVID,MPEG2,THEORA};
	}

}
