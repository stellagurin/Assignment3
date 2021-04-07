import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Bresenham extends JPanel {

    private BufferedImage canvas;
    private ArrayList<DataLine> datalines = new ArrayList<DataLine>();
    private int numOfLines;
    private double[][] concatMatrix;

    public Bresenham(int width, int height) {
        int x1,x2,y1,y2;
        double[][] matrix = {{0.0,0.0,0.0}, {0.0,0.0,0.0}, {0.0,0.0,0.0}};
        concatMatrix = matrix;
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.BLACK);
        numOfLines = 0;
    }

    // translate the dataline by Tx for the x axis and Ty for y
    public DataLine basicTranslate(int Tx, int Ty, DataLine dataline) {
        int x1 = dataline.getx1() + Tx;
        int y1 = dataline.gety1() + Ty;
        int x2 = dataline.getx2() + Tx;
        int y2 = dataline.gety2() + Ty;
        DataLine line = new DataLine(x1,y1,x2,y2);
        drawLine(line);
        return line;
    }

    // scale the dataline by Sx for the x axis and Sy for y
    public DataLine basicScale(double Sx, double Sy, DataLine line) {
        double x1 = Math.round(line.getx1() * Sx);
        double y1 = Math.round(line.gety1() * Sy);
        double x2 = Math.round(line.getx2() * Sx);
        double y2 = Math.round(line.gety2() * Sy);
        DataLine result = new DataLine((int)x1,(int)y1,(int)x2,(int)y2);
        drawLine(result);
        return result;
    }

    // rotate the dataline by the angle given in the parameters
    public DataLine basicRotate(double angle, DataLine dataline) {
        angle = Math.toRadians(angle);
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        double[][] rotate = {{cosAngle,-sinAngle,1.00}, {sinAngle,cosAngle,0.00}, {0.00,0.00,1.00}};
        double[][] point1 = {{(double)dataline.getx1(),(double)dataline.gety1(),1.00}};
        double[][] point2 = {{(double)dataline.getx2(),(double)dataline.gety2(),1.00}};

        double[][] result1 = Matrix.multiplicate(point1,rotate);
        double[][] result2 = Matrix.multiplicate(point2,rotate);
        DataLine line = new DataLine((int)result1[0][0], (int)result1[0][1], (int)result2[0][0],
                        (int)result2[0][1]);
        drawLine(line);
        return line;
    }

    // concatenate two matrices
    public void concatenate(double[][] matrix1, double[][] matrix2) {
        double[][] result = Matrix.multiplicate(matrix1,matrix2);
        concatMatrix = result;
    }

    // applies transformations from dataline
    public DataLine[] applyTransformation(double[][] matrix,
    ArrayList<DataLine> datalines, int num) {
        DataLine[] transformedLines = new DataLine[num];

        for (int i = 0; i < num; i++) {
            int x1 = datalines.get(i).getx1();
            int y1 = datalines.get(i).gety1();
            int x2 = datalines.get(i).getx2();
            int y2 = datalines.get(i).gety2();

            double[][] point1 = {{(double)x1,(double)y1,1.00}};
            double[][] point2 = {{(double)x2,(double)y2,1.00}};
            double[][] result1 = Matrix.multiplicate(point1,matrix);
            double[][] result2 = Matrix.multiplicate(point2,matrix);
            DataLine line = new DataLine((int)result1[0][0], (int)result1[0][1],
                            (int)result2[0][0], (int)result2[0][1]);
            transformedLines[i] = line;
            drawLine(line);
        }
        return transformedLines;
    }

    // displays pixels
    public void displayPixels(ArrayList<DataLine> datalines, int num) {
        for(int i = 0; i < num; i++) {
            drawLine(datalines.get(i));
        }
    }

    // get dataline from an input file
    public int inputLines() {
        int x1,y1,x2,y2;
        DataLine line;
        String input;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name of the input file (e.g. input.txt):");
        input = scan.nextLine();
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            StringTokenizer st;
            String text;
            try {
                while((text = br.readLine()) != null) {
                    count++;
                    st = new StringTokenizer(text);
                    x1 = Integer.parseInt(st.nextToken());
                    y1 = Integer.parseInt(st.nextToken());
                    x2 = Integer.parseInt(st.nextToken());
                    y2 = Integer.parseInt(st.nextToken());
                    line = new DataLine(x1,y1,x2,y2);
                    drawLine(line);
                }
            } catch (IOException ex) {
                System.out.println("Problem with file. Error.");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. Error.");
        }
        return count;
    }

    // put all lines in an output file
    public static int outputLines(ArrayList<DataLine> datalines, int num) {
        DataLine data;
        int x1,x2,y1,y2;
        String input;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a name for the txt file (e.g. output.txt): ");
        input = scanner.nextLine();
        int count = 0;

        Writer writer = null;

        try {
            writer =
                new BufferedWriter
                (new OutputStreamWriter(new FileOutputStream(input), "utf-8"));

            for(int i = 0; i < num; i++) {
                data = datalines.get(i);
                x1 = data.getx1();
                y1 = data.gety1();
                x2 = data.getx2();
                y2 = data.gety2();
                writer.write("Line " + i + ": (" + x1 + "," + y1 + ") (" + x2 + "," + y2 + ")\n" );
                count++;
            }//for

        } catch (IOException ex) {
            System.out.println("Error");
        } finally {
            try {writer.close();}
            catch (Exception ex) {
                System.out.println("Error");
            }
        }
        return count;
    }

    // general scale around a center
    public DataLine scale(double Sx, double Sy, int Cx, int Cy, DataLine line) {
        int x1 = line.getx1() - Cx;
        int y1 = line.gety1() - Cy;
        int x2 = line.getx2() - Cx;
        int y2 = line.gety2() - Cy;
        DataLine dataline = new DataLine(x1,y1,x2,y2);

        double x12 = Math.round(dataline.getx1() * Sx);
        double y12 = Math.round(dataline.gety1() * Sy);
        double x22 = Math.round(dataline.getx2() * Sx);
        double y22 = Math.round(dataline.gety2() * Sy);
        DataLine result = new DataLine((int)x12,(int)y12,(int)x22,(int)y22);

        x1 = result.getx1() + Cx;
        y1 = result.gety1() + Cy;
        x2 = result.getx2() + Cx;
        y2 = result.gety2() + Cy;
        DataLine scaleline = new DataLine(x1,y1,x2,y2);
        drawLine(scaleline);
        return scaleline;
    }

    // general rotate around a center
    public DataLine rotate(double angle, int Cx, int Cy,  DataLine line) {
        int x1 = line.getx1() - Cx;
        int y1 = line.gety1() - Cy;
        int x2 = line.getx2() - Cx;
        int y2 = line.gety2() - Cy;
        DataLine dataline = new DataLine(x1,y1,x2,y2);

        angle = Math.toRadians(angle);
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        double[][] rotate = {{cosAngle,-sinAngle,1.00}, {sinAngle,cosAngle,0.00}, {0.00,0.00,1.00}};
        double[][] point1 = {{(double)dataline.getx1(),(double)dataline.gety1(),1.00}};
        double[][] point2 = {{(double)dataline.getx2(),(double)dataline.gety2(),1.00}};

        double[][] result1 = Matrix.multiplicate(point1,rotate);
        double[][] result2 = Matrix.multiplicate(point2,rotate);
        DataLine result = new DataLine((int)result1[0][0], (int)result1[0][1], (int)result2[0][0],
                        (int)result2[0][1]);

        x1 = result.getx1() + Cx;
        y1 = result.gety1() + Cy;
        x2 = result.getx2() + Cx;
        y2 = result.gety2() + Cy;
        DataLine rotateline = new DataLine(x1,y1,x2,y2);
        drawLine(rotateline);
        return rotateline;
    }

    public static int getScreenWorkingWidth() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getMaximumWindowBounds().width;
    }

    public static int getScreenWorkingHeight() {
        return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getMaximumWindowBounds().height;
    }

    public double[][] getConcatMatrix() {
        return concatMatrix;
    }

    public ArrayList<DataLine> getDatalines() {
        return datalines;
    }

    public int getNumOfLines() {
        return numOfLines;
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }


    public void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    public void drawLine(DataLine dataline) {
        int rgb = 16777215;

        int x = dataline.getx1();
        int y = dataline.gety1();
        int x2 = dataline.getx2();
        int y2 = dataline.gety2();

        int w = x2 - x;
        int h = y2 - y;

        int dx1 = 0;
        int dy1 = 0;
        int dx2 = 0;
        int dy2 = 0;

        if (w < 0) {
            dx1 = -1;
        } else if (w > 0) {
            dx1 = 1;
        }

        if (h < 0) {
            dy1 = -1;
        } else if (h > 0) {
            dy1 = 1;
        }

        if (w < 0) {
            dx2 = -1;
        } else if (w > 0) {
            dx2 = 1;
        }

        int longest = Math.abs(w);
        int shortest = Math.abs(h);

        if (!(longest > shortest)) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) {
                dy2 = -1;
            } else if (h > 0) {
                dy2 = 1;
            }

            dx2 = 0;
        }

        int numerator = longest >> 1;

        for (int i = 0; i <= longest;i ++) {
            if(x >= 0 && y >=0 && x <= 639 && y <= 479) {
                canvas.setRGB((int)x, (int)y, rgb);
                numerator += shortest;
                if (!(numerator < longest)) {
                    numerator -= longest;
                    x += dx1;
                    y += dy1;
                } else {
                    x += dx2;
                    y += dy2;
                }
            }
        }
        repaint();
        datalines.add(dataline);
        numOfLines++;
    }
}
