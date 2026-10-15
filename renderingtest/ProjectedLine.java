package renderingtest;

public class ProjectedLine {
    public ProjectedVertex[] projectedLine = new ProjectedVertex[2];

    public ProjectedLine(ProjectedVertex v1, ProjectedVertex v2) {
        projectedLine[0] = v1;
        projectedLine[1] = v2;
    }

    @Override 
    public String toString() {
        return "("+projectedLine[0].x+", "+projectedLine[0].y+") -> ("+projectedLine[1].x+", "+projectedLine[1].y+")";
    }
}
