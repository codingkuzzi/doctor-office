import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PatientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctor_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deletePatientsQuery = "DELETE FROM patients *;";
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      con.createQuery(deletePatientsQuery).executeUpdate();
      con.createQuery(deleteDoctorsQuery).executeUpdate();
    }
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Patient firstPatient = new Patient("Anna",1, 1 );
    Patient secondPatient = new Patient("Anna",1, 1 );
    assertTrue(firstPatient.equals(secondPatient));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Patient newPatient = new Patient("Anna",1, 1 );
    newPatient.save();
    assertTrue(Patient.all().get(0).equals(newPatient));
  }

  @Test
  public void all_returnsAllInstancesOfPatient_true() {
    Patient firstPatient = new Patient("Anna", 1, 1);
    firstPatient.save();
    Patient secondPatient = new Patient("Ivan", 2, 1);
    secondPatient.save();
    assertEquals(true, Patient.all().get(0).equals(firstPatient));
    assertEquals(true, Patient.all().get(1).equals(secondPatient));
  }

  @Test
  public void save_assignsIdToObject() {
    Patient newPatient = new Patient("Anna",1, 1 );
    newPatient.save();
    Patient savedPatient = Patient.all().get(0);
    assertEquals(newPatient.getId(), savedPatient.getId());
  }

  @Test
   public void getId_patientsInstantiateWithAnId_1() {
     Patient newPatient = new Patient("Anna",1, 1 );
     newPatient.save();
     assertTrue(newPatient.getId() > 0);
   }

   @Test
   public void find_returnsPatientWithSameId_secondPatient() {
     Patient firstPatient = new Patient("Anna", 1, 1);
     firstPatient.save();
     Patient secondPatient = new Patient("Ivan", 2, 1);
     secondPatient.save();
     assertEquals(Patient.find(secondPatient.getId()), secondPatient);
   }

   @Test
  public void save_savesDoctorIdIntoDB_true() {
    Doctor newDoctor = new Doctor("Peter Petrov", physician);
    newDoctor.save();
    Patient newPatient = new Patient("Ivan", 2, newPatient.getId());
    newPatient.save();
    Patient savedPatient = Patient.find(newPatient.getId());
    assertEquals(savedPatient.getDoctorId(), newDoctor.getId());
  }
}
