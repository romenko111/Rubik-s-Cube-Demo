import java.awt.GraphicsConfiguration;
import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

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
	}
}
