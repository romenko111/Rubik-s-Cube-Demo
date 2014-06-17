package jp.romerome.rubikscubedemo;
import java.awt.Component;

import javax.media.j3d.BranchGroup;

import com.sun.prism.j2d.paint.MultipleGradientPaint.ColorSpaceType;

public class RubiksCube extends BranchGroup
{

	public static final int R =0;
	public static final int L =1;
	public static final int U =2;
	public static final int D =3;
	public static final int F = 4;
	public static final int B =5;

	private Component mComponent;
	private Cube[] mRight;
	private Cube[] mLeft;
	private Cube[] mUp;
	private Cube[] mDown;
	private Cube[] mFront;
	private Cube[] mBack;
	private double mR = 0.5;
	
	public RubiksCube(Component component)
	{
		mComponent = component;
		mRight = new Cube[9];
		mLeft = new Cube[9];
		mUp = new Cube[9];
		mDown = new Cube[9];
		mFront = new Cube[9];
		mBack = new Cube[9];
		
		RubikColor[] colors = new RubikColor[6];
		InitColor(colors);
		colors[RubiksCube.R] = RubikColor.RED;
		colors[RubiksCube.U] = RubikColor.WHITE;
		colors[RubiksCube.F] = RubikColor.GREEN;
		Cube cube = new Cube(mComponent, 1, 1, 1, mR, colors);
		mRight[0] = mFront[2] = mUp[8] =cube;
		addChild(cube);
		
		colors[RubiksCube.F] = RubikColor.BLACK;
		cube = new Cube(mComponent, 1, 1, 0, mR, colors);
		mRight[1] = mUp[5] = cube;
		addChild(cube);
		
		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, 1, 1, -1, mR, colors);
		mRight[2] = mUp[2] = mBack[0] = cube;
		addChild(cube);
		
		colors[RubiksCube.U] = RubikColor.BLACK;
		cube = new Cube(mComponent, 1, 0, -1, mR, colors);
		mRight[5] = mBack[3] = cube;
		addChild(cube);
		
		colors[RubiksCube.D] = RubikColor.BLUE;
		cube = new Cube(mComponent, 1, -1, -1, mR, colors);
		mRight[8] = mBack[6] = mDown[0]= cube;
		addChild(cube);
	}
	
	private void InitColor(RubikColor[] colors)
	{
		for(int i=0;i<colors.length;i++)
			colors[i] = RubikColor.BLACK;
	}

	public void Right(double angle)
	{
		for(int i = 0;i<mRight.length;i++)
		{
			if(mRight[i] != null)
				mRight[i].rotateX(angle);
		}
	}
	
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
