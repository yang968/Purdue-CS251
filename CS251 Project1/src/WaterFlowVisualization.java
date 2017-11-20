import java.applet.*;
import java.awt.*;
import java.util.*;

public class WaterFlowVisualization extends Applet {
    public int[][] delayTimeGrid;
    public int[][] reachTimeGrid;
    public boolean[][] getEarliestPathGrid;

    public int rows;
    public int columns;

    WaterFlowVisualization(WaterFlow waterFlow) {
        this.delayTimeGrid = waterFlow.getDelayTimeGrid();
        this.reachTimeGrid = waterFlow.getReachTimeGrid();
        this.rows = waterFlow.getRows();
        this.columns = waterFlow.getColumns();
        this.getEarliestPathGrid = waterFlow.getEarliestPathGrid();
    }

    public void init() {
        setSize(rows * 10, columns * 10);
        setVisible(true);
    }


    public void paint(Graphics g) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                /* To paint the path */
                
                if (delayTimeGrid[i][j] == 0)
                    g.setColor(Color.black);
                else if (reachTimeGrid[i][j] == -1)
                    g.setColor(Color.white);
                else if (getEarliestPathGrid[i][j])
                    g.setColor(Color.red);
                else
                    g.setColor(Color.cyan);
                g.fillRect(j * 20, i * 20, 20, 20);

                /*
                g.setColor(Color.black);
                g.setFont(new Font("Helvetica", Font.PLAIN, 14));

                g.drawString(String.valueOf(reachTimeGrid[i][j]), j * 20 + 5, i * 20 + 15);
                g.drawRect(j * 20, i * 20, 20, 20);
                */
                /* To paint the delayTime Grid */
                /*
                g.setColor(Color.black);
                g.setFont(new Font("Helvetica", Font.PLAIN, 14));

                g.drawString(String.valueOf(delayTimeGrid[i][j]), j * 20 + 5, i * 20 + 15);
                g.drawRect(j * 20, i * 20, 20, 20);
                */

            }
        }
    }
}
