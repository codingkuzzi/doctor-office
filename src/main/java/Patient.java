import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;


public class Patient {
  private String name;
  private int id;
  private int birthday;
  private int doctorId;

  public Patient(String name, int birthday, int doctorId) {
    this.name = name;
    this.birthday = birthday;
    this.doctorId = doctorId;
  }

  public String getName() {
      return name;
    }

    public static List<Patient> all() {
      String sql = "SELECT id, name, birthday, doctorId FROM patients";
      try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql).executeAndFetch(Patient.class);
      }
    }

    public int getId() {
      return id;
    }

    public int getDoctorId() {
    return doctorId;
  }

    public int getBirthday() {
      return birthday;
    }

    @Override
    public boolean equals(Object otherPatient) {
      if (!(otherPatient instanceof Patient)) {
        return false;
      } else {
        Patient newPatient = (Patient) otherPatient;
        return this.getName().equals(newPatient.getName()) &&
             this.getId() == newPatient.getId() &&
             this.getDoctorId() == newPatient.getDoctorId();
      }
    }

    public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patients (name, birthday, doctorId) VALUES (:name, :birthday, :doctorId);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("birthday", this.birthday)
        .addParameter("doctorId", this.doctorId)
        .executeUpdate()
        .getKey();
    }
  }
    public static Patient find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM patients WHERE id=:id;";
        Patient patient = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Patient.class);
        return patient;
      }
    }



}
