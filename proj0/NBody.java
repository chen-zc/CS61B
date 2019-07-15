public class NBody {
  public static void main(String[] args) {
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];
    double radius = readRadius(filename);
    Body[] bodies = readBodies(filename);

    StdDraw.enableDoubleBuffering();
    StdDraw.setScale(-radius, radius);
    double time = 0;
    while (time <= T) {
      double[] xForces = new double[bodies.length];
      double[] yForces = new double[bodies.length];

      int i = 0;
      for (Body b : bodies) {
        xForces[i] = b.calcNetForceExertedByX(bodies);
        yForces[i] = b.calcNetForceExertedByY(bodies);
        i = i + 1;
      }

      for (int j = 0; j < bodies.length; j += 1) {
        bodies[j].update(time, xForces[j], yForces[j]);
      }

      StdDraw.clear();
      StdDraw.picture(0, 0, "images/starfield.jpg");

      for (Body b : bodies) {
        b.draw();
      }
      StdDraw.show();
      StdDraw.pause(10);
      time += dt;
    }

    StdOut.printf("%d\n", bodies.length);
    StdOut.printf("%.2e\n", radius);
    for (int i = 0; i < bodies.length; i++) {
        StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                      bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                      bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
    }
  }


  public static double readRadius(String filename) {
    In file = new In(filename);
    int number = file.readInt();
    double radius = file.readDouble();
    return radius;
  }

  public static Body[] readBodies(String filename) {
    In file = new In(filename);
    int number = file.readInt();
    double radius = file.readDouble();
    Body[] bodies = new Body[number];
    for (int i = 0; i < number; i += 1) {
      double xxPos = file.readDouble();
      double yyPos = file.readDouble();
      double xxVel = file.readDouble();
      double yyVel = file.readDouble();
      double mass = file.readDouble();
      String imgFileName = file.readString();
      Body b = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
      bodies[i] = b;
    }
    return bodies;
  }


}
