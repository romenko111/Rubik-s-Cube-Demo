package jp.romerome.rubikscubedemo;

import java.awt.Color;
import java.awt.Component;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3d;

import jp.romerome.rubikscubedemo.RubiksCube.RubikColor;

import com.sun.j3d.utils.image.TextureLoader;

public class Cube extends BranchGroup
{

	TransformGroup mTransGroup;
	Transform3D mRot;
	Vector3d mPoint;
	private double mR;
	private double mX;
	private double mY;
	private double mZ;
	Point3d[] mVertices;
	Component mComponent;
	RubikColor[] mColors;

	public Cube(Component component, double x, double y, double z, double r,
			RubikColor[] colors)
	{
		mComponent = component;
		mR = r;
		mX = x;
		mY = y;
		mZ = z;
		mColors = colors;
		mPoint = new Vector3d(x, y, z);
		mTransGroup = new TransformGroup();
		mTransGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		mRot = new Transform3D();
		mVertices = new Point3d[8];
		mVertices[0] = new Point3d(-mR + mX, mR + mY, mR + mZ); // 左上前
		mVertices[1] = new Point3d(-mR + mX, -mR + mY, mR + mZ); // 左下前
		mVertices[2] = new Point3d(mR + mX, -mR + mY, mR + mZ); // 右下前
		mVertices[3] = new Point3d(mR + mX, mR + mY, mR + mZ); // 右上前
		mVertices[4] = new Point3d(-mR + mX, mR + mY, -mR + mZ); // 左上奥
		mVertices[5] = new Point3d(-mR + mX, -mR + mY, -mR + mZ);// 左下奥
		mVertices[6] = new Point3d(mR + mX, -mR + mY, -mR + mZ);// 右下奥
		mVertices[7] = new Point3d(mR + mX, mR + mY, -mR + mZ); // 右上奥

		generate();
	}

	private void generate()
	{
		Point3d[] vertices = new Point3d[4];
		//R
		vertices[0] = mVertices[3];
		vertices[1] = mVertices[2];
		vertices[2] = mVertices[6];
		vertices[3] = mVertices[7];
		mTransGroup.addChild(createShape3D(vertices,mColors[RubiksCube.R]));
		//L
		vertices[0] = mVertices[4];
		vertices[1] = mVertices[5];
		vertices[2] = mVertices[1];
		vertices[3] = mVertices[0];
		mTransGroup.addChild(createShape3D(vertices,mColors[RubiksCube.L]));
		//U
		vertices[0] = mVertices[4];
		vertices[1] = mVertices[0];
		vertices[2] = mVertices[3];
		vertices[3] = mVertices[7];
		mTransGroup.addChild(createShape3D(vertices,mColors[RubiksCube.U]));
		//D
		vertices[0] = mVertices[6];
		vertices[1] = mVertices[2];
		vertices[2] = mVertices[1];
		vertices[3] = mVertices[5];
		mTransGroup.addChild(createShape3D(vertices,mColors[RubiksCube.D]));
		//F
		vertices[0] = mVertices[0];
		vertices[1] = mVertices[1];
		vertices[2] = mVertices[2];
		vertices[3] = mVertices[3];
		mTransGroup.addChild(createShape3D(vertices,mColors[RubiksCube.F]));
		//B
		vertices[0] = mVertices[7];
		vertices[1] = mVertices[6];
		vertices[2] = mVertices[5];
		vertices[3] = mVertices[4];
		mTransGroup.addChild(createShape3D(vertices,mColors[RubiksCube.B]));
		addChild(mTransGroup);
	}

	private Shape3D createShape3D(Point3d[] vertices, RubikColor color)
	{

		TexCoord2f[] txcoords =
		{
			new TexCoord2f(0.0f, 0.0f), new TexCoord2f(1.0f, 0.0f), // 左下, 右下
			new TexCoord2f(1.0f, 1.0f), new TexCoord2f(0.0f, 1.0f) // 右上, 左上
		};

		QuadArray geometry = new QuadArray(4, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);
		geometry.setCoordinates(0, vertices);
		geometry.setTextureCoordinates(0, 0, txcoords);

		Texture2D texture2d = (Texture2D) new TextureLoader("image/"+color.getFileName(),
				mComponent).getTexture();
		Appearance app = new Appearance();
		app.setTexture(texture2d);

		Shape3D shape = new Shape3D(geometry, app);
		shape.setCapability(Shape3D.ALLOW_GEOMETRY_READ);

		return shape;
	}

	public void rotateX(double angle)
	{
		Transform3D trans = new Transform3D();
		trans.rotX(angle);
		trans.mul(mRot);
		mRot = trans;
		mTransGroup.setTransform(mRot);
	}

	public void rotateY(double angle)
	{
		Transform3D trans = new Transform3D();
		trans.rotY(angle);
		trans.mul(mRot);
		mRot = trans;
		mTransGroup.setTransform(mRot);
	}

	public void rotateZ(double angle)
	{
		Transform3D trans = new Transform3D();
		trans.rotZ(angle);
		trans.mul(mRot);
		mRot = trans;
		mTransGroup.setTransform(mRot);
	}


}
