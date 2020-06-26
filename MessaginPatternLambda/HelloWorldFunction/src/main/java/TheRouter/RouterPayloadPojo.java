package TheRouter;
/*
POJO with Content and our End Lambda Router Task. Since they are calle RouterTaskA B or C not the ARN
 */
public class RouterPayloadPojo {
    private String routerEndPoint;
    private String content;

    public String getRouterEndPoint() {
        return routerEndPoint;
    }

    public String getContent() {
        return content;
    }

    public RouterPayloadPojo(String routerEndPoint, String content) {
        this.routerEndPoint = routerEndPoint;
        this.content = content;
    }
}
