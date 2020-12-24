package me.whiteship.inflearnthejavatest;



public class Study {
    private StudyStauts status = StudyStauts.DRAFT;
    private String name;
    private int limit;
    Study(int limit){

        if(limit < 0){
            throw new IllegalArgumentException("limit은 0보다 커야 한다.");
        }
        this.limit=limit;
    }

    public Study(String name, int limit) {
        this.name = name;
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", name='" + name + '\'' +
                ", limit=" + limit +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudyStauts getStatus() {
        return this.status;
    }


    public void setStatus(StudyStauts status) {
        this.status = status;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
