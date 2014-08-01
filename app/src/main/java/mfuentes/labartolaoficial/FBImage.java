package mfuentes.labartolaoficial;

public class FBImage {
    private String icon;
    private String source;
    private String name;


    public FBImage(String icon,String source,String name)
    {
        this.icon = icon;
        this.setSource(source);
        this.setName(name);
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
}
