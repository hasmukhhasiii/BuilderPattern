package org.example.BuilderPattern;

class StudentBuilder {
    private String searchType;

    public StudentBuilder setSearchType(String searchType) {
        this.searchType = searchType;
        return this;
    }

    public StudentSearch build() {
        if ("admission".equalsIgnoreCase(searchType)) {
            return new SearchByAdmissionNumber();
        } else if ("name".equalsIgnoreCase(searchType)) {
            return new SearchByName();
        }
        throw new IllegalArgumentException("Invalid search type");
    }
}
