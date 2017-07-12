import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("doctors", Doctor.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("doctors/:id/patients/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Doctor doctor = Doctor.find(Integer.parseInt(request.params(":id")));
      model.put("doctor", doctor);
      model.put("template", "templates/doctor-patients-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/patients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("patients", Patient.all());
      model.put("template", "templates/patients.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/patients/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Patient patient = Patient.find(Integer.parseInt(request.params(":id")));
      model.put("task", task);
      model.put("template", "templates/patient.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/doctors/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/doctor-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/doctors", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("doctors", Doctor.all());
      model.put("template", "templates/doctors.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new-doctors", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String specialty = request.queryParams("specialty");
      Doctor newDoctor = new Doctor(name, specialty);
      newDoctor.save();
      // model.put("doctor", newDoctor);
      model.put("template", "templates/doctor-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/patients", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Doctor doctor = Doctor.find(Integer.parseInt(request.queryParams("doctorId")));
      String name = request.queryParams("name");
      int birthdate = Integer.parseInt(request.queryParams("birthdate"));
      Patient newPatient = new Patient(name, birthdate, newPatient.getDoctorId());
      Doctor.addPatient(newPatient);
      model.put("doctor", doctor);
      model.put("template", "templates/doctor-patient-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
