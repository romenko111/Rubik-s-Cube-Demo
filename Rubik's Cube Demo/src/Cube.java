import java.awt.*;
import javax.swing.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.universe.*;

public class Cube extends BranchGroup
{
	public static final int R =0;
	public static final int L =1;
	public static final int U =2;
	public static final int D =3;
	public static final int F = 4;
	public static final int B =5;

	private double mR;
	private double mX;
	private double mY;
	private double mZ;
	private Color3f[] mColors; //R,L,U,D,F,B
	private Color3f mBaseColor;
	Point3d[] mVertices;
	int[] mIndices = { 3, 2, 6,  3, 6, 7, //R
								4, 5, 1,  4, 1, 0, //L
								4, 0, 3,  4, 3, 7, //U
								1, 5, 6,  1, 6, 2, //D
								0, 1, 2,  0, 2, 3, //F
								7, 6, 5,  7, 5, 4 //B
							};
	int[] colorIndices = { 0, 0, 0,  0, 0, 0,    1, 1, 1,  1, 1, 1,
            2, 2, 2,  2, 2, 2,    3, 3, 3,  3, 3, 3,
            4, 4, 4,  4, 4, 4,    5, 5, 5,  5, 5, 5 };

	public Cube(double x,double y,double z,double r)
	{
		mR = r;
		mX = x;
		mY = y;
		mZ = z;
		mBaseColor = new Color3f(Color.BLACK);
		for (int i=0;i<=mColors.length;i++)
		{
			mColors[i] = new Color3f(Color.BLACK);
		}
		mVertices = new Point3d[8];
		mVertices[0] =  new Point3d(-mR+mX,mR+mY,mR+mZ);	//左上前
		mVertices[1] =  new Point3d(-mR+mX,-mR+mY,mR+mZ);	//左下前
		mVertices[2] =  new Point3d(mR+mX,-mR+mY,mR+mZ);	//右下前
		mVertices[3] =  new Point3d(mR+mX,mR+mY,mR+mZ);		//右上前
		mVertices[4] =  new Point3d(-mR+mX,mR+mY,-mR+mZ);	//左上奥
		mVertices[5] =  new Point3d(-mR+mX,-mR+mY,-mR+mZ);//左下奥
		mVertices[6] =  new Point3d(mR+mX,-mR+mY,-mR+mZ);//右下奥
		mVertices[7] =  new Point3d(mR+mX,mR+mY,-mR+mZ);	//右上奥

		generate();
	}

	private void generate()
	{
		 // 立方体を生成
        IndexedTriangleArray geometry = new IndexedTriangleArray(
            mVertices.length, GeometryArray.COORDINATES | GeometryArray.COLOR_3, mIndices.length);
        geometry.setCoordinates(0, mVertices);
        geometry.setCoordinateIndices(0, mIndices);
        geometry.setColors(0, mColors);
        geometry.setColorIndices(0, colorIndices);

        // 回転を設定
        Shape3D shape = new Shape3D(geometry);
        TransformGroup trans = new TransformGroup();

        // BranchGroup に登録
        trans.addChild(shape);
        addChild(trans);
	}

	public void setColors(Color3f[] colors)
	{
		mColors = colors;
		generate();
	}

}
