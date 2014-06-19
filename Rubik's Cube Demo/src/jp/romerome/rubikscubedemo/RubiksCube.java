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

	public void Right()
	{
		if(isMoving) return;
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(R), 0,10);
		
		Cube[] temp = new Cube[9];
		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mRight[i];
		}
		//R
		mRight[CU] = temp[LC]; mRight[LC] = temp[CD]; mRight[CD] = temp[RC]; mRight[RC] = temp[CU]; 
		mRight[LU] = temp[LD]; mRight[LD] = temp[RD]; mRight[RD] = temp[RU]; mRight[RU] = temp[LU];
		
		//B
		mBack[LU] = temp[LU]; mBack[LC] = temp[CU]; mBack[LD] = temp[RU];
		//U
		mUp[RU] = temp[LU]; mUp[RC] = temp[LC]; mUp[RD] = temp[LD];
		//F
		mFront[RU] = temp[LD]; mFront[RC] = temp[CD]; mFront[RD] = temp[RD];
		//D
		mDown[RU] = temp[RD]; mDown[RC] = temp[RC]; mDown[RD] = temp[RC];
	}

	public void Left()
	{
		if(isMoving) return;
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(L), 0,10);
		
		Cube[] temp = new Cube[9];
		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mLeft[i];
		}
		
		//L
		mLeft[CU] = temp[LC]; mLeft[LC] = temp[CD]; mLeft[CD] = temp[RC]; mLeft[RC] = temp[CU]; 
		mLeft[LU] = temp[LD]; mLeft[LD] = temp[RD]; mLeft[RD] = temp[RU]; mLeft[RU] = temp[LU];
		
		//B
		mBack[RU] = temp[LD]; mBack[RC] = temp[CD]; mBack[RD] = temp[RD];
		//U
		mUp[LU] = temp[LD]; mUp[LC] = temp[LC]; mUp[LD] = temp[LU];
		//F
		mFront[LU] = temp[LU]; mFront[LC] = temp[CU]; mFront[LD] = temp[RU];
		//D
		mDown[LU] = temp[RU]; mDown[LC] = temp[RC]; mDown[LD] = temp[RD];
	}
	
	public void Up()
	{
		if(isMoving) return;
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(U), 0,10);
		
		Cube[] temp = new Cube[9];
		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mUp[i];
		}
		
		//U
		mUp[CU] = temp[LC]; mUp[LC] = temp[CD]; mUp[CD] = temp[RC]; mUp[RC] = temp[CU]; 
		mUp[LU] = temp[LD]; mUp[LD] = temp[RD]; mUp[RD] = temp[RU]; mUp[RU] = temp[LU];
		
		//B
		mBack[LU] = temp[LU]; mBack[CU] = temp[LC]; mBack[RU] = temp[LD];
		//L
		mLeft[LU] = temp[LD]; mLeft[CU] = temp[CD]; mLeft[RU] = temp[RD];
		//F
		mFront[LU] = temp[RD]; mFront[CU] = temp[RC]; mFront[RU] = temp[RU];
		//R
		mRight[LU] = temp[RU]; mRight[CU] = temp[CU]; mRight[RU] = temp[LU];
	}

	public boolean isMoving()
	{
		return isMoving;
	}

	private class MyTimerTask extends TimerTask
	{
		private int cnt = 0;
		private int men;

		public MyTimerTask(int men)
		{
			this.men = men;
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
				switch (men)
				{
					case RubiksCube.R:
						for(int i = 0;i<mRight.length;i++)
						{
								mRight[i].rotateX(-Math.PI/18);
						}
						break;
						
					case RubiksCube.L:
						for(int i = 0;i<mLeft.length;i++)
						{
								mLeft[i].rotateX(Math.PI/18);
						}
						break;
						
					case RubiksCube.U:
						for(int i = 0;i<mUp.length;i++)
						{
								mUp[i].rotateY(-Math.PI/18);
						}
						break;

					default:
						break;
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
