package scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

import java.util.HashMap;
import java.util.Map;

public class Camera {

    public enum ProjectionType {
        FRONT, SIDE, PLAN, ISOMETRIC, DIMETRIC, TRIMETRIC,
        ONE_POINT, TWO_POINT, THREE_POINT, OBLIQUE_PERSPECTIVE,
        CAVALIER, CABINET
    }

    private static final Map<ProjectionType, Projection> PROJECTION_MAP = new HashMap<>();
    private static final Map<ProjectionType, ViewingTransform> VIEW_TRANSFORM_MAP = new HashMap<>();

    static {
        // Initialize the PROJECTION_MAP with lambda functions
        PROJECTION_MAP.put(ProjectionType.FRONT, gl2 -> gl2.glOrtho(-5, 5, -5, 5, -20, 20));
        PROJECTION_MAP.put(ProjectionType.SIDE, gl2 -> gl2.glOrtho(-5, 5, -5, 5, -20, 20));
        PROJECTION_MAP.put(ProjectionType.PLAN, gl2 -> gl2.glOrtho(-5, 5, -5, 5, -20, 20));
        PROJECTION_MAP.put(ProjectionType.ISOMETRIC, gl2 -> gl2.glOrtho(-7, 7, -7, 7, -20, 20));
        PROJECTION_MAP.put(ProjectionType.DIMETRIC, gl2 -> gl2.glOrtho(-7, 7, -7, 7, -20, 20));
        PROJECTION_MAP.put(ProjectionType.TRIMETRIC, gl2 -> gl2.glOrtho(-7, 7, -7, 7, -20, 20));
        PROJECTION_MAP.put(ProjectionType.ONE_POINT, gl2 -> gl2.glFrustum(-5, 5, -5, 5, 5, 25));
        PROJECTION_MAP.put(ProjectionType.TWO_POINT, gl2 -> gl2.glFrustum(-5, 5, -5,  5,  5, 25));
        PROJECTION_MAP.put(ProjectionType.THREE_POINT, gl2 -> gl2.glFrustum(-5, 5, -5, 5, 5, 25));
        PROJECTION_MAP.put(ProjectionType.OBLIQUE_PERSPECTIVE, gl2 -> gl2.glFrustum(-5, 5, -5, 5, 5, 25));
        // maybe add cavalier and cabinet

        // Initialize the VIEW_TRANSFORM_MAP with lambda functions
        VIEW_TRANSFORM_MAP.put(ProjectionType.FRONT, glu -> glu.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0));
        VIEW_TRANSFORM_MAP.put(ProjectionType.SIDE, glu -> glu.gluLookAt(10, 0, 0, 0, 0, 0, 0, 1, 0));
        VIEW_TRANSFORM_MAP.put(ProjectionType.PLAN, glu -> glu.gluLookAt(0, 10, 0, 0, 0, 0, 1, 0, 0));
        VIEW_TRANSFORM_MAP.put(ProjectionType.ISOMETRIC, glu -> glu.gluLookAt(5, 5, 5, 0, 0, 0, 0, 1, 0));
        VIEW_TRANSFORM_MAP.put(ProjectionType.DIMETRIC, glu -> glu.gluLookAt(5, 10, 5, 0, 0, 0, 0, 1, 0));
        VIEW_TRANSFORM_MAP.put(ProjectionType.TRIMETRIC, glu -> glu.gluLookAt(10, 5, 3, 0, 0, 0, 0, 1, 0));
        VIEW_TRANSFORM_MAP.put(ProjectionType.ONE_POINT, glu -> glu.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0));
        VIEW_TRANSFORM_MAP.put(ProjectionType.TWO_POINT, glu -> glu.gluLookAt(10, 0, 10, 0, 0, 0, 0, 1, 0));
        VIEW_TRANSFORM_MAP.put(ProjectionType.THREE_POINT, glu -> glu.gluLookAt(10, 10, 10, 0, 0, 0, 0, 1, 0));
        VIEW_TRANSFORM_MAP.put(ProjectionType.OBLIQUE_PERSPECTIVE, glu -> glu.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0));
        // maybe add cavalier and cabinet
    }

    private ProjectionType currentProjection;

    public void setProjection(ProjectionType projectionType) {
        this.currentProjection = projectionType;
    }

    public void applyTransformations(GL2 gl2, GLU glu) {
        if (currentProjection != null) {
            PROJECTION_MAP.get(currentProjection).applyProjection(gl2);
            VIEW_TRANSFORM_MAP.get(currentProjection).applyViewingTransform(glu);
            
            gl2.glMatrixMode(GL2.GL_MODELVIEW);
            gl2.glLoadIdentity();
        }
    }
}

@FunctionalInterface
interface Projection {
    void applyProjection(GL2 gl2);
}

@FunctionalInterface
interface ViewingTransform {
    void applyViewingTransform(GLU glu);
}

