package sg.edu.rp.c346.project04;

public class Part {
    private int id;
    private String partName;

    public Part(int id, String partName) {
        this.id = id;
        this.partName = partName;
    }

    public int getId() {
        return id;
    }

    public String getPartName() {
        return partName;
    }
}
