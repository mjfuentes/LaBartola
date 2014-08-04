package mfuentes.labartolaoficial.Model;

public class FBImage {
    private String icon;
    private String source;
    private String name;
    private String link;


    public FBImage(String icon,String source,String name,String link)
    {
        this.icon = icon;
        this.source = source;
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
