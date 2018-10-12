package eu.kudan.ar;

public interface MarkerMoveListener {
    void onMarkerUpdate(float[] poseMatrix, float xCoord, float yCoord, float zCoord);
}
