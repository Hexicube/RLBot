package rlbot;

public class Vector {
    public double x,y,z;
    
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector(Vector v) {
        set(v);
    }
    
    public Vector set(Vector v) {
        return set(v.x, v.y, v.z);
    }
    
    public Vector set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    public Vector mul(double scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
        return this;
    }
    
    public static Vector sub(Vector t1, Vector t2) {
        return new Vector(t1.x - t2.x, t1.y - t2.y, t1.z - t2.z);
    }
    
    public Vector sub(Vector t1) {
        this.x -= t1.x;
        this.y -= t1.y;
        this.z -= t1.z;
        return this;
    }
    
    public static Vector add(Vector t1, Vector t2) {
        return new Vector(t1.x + t2.x, t1.y + t2.y, t1.z + t2.z);
    }
    
    public Vector add(Vector t1) {
        this.x += t1.x;
        this.y += t1.y;
        this.z += t1.z;
        return this;
    }
    
    public double lenSq() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }
    
    public double length() {
        return Math.sqrt(lenSq());
    }
    
    public Vector invert(boolean clone) {
        if(clone) return new Vector(-x, -y, -z);
        
        this.x *= -1;
        this.y *= -1;
        this.z *= -1;
        return this;
    }
    
    public Vector normalize(boolean clone) {
        double norm = 1.0 / length();
        if(clone) return new Vector(x * norm, y * norm, z * norm);
        
        x *= norm;
        y *= norm;
        z *= norm;
        return this;
    }
    
    public static Vector cross(Vector v1, Vector v2) {
        return new Vector(v1.y * v2.z - v1.z * v2.y,
                          v2.x * v1.z - v2.z * v1.x,
                          v1.x * v2.y - v1.y * v2.x);
    }
    
    public double dot(Vector v1) {
        return this.x * v1.x + this.y * v1.y + this.z * v1.z;
    }
    
    @Override
    public int hashCode() {
        long bits = 7L;
        bits = 31L * bits + Double.doubleToLongBits(x);
        bits = 31L * bits + Double.doubleToLongBits(y);
        bits = 31L * bits + Double.doubleToLongBits(z);
        return (int)(bits ^ (bits >> 32));
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == this) { return true; }
        if(obj instanceof Vector) {
            Vector v = (Vector)obj;
            return (x == v.x) && (y == v.y) && (z == v.z);
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Vec3d[" + x + ", " + y + ", " + z + "]";
    }
}