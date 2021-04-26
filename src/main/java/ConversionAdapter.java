import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class ConversionAdapter {
    private ArrayList<String> photosPath = new ArrayList<>();

    public void reset(){
        photosPath = new ArrayList<>();
    }

    public File getPdf(){
        new PdfConverter().manipulatePdf(photosPath);

        //TODO: refactor...
        String destPath = null;
        for (Field field : PdfConverter.class.getDeclaredFields()) {
            if(field.getName().toLowerCase().contains("dest")){
                field.setAccessible(true);
                try {
                    destPath = field.get(null).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        //

        return new File(destPath);
    }

    public void add(String path){
        photosPath.add(path);
    }
}
