import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.StringTokenizer;

/**
 * @author :  dev parzival
 * @date :  18 Apr, 2021
 */
public class SoundController extends JPanel {
    public static PrintStream out;
    static{
        out=System.out;
    }
    private JSlider slider;
    private JLabel label;
    public SoundController(int width,int height,Double vol){
        this.setSize(width,height);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        label=new JLabel("Volume",JLabel.CENTER);
        this.add(label);
        slider=new JSlider(JSlider.HORIZONTAL,0,100,vol.intValue());
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new SoundChangeListener(label,slider));
        slider.setValue(vol.intValue());
        label.setText("Volume("+slider.getValue()+"%)");
        slider.setVisible(true);
        this.add(slider);
    }
    public void setLabelText(String text){
        label.setText(text);
    }
    public void setSliderValue(int val){
        slider.setValue(val);
    }
    public static class SoundChangeListener implements ChangeListener {
        private final JLabel label;
        private final JSlider slider;
        public SoundChangeListener(JLabel label,JSlider slider){
            this.label=label;
            this.slider=slider;
        }
        @Override
        public void stateChanged(ChangeEvent e) {
            setVolume(slider.getValue());
            label.setText("Volume("+slider.getValue()+"%)");

        }
    }
    public static double getVolume(){
        String operatingSystem=System.getProperty("os.name");
        out.println(operatingSystem);
        if(operatingSystem.equalsIgnoreCase("Linux")){
            Runtime runtime=Runtime.getRuntime();
            try {
                double value=0;
                Process pro = runtime.exec("amixer get Master");
//                out.println(pro.getInputStream().readAllBytes());
                BufferedReader stdIn=new BufferedReader(new InputStreamReader(pro.getInputStream()));
                BufferedReader stdErr=new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                String str=null;
                double left=-1;
                double right =-1;
                StringBuilder builder=new StringBuilder();
                boolean flag=true;
                while((str=stdIn.readLine())!=null){
                    if(flag) {
                        out.println("output :");
                        flag=false;
                    }
                    out.println(str);
                    builder.append(str);
                }
                StringTokenizer tokenizer=new StringTokenizer(builder.toString(),"[]");
                while(tokenizer.hasMoreTokens()){
                    String token=tokenizer.nextToken();
                    if(token.contains("%")){
                        if(left==-1){
                            String arr[]=token.split("%");
                            if(arr.length>0)
                                left=Integer.parseInt(arr[0]);
                        }else{
                            String arr[]=token.split("%");
                            if(arr.length>0)
                                right=Integer.parseInt(arr[0]);
                        }
                    }
                }
                out.println("left : "+left);
                out.println("right : "+right);
                if(left==-1 || right==-1){
                    out.println("Error while executing amixer command or during parsing");
                }else
                    value=(left+right)/2;
                flag=true;
                while((str=stdErr.readLine())!=null){
                    if(flag) {
                        out.println("error :");
                        flag=false;
                    }
                    out.println(str);
                }
                out.println("Execution finished");
                return value;
            }catch(IOException exception){
                out.println(exception);
            }
        }else if(operatingSystem.toLowerCase().contains("windows")){
            //TODO get volume
            Runtime runtime=Runtime.getRuntime();
            try {
                double value=0;
                Process pro = runtime.exec("soundvolumeview/soundbat.bat");
                BufferedReader stdIn=new BufferedReader(new InputStreamReader(pro.getInputStream()));
                BufferedReader stdErr=new BufferedReader(new InputStreamReader(pro.getErrorStream()));
                String str=null;
                StringBuilder builder=new StringBuilder();
                boolean flag=true;
                while((str=stdIn.readLine())!=null){
                    if(flag) {
                        out.println("output :");
                        flag=false;
                    }
                    out.println(str);
                    builder.append(str);
                }
                StringTokenizer tokenizer=new StringTokenizer(builder.toString()," ");
                while(tokenizer.hasMoreTokens()){
                    String token=tokenizer.nextToken();
//                    out.println("token : "+token);
                    boolean intValue=token.length()>0;
                    for(int i=0;i<token.length();i++)
                        if(!Character.isDigit(token.charAt(i))){
                            intValue=false;
                            break;
                        }
                    if(intValue)
                        value=Integer.parseInt(token)/10;
                }
                flag=true;
                while((str=stdErr.readLine())!=null){
                    if(flag) {
                        out.println("error :");
                        flag=false;
                    }
                    out.println(str);
                }
                out.println("Execution finished");
                out.println("value : "+value);
                return value;
            }catch(IOException exception){
                out.println(exception);
            }
        }
        return 0;
    }
    public static void setVolume(double volume){
        String os=System.getProperty("os.name");
        if(os.equalsIgnoreCase("Linux")){
            Runtime runtime=Runtime.getRuntime();
            try {
                Process pro = runtime.exec("amixer -D pulse sset Master " + volume);
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
                out.println("Unable to set volume using amixer");
            }
        }else if(os.toLowerCase().contains("windows")){
            Runtime runtime=Runtime.getRuntime();
            try{
                volume=65535*volume/100;
                Process pro=runtime.exec("nircmd/nircmd setsysvolume "+volume);
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
                out.println("Unable to set volume using nircmdc");
            }
        }
    }
}
