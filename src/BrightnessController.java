import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * @dev :   devpar
 * @date :   25-Apr-2021
 */
public class BrightnessController extends JPanel {
    public static PrintStream out;
    static{
        out=System.out;
    }
    private JSlider slider;
    private JLabel label;
    public BrightnessController(int width,int height,Double brightness){
        this.setSize(width,height);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        label=new JLabel("Brightness",JLabel.CENTER);
        this.add(label);
        slider=new JSlider(JSlider.HORIZONTAL,0,100,brightness.intValue());
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new BrightnessController.BrightnessChangeListener(label,slider));
        slider.setValue(brightness.intValue());
        label.setText("Brightness("+slider.getValue()+"%)");
        slider.setVisible(true);
        this.add(slider);
    }
    public void setLabelText(String text){
        label.setText(text);
    }
    public void setSliderValue(int val){
        slider.setValue(val);
    }
    public static class BrightnessChangeListener implements ChangeListener {
        private final JLabel label;
        private final JSlider slider;
        public BrightnessChangeListener(JLabel label, JSlider slider){
            this.label=label;
            this.slider=slider;
        }
        @Override
        public void stateChanged(ChangeEvent e) {
            setBrightness(slider.getValue());
            label.setText("Brightness("+slider.getValue()+"%)");

        }
    }
    public static double getBrightness(){
        String operatingSystem=System.getProperty("os.name");
        out.println(operatingSystem);
        if(operatingSystem.equalsIgnoreCase("Linux")){
            Runtime runtime=Runtime.getRuntime();

        }else if(operatingSystem.toLowerCase().contains("windows")){
            //TODO get volume
        }
        return 0;
    }
    public static void setBrightness(double brightness){
        String os=System.getProperty("os.name");
        if(os.equalsIgnoreCase("Linux")){
            Runtime runtime=Runtime.getRuntime();

        }else if(os.toLowerCase().contains("windows")){
            Runtime runtime=Runtime.getRuntime();
            try{
                Process pro=runtime.exec("nircmd/nircmd setbrightness "+brightness);
                BufferedReader stdIn=new BufferedReader(new InputStreamReader(pro.getInputStream()));
                BufferedReader stdErr=new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                String str=null;
                boolean flag=true;
                while((str=stdIn.readLine())!=null){
                    if(flag) {
                        out.println("output : ");
                        flag=false;
                    }
                    out.println(str);
                }
                flag=true;
                while((str=stdErr.readLine())!=null){
                    if(flag) {
                        out.println("error : ");
                        flag=false;
                    }
                    out.println(str);
                }
            }catch(IOException exception){
                out.println(exception);
                out.println("Unable to set brightness using nircmd");
            }
        }
    }
}
