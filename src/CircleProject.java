/*
YONG JING PING
1191202279
TT6V & TC1V
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CircleProject extends JFrame implements ActionListener{

    //Variables declaration
    JTextField xTextField1;
    JTextField yTextField1;
    JTextField textFieldRadiusLeft;
    JTextField xTextField2;
    JTextField yTextField2;
    JTextField textFieldRadiusRight;
    JLabel intersection, errorMessage;
    int x1, y1, x2, y2, r1, r2;
    JButton redraw;
    DrawPanel drawCircle = new DrawPanel();
    CircleMotion dragging = new CircleMotion();
    Boolean leftCircle, rightCircle;

    public static void main(String[] args) {
        //set up the frame
        CircleProject frame = new CircleProject();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setSize(580, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("YONG JING PING FINAL PROJECT");
    }

    //Constructor of CircleProject class
    public CircleProject() {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel theTitle = new JLabel("Two Circles Intersect? ");
        intersection = new JLabel("");
        titlePanel.add(theTitle);
        titlePanel.add(intersection);

        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
            JPanel userInputPanel = new JPanel(new GridLayout(2,1));

                JPanel centering = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JPanel gridPanel = new JPanel(new GridLayout(1, 2, 20, 0));

                        JPanel leftInputPanel = new JPanel(new GridLayout(4, 1));
                        JLabel inputTitle1 = new JLabel("Enter circle 1 info: ");
                        inputTitle1.setHorizontalAlignment(JLabel.CENTER);

                            JPanel xInputPanelLeft = new JPanel();
                            xTextField1 = new JTextField(10);
                            xInputPanelLeft.add(new JLabel("Center X: "));
                            xInputPanelLeft.add(xTextField1);

                            JPanel yInputPanelLeft = new JPanel();
                            yTextField1 = new JTextField(10);
                            yInputPanelLeft.add(new JLabel("Center Y: "));
                            yInputPanelLeft.add(yTextField1);

                            JPanel radiusLeft = new JPanel();
                            textFieldRadiusLeft = new JTextField(10);
                            radiusLeft.add(new JLabel("Radius: "));
                            radiusLeft.add(textFieldRadiusLeft);

                        leftInputPanel.add(inputTitle1);
                        leftInputPanel.add(xInputPanelLeft);
                        leftInputPanel.add(yInputPanelLeft);
                        leftInputPanel.add(radiusLeft);
                        leftInputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                        JPanel rightInputPanel = new JPanel(new GridLayout(4, 1));
                        JLabel inputTitle2 = new JLabel("Enter circle 2 info: ");
                        inputTitle2.setHorizontalAlignment(JLabel.CENTER);

                            JPanel xInputPanelRight = new JPanel();
                            xTextField2 = new JTextField(10);
                            xInputPanelRight.add(new JLabel("Center X: "));
                            xInputPanelRight.add(xTextField2);

                            JPanel yInputPanelRight = new JPanel();
                            yTextField2 = new JTextField(10);
                            yInputPanelRight.add(new JLabel("Center Y: "));
                            yInputPanelRight.add(yTextField2);

                            JPanel radiusRight = new JPanel();
                            textFieldRadiusRight = new JTextField(10);
                            radiusRight.add(new JLabel("Radius: "));
                            radiusRight.add(textFieldRadiusRight);

                        rightInputPanel.add(inputTitle2);
                        rightInputPanel.add(xInputPanelRight);
                        rightInputPanel.add(yInputPanelRight);
                        rightInputPanel.add(radiusRight);
                        rightInputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    gridPanel.add(leftInputPanel);
                    gridPanel.add(rightInputPanel);
                centering.add(gridPanel);

                JPanel errorPanel = new JPanel(new GridBagLayout());
                errorMessage = new JLabel("");
                errorPanel.add(errorMessage);
                errorMessage.setFont(new Font("Times New Roman",Font.BOLD,13));
                errorMessage.setForeground(Color.RED);

            userInputPanel.add(centering);
            userInputPanel.add(errorPanel);

//        Cannot use contentPanel.add(new DrawPanel()); because u are creating a new instance with no link
//        with the variable drawCircle which u had assign some performance in action performed
        contentPanel.add(drawCircle);

//        Add listener for dragging purpose
        drawCircle.addMouseListener(dragging);
        drawCircle.addMouseMotionListener(dragging);

        contentPanel.add(userInputPanel);

        redraw = new JButton("Redraw Circles");
        redraw.setEnabled(true);
        redraw.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(redraw);

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == redraw) {

            //to prevent error when user input
            try{

                String text = xTextField1.getText();
                x1 = Integer.parseInt(text);

                String text1 = xTextField2.getText();
                x2 = Integer.parseInt(text1);

                String text2 = yTextField1.getText();
                y1 = Integer.parseInt(text2);

                String text3 = yTextField2.getText();
                y2 = Integer.parseInt(text3);

                String text4 = textFieldRadiusLeft.getText();
                r1 = Integer.parseInt(text4);

                String text5 = textFieldRadiusRight.getText();
                r2 = Integer.parseInt(text5);

                drawCircle.setCenterColumn1(x1, y1, r1);
                drawCircle.setCenterColumn2(x2, y2, r2);
                intersect();
                errorMessage.setText("");
            }
            catch(NumberFormatException exception){
                errorMessage.setText("Invalid Input, please ensure you fill in every text box and it is integer type.");
            }

        }
    }

    //to determine whether it was intersect or not
    public void intersect(){
        double distC1C2 = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
        int radiusTwoCircles = r1+r2;

        // I used the distance between the coordinate of the two circle to deduct the total of both radius
        // to determine is there intersect occurs.
        if(distC1C2 >= radiusTwoCircles){
            intersection.setText("NO");
        }else{
            intersection.setText("YES");
        }
    }

    // The listener will call this to identify which action to perform
    class CircleMotion extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            //get the coordinate of the point you pressed
            int x = e.getX();
            int y = e.getY();

            leftCircle = null;
            rightCircle = null;

            //distance between center to mouse click
            double distCircle1 = Math.sqrt((x1-x)*(x1-x) + (y1-y)*(y1-y));
            double distCircle2 = Math.sqrt((x2-x)*(x2-x) + (y2-y)*(y2-y));

            //get the place in the circle that can be pressed
            if ( distCircle1 <= r1 ){
                leftCircle = true;
                rightCircle = false;
                drawCircle.setCenterColumn1(x1, y1, r1);
            }
            if ( distCircle2 <= r2 ) {
                leftCircle = false;
                rightCircle = true;
                drawCircle.setCenterColumn2(x2, y2, r2);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            int x = e.getX();
            int y = e.getY();

            if(leftCircle!=null && rightCircle!=null){
                if (leftCircle){
                    x1 = x;
                    y1 = y;
                    drawCircle.setCenterColumn1(x1, y1, r1);
                }
                else if(rightCircle){
                    x2 = x;
                    y2 = y;
                    drawCircle.setCenterColumn2(x2, y2, r2);
                }
                xTextField1.setText(String.valueOf(x1));
                yTextField1.setText(String.valueOf(y1));
                xTextField2.setText(String.valueOf(x2));
                yTextField2.setText(String.valueOf(y2));
                intersect();
                errorMessage.setText("");
            }
            else{
                errorMessage.setText("You are dragging white space.");
            }


        }
    }
}

