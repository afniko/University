package ua.com.foxminded.task.domain;

public class Group {

    private int id;
    private String title;
    private Department department;
    private int yearEntry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getYearEntry() {
        return yearEntry;
    }

    public void setYearEntry(int yearEntry) {
        this.yearEntry = yearEntry;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + yearEntry;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (yearEntry != other.yearEntry)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Group [id=" + id + ", title=" + title + ", department=" + department + ", yearEntry=" + yearEntry + "]";
    }

}
