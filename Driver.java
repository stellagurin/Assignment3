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
        int width = 900;
        int height = 900;
        int input;
        int counter = 0;
        int x1,y1,x2,y2, Tx, Ty,Tz, angle, Vx0, Vy0, Vx1, Vy1,Cx,Cy;
        double matrixInput, Sx, Sy, Sz;
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
            System.out.println("4: Output Lines");
            System.out.println("5: Exit");
            input = scan.nextInt();

            switch (input) {

            case 0:

                panel.inputLines();
                break;

            case 1:

                System.out.println("Enter the value for Tx.");
                Tx = scan.nextInt();
                System.out.println("Enter the value for Ty.");
                Ty = scan.nextInt();
                System.out.println("Enter the value for Tz.");
                Tz = scan.nextInt();

                panel.basicTranslate(Tx,Ty,Tz);
                break;

            case 2:

                System.out.println("Enter the value for Sx.");
                Sx = scan.nextDouble();
                System.out.println("Enter the value for Sy.");
                Sy = scan.nextDouble();
                System.out.println("Enter the value for Sz.");
                Sz = scan.nextDouble();

                panel.basicScale(Sx,Sy,Sz);
                break;

            case 3:

                System.out.println("Enter the axis to apply angle to (x, y, or z)");
                char letter = scan.next().charAt(0);

                System.out.println("Enter the value for angle.");
                angle = scan.nextInt();

                panel.basicRotate(letter, angle);
                break;

            case 4:

                panel.outputLines(panel.getDatalines(),
                panel.getNumOfLines());
                break;

            case 5:

                isRunning = false;
                break;

            default:

                System.out.println("Invalid input.");

            }
        }
        System.exit(0);
    }
}
