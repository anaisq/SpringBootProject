package main.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nameOfAction;

    private Timestamp timestamp;

    public Audit(String nameOfAction, Timestamp timestamp) {
        super();
        this.nameOfAction = nameOfAction;
        this.timestamp = timestamp;
    }

    public Audit() {
        this(null,null);
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfAction() {
        return nameOfAction;
    }

    public void setNameOfAction(String nameOfAction) {
        this.nameOfAction = nameOfAction;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "id=" + id +
                ", nameOfAction='" + nameOfAction + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audit audit = (Audit) o;
        return getId() == audit.getId() && Objects.equals(getNameOfAction(), audit.getNameOfAction()) && Objects.equals(getTimestamp(), audit.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNameOfAction(), getTimestamp());
    }
}