package com.k4rnaj1k;

public class Person {
    @CSVHeader("id")
    int id;
    @CSVHeader("name")
    String name;
    @CSVHeader("email")
    String email;

    @Override
    public String toString() {
        return "com.k4rnaj1k.Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public enum Fields {
        ID(1, "id"),
        NAME(2, "name"),
        EMAIL(3, "email");
        private int fieldIndex;
        private String fieldsName;

        Fields(int fieldIndex, String fieldsName) {
            this.fieldIndex = fieldIndex;
            this.fieldsName = fieldsName;
        }

        public int getFieldIndex() {
            return fieldIndex;
        }

        public String getFieldsName() {
            return fieldsName;
        }
    }
}
