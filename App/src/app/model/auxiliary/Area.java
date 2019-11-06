/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.model.auxiliary;

/**
 *
 * @author janek
 */
public class Area extends Point {
    private int SizeX;
    private int SizeY;

    public Area(int X, int Y, int SizeX, int SizeY) {
        super(X, Y);
        this.SizeX = SizeX;
        this.SizeY = SizeY;
    }

    public int getSizeX() {
        return SizeX;
    }

    public void setSizeX(int SizeX) {
        this.SizeX = SizeX;
    }

    public int getSizeY() {
        return SizeY;
    }

    public void setSizeY(int SizeY) {
        this.SizeY = SizeY;
    }

}
