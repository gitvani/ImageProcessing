/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Dao Trung Duyet
 */
public class Erosion {

    private BufferedImage inputImage;
    private BufferedImage outputImage;
    private int width ;
   	private int height ;
    public Erosion(BufferedImage inputImage) {
        this.inputImage = inputImage;
        width = inputImage.getWidth() ; 
        height = inputImage.getHeight() ; 
        outputImage = new BufferedImage( width , height , BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage getOutputImage() {
        return outputImage;
    }

    public int[] peripheryValue(int x, int y) {
        int[] value = new int[2];
        if (x < 0) {
            value[0] = 0;
        }
        if (x > width - 1) {
            value[0] = width- 1;
        }
        if (x > 0 && x < width) {
            value[0] = x;
        }

        if (y < 0) {
            value[1] = 0;
        }
        if (y > height - 1) {
            value[1] = height - 1;
        }
        if (y > 0 && y < height) {
            value[1] = y;
        }
        return value;
    }

    public void processing(int size) {
        int row=(size-1)/2;
        int col=(size-1)/2;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int dem = 0;  //dem so luong trung nhau.
                for (int k = -row; k <= row; k++) {
                    for (int m = -col; m <= col; m++) {
                        int x=0,y=0;
                        if ((i + k) < 0 || (i + k) >= width || (j + m) < 0 || (j + m) >= height) {
                            int[] v = new int[2];
                            v = peripheryValue((i+k), (j+m));
                            x = v[0];
                            y = v[1];
                        } else {
                            x = i+k;
                            y = j+m;
                        }
                        int pi = inputImage.getRGB(x,y);
                        int b = (pi) & 0xff;
                        if (b != 255) {
                            dem++;
                        }
                    }
                }
                if (dem == (size*size)) { //neu nhu ko co diem nao cham thi set mau giong anh goc
                    Color cl = new Color(0, 0, 0, 255);
                    int pi = cl.getRGB();
                    outputImage.setRGB(i, j, pi);
                } else if (dem != 9) {
                    Color cl = new Color(255, 255, 255, 255);
                    int pi = cl.getRGB();
                    outputImage.setRGB(i, j, pi);
                }
            }
        }
    }
}
