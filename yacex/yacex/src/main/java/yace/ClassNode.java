package yace;

public class ClassNode implements Renderable {
    private final int prefix;
    private final int keywordSuffix;
    private final String name;
    private final int nameSuffix;
    private final String body;
    private final int bodySuffix;

    public ClassNode(int prefix, int keywordSuffix, String name, int nameSuffix, String body, int bodySuffix) {
        this.prefix = prefix;
        this.keywordSuffix = keywordSuffix;
        this.name = name;
        this.nameSuffix = nameSuffix;
        this.body = body;
        this.bodySuffix = bodySuffix;
    }

    public ClassNode() {
        this(0, 0, "", 0, "", 0);
    }

    @Override
    public String render() {
        return renderSpacing(prefix) + "class" +
               renderSpacing(keywordSuffix) + name +
               renderSpacing(nameSuffix) + body + renderSpacing(bodySuffix);
    }

    private String renderSpacing(int spacing) {
        return new Spacing(spacing).render();
    }
}
