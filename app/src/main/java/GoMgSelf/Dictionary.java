package GoMgSelf;

public class Dictionary {

    private String name;



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

