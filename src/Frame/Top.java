package Frame;

import Classes.DataManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Hashtable;

public class Top extends JPanel implements ChangeListener {
    Pizzeria pizzeria;
    JSlider framesPerSecond;

    public Top(Pizzeria pizzeria) {
        this.pizzeria = pizzeria;

        JLabel title = new JLabel("PIZZERIA", SwingConstants.CENTER);
        title.setFont(new Font("SERIF", Font.ITALIC, 60));
        this.setBorder(new EmptyBorder(0, 100, 0, 0));
        this.setBackground(Color.orange);
        title.setForeground(Color.red);

//        JLabel logo = new JLabel(new ImageIcon("resources/logo.png"), SwingConstants.CENTER);
        JLabel logo = new JLabel(new ImageIcon(Top.class.getResource("/images/logo.png")), SwingConstants.CENTER);
        logo.setPreferredSize(new Dimension(150, 150));

        framesPerSecond = new JSlider(JSlider.HORIZONTAL, (int)DataManager.MIN_SPEED, (int)DataManager.MAX_SPEED, (int)DataManager.speed);
        framesPerSecond.setBackground(Color.orange);

        framesPerSecond.addChangeListener(this);
        framesPerSecond.setMajorTickSpacing(10);
        framesPerSecond.setPaintTicks(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put((int) (DataManager.MIN_SPEED), new JLabel("Wolno") );
        labelTable.put((int) DataManager.MAX_SPEED, new JLabel("Szybko") );
        framesPerSecond.setLabelTable( labelTable );

        framesPerSecond.setPaintLabels(true);

        this.add(logo);
        this.add(title);

        JLabel l = new JLabel();
        l.setPreferredSize(new Dimension(100, 1));
        this.add(l);
        this.add(framesPerSecond);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        DataManager.speed = framesPerSecond.getValue();
        pizzeria.timer.cancel();
        pizzeria.scheduleFrameUpdate();
    }
}
