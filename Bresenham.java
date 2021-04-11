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
    private String file = "";

    public Bresenham(int width, int height) {
        int x1,x2,y1,y2;
        double[][] matrix = {{0.0,0.0,0.0}, {0.0,0.0,0.0}, {0.0,0.0,0.0}};
        concatMatrix = matrix;
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.BLACK);
        numOfLines = 0;
    }

    // get dataline from an input file
    public int inputLines() {
        int x1,y1,z1,x2,y2,z2;
        DataLine line;
        String input;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name of the input file (e.g. input.txt):");
        input = scan.nextLine();
        file = input;
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
                    z1 = Integer.parseInt(st.nextToken());
                    x2 = Integer.parseInt(st.nextToken());
                    y2 = Integer.parseInt(st.nextToken());
                    z2 = Integer.parseInt(st.nextToken());
                    GraphicLine gline = new GraphicLine(x1,y1,z1,x2,y2,z2);
                    line = convert3DLine(gline);
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

    // translate the dataline by Tx for the x axis, Ty for y, and Tz for z
    public void basicTranslate(int Tx, int Ty, int Tz) {
        int x1,y1,z1,x2,y2,z2;
        DataLine line;
        String input = file;
        if (input == "") {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter name of the input file (e.g. input.txt):");
            input = scan.nextLine();
        }
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            StringTokenizer st;
            String text;
            try {
                while((text = br.readLine()) != null) {
                    count++;
                    st = new StringTokenizer(text);
                    x1 = Integer.parseInt(st.nextToken()) + Tx;
                    y1 = Integer.parseInt(st.nextToken()) + Ty;
                    z1 = Integer.parseInt(st.nextToken()) + Tz;
                    x2 = Integer.parseInt(st.nextToken()) + Tx;
                    y2 = Integer.parseInt(st.nextToken()) + Ty;
                    z2 = Integer.parseInt(st.nextToken()) + Tz;
                    GraphicLine gline = new GraphicLine(x1,y1,z1,x2,y2,z2);
                    line = convert3DLine(gline);
                    drawLine(line);
                }
            } catch (IOException ex) {
                System.out.println("Problem with file. Error.");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. Error.");
        }

    }

    // scale the dataline by Sx for the x axis, Sy for y, and Sz for z
    public void basicScale(double Sx, double Sy, double Sz) {
        int x1,y1,z1,x2,y2,z2;
        DataLine line;
        String input = file;
        if (input == "") {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter name of the input file (e.g. input.txt):");
            input = scan.nextLine();
        }
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            StringTokenizer st;
            String text;
            try {
                while((text = br.readLine()) != null) {
                    count++;
                    st = new StringTokenizer(text);
                    x1 = (int)(Integer.parseInt(st.nextToken()) * Sx);
                    y1 = (int)(Integer.parseInt(st.nextToken()) * Sy);
                    z1 = (int)(Integer.parseInt(st.nextToken()) * Sz);
                    x2 = (int)(Integer.parseInt(st.nextToken()) * Sx);
                    y2 = (int)(Integer.parseInt(st.nextToken()) * Sy);
                    z2 = (int)(Integer.parseInt(st.nextToken()) * Sz);
                    GraphicLine gline = new GraphicLine((int)x1,(int)y1,(int)z1,(int)x2,(int)y2,
                    (int)z2);
                    line = convert3DLine(gline);
                    drawLine(line);
                }
            } catch (IOException ex) {
                System.out.println("Problem with file. Error.");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. Error.");
        }

    }

    // rotate the dataline by the angle given in the parameters
    public void basicRotate(char side, double angle) {
        angle = Math.toRadians(angle);
        double cosAngle = Math.cos(angle);
        double sinAngle = Math.sin(angle);

        int x1,y1,z1,x2,y2,z2;
        DataLine line;
        String input = file;
        if (input == "") {
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter name of the input file (e.g. input.txt):");
            input = scan.nextLine();
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            StringTokenizer st;
            String text;
            try {
                while((text = br.readLine()) != null) {
                    if (side == 'x') {

                        st = new StringTokenizer(text);
                        x1 = Integer.parseInt(st.nextToken());
                        y1 = Integer.parseInt(st.nextToken());
                        z1 = Integer.parseInt(st.nextToken());
                        x2 = Integer.parseInt(st.nextToken());
                        y2 = Integer.parseInt(st.nextToken());
                        z2 = Integer.parseInt(st.nextToken());

                        double[][] rotate = {{1.00, 0.00, 0.00, 0.00},
                                             {0.00, cosAngle, sinAngle, 0.00},
                                             {0.00, -sinAngle, cosAngle, 0.00},
                                             {0.00, 0.00, 0.00, 1.00}};

                        double[][] point1 = {{(double)x1,
                                              (double)y1, (double) z1, 1.00}};
                        double[][] point2 = {{(double)x2,
                                              (double)y2, (double) z2, 1.00}};

                        double[][] result1 = Matrix.multiplicate(point1,rotate);
                        double[][] result2 = Matrix.multiplicate(point2,rotate);

                        GraphicLine gline = new GraphicLine((int)result1[0][0],
                        (int)result1[0][1],(int)result1[0][2],
                        (int)result2[0][0],(int)result2[0][1],
                        (int)result2[0][2]);
                        line = convert3DLine(gline);
                        drawLine(line);

                    } else if (side == 'y') {

                        st = new StringTokenizer(text);
                        x1 = Integer.parseInt(st.nextToken());
                        y1 = Integer.parseInt(st.nextToken());
                        z1 = Integer.parseInt(st.nextToken());
                        x2 = Integer.parseInt(st.nextToken());
                        y2 = Integer.parseInt(st.nextToken());
                        z2 = Integer.parseInt(st.nextToken());

                        double[][] rotate = {{cosAngle, 0.00, -sinAngle, 0.00},
                                             {0.00, 1.00, 0.00, 0.00},
                                             {sinAngle, 0.00, cosAngle, 0.00},
                                             {0.00, 0.00, 0.00, 1.00}};

                        double[][] point1 = {{(double)x1,
                                              (double)y1, (double) z1, 1.00}};
                        double[][] point2 = {{(double)x2,
                                              (double)y2, (double) z2, 1.00}};

                        double[][] result1 = Matrix.multiplicate(point1,rotate);
                        double[][] result2 = Matrix.multiplicate(point2,rotate);

                        GraphicLine gline = new GraphicLine((int)result1[0][0],
                        (int)result1[0][1],(int)result1[0][2],
                        (int)result2[0][0],(int)result2[0][1],
                        (int)result2[0][2]);
                        line = convert3DLine(gline);
                        drawLine(line);

                    } else if (side == 'z') {

                        st = new StringTokenizer(text);
                        x1 = Integer.parseInt(st.nextToken());
                        y1 = Integer.parseInt(st.nextToken());
                        z1 = Integer.parseInt(st.nextToken());
                        x2 = Integer.parseInt(st.nextToken());
                        y2 = Integer.parseInt(st.nextToken());
                        z2 = Integer.parseInt(st.nextToken());

                        double[][] rotate = {{cosAngle, sinAngle, 0.00, 0.00},
                                             {-sinAngle, cosAngle, 0.00, 0.00},
                                             {0.00, 0.00, 1.00, 0.00},
                                             {0.00, 0.00, 0.00, 1.00}};

                        double[][] point1 = {{(double)x1,
                                              (double)y1, (double) z1, 1.00}};
                        double[][] point2 = {{(double)x2,
                                              (double)y2, (double) z2, 1.00}};

                        double[][] result1 = Matrix.multiplicate(point1,rotate);
                        double[][] result2 = Matrix.multiplicate(point2,rotate);

                        GraphicLine gline = new GraphicLine((int)result1[0][0],
                        (int)result1[0][1],(int)result1[0][2],
                        (int)result2[0][0],(int)result2[0][1],
                        (int)result2[0][2]);
                        line = convert3DLine(gline);
                        drawLine(line);
                    } else {
                        System.out.println("Incorrect axis specified.");
                    }
                }
            } catch (IOException ex) {
                System.out.println("Problem with file. Error.");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. Error.");
        }

    }

    // concatenate two matrices
    public void concatenate(double[][] matrix1, double[][] matrix2) {
        double[][] result = Matrix.multiplicate(matrix1,matrix2);
        concatMatrix = result;
    }

    public DataLine convert3DLine (GraphicLine line) {
        double D = 2.5;
        double S = 50;
        double V = 100;

        int x1 = (int)((((D * line.getx1())/(S * line.getz1()))*V) + V);
        int y1 = (int)((((D * line.gety1())/(S * line.getz1()))*V) + V);
        int x2 = (int)((((D * line.getx2())/(S * line.getz2()))*V) + V);
        int y2 = (int)((((D * line.gety2())/(S * line.getz2()))*V) + V);

        DataLine gline = new DataLine(x1,y1,x2,y2);
        return gline;
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
            if(x >= 0 && y >=0 && x <= 1000 && y <= 1000) {
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
