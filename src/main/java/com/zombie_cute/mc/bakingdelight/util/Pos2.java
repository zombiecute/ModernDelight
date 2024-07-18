package com.zombie_cute.mc.bakingdelight.util;

public class Pos2 {
    private int x;
    private int y;
    public Pos2(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public Pos2 copy(){
        return new Pos2(this.x,this.y);
    }
    // Generate a Pos2( [0,maxX) , [0,maxY) )
    public static Pos2 random(int maxX, int maxY){
        return new Pos2(
                (int)(Math.random() * maxX),
                (int)(Math.random() * maxY)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pos2 pos2){
            return pos2.getX() == this.x && pos2.getY() == this.y;
        } else return false;
    }
    public boolean equals(int x, int y) {
        return x == this.x && y == this.y;
    }

}
