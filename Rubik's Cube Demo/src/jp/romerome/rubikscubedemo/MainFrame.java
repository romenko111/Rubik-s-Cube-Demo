package jp.romerome.rubikscubedemo;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Interpolator;
import javax.media.j3d.Light;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import jp.romerome.rubikscubedemo.RubiksCube.RubikColor;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO;

public class MainFrame extends JFrame implements KeyListener
{
	private RubiksCube mRubiksCube;
	private Cube mCube;
	private RotationInterpolator interpolator;
	private Alpha alpha;
	private double angle;

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
		//universe.addBranchGraph(mRubiksCube);

		mCube = new Cube(this, 1, 1, 1, 0.5, colors);
		TransformGroup rotation= mCube.getTransformGroup();
		alpha=new Alpha(
				1,
				Alpha.INCREASING_ENABLE,
				0,
				0,
				500,
				0,
				0,
				0,
				0,
				0);
		Transform3D axis = new Transform3D();
		axis.setRotation(new AxisAngle4d(0.0, 0.0, 1.0, Math.PI/2));
		interpolator=new RotationInterpolator(alpha,rotation,axis,0,(float) (Math.PI/2));
		BoundingSphere bounds=new BoundingSphere(
				new Point3d(0.0,0.0,0.0),100.0);	// 原点を中心とする半径 100.0 の範囲

			interpolator.setSchedulingBounds(bounds);
		rotation.addChild(interpolator);
		universe.addBranchGraph(mCube);

		// JFrame を表示
		setVisible(true);
		addKeyListener(this);
	}

	int x = 0;
	@Override
	public void keyPressed(KeyEvent e)
	{
		System.out.println("keypressed");
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_R:
				if(!mRubiksCube.isMoving())
				mRubiksCube.Right();

				if(alpha.finished())
				{
					angle+= Math.PI/2;
					if(angle > Math.PI *2)
						angle %= Math.PI*2;
					interpolator.setMinimumAngle((float) angle);
					interpolator.setMaximumAngle((float) (angle + Math.PI/2));
					alpha.setStartTime(System.currentTimeMillis());
				}

				break;

			case KeyEvent.VK_U:
				//mCube.rotateY(Math.PI/6);
				break;

			case KeyEvent.VK_F:
				//mCube.rotateZ(Math.PI/6);
				break;

			case KeyEvent.VK_L:
				mRubiksCube.Left(Math.PI/6);
				break;

			default:
				break;
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
