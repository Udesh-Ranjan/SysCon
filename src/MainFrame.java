import javax.swing.*;
import java.awt.*;

/**
 * @author :  dev parzival
 * @date :  18 Apr, 2021
 */
public class MainFrame extends JFrame {
    private SoundController soundController;
    private BrightnessController brightnessController;
    public MainFrame(int width,int height){
        setSize(width,height);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,
                10,10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final double volume=SoundController.getVolume();
        System.out.println("initializing volume : "+volume);
        soundController=new SoundController(300,300,volume);
        brightnessController=new BrightnessController(300,300,BrightnessController.getBrightness());
        this.add(soundController);
        this.add(brightnessController);
        soundController.setVisible(true);
        brightnessController.setVisible(true);
    }
}
