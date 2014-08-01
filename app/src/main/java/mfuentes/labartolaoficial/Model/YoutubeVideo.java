package mfuentes.labartolaoficial.Model;

public class YoutubeVideo {

    private String description;
    private String link;
    private String image;

    public YoutubeVideo(String description, String link, String image){
        this.setDescription(description);
        this.setLink(link);
        this.setImage(image);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String token) {
        this.link = token;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
