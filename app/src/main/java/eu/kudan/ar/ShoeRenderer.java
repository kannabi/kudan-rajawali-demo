package eu.kudan.ar;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.CubeMapTexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.Quaternion;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Cube;
import org.rajawali3d.primitives.Line3D;
import org.rajawali3d.renderer.Renderer;
import org.rajawali3d.scene.Scene;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static eu.kudan.ar.Utils.castToDouble;

public class ShoeRenderer extends Renderer implements MarkerMoveListener {

    private Object3D mainObject;
    private DirectionalLight mDirectionalLight;

    private float[] poseMatrix;

    private float xCoord = 0f;
    private float yCoord = 0f;
    private float zCoord = 0f;
    private final static float COORDINATE_RATIO = 100.0f;

    private final static double Z_CORRECTION = -120;
//    private final static double X_CORRECTION = -50;
    private final static double Y_CORRECTION = -50;

    private ObjectCorrection objectCorrection = new ObjectCorrection();

    public ShoeRenderer(Context context) {
        super(context);
    }

    @Override
    public void initScene() {

        mDirectionalLight = new DirectionalLight(-1f, .5f, 2f);
        mDirectionalLight.setColor(1f, 1f, 1f);
        mDirectionalLight.setPower(3);
        getCurrentScene().addLight(mDirectionalLight);

        getCurrentCamera().setZ(5f);
        getCurrentCamera().setFarPlane(4000f);

        LoaderOBJ parser = new LoaderOBJ(this, R.raw.shoe_rot_moved_obj);
        try
        {
            parser.parse();
            mainObject = parser.getParsedObject();
            objectCorrection.setScale(60.0);
            objectCorrection.setX(5.0);
            objectCorrection.setY(5.0);

            Object3D cylinder = mainObject.getChildByName("Cylinder");
            cylinder.setBlendingEnabled(true);
            cylinder.setDepthMaskEnabled(true);

            Material material = new Material();
            material.enableLighting(true);
            material.setDiffuseMethod(new DiffuseMethod.Lambert());
            material.setColorInfluence(0);
            Texture shoeTexture = new Texture("Shoe", R.drawable.shoe);
            try{
                material.addTexture(shoeTexture);
            } catch (ATexture.TextureException error){
                Log.d("ShoeRenderer.initScene", error.toString());
            }
            mainObject.getChildByName("group2_Box001").setMaterial(material);

            getCurrentScene().addChild(
                    mainObject
//                    mainObject = getCube()
            );
        }
        catch (ParsingException e) {
            e.printStackTrace();
        }

        getCurrentScene().setBackgroundColor(0f, 0f, 0f, 0f);
    }

    private Object3D getCube() {
        Material material = new Material();
        material.enableLighting(true);
        material.setDiffuseMethod(new DiffuseMethod.Lambert());
        Object3D object;
        int[] ids = {
                R.drawable.black, R.drawable.red, R.drawable.blue,
                R.drawable.green, R.drawable.purple, R.drawable.white
        };

        CubeMapTexture cubemaps = new CubeMapTexture("cubemaps", ids);
        cubemaps.isEnvironmentTexture(true);
        try {
            material.addTexture(cubemaps);
        } catch (ATexture.TextureException e) {
            e.printStackTrace();
        }

        object = new Cube(1.5f);
        object.setMaterial(material);
        object.setPosition(0f, 0f, 0f);
        return object;
    }

    private List<Line3D> getAxis() {
        return Arrays.asList(
                addLine(
                        getCurrentScene(),
                        new Vector3(0, 0, 0), new Vector3(5, 0, 0),
                        0xFFFF0000
                ),
                addLine(
                        getCurrentScene(),
                        new Vector3(0, 0, 0), new Vector3(0, 5, 0),
                        0xFF00FF00
                ),
                addLine(
                        getCurrentScene(),
                        new Vector3(0, 0, 0), new Vector3(0, 0, 5),
                        0xFF0000FF
                )
        );
    }

    private Line3D addLine(Scene scene, Vector3 start, Vector3 end, int color) {
        Stack<Vector3> stack = new Stack<>();
        Material axisMaterial = new Material();
        axisMaterial.useVertexColors(true);
        stack.push(start);
        stack.push(end);
        int[] colors = {color, color};

        Line3D line = new Line3D(stack, 3f, colors);
        line.setMaterial(axisMaterial);
        scene.addChild(line);
        return line;
    }


    @Override
    public void onTouchEvent(MotionEvent event) {
    }


    @Override
    public void onOffsetsChanged(float x, float y, float z, float w, int i, int j) {
    }

    private boolean watchVisible = false;


    double i = 0.0;
    int k = 0;
    @Override
    public void onRender(final long ellapsedRealtime, final double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);

        if (poseMatrix != null) {
            if (k++ != 1) {
                if (!watchVisible) {
                    mainObject.setVisible(true);
                }
                watchVisible = true;
                double[] moveMatrix =
                        castToDouble(poseMatrix)
                        ;
                updatePosition(mainObject, moveMatrix, true);
                correctScale(objectCorrection.getScale(), mainObject);
                poseMatrix = null;
                k = 0;
            }
        } else {
            mainObject.setVisible(false);
            watchVisible = false;
        }
    }

    private void printCoordinates() {
        System.out.println("=========================================");
        System.out.println(String.format("x: %f y: %f z: %f", xCoord, yCoord, zCoord));
    }

    private void correctScale(double newScale, Object3D object) {
        if (object.getScaleX() != newScale) {
            object.setScale(newScale);
        }
    }

    private void updatePosition(Object3D object, double[] moveMatrix, boolean correct) {
        object.setOrientation(
            new Quaternion().fromMatrix(moveMatrix)
        );

//        Log.d("POSITION", String.format("x: %f y: %f z: %f", xCoord, yCoord, zCoord));

        object.setX(xCoord + (correct ? objectCorrection.getX() : 0));
        object.setY(yCoord + (correct ? objectCorrection.getY() : 0));
        object.setZ(zCoord + (correct ? objectCorrection.getZ() : 0));
    }

    @Override
    public void onMarkerUpdate(float[] poseMatrix, float xCoord, float yCoord, float zCoord) {
        this.poseMatrix = poseMatrix;
        this.xCoord = yCoord * COORDINATE_RATIO;
        this.yCoord = -xCoord * COORDINATE_RATIO;
        this.zCoord = zCoord * COORDINATE_RATIO;
    }

    public ObjectCorrection getObjectCorrection() {
        return objectCorrection;
    }

    public void setObjectCorrection(ObjectCorrection objectCorrection) {
        this.objectCorrection = objectCorrection;
    }
}