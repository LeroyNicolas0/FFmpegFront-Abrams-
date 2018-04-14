package Enums;

public enum ACodec {
	VORBIS("vorbis","Vorbis audio encoding"),
	OPUS("libopus", "Opus audio enconding"),
	MP3LAME("mp3", "MP3 LAME encoding"),
	FLAC("flac","Free Audio Lossless Codec"),
	AAC("aac","Advanced Audio Codec"),
	ALL(null,"Marker for all supported Audio codecs");
	
	private String library;
	private String desc;
	
	ACodec(String lib, String desc){
		this.library=lib;
		this.desc=desc;
	}
	
	public String get_lib() {
		return this.library;
	}
	public String get_description() {
		return this.desc;
	}
	
	public static ACodec[] Get_All() {
		return new ACodec[] {VORBIS,OPUS,MP3LAME,FLAC,AAC};
	}

}
