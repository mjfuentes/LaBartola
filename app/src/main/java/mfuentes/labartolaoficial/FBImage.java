package mfuentes.labartolaoficial;

/**
 * Created by matifuentes on 7/29/14.
 */
public class FBImage {
    private String uri;
    private String shareLink;
    public FBImage(String u,String s)
    {
        setUri(u);
        setShareLink(s);
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }
}
