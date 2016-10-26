package model;

/**
 * Bean of gender type
 */
public class GenderType {
    private short id;
    private String type_name;

    public GenderType(final short id, final String type_name) {
        this.id = id;
        this.type_name = type_name;
    }

    public GenderType() {
    }

    public short getId() {
        return id;
    }

    public void setId(final short id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(final String type_name) {
        this.type_name = type_name;
    }

    @Override
    public String toString() {
        return "GenderType{" +
                "id=" + id +
                ", type_name='" + type_name + '\'' +
                '}';
    }
}
