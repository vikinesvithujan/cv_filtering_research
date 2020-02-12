export class MockData {
  static availableVacancies: string[] = [
    'Software Engineer',
    'Research Engineer',
    'Solutions Engineer',
    'System Engineer',
  ];

  static requirements: any = {
    'SoftwareEngineer': [
      'BSc in Computer Science/ Engineering; prior industry experience (minimum 1-2 years) will be an added advantage',
      'Strong development skill in Java language or C/C++ languages',
      'Strong analytical and troubleshooting skills',
      'Experience in and knowledge of enterprise technologies are an added advantage, e.g. Web services, XML, JSON, SSO, OAuth, API Management',
      'Prior work experience in a similar capacity would be an added benefit, but not a prerequisite',
    ],
    'ResearchEngineer': [
      'Your GPA and standing (rank) in the batch',
      'Your future plans and how this position will help you with these plans',
      'References to any of your publications or research work',
      'Links to examples of your writing (e.g. research papers, technical articles, blogs etc. Thesis is not accepted as an example)',
    ],
    'SolutionsEngineer': [
      'BSc in Computer Science/Engineering or equivalent',
      '2 years experience in application software development',
      'Willing to learn SOA, EDA, enterprise middleware and cloud computing, while actively participating in the entire process, from design to implementation',
      'Excellent customer interaction and communication skills',
    ],
    'SystemEngineer': [
      'Fresh graduate with BSc in Computer Science/Engineering or Equivalent or with a minimum of 1-2 years of industry experience',
      'Strong knowledge of Linux and Open Source software',
      'Comprehensive understanding of "Unix-like" Systems',
      'Familiarity with AWS and/or Azure platforms',
    ],
  };

  static responsibilities: any = {
    'SoftwareEngineer': [
      'Exhibit natural curiosity, an analytical mind, and willingness to explore the unknown',
      'Have an affinity for new challenges, a self-starting attitude, and be able to work with a global team',
      'Be able to take ownership of complex technical problems and drive them to resolution, individually, as well as work effectively through escalations with the engineering team',
      'Possess an amicable personality, excellent communication and interpersonal skills, including sensitivity to the local cultures of WSO2’s global customer base',
    ],
    'ResearchEngineer': [
      'We give our Research Engineers the room to focus, do deep work, and build their own brand full time focusing on research',
      'As a Research Engineer, you are expected to collaborate with other researchers, communicate, publish, and build visibility.',
      'Deep analytical skills, willingness to learn, and ability to write and communicate are essential for this role',
    ],
    'SolutionsEngineer': [
      'Ability to travel on short notice for short term assignments',
      'Exhibit natural curiosity, an analytical mind, and a willingness to explore the unknown.',
      'Have an affinity for new challenges, a self-starting attitude, and a willingness to work with a global team.',
      'Be able to take ownership of complex technical problems and drive them to resolution individually as well as work effectively through escalations with the engineering team.',
    ],
    'SystemEngineer': [
      'Setup and maintain enterprise deployments of WSO2 solutions for customers',
      'Analyzing logs and identifying potential issues in the deployments',
      'Introducing and integrating new technologies into existing data center environments',
    ],
  }
}
