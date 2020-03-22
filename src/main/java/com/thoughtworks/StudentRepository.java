package com.thoughtworks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
  private Connection conn = DbUtil.getConnection();

  public void save(List<Student> students) {
    students.forEach(this::save);
  }

  public void save(Student student) {
    // TODO:
    String sql = "INSERT INTO students(id, name, gender, admissionYear, birthday, classId)" +
                  "VALUES(" + "?,?,?,?,?,?)";
    try {
      PreparedStatement ptmt = conn.prepareStatement(sql);
      ptmt.setString(1, student.getId());
      ptmt.setString(2, student.getName());
      ptmt.setString(3, student.getGender());
      ptmt.setInt(4, student.getAdmissionYear());
      ptmt.setDate(5, new java.sql.Date(student.getBirthday().getTime()));
      ptmt.setString(6, student.getClassId());
      ptmt.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<Student> query() throws SQLException {
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT id, name, gender, admissionYear, birthday, classId FROM students");

    List<Student> students = new ArrayList<Student>();
    Student student = null;
    while(rs.next()){
      student = new Student();
      student.setId(rs.getString("id"));
      student.setName(rs.getString("name"));
      student.setGender(rs.getString("gender"));
      student.setAdmissionYear(rs.getInt("admissionYear"));
      student.setBirthday(rs.getDate("birthday"));
      student.setClassId(rs.getString("classId"));
      students.add(student);
    }
    return students;
  }

  public List<Student> queryByClassId(String classId) throws SQLException {
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT id, name, gender, admissionYear, birthday, classId FROM students WHERE classId = \'" + classId + "\'");

    List<Student> students = new ArrayList<Student>();
    Student student = null;
    while(rs.next()){
      student = new Student();
      student.setId(rs.getString("id"));
      student.setName(rs.getString("name"));
      student.setGender(rs.getString("gender"));
      student.setAdmissionYear(rs.getInt("admissionYear"));
      student.setBirthday(rs.getDate("birthday"));
      student.setClassId(rs.getString("classId"));
      students.add(student);
    }
    return students;
  }

  public void update(String id, Student student) throws SQLException {
    String sql = "UPDATE students" +
            " SET name=?, gender=?, admissionYear=?, birthday=?, classId=?"+
            " WHERE id=?";
    //预编译
    PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行

    //传参
    ptmt.setString(1, student.getName());
    ptmt.setString(2, student.getGender());
    ptmt.setInt(3, student.getAdmissionYear());
    ptmt.setDate(4, new java.sql.Date(student.getBirthday().getTime()));
    ptmt.setString(5, student.getClassId());
    ptmt.setString(6, id);
    //执行
    ptmt.execute();
  }

  public void delete(String id) throws SQLException {
    String sql = "DELETE FROM students WHERE id=?";
    PreparedStatement ptmt = conn.prepareStatement(sql);
    ptmt.setString(1, id);
    ptmt.execute();
    query();
  }
}
