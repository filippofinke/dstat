
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author filippofinke
 */
public class ServerPanel extends JPanel implements RequestsListener {

    private final int PADDING = 10;
    
    private List<Integer> requests;
    
    private int max = 1;
    
    private int min = Integer.MAX_VALUE;
    
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        int size = requests.size();
        Point old = null;
        if (size > 0) {
            int baseLength = (getWidth() - PADDING * size) / size;
            for (int i = 0; i < size; i++) {
                int count = requests.get(i);
                int height = count * (getHeight() / max);
                int x = i * baseLength + baseLength / 2;
                int y = getHeight() - height;
                Point current = new Point(x, y);
                if(old != null) {
                    g.setColor(Color.WHITE);
                    g.drawLine(old.x, old.y, current.x, current.y);
                }
                old = new Point(current.x, current.y);
                g.setColor(Color.WHITE);
                g.drawString(count + "r/s", x, y);
            }
        }
        g.drawString("Max " + max + ", Min " + min + " r/s", 10, getHeight() - 10);
    }

    public ServerPanel() {
        this.requests = new ArrayList<>();
    }

    @Override
    public void updateRequests(int requests) {
        this.requests.add(requests);
        if(requests > max) max = requests;
        if(requests < min) min = requests;
        if(this.requests.size() > 10) {
            this.requests.remove(0);
        }
        repaint();
    }

}
