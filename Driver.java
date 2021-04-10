import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.util.Scanner;
import java.util.ArrayList;

public class Driver {
    public static void main(String[] args) {

        // variables used in while loop
        Boolean isRunning = true;
        Boolean is3x3 = false;
        DataLine line;
        int width = 1000;
        int height = 1000;
        int input;
        int counter = 0;
        int x1,y1,x2,y2, Tx, Ty, angle, Vx0, Vy0, Vx1, Vy1,Cx,Cy;
        double matrixInput, Sx, Sy;
        double[][] matrix1,matrix2;
        JFrame frame = new JFrame("Assignment 2");
        Scanner scan = new Scanner(System.in);
        Bresenham panel = new Bresenham(width, height);

        // for GUI
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // loop
        while (isRunning) {
            System.out.println("What would you like to do?");
            System.out.println("0: Draw 3D Object");
            System.out.println("1: Basic Translation");
            System.out.println("2: Basic Scale");
            System.out.println("3: Basic Rotate");
            System.out.println("4: Exit");
            input = scan.nextInt();

            switch (input) {

            case 0:

                panel.inputLines();
                break;

            case 1:

                System.out.println("Enter the x1 for the line");
                x1 = scan.nextInt();
                System.out.println("Enter the y1 for the line");
                y1 = scan.nextInt();
                System.out.println("Enter the x2 for the line");
                x2 = scan.nextInt();
                System.out.println("Enter the y2 for the line");
                y2 = scan.nextInt();

                line = new DataLine(x1,y1,x2,y2);

                System.out.println("Enter the value for Tx.");
                Tx = scan.nextInt();
                System.out.println("Enter the value for Ty.");
                Ty = scan.nextInt();

                panel.basicTranslate(Tx,Ty,line);
                break;

            case 2:

                System.out.println("Enter the x1 for the line");
                x1 = scan.nextInt();
                System.out.println("Enter the y1 for the line");
                y1 = scan.nextInt();
                System.out.println("Enter the x2 for the line");
                x2 = scan.nextInt();
                System.out.println("Enter the y2 for the line");
                y2 = scan.nextInt();

                line = new DataLine(x1,y1,x2,y2);

                System.out.println("Enter the value for Sx.");
                Sx = scan.nextDouble();
                System.out.println("Enter the value for Sy.");
                Sy = scan.nextDouble();

                panel.basicScale(Sx,Sy,line);
                break;

            case 3:

                System.out.println("Enter the x1 for the line");
                x1 = scan.nextInt();
                System.out.println("Enter the y1 for the line");
                y1 = scan.nextInt();
                System.out.println("Enter the x2 for the line");
                x2 = scan.nextInt();
                System.out.println("Enter the y2 for the line");
                y2 = scan.nextInt();

                line = new DataLine(x1,y1,x2,y2);

                System.out.println("Enter the value for angle.");
                angle = scan.nextInt();

                panel.basicRotate(angle,line);
                break;

            case 4:

                isRunning = false;
                break;

            default:

                System.out.println("Invalid input.");

            }
        }
        System.exit(0);
    }
}
