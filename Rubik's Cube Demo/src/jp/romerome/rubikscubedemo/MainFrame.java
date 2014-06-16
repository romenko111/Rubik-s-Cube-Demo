package jp.romerome.rubikscubedemo;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import jp.romerome.rubikscubedemo.RubiksCube.RubikColor;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class MainFrame extends JFrame implements KeyListener
{
	private Cube mCube;

	public static void main(String[] args)
	{
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
        cameraTrans.lookAt(new Point3d(10,10,10),new Point3d(0,0,0), new Vector3d(0,1,0));
        cameraTrans.invert();
        Camera.setTransform(cameraTrans);

        RubikColor[] colors = {RubikColor.RED,RubikColor.ORANGE,RubikColor.WHITE,RubikColor.BLUE,RubikColor.GREEN,RubikColor.YELLOW};
		mCube  = new Cube(this,1, 1, 1, 0.5, colors);
		universe.addBranchGraph(mCube);

		// JFrame を表示
		setVisible(true);
		addKeyListener(this);
	}

	int x = 0;
	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_R:
				mCube.rotateX(Math.PI/6);
				break;

			case KeyEvent.VK_U:
				mCube.rotateY(Math.PI/6);
				break;

			case KeyEvent.VK_F:
				mCube.rotateZ(Math.PI/6);
				break;

			default:
				break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
}
