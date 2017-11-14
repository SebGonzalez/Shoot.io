package Client.Util;

public class Vec2
{
    float x, y;

    public Vec2(float f)
    {
        this(f, f);
    }

    public Vec2(Vec2 v)
    {
        this(v.x, v.y);
    }

    public Vec2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vec2 clone()
    {
        return new Vec2(this);
    }

    public float length()
    {
        return (float) Math.sqrt(dot(this));
    }

    public float dot(Vec2 v)
    {
        return this.x * v.x + this.y * v.y;
    }

    public Vec2 normalize()
    {
        return div(length());
    }

    public float distance(Vec2 v)
    {
        return sub(v).length();
    }

    public Vec2 add(float x, float y)
    {
        return new Vec2(this.x + x, this.y + y);
    }

    public Vec2 add(float f)
    {
        return add(f, f);
    }

    public Vec2 add(Vec2 v)
    {
        return add(v.x, v.y);
    }

    public Vec2 sub(float x, float y)
    {
        return new Vec2(this.x - x, this.y - y);
    }

    public Vec2 sub(float f)
    {
        return sub(f, f);
    }

    public Vec2 sub(Vec2 v)
    {
        return sub(v.x, v.y);
    }

    public Vec2 mult(float x, float y)
    {
        return new Vec2(this.x * x, this.y * y);
    }

    public Vec2 mult(float f)
    {
        return mult(f, f);
    }

    public Vec2 mult(Vec2 v)
    {
        return mult(v.x, v.y);
    }

    public Vec2 div(float x, float y)
    {
        return new Vec2(this.x / x, this.y / y);
    }

    public Vec2 div(float f)
    {
        return div(f, f);
    }

    public Vec2 div(Vec2 v)
    {
        return div(v.x, v.y);
    }
}
