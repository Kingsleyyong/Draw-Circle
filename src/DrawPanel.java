/*
YONG JING PING
1191202279
TT6V & TC1V
*/

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {
    //    the coordinate's variables is the start point of it.
    int coordinateX1=0, coordinateX2=0, coordinateY1=0, coordinateY2=0, radius1=0, radius2=0, length1=0, length2=0;

    // set mutators
    public void setCenterColumn1(int x, int y, int radius1){
        this.coordinateX1 = x-radius1;
        this.coordinateY1 = y-radius1;
        this.radius1 = radius1;
        length1 = radius1*2;
        repaint();
    }
    public void setCenterColumn2(int x, int y, int radius2){
        this.coordinateX2=x-radius2;
        this.coordinateY2=y-radius2;
        this.radius2=radius2;
        length2 = radius2*2;
        repaint();
    }

    // set bg to white
    public DrawPanel(){
        setBackground(Color.WHITE);
    }

    //draw the circles
    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);

//        Circle for column 1
        g.drawOval(coordinateX1,coordinateY1,length1,length1);


//        Circle for column 2
        g.drawOval(coordinateX2,coordinateY2,length2,length2);

    }


}
