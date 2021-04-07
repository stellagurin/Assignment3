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
            System.out.println("0: Draw Line");
            System.out.println("1: Basic Translation");
            System.out.println("2: Basic Scale");
            System.out.println("3: Scale");
            System.out.println("4: Basic Rotate");
            System.out.println("5: Rotate");
            System.out.println("6: Apply Transformation");
            System.out.println("7: Input Lines");
            System.out.println("8: Output Lines");
            System.out.println("9: Exit");
            input = scan.nextInt();

            switch (input) {

            case 0:

                System.out.println("Enter the x1 for the line");
                x1 = scan.nextInt();
                System.out.println("Enter the y1 for the line");
                y1 = scan.nextInt();
                System.out.println("Enter the x2 for the line");
                x2 = scan.nextInt();
                System.out.println("Enter the y2 for the line");
                y2 = scan.nextInt();

                line = new DataLine(x1,y1,x2,y2);
                panel.drawLine(line);
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

                System.out.println("Enter the value for Sx.");
                Sx = scan.nextDouble();
                System.out.println("Enter the value for Sy.");
                Sy = scan.nextDouble();

                System.out.println("Enter the value for Cx.");
                Cx = scan.nextInt();
                System.out.println("Enter the value for Cy.");
                Cy = scan.nextInt();

                panel.scale(Sx,Sy,Cx,Cy,line);
                break;

            case 4:

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

            case 5:

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

                System.out.println("Enter the value for Cx.");
                Cx = scan.nextInt();
                System.out.println("Enter the value for Cy.");
                Cy = scan.nextInt();

                panel.rotate(angle,Cx,Cy,line);
                break;

            case 6:

                ArrayList<DataLine> datalines = panel.getDatalines();
                double[][] matrix = new double[3][3];
                int num = panel.getNumOfLines();

                System.out.println("Select option.\n1: Use stored matrix.\n2:"
                + " Enter new 3 x 3 matrix.");
                input = scan.nextInt();
                counter = 0;
                if(input == 1) {
                    matrix = panel.getConcatMatrix();
                    panel.applyTransformation(matrix, datalines, num);
                }
                else if(input == 2) {
                    for(int i = 0; i < 3; i++) {
                        for(int j = 0; j < 3; j++) {
                            System.out.println("Enter number " + ++counter);
                            matrixInput = scan.nextDouble();
                            matrix[i][j] = matrixInput;
                        }
                    }
                    panel.applyTransformation(matrix, datalines, num);
                }
                else {
                    System.out.println("Invalid input");
                }
                break;

            case 7:

                panel.inputLines();
                break;

            case 8:

                panel.outputLines(panel.getDatalines(),
                panel.getNumOfLines());
                break;

            case 9:

                isRunning = false;
                break;

            default:

                System.out.println("Invalid input.");

            }
        }
        System.exit(0);
    }
}
