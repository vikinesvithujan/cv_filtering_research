package com.research.recruiter.handler;

import com.research.recruiter.model.*;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OntoHandlerImpl implements OntoHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OntoHandlerImpl.class);

    private static final String SOURCE = "src\\main\\resources\\candidate_ontology.owl";
    private static final String NS = "http://www.semanticweb.org/user/ontologies/2020/0/candidate-ontology" + "#";
    private static final String BASE = "RDF/XML";

    private static final String RDFS = "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>";
    private static final String OWL = "PREFIX owl:<http://www.w3.org/2002/07/owl#>";
    private static final String XSD = "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>";
    private static final String RDF = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    private static final String USER = "PREFIX candidate: <http://www.semanticweb.org/user/ontologies/2020/0/candidate-ontology#>";

    private static final String USER_PREFIX = RDFS + OWL + XSD + RDF + USER;

    private OntModel ontModel;

    @PostConstruct
    private void init() {
        ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

        try (InputStream inputStream = new FileInputStream(SOURCE)) {
            ontModel.read(inputStream, BASE);
        } catch (IOException e) {
            LOGGER.error("Error occurred when reading the ontology", e);
        }
    }

    @Override
    public void addCandidate(Candidate candidate) {
        Individual candidateIndividual = ontModel.createIndividual(NS + candidate.getCandidateId(), ontModel.getOntClass(NS + "Candidate"));

        candidateIndividual.addProperty(ontModel.getDatatypeProperty(NS + "firstName"), candidate.getFirstName().trim());
        candidateIndividual.addProperty(ontModel.getDatatypeProperty(NS + "lastName"), candidate.getLastName().trim());
        candidateIndividual.addProperty(ontModel.getDatatypeProperty(NS + "gender"), candidate.getGender().trim());
        candidateIndividual.addProperty(ontModel.getDatatypeProperty(NS + "dateOfBirth"), candidate.getDateOfBirth().trim());
        candidateIndividual.addProperty(ontModel.getDatatypeProperty(NS + "address"), candidate.getAddress().trim());
        candidateIndividual.addProperty(ontModel.getDatatypeProperty(NS + "country"), candidate.getCountry().trim());
        candidateIndividual.addProperty(ontModel.getDatatypeProperty(NS + "email"), candidate.getEmail().trim());
        candidateIndividual.addProperty(ontModel.getDatatypeProperty(NS + "phone"), candidate.getPhone().trim());
        candidateIndividual.addProperty(ontModel.getDatatypeProperty(NS + "nic"), candidate.getNic().trim());
        candidateIndividual.addProperty(ontModel.getDatatypeProperty(NS + "linkedIn"), candidate.getLinkedIn().trim());
        candidateIndividual.addProperty(ontModel.getDatatypeProperty(NS + "position"), candidate.getPosition().trim());

        int count = 1;
        for (TechnicalSkill skill : candidate.getTechnicalSkills()) {
            Individual skillIndividual = ontModel.createIndividual(NS + candidate.getCandidateId() + "-TS-" + (count++), ontModel.getOntClass(NS + Skill.TECHNICAL));

            skillIndividual.addProperty(ontModel.getDatatypeProperty(NS + "ts_skill"), skill.getSkill().trim().toUpperCase());
            skillIndividual.addProperty(ontModel.getDatatypeProperty(NS + "ts_rate"), String.valueOf(skill.getRate()));

            candidateIndividual.addProperty(ontModel.getObjectProperty(NS + "possess"), skillIndividual);
        }

        count = 1;
        for (SoftSkill skill : candidate.getSoftSkills()) {
            Individual skillIndividual = ontModel.createIndividual(NS + candidate.getCandidateId() + "-SS-" + (count++), ontModel.getOntClass(NS + Skill.SOFT));

            skillIndividual.addProperty(ontModel.getDatatypeProperty(NS + "ss_skill"), skill.getSkill().trim().toUpperCase());
            skillIndividual.addProperty(ontModel.getDatatypeProperty(NS + "ss_rate"), String.valueOf(skill.getRate()));

            candidateIndividual.addProperty(ontModel.getObjectProperty(NS + "possess"), skillIndividual);
        }

        count = 1;
        for (EducationalQualification qualification : candidate.getEducationalQualifications()) {
            Individual qualificationIndividual = ontModel.createIndividual(NS + candidate.getCandidateId() + "-EQ-" + (count++), ontModel.getOntClass(NS + "Educational"));

            qualificationIndividual.addProperty(ontModel.getDatatypeProperty(NS + "eq_name"), qualification.getName().trim());
            qualificationIndividual.addProperty(ontModel.getDatatypeProperty(NS + "eq_institute"), qualification.getInstitute().trim());
            qualificationIndividual.addProperty(ontModel.getDatatypeProperty(NS + "eq_certificate"), qualification.getCertificate().trim());
            qualificationIndividual.addProperty(ontModel.getDatatypeProperty(NS + "eq_gpa"), qualification.getGpa().trim());

            candidateIndividual.addProperty(ontModel.getObjectProperty(NS + "holds"), qualificationIndividual);
        }

        count = 1;
        for (ProfessionalQualification qualification : candidate.getProfessionalQualifications()) {
            Individual qualificationIndividual = ontModel.createIndividual(NS + candidate.getCandidateId() + "-PQ-" + (count++), ontModel.getOntClass(NS + "Professional"));

            qualificationIndividual.addProperty(ontModel.getDatatypeProperty(NS + "pq_name"), qualification.getInstitute().trim());
            qualificationIndividual.addProperty(ontModel.getDatatypeProperty(NS + "pq_institute"), qualification.getInstitute().trim());
            qualificationIndividual.addProperty(ontModel.getDatatypeProperty(NS + "pq_level"), qualification.getLevel().trim());

            candidateIndividual.addProperty(ontModel.getObjectProperty(NS + "holds"), qualificationIndividual);
        }

        count = 1;
        for (Experience experience : candidate.getWorkExperience()) {
            Individual experienceIndividual = ontModel.createIndividual(NS + candidate.getCandidateId() + "-EX-" + (count++), ontModel.getOntClass(NS + "Experience"));

            experienceIndividual.addProperty(ontModel.getDatatypeProperty(NS + "e_company"), experience.getCompany().trim());
            experienceIndividual.addProperty(ontModel.getDatatypeProperty(NS + "e_designation"), experience.getDesignation().trim());
            experienceIndividual.addProperty(ontModel.getDatatypeProperty(NS + "e_duration"), String.valueOf(experience.getDuration()));

            candidateIndividual.addProperty(ontModel.getObjectProperty(NS + "has"), experienceIndividual);
        }

        count = 1;
        for (Project project : candidate.getProjects()) {
            Individual projectIndividual = ontModel.createIndividual(NS + candidate.getCandidateId() + "-PR-" + (count++), ontModel.getOntClass(NS + "Project"));

            projectIndividual.addProperty(ontModel.getDatatypeProperty(NS + "p_title"), project.getTitle().trim());
            projectIndividual.addProperty(ontModel.getDatatypeProperty(NS + "p_description"), project.getDescription().trim());
            projectIndividual.addProperty(ontModel.getDatatypeProperty(NS + "p_technologies"), project.getTechnologies().trim());

            candidateIndividual.addProperty(ontModel.getObjectProperty(NS + "done"), projectIndividual);
        }

        try {
            ontModel.write(new FileWriter(SOURCE), "RDF/XML");
        } catch (IOException e) {
            LOGGER.error("Error occurred when writing the ontology", e);
        }
    }

    @Override
    public List<Candidate> getCandidatesBasicDetails() {
        String queryString = USER_PREFIX + "SELECT DISTINCT * WHERE { " +
                "?candidate rdf:type candidate:Candidate . " +
                "?candidate candidate:firstName ?firstName . " +
                "?candidate candidate:lastName ?lastName . " +
                "?candidate candidate:gender ?gender . " +
                "?candidate candidate:dateOfBirth ?dateOfBirth . " +
                "?candidate candidate:address ?address . " +
                "?candidate candidate:country ?country . " +
                "?candidate candidate:email ?email . " +
                "?candidate candidate:phone ?phone . " +
                "?candidate candidate:nic ?nic . " +
                "?candidate candidate:linkedIn ?linkedIn ." +
                "?candidate candidate:position ?position" +
                "} ORDER BY ?candidate";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, ontModel);

        ResultSet resultSet = queryExecution.execSelect();
        List<Candidate> candidateList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();

            Candidate candidate = new Candidate();
            candidate.setCandidateId(solution.getResource("candidate").getLocalName());
            candidate.setFirstName(solution.get("firstName").toString());
            candidate.setLastName(solution.get("lastName").toString());
            candidate.setGender(solution.get("gender").toString());
            candidate.setDateOfBirth(solution.get("dateOfBirth").toString());
            candidate.setAddress(solution.get("address").toString());
            candidate.setCountry(solution.get("country").toString());
            candidate.setEmail(solution.get("email").toString());
            candidate.setPhone(solution.get("phone").toString());
            candidate.setNic(solution.get("nic").toString());
            candidate.setLinkedIn(solution.get("linkedIn").toString());
            candidate.setPosition(solution.get("position").toString());

            candidateList.add(candidate);
        }
        return candidateList;
    }

    @Override
    public Candidate getCandidateBasicDetails(String candidateId) {
        String queryString = USER_PREFIX + "SELECT * WHERE { " +
                "?candidate rdf:type candidate:Candidate . " +
                "FILTER (?candidate IN ( candidate:" + candidateId + " )) . " +
                "?candidate candidate:firstName ?firstName . " +
                "?candidate candidate:lastName ?lastName . " +
                "?candidate candidate:gender ?gender . " +
                "?candidate candidate:dateOfBirth ?dateOfBirth . " +
                "?candidate candidate:address ?address . " +
                "?candidate candidate:country ?country . " +
                "?candidate candidate:email ?email . " +
                "?candidate candidate:phone ?phone . " +
                "?candidate candidate:nic ?nic . " +
                "?candidate candidate:linkedIn ?linkedIn ." +
                "?candidate candidate:position ?position" +
                "}";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, ontModel);

        ResultSet resultSet = queryExecution.execSelect();
        Candidate candidate = new Candidate();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();

            candidate.setCandidateId(solution.getResource("candidate").getLocalName());
            candidate.setFirstName(solution.get("firstName").toString());
            candidate.setLastName(solution.get("lastName").toString());
            candidate.setGender(solution.get("gender").toString());
            candidate.setDateOfBirth(solution.get("dateOfBirth").toString());
            candidate.setAddress(solution.get("address").toString());
            candidate.setCountry(solution.get("country").toString());
            candidate.setEmail(solution.get("email").toString());
            candidate.setPhone(solution.get("phone").toString());
            candidate.setNic(solution.get("nic").toString());
            candidate.setLinkedIn(solution.get("linkedIn").toString());
            candidate.setPosition(solution.get("position").toString());
        }
        return candidate;
    }

    @Override
    public List<TechnicalSkill> getCandidateTechnicalSkills(String candidateId) {
        String queryString = USER_PREFIX + "SELECT * WHERE { " +
                "candidate:" + candidateId + " candidate:possess ?skill . " +
                "?skill candidate:ts_skill ?name . " +
                "?skill candidate:ts_rate ?rate" +
                "}";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, ontModel);

        ResultSet resultSet = queryExecution.execSelect();
        List<TechnicalSkill> technicalSkillList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();

            TechnicalSkill technicalSkill = new TechnicalSkill();
            technicalSkill.setSkill(solution.get("name").toString());
            technicalSkill.setRate(Integer.parseInt(solution.get("rate").toString()));

            technicalSkillList.add(technicalSkill);
        }

        return technicalSkillList;
    }

    @Override
    public List<SoftSkill> getCandidateSoftSkills(String candidateId) {
        String queryString = USER_PREFIX + "SELECT * WHERE { " +
                "candidate:" + candidateId + " candidate:possess ?skill . " +
                "?skill candidate:ss_skill ?name . " +
                "?skill candidate:ss_rate ?rate" +
                "}";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, ontModel);

        ResultSet resultSet = queryExecution.execSelect();
        List<SoftSkill> softSkillList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();

            SoftSkill softSkill = new SoftSkill();
            softSkill.setSkill(solution.get("name").toString());
            softSkill.setRate(Integer.parseInt(solution.get("rate").toString()));

            softSkillList.add(softSkill);
        }

        return softSkillList;
    }

    @Override
    public List<Project> getCandidateProjects(String candidateId) {
        String queryString = USER_PREFIX + "SELECT * WHERE { " +
                "candidate:" + candidateId + " candidate:done ?project . " +
                "?project candidate:p_title ?title . " +
                "?project candidate:p_description ?description . " +
                "?project candidate:p_technologies ?technologies" +
                "}";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, ontModel);

        ResultSet resultSet = queryExecution.execSelect();
        List<Project> projectList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();

            Project project = new Project();
            project.setTitle(solution.get("title").toString());
            project.setDescription(solution.get("description").toString());
            project.setTechnologies(solution.get("technologies").toString());

            projectList.add(project);
        }

        return projectList;
    }

    @Override
    public List<Experience> getCandidateExperience(String candidateId) {
        String queryString = USER_PREFIX + "SELECT * WHERE { " +
                "candidate:" + candidateId + " candidate:has ?experience . " +
                "?experience candidate:e_company ?company . " +
                "?experience candidate:e_designation ?designation . " +
                "?experience candidate:e_duration ?duration" +
                "}";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, ontModel);

        ResultSet resultSet = queryExecution.execSelect();
        List<Experience> experienceList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();

            Experience experience = new Experience();
            experience.setCompany(solution.get("company").toString());
            experience.setDesignation(solution.get("designation").toString());
            experience.setDuration(Integer.parseInt(solution.get("duration").toString()));

            experienceList.add(experience);
        }

        return experienceList;
    }

    @Override
    public List<EducationalQualification> getCandidateEducationalQualification(String candidateId) {
        String queryString = USER_PREFIX + "SELECT * WHERE { " +
                "candidate:" + candidateId + " candidate:holds ?qualification . " +
                "?qualification rdf:type candidate:Educational . " +
                "?qualification candidate:eq_name ?name . " +
                "?qualification candidate:eq_institute ?institute . " +
                "?qualification candidate:eq_certificate ?certificate . " +
                "?qualification candidate:eq_gpa ?gpa" +
                "}";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, ontModel);

        ResultSet resultSet = queryExecution.execSelect();
        List<EducationalQualification> educationalQualificationList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();

            EducationalQualification educationalQualification = new EducationalQualification();
            educationalQualification.setName(solution.get("name").toString());
            educationalQualification.setInstitute(solution.get("institute").toString());
            educationalQualification.setCertificate(solution.get("certificate").toString());
            educationalQualification.setGpa(solution.get("gpa").toString());

            educationalQualificationList.add(educationalQualification);
        }

        return educationalQualificationList;
    }

    @Override
    public List<ProfessionalQualification> getCandidateProfessionalQualification(String candidateId) {
        String queryString = USER_PREFIX + "SELECT * WHERE { " +
                "candidate:" + candidateId + " candidate:holds ?qualification . " +
                "?qualification rdf:type candidate:Professional . " +
                "?qualification candidate:pq_name ?name . " +
                "?qualification candidate:pq_institute ?institute . " +
                "?qualification candidate:pq_level ?level . " +
                "}";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, ontModel);

        ResultSet resultSet = queryExecution.execSelect();
        List<ProfessionalQualification> professionalQualificationList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();

            ProfessionalQualification professionalQualification = new ProfessionalQualification();
            professionalQualification.setName(solution.get("name").toString());
            professionalQualification.setInstitute(solution.get("institute").toString());
            professionalQualification.setLevel(solution.get("level").toString());

            professionalQualificationList.add(professionalQualification);
        }

        return professionalQualificationList;
    }

    @Override
    public List<String> getCandidateIdsBySkills(List<String> skills) {
        skills = skills.stream().map(skill -> skill = "'" + skill.toUpperCase() + "'").collect(Collectors.toList());
        String queryString = USER_PREFIX + "SELECT DISTINCT ?candidateId ?name WHERE { " +
                "?candidateId candidate:possess ?skill . " +
                "?skill candidate:ts_skill ?name . " +
                "FILTER (?name IN (" +
                String.join(",", skills) +
                ")) }";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, ontModel);

        ResultSet resultSet = queryExecution.execSelect();
        HashMap<String, List<String>> skillMap = new HashMap<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();

            String candidateId = solution.getResource("candidateId").getLocalName();
            String skill = solution.getLiteral("name").toString();
            if (!skillMap.containsKey(candidateId)) {
                skillMap.put(candidateId, new ArrayList<>(Collections.singletonList(skill)));
            } else {
                if (!skillMap.get(candidateId).contains(skill)) {
                    List<String> skillList = skillMap.get(candidateId);
                    skillList.add(skill);
                    skillMap.put(candidateId, skillList);
                }
            }
        }

        List<String> candidateIdList = new ArrayList<>();
        List<String> finalSkills = skills;
        skillMap.forEach((k, v) -> {
            if (v.size() == finalSkills.size())
                candidateIdList.add(k);
        });
        return candidateIdList;
    }

    @Override
    public List<String> getCandidateIdsByPosition(String position) {
        String queryString = USER_PREFIX + "SELECT DISTINCT ?candidateId WHERE { " +
                "?candidateId candidate:position '" + position + "'" +
                " }";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, ontModel);

        ResultSet resultSet = queryExecution.execSelect();
        List<String> candidateIdList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();

            String candidateId = solution.getResource("candidateId").getLocalName();
            candidateIdList.add(candidateId);
        }
        return candidateIdList;
    }

    @Override
    public List<String> getCandidateIdsBySkillsAndPosition(List<String> skills, String position) {
        skills = skills.stream().map(skill -> skill = "'" + skill.toUpperCase() + "'").collect(Collectors.toList());
        String queryString = USER_PREFIX + "SELECT DISTINCT ?candidateId ?name WHERE { " +
                "?candidateId candidate:position '" + position + "' . " +
                "?candidateId candidate:possess ?skill . " +
                "?skill candidate:ts_skill ?name . " +
                "FILTER (?name IN (" +
                String.join(",", skills) +
                ")) }";

        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecution = QueryExecutionFactory.create(query, ontModel);

        ResultSet resultSet = queryExecution.execSelect();
        HashMap<String, List<String>> skillMap = new HashMap<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();

            String candidateId = solution.getResource("candidateId").getLocalName();
            String skill = solution.getLiteral("name").toString();
            if (!skillMap.containsKey(candidateId)) {
                skillMap.put(candidateId, new ArrayList<>(Collections.singletonList(skill)));
            } else {
                if (!skillMap.get(candidateId).contains(skill)) {
                    List<String> skillList = skillMap.get(candidateId);
                    skillList.add(skill);
                    skillMap.put(candidateId, skillList);
                }
            }
        }

        List<String> candidateIdList = new ArrayList<>();
        List<String> finalSkills = skills;
        skillMap.forEach((k, v) -> {
            if (v.size() == finalSkills.size())
                candidateIdList.add(k);
        });
        return candidateIdList;
    }
}

