Setting Up and Running the Application
1) Clone the Repository:  
  git clone https://github.com/your-username/discogs-backend-challenge.git
  cd discogs-backend-challenge
2) Configure the Database:  
  Create a database named in your preferred database management system (e.g., MySQL, PostgreSQL).
  Update the application.properties file located in src/main/resources with your database configuration:
  spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_SCHEMA
  spring.datasource.username=YOUR_USERNAME
  spring.datasource.password=YOUR_PASSWORD
  spring.jpa.hibernate.ddl-auto=update
  spring.jpa.show-sql=true
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
3) Build the Project:  
  mvn clean install
4) Run the Application:  
  mvn spring-boot:run
5) Running Unit Tests
  Run Tests Using Maven:
  mvn test
6) Results of Unit Tests
   a) ArtistRepositoryTest:  
    - Test Method: testFindByName
    - Description: Verifies that an artist can be found by their name.
    - Result: Should pass if the artist is correctly saved and retrieved.
  b) MasterServiceTest:  
    - Test Method: testGetMasterByTitle
    - Description: Verifies that a master can be found by its title.
    - Result: Should pass if the master is correctly retrieved by title.
  c) ReleaseServiceTest:
    - Test Method: testSaveRelease
    - Description: Verifies that a release can be saved.
    - Result: Should pass if the release is correctly saved and retrieved.
