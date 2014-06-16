package jp.romerome.rubikscubedemo;
import javax.media.j3d.BranchGroup;

public class RubiksCube extends BranchGroup
{

	public static final int R =0;
	public static final int L =1;
	public static final int U =2;
	public static final int D =3;
	public static final int F = 4;
	public static final int B =5;

	enum RubikColor
	{
		WHITE("white.png"),BLUE("blue.png"),
		RED("red.png"),ORANGE("orange.png"),
		GREEN("green.png"),YELLOW("yellow.png"),
		BLACK("black.png");

		private String mFileName;

		private RubikColor(String filename)
		{
			mFileName = filename;
		}

		public String getFileName()
		{
			return mFileName;
		}
	}

}
