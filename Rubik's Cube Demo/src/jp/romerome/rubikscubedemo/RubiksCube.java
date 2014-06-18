package jp.romerome.rubikscubedemo;
import java.awt.Component;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.j3d.BranchGroup;

public class RubiksCube extends BranchGroup
{

	public static final int R =0;
	public static final int L =1;
	public static final int U =2;
	public static final int D =3;
	public static final int F = 4;
	public static final int B =5;
	public static final int LU = 0;
	public static final int CU = 1;
	public static final int RU = 2;
	public static final int LC = 3;
	public static final int CC = 4;
	public static final int RC = 5;
	public static final int LD = 6;
	public static final int CD = 7;
	public static final int RD = 8;

	private Component mComponent;
	private Cube[] mRight;
	private Cube[] mLeft;
	private Cube[] mUp;
	private Cube[] mDown;
	private Cube[] mFront;
	private Cube[] mBack;
	private double mR = 0.5;
	private boolean isMoving;

	public RubiksCube(Component component)
	{
		mComponent = component;
		mRight = new Cube[9];
		mLeft = new Cube[9];
		mUp = new Cube[9];
		mDown = new Cube[9];
		mFront = new Cube[9];
		mBack = new Cube[9];
		isMoving = false;

		genRubikCube();
	}

	private void genRubikCube()
	{
		RubikColor[] colors = new RubikColor[6];
		//---------R--------------
		InitColor(colors);
		colors[RubiksCube.R] = RubikColor.RED;
		colors[RubiksCube.U] = RubikColor.WHITE;
		colors[RubiksCube.F] = RubikColor.GREEN;
		Cube cube = new Cube(mComponent, 1, 1, 1, mR, colors);
		mRight[LU] = mFront[RU] = mUp[RD] =cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.BLACK;
		cube = new Cube(mComponent, 1, 1, 0, mR, colors);
		mRight[CU] = mUp[RC] = cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, 1, 1, -1, mR, colors);
		mRight[RU] = mUp[RU] = mBack[LU] = cube;
		addChild(cube);

		colors[RubiksCube.U] = RubikColor.BLACK;
		cube = new Cube(mComponent, 1, 0, -1, mR, colors);
		mRight[RC] = mBack[LC] = cube;
		addChild(cube);

		colors[RubiksCube.D] = RubikColor.BLUE;
		cube = new Cube(mComponent, 1, -1, -1, mR, colors);
		mRight[RD] = mBack[LD] = mDown[RD]= cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.BLACK;
		cube = new Cube(mComponent, 1, -1, 0, mR, colors);
		mRight[CD] = mDown[RC]= cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.GREEN;
		cube = new Cube(mComponent, 1, -1, 1, mR, colors);
		mRight[LD] = mDown[RU]= mFront[RD] = cube;
		addChild(cube);

		colors[RubiksCube.D] = RubikColor.BLACK;
		cube = new Cube(mComponent, 1, 0, 1, mR, colors);
		mRight[LC] = mFront[RC]= cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.BLACK;
		cube = new Cube(mComponent, 1, 0, 0, mR, colors);
		mRight[CC] = cube;
		addChild(cube);
		//--------------------------
		//-----------L-------------
		InitColor(colors);
		colors[RubiksCube.L] = RubikColor.ORANGE;
		colors[RubiksCube.U] = RubikColor.WHITE;
		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, -1, 1, -1, mR, colors);
		mLeft[LU] = mBack[RU] = mUp[LU] =cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.BLACK;
		cube = new Cube(mComponent, -1, 1, 0, mR, colors);
		mLeft[CU] = mUp[LC] =cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.GREEN;
		cube = new Cube(mComponent, -1, 1, 1, mR, colors);
		mLeft[RU] = mUp[LD] = mFront[LU] = cube;
		addChild(cube);

		colors[RubiksCube.U] = RubikColor.BLACK;
		cube = new Cube(mComponent, -1, 0, 1, mR, colors);
		mLeft[RC] = mFront[LC] = cube;
		addChild(cube);

		colors[RubiksCube.D] = RubikColor.BLUE;
		cube = new Cube(mComponent, -1, -1, 1, mR, colors);
		mLeft[RD] = mFront[LD] =  mDown[LU] = cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.BLACK;
		cube = new Cube(mComponent, -1, -1, 0, mR, colors);
		mLeft[CD] = mDown[LC] = cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, -1, -1, -1, mR, colors);
		mLeft[LD] = mDown[LD] =  mBack[RD] = cube;
		addChild(cube);

		colors[RubiksCube.D] = RubikColor.BLACK;
		cube = new Cube(mComponent, -1, 0, -1, mR, colors);
		mLeft[LC] = mBack[RC] = cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.BLACK;
		cube = new Cube(mComponent, -1, 0, 0, mR, colors);
		mLeft[CC] = cube;
		addChild(cube);
		//---------------------------------------
		//----------U------------------
		InitColor(colors);
		colors[RubiksCube.U] = RubikColor.WHITE;
		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, 0, 1, -1, mR, colors);
		mUp[CU] = mBack[CU] = cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.BLACK;
		cube = new Cube(mComponent, 0, 1, 0, mR, colors);
		mUp[CC] =  cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.GREEN;
		cube = new Cube(mComponent, 0, 1, 1, mR, colors);
		mUp[CD] = mFront[CU] = cube;
		addChild(cube);
		//-------------------------------
		//---------D------------
		InitColor(colors);
		colors[RubiksCube.D] = RubikColor.BLUE;
		colors[RubiksCube.F] = RubikColor.GREEN;
		cube = new Cube(mComponent, 0, -1, 1, mR, colors);
		mDown[CU] = mFront[CD] = cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.BLACK;
		cube = new Cube(mComponent, 0, -1, 0, mR, colors);
		mDown[CC] =  cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, 0, -1, -1, mR, colors);
		mDown[CD] = mBack[CD] = cube;
		addChild(cube);
		//-----------------------------
		//---------F--------------
		InitColor(colors);
		colors[RubiksCube.F] = RubikColor.GREEN;
		cube = new Cube(mComponent, 0, 0, 1, mR, colors);
		mFront[CC] = cube;
		addChild(cube);
		//---------------------
		//--------B-------------
		InitColor(colors);
		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, 0, 0, -1, mR, colors);
		mBack[CC] = cube;
		addChild(cube);
		//------------------------
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
				mRight[i].rotateX(angle);
		}
	}

	public void Right()
	{
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(mRight), 0,50);
	}

	public void Left(double angle)
	{
		for(int i = 0;i<mLeft.length;i++)
		{
			if(mLeft[i] != null)
				mLeft[i].rotateX(angle);
		}
	}

	public boolean isMoving()
	{
		return isMoving;
	}

	private class MyTimerTask extends TimerTask
	{
		private int cnt = 0;
		private Cube[] cubes;

		public MyTimerTask(Cube[] cubes)
		{
			this.cubes = cubes;
		}

		public void setCubes(Cube[] cubes)
		{
			this.cubes = cubes;
		}

		@Override
		public void run()
		{
			isMoving = true;
			cnt++;
			if(cnt > 9)
			{
				this.cancel();
				isMoving = false;
			}
			else
			{
				for(int i = 0;i<cubes.length;i++)
				{
						cubes[i].rotateX(Math.PI/18);
				}
			}
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
