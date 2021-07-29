package com.k4rnaj1k;

import com.k4rnaj1k.model.Lesson;
import com.k4rnaj1k.model.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.function.Supplier;

public class DBWorker {

    private final Supplier<Session> persistence;

    public DBWorker(Supplier<Session> persistence) {
        this.persistence = persistence;
    }

    private List<Student> getStudents() {
        Session session = persistence.get();
        Query<Student> listStudents = session.createQuery("from Student", Student.class);
        return listStudents.list();
    }

    public void printStudents() {
        List<Student> students = getStudents();
        System.out.println("Current students list:");
        System.out.println("id\tname\tsurname\t\tgroup");
        for (Student s :
                students) {
            System.out.println(s.getStudent_id() + "|\t" + s.getName() + "|\t" + s.getSurname() + "|\t" + s.getGroup().getGroup_name());
        }
    }

    public void getStudentsClosestLesson(Long id) {
        Session session = persistence.get();
        Query<Lesson> q = session.createQuery("select l from Lesson l where l.lessons_group = (select s.group from Student s where student_id = " + id + ") order by l.time asc", Lesson.class);
        q.setMaxResults(1);
        List<Lesson> lessons = q.getResultList();
        if (lessons.size() > 0) {
            Lesson lesson = lessons.get(0);
            System.out.println("Closest lesson for student with id " + id);
            System.out.println(lesson);
        } else {
            System.out.println("Couldn't find any lessons for student in db, please try again.");
        }
    }
}
