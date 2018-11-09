package www.lixiangfei.top.videoviewpager;

public class VpBean {
    public static final int TYPE_PIC = 1;
    public static final int TYPE_VIDEO = 2;

    private int type;
    private String url;
    private String videoUrl;



    public VpBean(int type, String url) {
        this.type = type;
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
