public class Body {
  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;
  public double G = 6.67e-11;


  public Body(double xP, double yP, double xV,
              double yV, double m, String img) {
                xxPos = xP;
                yyPos = yP;
                xxVel = xV;
                yyVel = yV;
                mass = m;
                imgFileName = "images/" + img;
              }

  public Body(Body b) {
    xxPos = b.xxPos;
    yyPos = b.yyPos;
    xxVel = b.xxVel;
    yyVel = b.yyVel;
    mass = b.mass;
    imgFileName = b.imgFileName;
  }

  public double calcDistance(Body b) {
    double x;
    double y;
    x = b.xxPos - this.xxPos;
    y = b.yyPos - this.yyPos;
    return Math.sqrt(x*x + y*y);
  }

  public double calcForceExertedBy(Body b) {
    return G*this.mass*b.mass/(calcDistance(b)*calcDistance(b));
  }

  public double calcForceExertedByX(Body b) {
    double dx = b.xxPos - this.xxPos;
    return calcForceExertedBy(b)*dx/calcDistance(b);
  }

  public double calcForceExertedByY(Body b) {
    double dy = b.yyPos - this.yyPos;
    return calcForceExertedBy(b)*dy/calcDistance(b);
  }

  public double calcNetForceExertedByX(Body[] b) {
    double xNetForce = 0.0;
    for (int i = 0; i < b.length; i += 1) {
      if (this.equals(b[i])) {
        continue;
      }
      xNetForce += this.calcForceExertedByX(b[i]);
    }
    return xNetForce;
  }

  public double calcNetForceExertedByY(Body[] b) {
    double yNetForce = 0.0;
    for (int i = 0; i < b.length; i += 1) {
      if (this.equals(b[i])) {
        continue;
      }
      yNetForce += this.calcForceExertedByY(b[i]);
    }
    return yNetForce;
  }

  public void update(double t, double Fx, double Fy) {
    double ax = Fx / this.mass;
    double ay = Fy / this.mass;
    double newvx = this.xxVel + t * ax;
    double newvy = this.yyVel + t * ay;
    double newx = this.xxPos + t * newvx;
    double newy = this.yyPos + t * newvy;
    this.xxPos = newx;
    this.yyPos = newy;
    this.xxVel = newvx;
    this.yyVel = newvy;
  }

  public void draw() {
    StdDraw.picture(this.xxPos, this.yyPos, this.imgFileName);
  }

}
