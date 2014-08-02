package mfuentes.labartolaoficial.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import mfuentes.labartolaoficial.Model.FBImage;

public class ImageController extends Observable{

    private List<FBImage> images;
    private static ImageController instance;

    public List<FBImage> getImages() {
        if (this.images == null){
            this.images = new ArrayList<FBImage>();
        }
        return images;
    }

    public void addImage(FBImage image){
        this.getImages().add(image);
        setChanged();
        notifyObservers();
    }

    public static ImageController getInstance(){
        if (instance == null){
            instance = new ImageController();
        }
        return instance;
    }
}
