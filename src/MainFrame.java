import javax.swing.*;
import java.awt.*;

/**
 * @author :  dev parzival
 * @date :  18 Apr, 2021
 */
public class MainFrame extends JFrame {
    private SoundController soundController;
    public MainFrame(int width,int height){
        setSize(width,height);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,
                10,10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        soundController=new SoundController(300,300,SoundController.getVolume());
        this.add(soundController);
        soundController.setVisible(true);
    }
}
