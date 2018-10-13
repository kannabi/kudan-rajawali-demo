package eu.kudan.ar;

import org.rajawali3d.math.Quaternion;
import org.rajawali3d.math.vector.Vector3;

public interface MarkerMoveListener {
//    void onMarkerUpdate(float[] poseMatrix, float xCoord, float yCoord, float zCoord);

    void onMarkerUpdate(Quaternion quaternion, float xCoord, float yCoord, float zCoord);
}
