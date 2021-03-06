package jp.romerome.rubikscubedemo;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.j3d.BranchGroup;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class RubiksCube extends BranchGroup implements FinishListener
{

	public static final int R = 0;
	public static final int L = 1;
	public static final int U = 2;
	public static final int D = 3;
	public static final int F = 4;
	public static final int B = 5;
	public static final int M = 6;
	public static final int S = 7;
	public static final int E = 8;
	public static final int Rev = 0b10000000;
	public static final int WR = 9;
	public static final int WL = 10;
	public static final int WU = 11;
	public static final int WD = 12;
	public static final int WF = 13;
	public static final int WB = 14;
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
	private Cube[] mMiddle;
	private Cube[] mStanding;
	private Cube[] mEquatorial;
	private Cube[] temp;
	private double mR = 0.5;
	private boolean isMoving;
	private int [] mScramble;
	private int scrambleIndex;
	private int audioIndex = 0;
	private Clip[] mAudio;

	public RubiksCube(Component component)
	{
		mComponent = component;
		mRight = new Cube[9];
		mLeft = new Cube[9];
		mUp = new Cube[9];
		mDown = new Cube[9];
		mFront = new Cube[9];
		mBack = new Cube[9];
		mMiddle = new Cube[9];
		mStanding = new Cube[9];
		mEquatorial = new Cube[9];
		temp = new Cube[9];
		isMoving = false;

		mAudio = new Clip[4];
		AudioInputStream[] audioIn = new AudioInputStream[4];
		try
		{
			for (int i = 0; i < 4; i++)
			{
                audioIn[i] = AudioSystem.getAudioInputStream(new File("sound/cube.wav"));
                mAudio[i] = AudioSystem.getClip();
                mAudio[i].open(audioIn[i]);
			}
		} catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}

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
		mRight[CU] = mUp[RC] = mStanding[RU] = cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, 1, 1, -1, mR, colors);
		mRight[RU] = mUp[RU] = mBack[LU] = cube;
		addChild(cube);

		colors[RubiksCube.U] = RubikColor.BLACK;
		cube = new Cube(mComponent, 1, 0, -1, mR, colors);
		mRight[RC] = mBack[LC] =  mEquatorial[RD] = cube;
		addChild(cube);

		colors[RubiksCube.D] = RubikColor.BLUE;
		cube = new Cube(mComponent, 1, -1, -1, mR, colors);
		mRight[RD] = mBack[LD] = mDown[RD]= cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.BLACK;
		cube = new Cube(mComponent, 1, -1, 0, mR, colors);
		mRight[CD] = mDown[RC]=  mStanding[RD] = cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.GREEN;
		cube = new Cube(mComponent, 1, -1, 1, mR, colors);
		mRight[LD] = mDown[RU]= mFront[RD] = cube;
		addChild(cube);

		colors[RubiksCube.D] = RubikColor.BLACK;
		cube = new Cube(mComponent, 1, 0, 1, mR, colors);
		mRight[LC] = mFront[RC]= mEquatorial[RU] = cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.BLACK;
		cube = new Cube(mComponent, 1, 0, 0, mR, colors);
		mRight[CC] = mStanding[RC] = mEquatorial[RC] = cube;
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
		mLeft[CU] = mUp[LC] = mStanding[LU] =cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.GREEN;
		cube = new Cube(mComponent, -1, 1, 1, mR, colors);
		mLeft[RU] = mUp[LD] = mFront[LU] = cube;
		addChild(cube);

		colors[RubiksCube.U] = RubikColor.BLACK;
		cube = new Cube(mComponent, -1, 0, 1, mR, colors);
		mLeft[RC] = mFront[LC] =  mEquatorial[LU] = cube;
		addChild(cube);

		colors[RubiksCube.D] = RubikColor.BLUE;
		cube = new Cube(mComponent, -1, -1, 1, mR, colors);
		mLeft[RD] = mFront[LD] =  mDown[LU] = cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.BLACK;
		cube = new Cube(mComponent, -1, -1, 0, mR, colors);
		mLeft[CD] = mDown[LC] =  mStanding[LD] = cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, -1, -1, -1, mR, colors);
		mLeft[LD] = mDown[LD] =  mBack[RD] = cube;
		addChild(cube);

		colors[RubiksCube.D] = RubikColor.BLACK;
		cube = new Cube(mComponent, -1, 0, -1, mR, colors);
		mLeft[LC] = mBack[RC] = mEquatorial[LD] = cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.BLACK;
		cube = new Cube(mComponent, -1, 0, 0, mR, colors);
		mLeft[CC] = mStanding[LC] = mEquatorial[LC] = cube;
		addChild(cube);
		//---------------------------------------
		//----------U------------------
		InitColor(colors);
		colors[RubiksCube.U] = RubikColor.WHITE;
		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, 0, 1, -1, mR, colors);
		mUp[CU] = mBack[CU] = mMiddle[LU] = cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.BLACK;
		cube = new Cube(mComponent, 0, 1, 0, mR, colors);
		mUp[CC] =  mMiddle[CU] = mStanding[CU] = cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.GREEN;
		cube = new Cube(mComponent, 0, 1, 1, mR, colors);
		mUp[CD] = mFront[CU] = mMiddle[RU] = cube;
		addChild(cube);
		//-------------------------------
		//---------D------------
		InitColor(colors);
		colors[RubiksCube.D] = RubikColor.BLUE;
		colors[RubiksCube.F] = RubikColor.GREEN;
		cube = new Cube(mComponent, 0, -1, 1, mR, colors);
		mDown[CU] = mFront[CD] = mMiddle[RD] = cube;
		addChild(cube);

		colors[RubiksCube.F] = RubikColor.BLACK;
		cube = new Cube(mComponent, 0, -1, 0, mR, colors);
		mDown[CC] =  mMiddle[CD] = mStanding[CD] = cube;
		addChild(cube);

		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, 0, -1, -1, mR, colors);
		mDown[CD] = mBack[CD] = mMiddle[LD] = cube;
		addChild(cube);
		//-----------------------------
		//---------F--------------
		InitColor(colors);
		colors[RubiksCube.F] = RubikColor.GREEN;
		cube = new Cube(mComponent, 0, 0, 1, mR, colors);
		mFront[CC] = mMiddle[RC] = mEquatorial[CU] = cube;
		addChild(cube);
		//---------------------
		//--------B-------------
		InitColor(colors);
		colors[RubiksCube.B] = RubikColor.YELLOW;
		cube = new Cube(mComponent, 0, 0, -1, mR, colors);
		mBack[CC] = mMiddle[LC] = mEquatorial[CD] = cube;
		addChild(cube);
		//------------------------
	}

	private void InitColor(RubikColor[] colors)
	{
		for(int i=0;i<colors.length;i++)
			colors[i] = RubikColor.BLACK;
	}

	private void Rotate(Cube[] cubes, Cube[] temp)
	{
		cubes[CU] = temp[LC]; cubes[LC] = temp[CD]; cubes[CD] = temp[RC]; cubes[RC] = temp[CU];
		cubes[LU] = temp[LD]; cubes[LD] = temp[RD]; cubes[RD] = temp[RU]; cubes[RU] = temp[LU];
	}

	private void RotateRev(Cube[] cubes, Cube[] temp)
	{
		cubes[CU] = temp[RC]; cubes[LC] = temp[CU]; cubes[CD] = temp[LC]; cubes[RC] = temp[CD];
		cubes[LU] = temp[RU]; cubes[LD] = temp[LU]; cubes[RD] = temp[LD]; cubes[RU] = temp[RD];
	}

	public void Right()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(R), 0,10);


		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mRight[i];
		}
		Rotate(mRight, temp);

		//B
		mBack[LU] = mRight[RU]; mBack[LC] = mRight[RC]; mBack[LD] = mRight[RD];
		//U
		mUp[RU] = mRight[RU]; mUp[RC] = mRight[CU]; mUp[RD] = mRight[LU];
		//F
		mFront[RU] = mRight[LU]; mFront[RC] = mRight[LC]; mFront[RD] = mRight[LD];
		//D
		mDown[RU] = mRight[LD]; mDown[RC] = mRight[CD]; mDown[RD] = mRight[RD];
		//S
		mStanding[RU] = mRight[CU]; mStanding[RC] = mRight[CC]; mStanding[RD] = mRight[CD];
		//E
		mEquatorial[RU] = mRight[LC]; mEquatorial[RC] = mRight[CC]; mEquatorial[RD]=mRight[RC];
	}

	public void RightRev()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(R | Rev), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mRight[i];
		}
		RotateRev(mRight, temp);

		//B
		mBack[LU] = mRight[RU]; mBack[LC] = mRight[RC]; mBack[LD] = mRight[RD];
		//U
		mUp[RU] = mRight[RU]; mUp[RC] = mRight[CU]; mUp[RD] = mRight[LU];
		//F
		mFront[RU] = mRight[LU]; mFront[RC] = mRight[LC]; mFront[RD] = mRight[LD];
		//D
		mDown[RU] = mRight[LD]; mDown[RC] = mRight[CD]; mDown[RD] = mRight[RD];
		//S
		mStanding[RU] = mRight[CU]; mStanding[RC] = mRight[CC]; mStanding[RD] = mRight[CD];
		//E
		mEquatorial[RU] = mRight[LC]; mEquatorial[RC] = mRight[CC]; mEquatorial[RD]=mRight[RC];
	}

	public void Left()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(L), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mLeft[i];
		}
		Rotate(mLeft, temp);

		//B
		mBack[RU] = mLeft[LU]; mBack[RC] = mLeft[LC]; mBack[RD] = mLeft[LD];
		//U
		mUp[LU] = mLeft[LU]; mUp[LC] = mLeft[CU]; mUp[LD] = mLeft[RU];
		//F
		mFront[LU] = mLeft[RU]; mFront[LC] = mLeft[RC]; mFront[LD] = mLeft[RD];
		//D
		mDown[LU] = mLeft[RD]; mDown[LC] = mLeft[CD]; mDown[LD] = mLeft[LD];
		//S
		mStanding[LU] = mLeft[CU]; mStanding[LC] = mLeft[CC]; mStanding[LD] = mLeft[CD];
		//E
		mEquatorial[LD] = mLeft[LC]; mEquatorial[LC] = mLeft[CC]; mEquatorial[LU] = mLeft[RC];
	}

	public void LeftRev()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(L | Rev), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mLeft[i];
		}
		RotateRev(mLeft, temp);

		//B
		mBack[RU] = mLeft[LU]; mBack[RC] = mLeft[LC]; mBack[RD] = mLeft[LD];
		//U
		mUp[LU] = mLeft[LU]; mUp[LC] = mLeft[CU]; mUp[LD] = mLeft[RU];
		//F
		mFront[LU] = mLeft[RU]; mFront[LC] = mLeft[RC]; mFront[LD] = mLeft[RD];
		//D
		mDown[LU] = mLeft[RD]; mDown[LC] = mLeft[CD]; mDown[LD] = mLeft[LD];
		//S
		mStanding[LU] = mLeft[CU]; mStanding[LC] = mLeft[CC]; mStanding[LD] = mLeft[CD];
		//E
		mEquatorial[LD] = mLeft[LC]; mEquatorial[LC] = mLeft[CC]; mEquatorial[LU] = mLeft[RC];
	}

	public void Up()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(U), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mUp[i];
		}
		Rotate(mUp, temp);

		//B
		mBack[LU] = mUp[RU]; mBack[CU] = mUp[CU]; mBack[RU] = mUp[LU];
		//L
		mLeft[LU] = mUp[LU]; mLeft[CU] = mUp[LC]; mLeft[RU] = mUp[LD];
		//F
		mFront[LU] = mUp[LD]; mFront[CU] = mUp[CD]; mFront[RU] = mUp[RD];
		//R
		mRight[LU] = mUp[RD]; mRight[CU] = mUp[RC]; mRight[RU] = mUp[RU];
		//M
		mMiddle[LU] = mUp[CU]; mMiddle[CU] = mUp[CC]; mMiddle[RU] = mUp[CD];
		//S
		mStanding[LU] = mUp[LC]; mStanding[CU] = mUp[CC]; mStanding[RU] = mUp[RC];
	}

	public void UpRev()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(U | Rev), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mUp[i];
		}
		RotateRev(mUp, temp);

		//B
		mBack[LU] = mUp[RU]; mBack[CU] = mUp[CU]; mBack[RU] = mUp[LU];
		//L
		mLeft[LU] = mUp[LU]; mLeft[CU] = mUp[LC]; mLeft[RU] = mUp[LD];
		//F
		mFront[LU] = mUp[LD]; mFront[CU] = mUp[CD]; mFront[RU] = mUp[RD];
		//R
		mRight[LU] = mUp[RD]; mRight[CU] = mUp[RC]; mRight[RU] = mUp[RU];
		//M
		mMiddle[LU] = mUp[CU]; mMiddle[CU] = mUp[CC]; mMiddle[RU] = mUp[CD];
		//S
		mStanding[LU] = mUp[LC]; mStanding[CU] = mUp[CC]; mStanding[RU] = mUp[RC];
	}

	public void Down()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(D), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mDown[i];
		}
		Rotate(mDown, temp);

		//R
		mRight[LD] = mDown[RU]; mRight[CD] = mDown[RC]; mRight[RD] = mDown[RD];
		//B
		mBack[LD] = mDown[RD]; mBack[CD] = mDown[CD]; mBack[RD] = mDown[LD];
		//L
		mLeft[LD] = mDown[LD]; mLeft[CD] = mDown[LC]; mLeft[RD] = mDown[LU];
		//F
		mFront[LD] = mDown[LU]; mFront[CD] = mDown[CU]; mFront[RD] = mDown[RU];
		//M
		mMiddle[LD] = mDown[CD]; mMiddle[CD] = mDown[CC]; mMiddle[RD] = mDown[CU];
		//S
		mStanding[LD] = mDown[LC]; mStanding[CD] = mDown[CC]; mStanding[RD] = mDown[RC];
	}

	public void DownRev()
	{
		{
			if(isMoving) return;
			Sound();
			Timer timer = new Timer();
			timer.schedule(new MyTimerTask(D | Rev), 0,10);

			for(int i=0;i<temp.length;i++)
			{
				temp[i] = mDown[i];
			}
			RotateRev(mDown, temp);

			//R
			mRight[LD] = mDown[RU]; mRight[CD] = mDown[RC]; mRight[RD] = mDown[RD];
			//B
			mBack[LD] = mDown[RD]; mBack[CD] = mDown[CD]; mBack[RD] = mDown[LD];
			//L
			mLeft[LD] = mDown[LD]; mLeft[CD] = mDown[LC]; mLeft[RD] = mDown[LU];
			//F
			mFront[LD] = mDown[LU]; mFront[CD] = mDown[CU]; mFront[RD] = mDown[RU];
			//M
			mMiddle[LD] = mDown[CD]; mMiddle[CD] = mDown[CC]; mMiddle[RD] = mDown[CU];
			//S
			mStanding[LD] = mDown[LC]; mStanding[CD] = mDown[CC]; mStanding[RD] = mDown[RC];
		}
	}

	public void Front()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(F), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mFront[i];
		}
		Rotate(mFront, temp);

		//R
		mRight[LU] = mFront[RU]; mRight[LC] = mFront[RC]; mRight[LD] = mFront[RD];
		//D
		mDown[RU] = mFront[RD]; mDown[CU] = mFront[CD]; mDown[LU] = mFront[LD];
		//L
		mLeft[RD] = mFront[LD]; mLeft[RC] = mFront[LC]; mLeft[RU] = mFront[LU];
		//U
		mUp[LD] = mFront[LU]; mUp[CD] = mFront[CU]; mUp[RD] = mFront[RU];
		//M
		mMiddle[RU] = mFront[CU]; mMiddle[RC] = mFront[CC]; mMiddle[RD] = mFront[CD];
		//E
		mEquatorial[LU] = mFront[LC]; mEquatorial[CU] = mFront[CC]; mEquatorial[RU] = mFront[RC];
	}

	public void FrontRev()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(F | Rev), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mFront[i];
		}
		RotateRev(mFront, temp);

		//R
		mRight[LU] = mFront[RU]; mRight[LC] = mFront[RC]; mRight[LD] = mFront[RD];
		//D
		mDown[RU] = mFront[RD]; mDown[CU] = mFront[CD]; mDown[LU] = mFront[LD];
		//L
		mLeft[RD] = mFront[LD]; mLeft[RC] = mFront[LC]; mLeft[RU] = mFront[LU];
		//U
		mUp[LD] = mFront[LU]; mUp[CD] = mFront[CU]; mUp[RD] = mFront[RU];
		//M
		mMiddle[RU] = mFront[CU]; mMiddle[RC] = mFront[CC]; mMiddle[RD] = mFront[CD];
		//E
		mEquatorial[LU] = mFront[LC]; mEquatorial[CU] = mFront[CC]; mEquatorial[RU] = mFront[RC];
	}

	public void Back()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(B), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mBack[i];
		}
		Rotate(mBack, temp);

		//L
		mLeft[LU] = mBack[RU]; mLeft[LC] = mBack[RC]; mLeft[LD] = mBack[RD];
		//D
		mDown[LD] = mBack[RD]; mDown[CD] = mBack[CD]; mDown[RD] = mBack[LD];
		//R
		mRight[RD] = mBack[LD]; mRight[RC] = mBack[LC]; mRight[RU] = mBack[LU];
		//U
		mUp[RU] = mBack[LU]; mUp[CU] = mBack[CU]; mUp[LU] = mBack[RU];
		//M
		mMiddle[LC] = mBack[CC]; mMiddle[LD] = mBack[CD]; mMiddle[LU] = mBack[CU];
		//E
		mEquatorial[LD] = mBack[RC]; mEquatorial[CD] = mBack[CC]; mEquatorial[RD] = mBack[LC];
	}

	public void BackRev()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(B | Rev), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mBack[i];
		}
		RotateRev(mBack, temp);

		//L
		mLeft[LU] = mBack[RU]; mLeft[LC] = mBack[RC]; mLeft[LD] = mBack[RD];
		//D
		mDown[LD] = mBack[RD]; mDown[CD] = mBack[CD]; mDown[RD] = mBack[LD];
		//R
		mRight[RD] = mBack[LD]; mRight[RC] = mBack[LC]; mRight[RU] = mBack[LU];
		//U
		mUp[RU] = mBack[LU]; mUp[CU] = mBack[CU]; mUp[LU] = mBack[RU];
		//M
		mMiddle[LC] = mBack[CC]; mMiddle[LD] = mBack[CD]; mMiddle[LU] = mBack[CU];
		//E
		mEquatorial[LD] = mBack[RC]; mEquatorial[CD] = mBack[CC]; mEquatorial[RD] = mBack[LC];
	}

	public void Middle()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(M), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mMiddle[i];
		}
		Rotate(mMiddle, temp);

		mUp[CU] = mBack[CU] = mMiddle[LU];
		mUp[CC] = mStanding[CU] = mMiddle[CU];
		mUp[CD] = mFront[CU] = mMiddle[RU];
		mBack[CC] = mEquatorial[CD] = mMiddle[LC];
		mFront[CC] = mEquatorial[CU] = mMiddle[RC];
		mDown[CD] = mBack[CD] = mMiddle[LD];
		mDown[CC] = mStanding[CD] = mMiddle[CD];
		mDown[CU] = mFront[CD] = mMiddle[RD];
	}

	public void MiddleRev()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(M | Rev), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mMiddle[i];
		}
		RotateRev(mMiddle, temp);

		mUp[CU] = mBack[CU] = mMiddle[LU];
		mUp[CC] = mStanding[CU] = mMiddle[CU];
		mUp[CD] = mFront[CU] = mMiddle[RU];
		mBack[CC] = mEquatorial[CD] = mMiddle[LC];
		mFront[CC] = mEquatorial[CU] = mMiddle[RC];
		mDown[CD] = mBack[CD] = mMiddle[LD];
		mDown[CC] = mStanding[CD] = mMiddle[CD];
		mDown[CU] = mFront[CD] = mMiddle[RD];
	}

	public void Standing()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(S), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mStanding[i];
		}
		Rotate(mStanding, temp);

		mUp[LC] = mLeft[CU] = mStanding[LU];
		mUp[CC] = mMiddle[CU] = mStanding[CU];
		mUp[RC] = mRight[CU] = mStanding[RU];
		mLeft[CC] = mEquatorial[LC] = mStanding[LC];
		mRight[CC] = mEquatorial[RC] = mStanding[RC];
		mLeft[CD] = mDown[LC] = mStanding[LD];
		mDown[CC] = mMiddle[CD] = mStanding[CD];
		mRight[CD] = mDown[RC] = mStanding[RD];
	}

	public void StandingRev()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(S | Rev), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mStanding[i];
		}
		RotateRev(mStanding, temp);

		mUp[LC] = mLeft[CU] = mStanding[LU];
		mUp[CC] = mMiddle[CU] = mStanding[CU];
		mUp[RC] = mRight[CU] = mStanding[RU];
		mLeft[CC] = mEquatorial[LC] = mStanding[LC];
		mRight[CC] = mEquatorial[RC] = mStanding[RC];
		mLeft[CD] = mDown[LC] = mStanding[LD];
		mDown[CC] = mMiddle[CD] = mStanding[CD];
		mRight[CD] = mDown[RC] = mStanding[RD];
	}

	public void Equatorial()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(E), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mEquatorial[i];
		}
		Rotate(mEquatorial, temp);

		mLeft[LC] = mBack[RC] = mEquatorial[LD];
		mBack[CC] = mMiddle[LC] = mEquatorial[CD];
		mRight[RC] = mBack[LC] = mEquatorial[RD];
		mLeft[CC] = mStanding[LC] = mEquatorial[LC];
		mRight[CC] = mStanding[RC] = mEquatorial[RC];
		mLeft[RC] = mFront[LC] = mEquatorial[LU];
		mFront[CC] = mMiddle[RC] = mEquatorial[CU];
		mRight[LC] = mFront[RC] = mEquatorial[RU];
	}

	public void EquatorialRev()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(E | Rev), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mEquatorial[i];
		}
		RotateRev(mEquatorial, temp);

		mLeft[LC] = mBack[RC] = mEquatorial[LD];
		mBack[CC] = mMiddle[LC] = mEquatorial[CD];
		mRight[RC] = mBack[LC] = mEquatorial[RD];
		mLeft[CC] = mStanding[LC] = mEquatorial[LC];
		mRight[CC] = mStanding[RC] = mEquatorial[RC];
		mLeft[RC] = mFront[LC] = mEquatorial[LU];
		mFront[CC] = mMiddle[RC] = mEquatorial[CU];
		mRight[LC] = mFront[RC] = mEquatorial[RU];
	}

	public void WholeRight()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(WR), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mRight[i];
		}
		Rotate(mRight, temp);
		//B
		mBack[LU] = mRight[RU]; mBack[LC] = mRight[RC]; mBack[LD] = mRight[RD];
		//U
		mUp[RU] = mRight[RU]; mUp[RC] = mRight[CU]; mUp[RD] = mRight[LU];
		//F
		mFront[RU] = mRight[LU]; mFront[RC] = mRight[LC]; mFront[RD] = mRight[LD];
		//D
		mDown[RU] = mRight[LD]; mDown[RC] = mRight[CD]; mDown[RD] = mRight[RD];
		//S
		mStanding[RU] = mRight[CU]; mStanding[RC] = mRight[CC]; mStanding[RD] = mRight[CD];
		//E
		mEquatorial[RU] = mRight[LC]; mEquatorial[RC] = mRight[CC]; mEquatorial[RD]=mRight[RC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mLeft[i];
		}
		RotateRev(mLeft, temp);

		//B
		mBack[RU] = mLeft[LU]; mBack[RC] = mLeft[LC]; mBack[RD] = mLeft[LD];
		//U
		mUp[LU] = mLeft[LU]; mUp[LC] = mLeft[CU]; mUp[LD] = mLeft[RU];
		//F
		mFront[LU] = mLeft[RU]; mFront[LC] = mLeft[RC]; mFront[LD] = mLeft[RD];
		//D
		mDown[LU] = mLeft[RD]; mDown[LC] = mLeft[CD]; mDown[LD] = mLeft[LD];
		//S
		mStanding[LU] = mLeft[CU]; mStanding[LC] = mLeft[CC]; mStanding[LD] = mLeft[CD];
		//E
		mEquatorial[LD] = mLeft[LC]; mEquatorial[LC] = mLeft[CC]; mEquatorial[LU] = mLeft[RC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mMiddle[i];
		}
		RotateRev(mMiddle, temp);
		mUp[CU] = mBack[CU] = mMiddle[LU];
		mUp[CC] = mStanding[CU] = mMiddle[CU];
		mUp[CD] = mFront[CU] = mMiddle[RU];
		mBack[CC] = mEquatorial[CD] = mMiddle[LC];
		mFront[CC] = mEquatorial[CU] = mMiddle[RC];
		mDown[CD] = mBack[CD] = mMiddle[LD];
		mDown[CC] = mStanding[CD] = mMiddle[CD];
		mDown[CU] = mFront[CD] = mMiddle[RD];
	}

	public void WholeLeft()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(WL), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mRight[i];
		}
		RotateRev(mRight, temp);
		//B
		mBack[LU] = mRight[RU]; mBack[LC] = mRight[RC]; mBack[LD] = mRight[RD];
		//U
		mUp[RU] = mRight[RU]; mUp[RC] = mRight[CU]; mUp[RD] = mRight[LU];
		//F
		mFront[RU] = mRight[LU]; mFront[RC] = mRight[LC]; mFront[RD] = mRight[LD];
		//D
		mDown[RU] = mRight[LD]; mDown[RC] = mRight[CD]; mDown[RD] = mRight[RD];
		//S
		mStanding[RU] = mRight[CU]; mStanding[RC] = mRight[CC]; mStanding[RD] = mRight[CD];
		//E
		mEquatorial[RU] = mRight[LC]; mEquatorial[RC] = mRight[CC]; mEquatorial[RD]=mRight[RC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mLeft[i];
		}
		Rotate(mLeft, temp);
		//B
		mBack[RU] = mLeft[LU]; mBack[RC] = mLeft[LC]; mBack[RD] = mLeft[LD];
		//U
		mUp[LU] = mLeft[LU]; mUp[LC] = mLeft[CU]; mUp[LD] = mLeft[RU];
		//F
		mFront[LU] = mLeft[RU]; mFront[LC] = mLeft[RC]; mFront[LD] = mLeft[RD];
		//D
		mDown[LU] = mLeft[RD]; mDown[LC] = mLeft[CD]; mDown[LD] = mLeft[LD];
		//S
		mStanding[LU] = mLeft[CU]; mStanding[LC] = mLeft[CC]; mStanding[LD] = mLeft[CD];
		//E
		mEquatorial[LD] = mLeft[LC]; mEquatorial[LC] = mLeft[CC]; mEquatorial[LU] = mLeft[RC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mMiddle[i];
		}
		Rotate(mMiddle, temp);
		mUp[CU] = mBack[CU] = mMiddle[LU];
		mUp[CC] = mStanding[CU] = mMiddle[CU];
		mUp[CD] = mFront[CU] = mMiddle[RU];
		mBack[CC] = mEquatorial[CD] = mMiddle[LC];
		mFront[CC] = mEquatorial[CU] = mMiddle[RC];
		mDown[CD] = mBack[CD] = mMiddle[LD];
		mDown[CC] = mStanding[CD] = mMiddle[CD];
		mDown[CU] = mFront[CD] = mMiddle[RD];
	}

	public void WholeUp()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(WU), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mUp[i];
		}
		Rotate(mUp, temp);
		//B
		mBack[LU] = mUp[RU]; mBack[CU] = mUp[CU]; mBack[RU] = mUp[LU];
		//L
		mLeft[LU] = mUp[LU]; mLeft[CU] = mUp[LC]; mLeft[RU] = mUp[LD];
		//F
		mFront[LU] = mUp[LD]; mFront[CU] = mUp[CD]; mFront[RU] = mUp[RD];
		//R
		mRight[LU] = mUp[RD]; mRight[CU] = mUp[RC]; mRight[RU] = mUp[RU];
		//M
		mMiddle[LU] = mUp[CU]; mMiddle[CU] = mUp[CC]; mMiddle[RU] = mUp[CD];
		//S
		mStanding[LU] = mUp[LC]; mStanding[CU] = mUp[CC]; mStanding[RU] = mUp[RC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mDown[i];
		}
		RotateRev(mDown, temp);
		//R
		mRight[LD] = mDown[RU]; mRight[CD] = mDown[RC]; mRight[RD] = mDown[RD];
		//B
		mBack[LD] = mDown[RD]; mBack[CD] = mDown[CD]; mBack[RD] = mDown[LD];
		//L
		mLeft[LD] = mDown[LD]; mLeft[CD] = mDown[LC]; mLeft[RD] = mDown[LU];
		//F
		mFront[LD] = mDown[LU]; mFront[CD] = mDown[CU]; mFront[RD] = mDown[RU];
		//M
		mMiddle[LD] = mDown[CD]; mMiddle[CD] = mDown[CC]; mMiddle[RD] = mDown[CU];
		//S
		mStanding[LD] = mDown[LC]; mStanding[CD] = mDown[CC]; mStanding[RD] = mDown[RC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mEquatorial[i];
		}
		RotateRev(mEquatorial, temp);
		mLeft[LC] = mBack[RC] = mEquatorial[LD];
		mBack[CC] = mMiddle[LC] = mEquatorial[CD];
		mRight[RC] = mBack[LC] = mEquatorial[RD];
		mLeft[CC] = mStanding[LC] = mEquatorial[LC];
		mRight[CC] = mStanding[RC] = mEquatorial[RC];
		mLeft[RC] = mFront[LC] = mEquatorial[LU];
		mFront[CC] = mMiddle[RC] = mEquatorial[CU];
		mRight[LC] = mFront[RC] = mEquatorial[RU];
	}

	public void WholeDown()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(WD), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mUp[i];
		}
		RotateRev(mUp, temp);
		//B
		mBack[LU] = mUp[RU]; mBack[CU] = mUp[CU]; mBack[RU] = mUp[LU];
		//L
		mLeft[LU] = mUp[LU]; mLeft[CU] = mUp[LC]; mLeft[RU] = mUp[LD];
		//F
		mFront[LU] = mUp[LD]; mFront[CU] = mUp[CD]; mFront[RU] = mUp[RD];
		//R
		mRight[LU] = mUp[RD]; mRight[CU] = mUp[RC]; mRight[RU] = mUp[RU];
		//M
		mMiddle[LU] = mUp[CU]; mMiddle[CU] = mUp[CC]; mMiddle[RU] = mUp[CD];
		//S
		mStanding[LU] = mUp[LC]; mStanding[CU] = mUp[CC]; mStanding[RU] = mUp[RC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mDown[i];
		}
		Rotate(mDown, temp);
		//R
		mRight[LD] = mDown[RU]; mRight[CD] = mDown[RC]; mRight[RD] = mDown[RD];
		//B
		mBack[LD] = mDown[RD]; mBack[CD] = mDown[CD]; mBack[RD] = mDown[LD];
		//L
		mLeft[LD] = mDown[LD]; mLeft[CD] = mDown[LC]; mLeft[RD] = mDown[LU];
		//F
		mFront[LD] = mDown[LU]; mFront[CD] = mDown[CU]; mFront[RD] = mDown[RU];
		//M
		mMiddle[LD] = mDown[CD]; mMiddle[CD] = mDown[CC]; mMiddle[RD] = mDown[CU];
		//S
		mStanding[LD] = mDown[LC]; mStanding[CD] = mDown[CC]; mStanding[RD] = mDown[RC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mEquatorial[i];
		}
		Rotate(mEquatorial, temp);
		mLeft[LC] = mBack[RC] = mEquatorial[LD];
		mBack[CC] = mMiddle[LC] = mEquatorial[CD];
		mRight[RC] = mBack[LC] = mEquatorial[RD];
		mLeft[CC] = mStanding[LC] = mEquatorial[LC];
		mRight[CC] = mStanding[RC] = mEquatorial[RC];
		mLeft[RC] = mFront[LC] = mEquatorial[LU];
		mFront[CC] = mMiddle[RC] = mEquatorial[CU];
		mRight[LC] = mFront[RC] = mEquatorial[RU];
	}

	public void WholeFront()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(WF), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mFront[i];
		}
		Rotate(mFront, temp);
		//R
		mRight[LU] = mFront[RU]; mRight[LC] = mFront[RC]; mRight[LD] = mFront[RD];
		//D
		mDown[RU] = mFront[RD]; mDown[CU] = mFront[CD]; mDown[LU] = mFront[LD];
		//L
		mLeft[RD] = mFront[LD]; mLeft[RC] = mFront[LC]; mLeft[RU] = mFront[LU];
		//U
		mUp[LD] = mFront[LU]; mUp[CD] = mFront[CU]; mUp[RD] = mFront[RU];
		//M
		mMiddle[RU] = mFront[CU]; mMiddle[RC] = mFront[CC]; mMiddle[RD] = mFront[CD];
		//E
		mEquatorial[LU] = mFront[LC]; mEquatorial[CU] = mFront[CC]; mEquatorial[RU] = mFront[RC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mBack[i];
		}
		RotateRev(mBack, temp);
		//L
		mLeft[LU] = mBack[RU]; mLeft[LC] = mBack[RC]; mLeft[LD] = mBack[RD];
		//D
		mDown[LD] = mBack[RD]; mDown[CD] = mBack[CD]; mDown[RD] = mBack[LD];
		//R
		mRight[RD] = mBack[LD]; mRight[RC] = mBack[LC]; mRight[RU] = mBack[LU];
		//U
		mUp[RU] = mBack[LU]; mUp[CU] = mBack[CU]; mUp[LU] = mBack[RU];
		//M
		mMiddle[LC] = mBack[CC]; mMiddle[LD] = mBack[CD]; mMiddle[LU] = mBack[CU];
		//E
		mEquatorial[LD] = mBack[RC]; mEquatorial[CD] = mBack[CC]; mEquatorial[RD] = mBack[LC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mStanding[i];
		}
		Rotate(mStanding, temp);
		mUp[LC] = mLeft[CU] = mStanding[LU];
		mUp[CC] = mMiddle[CU] = mStanding[CU];
		mUp[RC] = mRight[CU] = mStanding[RU];
		mLeft[CC] = mEquatorial[LC] = mStanding[LC];
		mRight[CC] = mEquatorial[RC] = mStanding[RC];
		mLeft[CD] = mDown[LC] = mStanding[LD];
		mDown[CC] = mMiddle[CD] = mStanding[CD];
		mRight[CD] = mDown[RC] = mStanding[RD];
	}

	public void WholeBack()
	{
		if(isMoving) return;
		Sound();
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(WB), 0,10);

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mFront[i];
		}
		RotateRev(mFront, temp);
		//R
		mRight[LU] = mFront[RU]; mRight[LC] = mFront[RC]; mRight[LD] = mFront[RD];
		//D
		mDown[RU] = mFront[RD]; mDown[CU] = mFront[CD]; mDown[LU] = mFront[LD];
		//L
		mLeft[RD] = mFront[LD]; mLeft[RC] = mFront[LC]; mLeft[RU] = mFront[LU];
		//U
		mUp[LD] = mFront[LU]; mUp[CD] = mFront[CU]; mUp[RD] = mFront[RU];
		//M
		mMiddle[RU] = mFront[CU]; mMiddle[RC] = mFront[CC]; mMiddle[RD] = mFront[CD];
		//E
		mEquatorial[LU] = mFront[LC]; mEquatorial[CU] = mFront[CC]; mEquatorial[RU] = mFront[RC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mBack[i];
		}
		Rotate(mBack, temp);
		//L
		mLeft[LU] = mBack[RU]; mLeft[LC] = mBack[RC]; mLeft[LD] = mBack[RD];
		//D
		mDown[LD] = mBack[RD]; mDown[CD] = mBack[CD]; mDown[RD] = mBack[LD];
		//R
		mRight[RD] = mBack[LD]; mRight[RC] = mBack[LC]; mRight[RU] = mBack[LU];
		//U
		mUp[RU] = mBack[LU]; mUp[CU] = mBack[CU]; mUp[LU] = mBack[RU];
		//M
		mMiddle[LC] = mBack[CC]; mMiddle[LD] = mBack[CD]; mMiddle[LU] = mBack[CU];
		//E
		mEquatorial[LD] = mBack[RC]; mEquatorial[CD] = mBack[CC]; mEquatorial[RD] = mBack[LC];

		for(int i=0;i<temp.length;i++)
		{
			temp[i] = mStanding[i];
		}
		RotateRev(mStanding, temp);
		mUp[LC] = mLeft[CU] = mStanding[LU];
		mUp[CC] = mMiddle[CU] = mStanding[CU];
		mUp[RC] = mRight[CU] = mStanding[RU];
		mLeft[CC] = mEquatorial[LC] = mStanding[LC];
		mRight[CC] = mEquatorial[RC] = mStanding[RC];
		mLeft[CD] = mDown[LC] = mStanding[LD];
		mDown[CC] = mMiddle[CD] = mStanding[CD];
		mRight[CD] = mDown[RC] = mStanding[RD];
	}

	public boolean isMoving()
	{
		return isMoving;
	}

	public void Shuffle()
	{
		if(isMoving) return;
		Sound();
		mScramble = Scranble();
		scrambleIndex = 0;
		Timer timer = new Timer();
		timer.schedule(new MyTimerTask(mScramble[scrambleIndex],this), 0,10);
	}

	private int[] Scranble()
	{
		int[] scranble = new int[30];

		Random rnd = new Random();
		Random rnd2 = new Random();
		for(int i=0;i<scranble.length;i++)
		{
			scranble[i] = rnd.nextInt(9);
			int n = rnd2.nextInt(2);
			if(n == 1)
			{
				scranble[i] = scranble[i] | Rev;
			}
		}
		return scranble;
	}

	@Override
	public void finish(int men)
	{
		Exchange(men);
		scrambleIndex++;
		if(scrambleIndex < mScramble.length)
		{
			Sound();
			Timer timer = new Timer();
			timer.schedule(new MyTimerTask(mScramble[scrambleIndex],this), 0,10);
		}
	}

	private void Exchange(int men)
	{
		switch(men)
		{
			case R:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mRight[i];
				}
				Rotate(mRight, temp);
				//B
				mBack[LU] = mRight[RU]; mBack[LC] = mRight[RC]; mBack[LD] = mRight[RD];
				//U
				mUp[RU] = mRight[RU]; mUp[RC] = mRight[CU]; mUp[RD] = mRight[LU];
				//F
				mFront[RU] = mRight[LU]; mFront[RC] = mRight[LC]; mFront[RD] = mRight[LD];
				//D
				mDown[RU] = mRight[LD]; mDown[RC] = mRight[CD]; mDown[RD] = mRight[RD];
				//S
				mStanding[RU] = mRight[CU]; mStanding[RC] = mRight[CC]; mStanding[RD] = mRight[CD];
				//E
				mEquatorial[RU] = mRight[LC]; mEquatorial[RC] = mRight[CC]; mEquatorial[RD]=mRight[RC];
				break;

			case L:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mLeft[i];
				}
				Rotate(mLeft, temp);
				//B
				mBack[RU] = mLeft[LU]; mBack[RC] = mLeft[LC]; mBack[RD] = mLeft[LD];
				//U
				mUp[LU] = mLeft[LU]; mUp[LC] = mLeft[CU]; mUp[LD] = mLeft[RU];
				//F
				mFront[LU] = mLeft[RU]; mFront[LC] = mLeft[RC]; mFront[LD] = mLeft[RD];
				//D
				mDown[LU] = mLeft[RD]; mDown[LC] = mLeft[CD]; mDown[LD] = mLeft[LD];
				//S
				mStanding[LU] = mLeft[CU]; mStanding[LC] = mLeft[CC]; mStanding[LD] = mLeft[CD];
				//E
				mEquatorial[LD] = mLeft[LC]; mEquatorial[LC] = mLeft[CC]; mEquatorial[LU] = mLeft[RC];
				break;

			case U:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mUp[i];
				}
				Rotate(mUp, temp);
				//B
				mBack[LU] = mUp[RU]; mBack[CU] = mUp[CU]; mBack[RU] = mUp[LU];
				//L
				mLeft[LU] = mUp[LU]; mLeft[CU] = mUp[LC]; mLeft[RU] = mUp[LD];
				//F
				mFront[LU] = mUp[LD]; mFront[CU] = mUp[CD]; mFront[RU] = mUp[RD];
				//R
				mRight[LU] = mUp[RD]; mRight[CU] = mUp[RC]; mRight[RU] = mUp[RU];
				//M
				mMiddle[LU] = mUp[CU]; mMiddle[CU] = mUp[CC]; mMiddle[RU] = mUp[CD];
				//S
				mStanding[LU] = mUp[LC]; mStanding[CU] = mUp[CC]; mStanding[RU] = mUp[RC];
				break;

			case D:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mDown[i];
				}
				Rotate(mDown, temp);
				//R
				mRight[LD] = mDown[RU]; mRight[CD] = mDown[RC]; mRight[RD] = mDown[RD];
				//B
				mBack[LD] = mDown[RD]; mBack[CD] = mDown[CD]; mBack[RD] = mDown[LD];
				//L
				mLeft[LD] = mDown[LD]; mLeft[CD] = mDown[LC]; mLeft[RD] = mDown[LU];
				//F
				mFront[LD] = mDown[LU]; mFront[CD] = mDown[CU]; mFront[RD] = mDown[RU];
				//M
				mMiddle[LD] = mDown[CD]; mMiddle[CD] = mDown[CC]; mMiddle[RD] = mDown[CU];
				//S
				mStanding[LD] = mDown[LC]; mStanding[CD] = mDown[CC]; mStanding[RD] = mDown[RC];
				break;

			case F:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mFront[i];
				}
				Rotate(mFront, temp);
				//R
				mRight[LU] = mFront[RU]; mRight[LC] = mFront[RC]; mRight[LD] = mFront[RD];
				//D
				mDown[RU] = mFront[RD]; mDown[CU] = mFront[CD]; mDown[LU] = mFront[LD];
				//L
				mLeft[RD] = mFront[LD]; mLeft[RC] = mFront[LC]; mLeft[RU] = mFront[LU];
				//U
				mUp[LD] = mFront[LU]; mUp[CD] = mFront[CU]; mUp[RD] = mFront[RU];
				//M
				mMiddle[RU] = mFront[CU]; mMiddle[RC] = mFront[CC]; mMiddle[RD] = mFront[CD];
				//E
				mEquatorial[LU] = mFront[LC]; mEquatorial[CU] = mFront[CC]; mEquatorial[RU] = mFront[RC];
				break;

			case B:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mBack[i];
				}
				Rotate(mBack, temp);
				//L
				mLeft[LU] = mBack[RU]; mLeft[LC] = mBack[RC]; mLeft[LD] = mBack[RD];
				//D
				mDown[LD] = mBack[RD]; mDown[CD] = mBack[CD]; mDown[RD] = mBack[LD];
				//R
				mRight[RD] = mBack[LD]; mRight[RC] = mBack[LC]; mRight[RU] = mBack[LU];
				//U
				mUp[RU] = mBack[LU]; mUp[CU] = mBack[CU]; mUp[LU] = mBack[RU];
				//M
				mMiddle[LC] = mBack[CC]; mMiddle[LD] = mBack[CD]; mMiddle[LU] = mBack[CU];
				//E
				mEquatorial[LD] = mBack[RC]; mEquatorial[CD] = mBack[CC]; mEquatorial[RD] = mBack[LC];
				break;

			case M:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mMiddle[i];
				}
				Rotate(mMiddle, temp);
				mUp[CU] = mBack[CU] = mMiddle[LU];
				mUp[CC] = mStanding[CU] = mMiddle[CU];
				mUp[CD] = mFront[CU] = mMiddle[RU];
				mBack[CC] = mEquatorial[CD] = mMiddle[LC];
				mFront[CC] = mEquatorial[CU] = mMiddle[RC];
				mDown[CD] = mBack[CD] = mMiddle[LD];
				mDown[CC] = mStanding[CD] = mMiddle[CD];
				mDown[CU] = mFront[CD] = mMiddle[RD];
				break;

			case S:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mStanding[i];
				}
				Rotate(mStanding, temp);
				mUp[LC] = mLeft[CU] = mStanding[LU];
				mUp[CC] = mMiddle[CU] = mStanding[CU];
				mUp[RC] = mRight[CU] = mStanding[RU];
				mLeft[CC] = mEquatorial[LC] = mStanding[LC];
				mRight[CC] = mEquatorial[RC] = mStanding[RC];
				mLeft[CD] = mDown[LC] = mStanding[LD];
				mDown[CC] = mMiddle[CD] = mStanding[CD];
				mRight[CD] = mDown[RC] = mStanding[RD];
				break;

			case E:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mEquatorial[i];
				}
				Rotate(mEquatorial, temp);
				mLeft[LC] = mBack[RC] = mEquatorial[LD];
				mBack[CC] = mMiddle[LC] = mEquatorial[CD];
				mRight[RC] = mBack[LC] = mEquatorial[RD];
				mLeft[CC] = mStanding[LC] = mEquatorial[LC];
				mRight[CC] = mStanding[RC] = mEquatorial[RC];
				mLeft[RC] = mFront[LC] = mEquatorial[LU];
				mFront[CC] = mMiddle[RC] = mEquatorial[CU];
				mRight[LC] = mFront[RC] = mEquatorial[RU];
				break;

			case R | Rev:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mRight[i];
				}
				RotateRev(mRight, temp);
				//B
				mBack[LU] = mRight[RU]; mBack[LC] = mRight[RC]; mBack[LD] = mRight[RD];
				//U
				mUp[RU] = mRight[RU]; mUp[RC] = mRight[CU]; mUp[RD] = mRight[LU];
				//F
				mFront[RU] = mRight[LU]; mFront[RC] = mRight[LC]; mFront[RD] = mRight[LD];
				//D
				mDown[RU] = mRight[LD]; mDown[RC] = mRight[CD]; mDown[RD] = mRight[RD];
				//S
				mStanding[RU] = mRight[CU]; mStanding[RC] = mRight[CC]; mStanding[RD] = mRight[CD];
				//E
				mEquatorial[RU] = mRight[LC]; mEquatorial[RC] = mRight[CC]; mEquatorial[RD]=mRight[RC];
				break;

			case L | Rev:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mLeft[i];
				}
				RotateRev(mLeft, temp);
				//B
				mBack[RU] = mLeft[LU]; mBack[RC] = mLeft[LC]; mBack[RD] = mLeft[LD];
				//U
				mUp[LU] = mLeft[LU]; mUp[LC] = mLeft[CU]; mUp[LD] = mLeft[RU];
				//F
				mFront[LU] = mLeft[RU]; mFront[LC] = mLeft[RC]; mFront[LD] = mLeft[RD];
				//D
				mDown[LU] = mLeft[RD]; mDown[LC] = mLeft[CD]; mDown[LD] = mLeft[LD];
				//S
				mStanding[LU] = mLeft[CU]; mStanding[LC] = mLeft[CC]; mStanding[LD] = mLeft[CD];
				//E
				mEquatorial[LD] = mLeft[LC]; mEquatorial[LC] = mLeft[CC]; mEquatorial[LU] = mLeft[RC];
				break;

			case U | Rev:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mUp[i];
				}
				RotateRev(mUp, temp);
				//B
				mBack[LU] = mUp[RU]; mBack[CU] = mUp[CU]; mBack[RU] = mUp[LU];
				//L
				mLeft[LU] = mUp[LU]; mLeft[CU] = mUp[LC]; mLeft[RU] = mUp[LD];
				//F
				mFront[LU] = mUp[LD]; mFront[CU] = mUp[CD]; mFront[RU] = mUp[RD];
				//R
				mRight[LU] = mUp[RD]; mRight[CU] = mUp[RC]; mRight[RU] = mUp[RU];
				//M
				mMiddle[LU] = mUp[CU]; mMiddle[CU] = mUp[CC]; mMiddle[RU] = mUp[CD];
				//S
				mStanding[LU] = mUp[LC]; mStanding[CU] = mUp[CC]; mStanding[RU] = mUp[RC];
				break;

			case D | Rev:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mDown[i];
				}
				RotateRev(mDown, temp);
				//R
				mRight[LD] = mDown[RU]; mRight[CD] = mDown[RC]; mRight[RD] = mDown[RD];
				//B
				mBack[LD] = mDown[RD]; mBack[CD] = mDown[CD]; mBack[RD] = mDown[LD];
				//L
				mLeft[LD] = mDown[LD]; mLeft[CD] = mDown[LC]; mLeft[RD] = mDown[LU];
				//F
				mFront[LD] = mDown[LU]; mFront[CD] = mDown[CU]; mFront[RD] = mDown[RU];
				//M
				mMiddle[LD] = mDown[CD]; mMiddle[CD] = mDown[CC]; mMiddle[RD] = mDown[CU];
				//S
				mStanding[LD] = mDown[LC]; mStanding[CD] = mDown[CC]; mStanding[RD] = mDown[RC];
				break;

			case F | Rev:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mFront[i];
				}
				RotateRev(mFront, temp);
				//R
				mRight[LU] = mFront[RU]; mRight[LC] = mFront[RC]; mRight[LD] = mFront[RD];
				//D
				mDown[RU] = mFront[RD]; mDown[CU] = mFront[CD]; mDown[LU] = mFront[LD];
				//L
				mLeft[RD] = mFront[LD]; mLeft[RC] = mFront[LC]; mLeft[RU] = mFront[LU];
				//U
				mUp[LD] = mFront[LU]; mUp[CD] = mFront[CU]; mUp[RD] = mFront[RU];
				//M
				mMiddle[RU] = mFront[CU]; mMiddle[RC] = mFront[CC]; mMiddle[RD] = mFront[CD];
				//E
				mEquatorial[LU] = mFront[LC]; mEquatorial[CU] = mFront[CC]; mEquatorial[RU] = mFront[RC];
				break;

			case B | Rev:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mBack[i];
				}
				RotateRev(mBack, temp);
				//L
				mLeft[LU] = mBack[RU]; mLeft[LC] = mBack[RC]; mLeft[LD] = mBack[RD];
				//D
				mDown[LD] = mBack[RD]; mDown[CD] = mBack[CD]; mDown[RD] = mBack[LD];
				//R
				mRight[RD] = mBack[LD]; mRight[RC] = mBack[LC]; mRight[RU] = mBack[LU];
				//U
				mUp[RU] = mBack[LU]; mUp[CU] = mBack[CU]; mUp[LU] = mBack[RU];
				//M
				mMiddle[LC] = mBack[CC]; mMiddle[LD] = mBack[CD]; mMiddle[LU] = mBack[CU];
				//E
				mEquatorial[LD] = mBack[RC]; mEquatorial[CD] = mBack[CC]; mEquatorial[RD] = mBack[LC];
				break;

			case M | Rev:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mMiddle[i];
				}
				RotateRev(mMiddle, temp);
				mUp[CU] = mBack[CU] = mMiddle[LU];
				mUp[CC] = mStanding[CU] = mMiddle[CU];
				mUp[CD] = mFront[CU] = mMiddle[RU];
				mBack[CC] = mEquatorial[CD] = mMiddle[LC];
				mFront[CC] = mEquatorial[CU] = mMiddle[RC];
				mDown[CD] = mBack[CD] = mMiddle[LD];
				mDown[CC] = mStanding[CD] = mMiddle[CD];
				mDown[CU] = mFront[CD] = mMiddle[RD];
				break;

			case S | Rev:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mStanding[i];
				}
				RotateRev(mStanding, temp);
				mUp[LC] = mLeft[CU] = mStanding[LU];
				mUp[CC] = mMiddle[CU] = mStanding[CU];
				mUp[RC] = mRight[CU] = mStanding[RU];
				mLeft[CC] = mEquatorial[LC] = mStanding[LC];
				mRight[CC] = mEquatorial[RC] = mStanding[RC];
				mLeft[CD] = mDown[LC] = mStanding[LD];
				mDown[CC] = mMiddle[CD] = mStanding[CD];
				mRight[CD] = mDown[RC] = mStanding[RD];
				break;

			case E | Rev:
				for(int i=0;i<temp.length;i++)
				{
					temp[i] = mEquatorial[i];
				}
				RotateRev(mEquatorial, temp);
				mLeft[LC] = mBack[RC] = mEquatorial[LD];
				mBack[CC] = mMiddle[LC] = mEquatorial[CD];
				mRight[RC] = mBack[LC] = mEquatorial[RD];
				mLeft[CC] = mStanding[LC] = mEquatorial[LC];
				mRight[CC] = mStanding[RC] = mEquatorial[RC];
				mLeft[RC] = mFront[LC] = mEquatorial[LU];
				mFront[CC] = mMiddle[RC] = mEquatorial[CU];
				mRight[LC] = mFront[RC] = mEquatorial[RU];
				break;

			default:
				break;
		}
	}

	private void Sound()
	{
		mAudio[audioIndex].setFramePosition(0);
        mAudio[audioIndex].start();
        if (audioIndex < 3) {
            audioIndex++;
        } else {
            audioIndex = 0;
        }
	}

	private class MyTimerTask extends TimerTask
	{
		private int cnt = 0;
		private int men;
		private FinishListener mListener;

		public MyTimerTask(int men)
		{
			this.setMen(men);
		}

		public MyTimerTask(int men,FinishListener listener)
		{
			this.setMen(men);
			mListener = listener;
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
				if(mListener != null)
				{
					mListener.finish(men);
				}
			}
			else
			{
				if((getMen() & Rev) == Rev)
				{
					int m = getMen() & ~Rev;
					switch (m)
					{
						case RubiksCube.R:
							for(int i = 0;i<mRight.length;i++)
							{
								mRight[i].rotateX(Math.PI/18);
							}
							break;

						case RubiksCube.L:
							for(int i = 0;i<mLeft.length;i++)
							{
								mLeft[i].rotateX(-Math.PI/18);
							}
							break;

						case RubiksCube.U:
							for(int i = 0;i<mUp.length;i++)
							{
								mUp[i].rotateY(Math.PI/18);
							}
							break;

						case RubiksCube.D:
							for(int i = 0;i<mDown.length;i++)
							{
								mDown[i].rotateY(-Math.PI/18);
							}
							break;

						case RubiksCube.F:
							for(int i = 0;i<mFront.length;i++)
							{
								mFront[i].rotateZ(Math.PI/18);
							}
							break;

						case RubiksCube.B:
							for(int i = 0;i<mBack.length;i++)
							{
								mBack[i].rotateZ(-Math.PI/18);
							}
							break;

						case RubiksCube.M:
							for(int i = 0;i<mMiddle.length;i++)
							{
								if(mMiddle[i] != null)
									mMiddle[i].rotateX(-Math.PI/18);
							}
							break;

						case RubiksCube.S:
							for(int i = 0;i<mStanding.length;i++)
							{
								if(mStanding[i] != null)
									mStanding[i].rotateZ(Math.PI/18);
							}
							break;

						case RubiksCube.E:
							for(int i = 0;i<mEquatorial.length;i++)
							{
								if(mEquatorial[i] != null)
									mEquatorial[i].rotateY(-Math.PI/18);
							}
							break;

						default:
							break;
					}
				}
				else
				{
					switch (getMen())
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

						case RubiksCube.D:
							for(int i = 0;i<mDown.length;i++)
							{
								mDown[i].rotateY(Math.PI/18);
							}
							break;

						case RubiksCube.F:
							for(int i = 0;i<mFront.length;i++)
							{
								mFront[i].rotateZ(-Math.PI/18);
							}
							break;

						case RubiksCube.B:
							for(int i = 0;i<mBack.length;i++)
							{
								mBack[i].rotateZ(Math.PI/18);
							}
							break;

						case RubiksCube.M:
							for(int i = 0;i<mMiddle.length;i++)
							{
								if(mMiddle[i] != null)
									mMiddle[i].rotateX(Math.PI/18);
							}
							break;

						case RubiksCube.S:
							for(int i = 0;i<mStanding.length;i++)
							{
								if(mStanding[i] != null)
									mStanding[i].rotateZ(-Math.PI/18);
							}
							break;

						case RubiksCube.E:
							for(int i = 0;i<mEquatorial.length;i++)
							{
								if(mEquatorial[i] != null)
									mEquatorial[i].rotateY(Math.PI/18);
							}
							break;

						case RubiksCube.WR:
							for(int i = 0;i<mRight.length;i++)
							{
								mRight[i].rotateX(-Math.PI/18);
								mLeft[i].rotateX(-Math.PI/18);
								if(mMiddle[i] != null)
									mMiddle[i].rotateX(-Math.PI/18);
							}
							break;

						case RubiksCube.WL:
							for(int i = 0;i<mRight.length;i++)
							{
								mRight[i].rotateX(Math.PI/18);
								mLeft[i].rotateX(Math.PI/18);
								if(mMiddle[i] != null)
									mMiddle[i].rotateX(Math.PI/18);
							}
							break;

						case RubiksCube.WU:
							for(int i = 0;i<mUp.length;i++)
							{
								mUp[i].rotateY(-Math.PI/18);
								mDown[i].rotateY(-Math.PI/18);
								if(mEquatorial[i] != null)
									mEquatorial[i].rotateY(-Math.PI/18);
							}
							break;

						case RubiksCube.WD:
							for(int i = 0;i<mUp.length;i++)
							{
								mUp[i].rotateY(Math.PI/18);
								mDown[i].rotateY(Math.PI/18);
								if(mEquatorial[i] != null)
									mEquatorial[i].rotateY(Math.PI/18);
							}
							break;

						case RubiksCube.WF:
							for(int i = 0;i<mFront.length;i++)
							{
								mFront[i].rotateZ(-Math.PI/18);
								mBack[i].rotateZ(-Math.PI/18);
								if(mStanding[i] != null)
									mStanding[i].rotateZ(-Math.PI/18);
							}
							break;

						case RubiksCube.WB:
							for(int i = 0;i<mFront.length;i++)
							{
								mFront[i].rotateZ(Math.PI/18);
								mBack[i].rotateZ(Math.PI/18);
								if(mStanding[i] != null)
									mStanding[i].rotateZ(Math.PI/18);
							}
							break;

						default:
							break;
					}
				}

			}
		}

		public int getMen()
		{
			return men;
		}

		public void setMen(int men)
		{
			this.men = men;
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
