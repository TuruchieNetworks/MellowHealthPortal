# Introduction
Greetings recruiters and tech enthusiasts! Mellow Health Portal stands as a testament to our commitment to organized code, robust functionalities, and the seamless integration of Electronic Health Records (EHR) and Electronic Data Capture (EDC) capabilities. We invite you to explore our codebase, witness the power of our utilities, and envision the transformative impact of our comprehensive healthcare solution.

## Comprehensive Healthcare Solution
Mellow Health Portal is more than just a healthcare application; it's a comprehensive solution designed to seamlessly integrate EHR and EDC capabilities for efficient patient care and clinical research.

### File Structure
#### Controllers
Admin Controller: /src/main/java/com/turuchie/mellowhealthportal/controllers/AdminController.java
Physician Controller: /src/main/java/com/turuchie/mellowhealthportal/controllers/PhysicianController.java
Physicians-Patient Controller: /src/main/java/com/turuchie/mellowhealthportal/controllers/PhysiciansPatientController.java
Clinical Operations Controllers
Incident Report Controller: /src/main/java/com/turuchie/mellowhealthportal/controllers/ClinicalOperationsControllers/IncidentReportController.java
Patient Case Controller: /src/main/java/com/turuchie/mellowhealthportal/controllers/ClinicalOperationsControllers/PatientCaseController.java
Diagnostic Procedures Controllers
Coagulation Records Controller: /src/main/java/com/turuchie/mellowhealthportal/controllers/DiagnosticProceduresControllers/CoagulationRecordsController.java
Other Diagnostic Controllers: (List not included for brevity)
Patient Operations Controller
Adverse Effect Controller: /src/main/java/com/turuchie/mellowhealthportal/controllers/PatientOperationsController/AdverseEffectController.java
Insurance Information Controller: /src/main/java/com/turuchie/mellowhealthportal/controllers/PatientOperationsController/InsuranceInformationController.java
Other Patient Operations Controllers: (List not included for brevity)

#### Models
Administration Models: /src/main/java/com/turuchie/mellowhealthportal/models/Administrations
Clinical Operations Models: /src/main/java/com/turuchie/mellowhealthportal/models/ClinicalOperations
Diagnostic Procedures Models: /src/main/java/com/turuchie/mellowhealthportal/models/DiagnosticProcedures
Patient Operations Models: /src/main/java/com/turuchie/mellowhealthportal/models/PatientOperations
Physicians Models: /src/main/java/com/turuchie/mellowhealthportal/models/Physicians

#### Repositories
Clinical Operations Repositories: /src/main/java/com/turuchie/mellowhealthportal/repositories/ClinicalOperationsRepositories
Diagnostic Procedures Repositories: /src/main/java/com/turuchie/mellowhealthportal/repositories/DiagnosticProceduresRepositories
Patient Operations Repositories: /src/main/java/com/turuchie/mellowhealthportal/repositories/PatientOperationsRepositories

#### Services
Clinical Operations Services: /src/main/java/com/turuchie/mellowhealthportal/services/ClinicalOperationsServices
Diagnostic Procedures Services: /src/main/java/com/turuchie/mellowhealthportal/services/DiagnosticProceduresServices
Patient Operations Services: /src/main/java/com/turuchie/mellowhealthportal/services/PatientOperationsServices

#### Utils
Various Utility Classes: /src/main/java/com/turuchie/mellowhealthportal/utils
Highlighted Utils:
AgeRange.java, BirthdayUtils.java, DiagnosticUtils.java, InsuranceUtil.java, SearchUtil.java, and more.

#### Resources
Static Resources: /src/main/resources/static
HTML Templates: /src/main/resources/templates
Configuration Properties: /src/main/resources/application.properties


## Mellow Health Portal - Getting Started Guide.
To make the most of our application, follow these steps to set up initial data and explore various functionalities:

### Step 1: Creating Essential Records
#### 1.1 Create a Physician
Navigate to the Physician Controller (/src/main/java/com/turuchie/mellowhealthportal/controllers/PhysicianController.java) and use the provided endpoints to create at least one physician.

#### 1.2 Create a Patient
Visit the Patients Controller (/src/main/java/com/turuchie/mellowhealthportal/controllers/PatientOperationsController/PatientsController.java) and use the available endpoints to add a patient.

#### 1.3 Establish a Physicians-Patients Relationship
In the Physicians-Patient Controller (/src/main/java/com/turuchie/mellowhealthportal/controllers/PhysiciansPatientController.java), associate the created physician with the patient. This relationship is crucial for streamlined communication and care coordination.

### Step 2: Patient Case Management
#### 2.1 Create a Patient Case
Go to the Patient Case Controller (/src/main/java/com/turuchie/mellowhealthportal/controllers/ClinicalOperationsControllers/PatientCaseController.java) and create a patient case for the established physician-patient relationship.

### Step 3: Exploring Additional Functionalities
#### 3.1 Current Medication and Dose Regimen
Utilize the Current Medication Controller (/src/main/java/com/turuchie/mellowhealthportal/controllers/DiagnosticProceduresControllers/CurrentMedicationsController.java) to add current medication records.
Once current medications are available, proceed to the Dose Regimen Controller (/src/main/java/com/turuchie/mellowhealthportal/controllers/DiagnosticProceduresControllers/DoseRegimenController.java) to set up dose regimens that depend on current medications.
#### 3.2 Other Diagnostic Procedures
Explore other diagnostic procedures available in the Diagnostic Procedures Controllers. Each procedure might have dependencies or relationships with other records.

#### Step 4: Testing and Further Exploration
Now that you have set up essential records, feel free to test various functionalities. Create patient vital records, insurance information, and explore incident reporting. The robust codebase and utilities are designed to handle complex healthcare scenarios.
Remember to check the logs for any error messages, and feel free to refer to the comprehensive utils package (/src/main/java/com/turuchie/mellowhealthportal/utils) for additional support during testing.

Mellow Health Portal Boasts of a many to many relationship involing physicans, patients, patient_cases, physicians_patients, insurance_information, physicians_address, patients_address, admin and none-entity classes- loginPatient, loginPhysician and AdminLogin!
These functionalities, coupled with dynamic display options, authentication mechanisms, and full CRUD operations, make the application a powerful tool for healthcare professionals.

## Here Are Basic Highlights Of Our Database Structure
- Physicians Table: A database table dedicated to storing information about physicians, facilitating efficient management and retrieval of data related to medical practitioners.

- Patients Management: Robust functionality for managing patient records, ensuring secure storage and easy access to vital patient information.

- Admin Panel: An intuitive admin panel equipped with tools for overseeing and managing various aspects of the application, ensuring smooth operation and compliance with regulatory requirements- For instance and admin can delete a user!

- Physicians-Patients Relationship: A feature that establishes and manages relationships between physicians and their patients, streamlining communication and enhancing care coordination.

- Patients Cases And Diagnostic Operations: Dedicated functionality for documenting and tracking patient cases, enabling thorough analysis and informed decision-making by healthcare providers.

- Incident Reporting: A robust incident reporting system that allows patients to report and address new medical incidents promptly and effectively, promoting patient safety and quality improvement.

- Insurance Information Management: Tools for managing insurance information, simplifying the process of verifying coverage and processing claims.

- Dynamic Display: Flexible display options that adapt to user preferences and device specifications, ensuring an optimal user experience across different platforms.

- Filtered Display: To provide focused access to information Authentication and Validation: Robust authentication mechanisms and data validation processes to safeguard sensitive information and maintain data integrity.

- Full CRUD Operations: Comprehensive support for Create, Read, Update, and Delete operations, empowering users to manage data efficiently and effectively.

- Profile Edits with Re-Encryption: Secure profile editing capabilities with built-in re-encryption functionality, ensuring that sensitive data remains protected at all times.

- Physicians-Patients Relationship Enforcement: Measures to enforce one patient per physician relationship, promoting adherence to healthcare protocols and standards.
  
Happy exploring, and we appreciate your engagement with Mellow Health Portal! If you have any questions or need assistance, don't hesitate to reach out.
