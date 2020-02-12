import { Experience } from './experience.model';
import { Project } from './project.model';
import { EducationalQualification } from './educationalQualification.model';
import { ProfessionalQualification } from './professionalQualification.model';

export class Candidate {
    candidateId: string;
    position: string;
    firstName: string;
    lastName: string;
    gender: string;
    dateOfBirth: string;
    address: string;
    country: string;
    email: string;
    phone: string;
    nic: string;
    linkedIn: string;
    technicalSkills: string[];
    softSkills: string[];
    educationalQualifications: EducationalQualification[];
    professionalQualifications: ProfessionalQualification[];
    projects: Project[];
    workExperience: Experience[];
}