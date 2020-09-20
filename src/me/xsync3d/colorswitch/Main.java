package me.xsync3d.colorswitch;

import java.io.File;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class Main {


    private static Color toGreen(int color) {

        /**
         *  This function was originally meant to turn images into greyscale.
         *  This is the best example of a bug turning into feature.
         */

        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;
        //Y' = 0.2989 R + 0.5870 G + 0.1140 B

        Color newGreen = new Color((int) (0.2989*red), (int) (0.5870*green), (int) (0.1140*blue));

        return newGreen;
    }

    private static Color toGreyscale(int color) {

        /**
         *  Yes there is a built-in function for this, I decided to write my own version of it
         *  to learn how it actually works though.
         */

        //RGB values are funny in java and are usually returned/stored as integers
        //this is just a hexadecimal representation of bits in an rgb value
        int blue = color & 0xff;
        int green = (color & 0xff00) >> 8;
        int red = (color & 0xff0000) >> 16;

        //Y' = 0.2989 R + 0.5870 G + 0.1140 B is the greyscale equation
        int sum = (int) (0.2989*red) + (int) (0.5870*green) + (int) (0.1140*blue);

        Color newBlack = new Color(sum, sum, sum);

        return newBlack;
    }


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        input.useDelimiter("\r\n");
        System.out.println("Enter the path to your image (exclude 'C:\\User\\): ");
        String path = input.nextLine();
        String fileFormat = path.substring(path.lastIndexOf(".") + 1);

        String user = System.getenv("USERPROFILE");
        File file = new File(user + "\\" + path);


        System.out.println("File path: " + file);

        try {
            BufferedImage image = ImageIO.read(file);
            int width = image.getWidth();
            int height = image.getHeight();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Color newColor = toGreyscale(image.getRGB(x, y));
                    int newPixel = newColor.getRGB();
                    image.setRGB(x, y, newPixel);
                }
            }

            File outputFile = new File(user + "\\" + path);
            System.out.println("Output at: " + outputFile);
            ImageIO.write(image, "png", outputFile);

        } catch (IOException e) {
            System.out.println("An Error has occurred: ");
            e.printStackTrace();
        }
    }

}



























