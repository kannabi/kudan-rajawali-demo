package eu.kudan.ar;

public class Utils {

    public static String toStringMatrix44(float[] matrix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, j = 1; i < 16; ++i, ++j) {
            stringBuilder
                    .append(matrix[i])
                    .append(" | ");
            if (j == 4) {
                j = 0;
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    public static String toStringMatrix44(double[] matrix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, j = 1; i < 16; ++i, ++j) {
            stringBuilder
                    .append(matrix[i])
                    .append(" | ");
            if (j == 4) {
                j = 0;
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    public static String toStringMatrix34(float[] matrix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, j = 1; i < 12; ++i, ++j) {
            stringBuilder
                    .append(matrix[i])
                    .append(" | ");
            if (j == 4) {
                j = 0;
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    public static double[] castToDouble(float[] matrix) {
        double[] res = new double[16];
        for (int i = 0; i < matrix.length; ++i) {
            res[i] = matrix[i];
        }
        return res;
    }
}
