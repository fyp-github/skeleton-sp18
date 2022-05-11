public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private final double G = 6.67e-11;

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFilename) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFilename;
    }

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet planet) {
        double deltaX = planet.xxPos - xxPos;
        double deltaY = planet.yyPos - yyPos;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public double calcForceExertedBy(Planet planet) {
        double r = calcDistance(planet);
        return G * mass * planet.mass / (r * r);
    }

    public double calcForceExertedByX(Planet planet) {
        double cos = (planet.xxPos - xxPos) / calcDistance(planet);
        return calcForceExertedBy(planet) * cos;
    }

    public double calcForceExertedByY(Planet planet) {
        double sin = (planet.yyPos - yyPos) / calcDistance(planet);
        return calcForceExertedBy(planet) * sin;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double fx = 0.0;
        for (Planet planet : planets) {
            if(planet.equals(this))
                continue;
            fx += calcForceExertedByX(planet);
        }
        return fx;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double fy = 0.0;
        for (Planet planet : planets) {
            if(planet.equals(this))
                continue;
            fy += calcForceExertedByY(planet);
        }
        return fy;
    }

    public void update(double dt, double fx, double fy) {
        double ax = fx / mass;
        double ay = fy / mass;
        xxVel += ax * dt;
        yyVel += ay * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
