import java.awt.*;
import javax.swing.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.universe.*;

public class Cube extends BranchGroup
{

	private double mR = 0.5;
	private double mX = 0;
	private double mY = 0;
	private Color3f[] mColors; //F, ,U,R
	Point3d[] vertices;
	int[] mIndices = { 0, 1, 2,  0, 2, 3,    4, 5, 1,  4, 1, 0,
            4, 0, 3,  4, 3, 7,    3, 2, 6,  3, 6, 7,
            7, 6, 5,  7, 5, 4,    1, 5, 6,  1, 6, 2 };
	int[] colorIndices = { 0, 0, 0,  0, 0, 0,    1, 1, 1,  1, 1, 1,
            2, 2, 2,  2, 2, 2,    3, 3, 3,  3, 3, 3,
            4, 4, 4,  4, 4, 4,    5, 5, 5,  5, 5, 5 };

	public Cube()
	{
		mR = 0.5;
		mX = 0;
		mY = 0;
		mColors = new Color3f[6];
		for(int i=0;i<mColors.length;i++)
		{
			mColors[i] = new Color3f();
		}
		vertices = new Point3d[8];
		vertices[0] =  new Point3d(-mR,mR,mR);	//左上前
		vertices[1] =  new Point3d(-mR,-mR,mR);	//左下前
		vertices[2] =  new Point3d(mR,-mR,mR);	//右下前
		vertices[3] =  new Point3d(mR,mR,mR);		//右上前
		vertices[4] =  new Point3d(-mR,mR,-mR);	//左上奥
		vertices[5] =  new Point3d(-mR,-mR,-mR);//左下奥
		vertices[6] =  new Point3d(mR,-mR,-mR);//右下奥
		vertices[7] =  new Point3d(mR,mR,-mR);	//右上奥
	}

}
