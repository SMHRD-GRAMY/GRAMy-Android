package GoMgSelf;

public class Dictionary {

    // private String id;
    private String name;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String list) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 매개변수로 id 나중에 추가ㄴ
    public Dictionary(String name) {
        // this.id = id;
        this.name = name;
    }
}

