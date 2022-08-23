package yace;

public class ClassBuilder {
    private int prefix = 0;
    private int keywordSuffix = 0;
    private String name = "";
    private int nameSuffix = 0;
    private String body = "";
    private int bodySuffix = 0;

    public ClassBuilder setPrefix(int prefix) {
        this.prefix = prefix;
        return this;
    }

    public ClassBuilder setKeywordSuffix(int keywordSuffix) {
        this.keywordSuffix = keywordSuffix;
        return this;
    }

    public ClassNode build() {
        return new ClassNode(prefix, keywordSuffix, name, nameSuffix, body, bodySuffix);
    }

    public ClassBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ClassBuilder setNameSuffix(int spacing) {
        this.nameSuffix = spacing;
        return this;
    }

    public ClassBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public ClassBuilder setBodySuffix(int spacing) {
        this.bodySuffix = spacing;
        return this;
    }
}