package j.wu.media.mp3;

public interface PublicInfo {

	/*
	char Header[3];     //��ǩͷ������"TAG"������Ϊû�б�ǩ 
	char Title[30];     //���� 
	char Artist[30];    //���� 
	char Album[30];     //ר�� 
	char Year[4];       //��Ʒ��� 
	char Comment[28];  //��ע 
	char reserve;       //���� 
	char track;        //���� 
	char Genre;         //���� 
	*/
	public String getTitle();
	public String getArtist();
	public String getAlbum();
	public String getYaer();
	public String getComment();
	public String getGenre();
	}
