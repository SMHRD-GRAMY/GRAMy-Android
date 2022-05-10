package GoMgSelf;

public class Dictionary {

    private String list;
    private String name;
    private String count;

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Dictionary(String list, String name, String count) {
        this.list = list;
        this.name = name;
        this.count = count;
    }
}

