package mfuentes.labartolaoficial;

/**
 * Created by matifuentes on 7/31/14.
 */
public class YoutubeVideo {

    private String description;
    private String token;

    public YoutubeVideo(String description, String token){
        this.setDescription(description);
        this.setToken(token);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
