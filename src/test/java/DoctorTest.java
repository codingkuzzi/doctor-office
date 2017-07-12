import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class DoctorTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctor_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      String deletePatientsQuery = "DELETE FROM categories *;";
      con.createQuery(deleteDoctorsQuery).executeUpdate();
      con.createQuery(deletePatientsQuery).executeUpdate();
    }
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Doctor firstDoctor = new Doctor("Ivan Ivanov", dentist);
    Doctor secondDoctor = new Doctor("Ivan Ivanov", dentist);
    assertTrue(firstDoctor.equals(secondDoctor));
  }
  @Test
  public void save_returnsTrueIfNamesAretheSame() {
    Doctor newDoctor = new Doctor("Ivan Ivanov", dentist);
    newDoctor.save();
    assertTrue(Doctor.all().get(0).equals(newDoctor));
  }

  @Test
  public void all_returnsAllInstancesOfDoctor_true() {
    Doctor firstDoctor = new Doctor("Ivan Ivanov", dentist);
    firstDoctor.save();
    Doctor secondDoctor = new Doctor("Peter Petrov", physician);
    secondDoctor.save();
    assertEquals(true, Doctor.all().get(0).equals(firstDoctor));
    assertEquals(true, Doctor.all().get(1).equals(secondDoctor));
  }

  @Test
  public void save_assignsIdToObject() {
    Doctor newDoctor = new Doctor("Ivan Ivanov", dentist);
    newDoctor.save();
    Doctor savedDoctor = Doctor.all().get(0);
    assertEquals(newDoctor.getId(), savedDoctor.getId());
  }

  @Test
  public void getId_doctorInstantiateWithAnID() {
    Doctor newDoctor = new Doctor("Ivan Ivanov", dentist);
    newDoctor.save();
    assertTrue(newDoctor.getId() > 0);
  }

  @Test
  public void find_returnsDoctorWithSameId_secondDoctor() {
    Doctor firstDoctor = new Doctor("Ivan Ivanov", dentist);
    firstDoctor.save();
    Doctor secondDoctor = new Doctor("Peter Petrov", physician);
    secondDoctor.save();
    assertEquals(Doctor.find(secondDoctor.getId()), secondDoctor);
  }

  @Test
  public void getPatients_retrievesALlPatientsFromDatabase_patientsList() {
    Doctor newDoctor = new Doctor("Ivan Ivanov", dentist);
    newDoctor.save();
    Patient firstPatient = new Patient("Anna", 1, 1);
    firstPatient.save();
    Patient secondPatient = new Patient("Ivan", 2, 1);
    secondPatient.save();
    Patient[] patients = new Patient[] { firstPatient, secondPatient };
    assertTrue(newDoctor.getPatients().containsAll(Arrays.asList(patients)));
  }

}
