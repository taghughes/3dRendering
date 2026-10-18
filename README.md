Weekend project that renders lines from a 3d plane onto a 2d JPanel. 

Implemented classes include:

Vertex: a point in 3d space.
  Instance Variables:
    x (double)
    y (double)
    z (double)

  Constructors:
    Vertex();
      sets x, y, and z to default value of 0.
    Vertex(double x_position, double y_position, double z_position);
      sets x, y, and z to the positions indicated in the arguments.

  Methods:
    setPos(double x_position, double y_position, double z_position)
      sets x to x_position, y to y_position, z to z_position
    changePos(double x_change, double y_change, double z_change)
      adds x_change to x, y_change to y, z_change to z
    add(Vertex v1) returns: Vertex
      adds v1.x to x, v1.y to y, and v1.z to z, returns this vector
    multiply(double factor) returns: Vertex
      multiplies x, y, and z by a factor, returns this vector
    toString() returns: String
      format: (*x*, *y*, *z*)

Line: two connected vertices.
  Class Variables:
    lines (ArrayList<Line>)

  Instance Variables:
    v1 (Vertex)
    v2 (Vertex)
    length (double)

  Constructors:
    Line(Vertex vertex1, Vertex vertex2);
      sets v1 and v2 to the vertices in the arguments, uses pythagorean theorem for length.
    
  Methods:
    toString()
      format: *v1* -> *v2*; *length*
    Static Methods:
      getLines() returns: ArrayList<Line>
        returns lines.

**README IS A WORK IN PROGRESS**

Camera:
  Instance Variables:
    cameraPosition (Vertex)
    xrot (double)
    yrot (double)
    fov (int)
    screenWidth (int)
    screenHeight (int)
    focalLength (double)
    screenCenterX (int)
    screenCenterY (int)
    CAMERA_SPEED (double)
    CAMERA_ROTATE_SPEED (double)

  Constructors:
    Camera();
      Initializes with default values. Default


ProjectedVertex:

ProjectedLine:

LineRenderer (extends JPanel):
