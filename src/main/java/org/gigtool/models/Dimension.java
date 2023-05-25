package org.gigtool.models;

/**
 * This class represents an abstract idea of the size of a piece of equipment
 */
public class Dimension {
    private int length;
    private int width;
    private int height;

    public Dimension(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }



    /***
     * Calc the Volume of an imaginary cuboid
     * @return volume
     */
    public int getCuboidVolume(){
        return this.height * this.length * this.width;
    }

}
