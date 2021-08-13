package com.we_connect_students;

class SubjectList {
    String subIndexId;
    String subName;

    private boolean isSelected;

    public SubjectList(String subjectIndexId, String subjectName) {
        this.subIndexId = subjectIndexId;
        this.subName = subjectName;

    }

    public String getSubIndexId() {
        return subIndexId;
    }

    public void setSubIndexId(String subIndexId) {
        this.subIndexId = subIndexId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
    public boolean getSelected() {
        return isSelected;
    }



    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
