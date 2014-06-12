import java.awt.Color;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class MainFrame extends JFrame
{
	public static void main(String[] args)
	{
		new MainFrame();
	}

	public MainFrame()
	{
		super("Rubik's Cube Demo");
		setSize(300, 300);
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
        cameraTrans.lookAt(new Point3d(5,5,10),new Point3d(0,0,0), new Vector3d(0,1,0));
        cameraTrans.invert();
        Camera.setTransform(cameraTrans);

        Color3f[] colors = new Color3f[6];
		colors[0] = new Color3f(Color.RED);
		colors[1] = new Color3f(Color.ORANGE);
		colors[2] = new Color3f(Color.WHITE);
		colors[3] = new Color3f(Color.BLUE);
		colors[4] = new Color3f(Color.GREEN);
		colors[5] = new Color3f(Color.YELLOW);
        Cube cube  = new Cube(0, 0, 0, 0.5);
        universe.addBranchGraph(cube);

        // JFrame を表示
        setVisible(true);
	}
}
