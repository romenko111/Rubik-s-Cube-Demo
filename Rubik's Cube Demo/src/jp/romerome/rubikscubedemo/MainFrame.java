package jp.romerome.rubikscubedemo;
import java.awt.GraphicsConfiguration;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import jp.romerome.rubikscubedemo.RubiksCube.RubikColor;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class MainFrame extends JFrame implements KeyListener
{
	private RubiksCube mRubiksCube;

	public static void main(String[] args)
	{
		System.out.println("LD Library Path:" + System.getProperty("java.library.path"));
		new MainFrame();
	}

	public MainFrame()
	{
		super("Rubik's Cube Demo");
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Java3D関係の設定
		GraphicsConfiguration g_config = SimpleUniverse
				.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(g_config);
		canvas.addKeyListener(this);
		add(canvas);
		setVisible(true);

		// SimpleUniverseを生成
		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.getViewingPlatform().setNominalViewingTransform();

		//視点についてのハードウェア情報を取得。
        ViewingPlatform vp = universe.getViewingPlatform();

        //視点のための座標変換クラスを用意
        TransformGroup Camera = vp.getViewPlatformTransform();

        Transform3D cameraTrans = new Transform3D();
        cameraTrans.lookAt(new Point3d(7,8,10),new Point3d(0,0,0), new Vector3d(0,1,0));
        cameraTrans.invert();
        Camera.setTransform(cameraTrans);

        RubikColor[] colors = {RubikColor.RED,RubikColor.ORANGE,RubikColor.WHITE,RubikColor.BLUE,RubikColor.GREEN,RubikColor.YELLOW};
		mRubiksCube  = new RubiksCube(this);
		universe.addBranchGraph(mRubiksCube);

		// JFrame を表示
		setVisible(true);
		addKeyListener(this);
	}

	int x = 0;
	@Override
	public void keyPressed(KeyEvent e)
	{
		System.out.println("keypressed");
		if((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0)
		{
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_R:
					if(!mRubiksCube.isMoving())
					mRubiksCube.WholeRight();
					break;

				case KeyEvent.VK_U:
					mRubiksCube.WholeUp();
					break;

				case KeyEvent.VK_F:
					mRubiksCube.WholeFront();
					break;

				case KeyEvent.VK_L:
					mRubiksCube.WholeLeft();
					break;

				case KeyEvent.VK_D:
					mRubiksCube.WholeDown();
					break;

				case KeyEvent.VK_B:
					mRubiksCube.WholeBack();
					break;

				default:
					break;
			}
		}
		else if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0)
		{
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_R:
					if(!mRubiksCube.isMoving())
					mRubiksCube.RightRev();
					break;

				case KeyEvent.VK_U:
					mRubiksCube.UpRev();
					break;

				case KeyEvent.VK_F:
					mRubiksCube.FrontRev();
					break;

				case KeyEvent.VK_L:
					mRubiksCube.LeftRev();
					break;

				case KeyEvent.VK_D:
					mRubiksCube.DownRev();
					break;

				case KeyEvent.VK_B:
					mRubiksCube.BackRev();
					break;

				case KeyEvent.VK_M:
					mRubiksCube.MiddleRev();
					break;

				case KeyEvent.VK_S:
					mRubiksCube.StandingRev();
					break;

				case KeyEvent.VK_E:
					mRubiksCube.EquatorialRev();
					break;

				default:
					break;
			}
		}
		else
		{
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_R:
					if(!mRubiksCube.isMoving())
					mRubiksCube.Right();
					break;

				case KeyEvent.VK_U:
					mRubiksCube.Up();
					break;

				case KeyEvent.VK_F:
					mRubiksCube.Front();
					break;

				case KeyEvent.VK_L:
					mRubiksCube.Left();
					break;

				case KeyEvent.VK_D:
					mRubiksCube.Down();
					break;

				case KeyEvent.VK_B:
					mRubiksCube.Back();
					break;

				case KeyEvent.VK_M:
					mRubiksCube.Middle();
					break;

				case KeyEvent.VK_S:
					mRubiksCube.Standing();
					break;

				case KeyEvent.VK_E:
					mRubiksCube.Equatorial();
					break;

				default:
					break;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		System.out.println("keyreleased");
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		System.out.println("keytyped");
	}
}
