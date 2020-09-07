
<img src="https://raw.githubusercontent.com/rusevradoslav/DevUni/master/src/main/resources/static/images/5198robot.ico" align="right"/>

# DevUni

![Build](https://github.com/rusevradoslav/DevUni/workflows/Build/badge.svg?branch=master) 
[![codecov](https://codecov.io/gh/rusevradoslav/DevUni/branch/master/graph/badge.svg)](https://codecov.io/gh/rusevradoslav/DevUni)


**Contributor:**

*  Radoslav Rusev

**Description:**

DevUni is an online platform for tutoring.
It enables everyone who decide to engage in programming to enroll in courses in one of the leading programming languages. 
DevUni also enables non-registered users to browse across the web page. They can see all courses and detailed information for every single one of them. 
In Devuni there are various roles with different degree of responsibility - root admin, admin, teacher and student. 
Students can enroll in courses, watch videos, download assignments and submit their assignments for every lecture.
After successfully completing the tasks (average grade of each submitted assignment is equal or greater than the course pass percentage) the course completed with a pass goes into the completed courses section. 
Students in DevUni also have the opportunity to send teacher requests to admin and after confirmation they get the chance to receive teacher privileges. 
Teachers in this application have the opportunity to create courses which must contain lectures and assignments for every lecture and then rate the submitted homework. 
Every teacher in DevUni is required to have About Me file with self-description and knowledge level. Admins in DevUni can do all students’ operations and have some extra functionality. 
The main purpose is to approve teacher requests and newly created courses and promote or demote user privileges. 
The root admin has the same privileges as admin but the difference is that that root admin can't enroll in courses and can make new admin or demote admin privileges.

**Technologies:**

**Front-End:**

*   HTML
*   CSS
*   Bootstrap
*   JavaScript
*   Thymeleaf 

 
**Back-End:**
*   Java
*   Spring Boot 
*   Spring MVC 
*   Spring Security
*   MySQL Database 
*   Hibernate
*   JPA
*   Cloudinary 

## Running

**DevUni run requirements:**

**Java (JRE) 14**

* <b>Windows</b> and <b>MacOS X</b> installers include JRE so just use them and don't think about internals.
* On <b>Linux</b> you may need to install Java manually (usually by running `sudo apt-get install openjdk-14-jdk` or something similar).
* If you don't use installer (on Windows or Mac OS X) you may need to download Java (JDK) from <a href="https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html">Oracle website</a>.

**MySQL Workbench 8.0.21**

* You can download MySQL Workbench from official <a href="https://dev.mysql.com/downloads/workbench/"> MySQL Workbench website.

## Building

#### Prerequisites:

 1. Java (JDK) 14 
 2. Apache Maven 3+
 3. MySQL Dialect 8
 4. Internet access
 5. Git client

#### Build
**Before build the project you need follow a few steps:**

 1. Clone GitHub Repository using the following command:
 
   ```
    $ git clone https://github.com/rusevradoslav/DevUni.git
   ```
 2. In application properties file you need to enter your personal database username and password:
   ```
   spring.datasource.username=[DB USERNAME]
   spring.datasource.password=[DB PASSWORD]
   ```

  Application properties file is in `src/main/resources/application.properties`

   
 3. Run MySql Script 

    If you want to create database with valid data click right button on devuni_script.sql and select command `Run devuni_script.sql ``

 4. Maven Configuration 
   ```
    cd  [PROJECT ROOT DIRECTORY]
    mvn package
    mvn clean install
    mvn spring-boot:run
   ```
 5. Run this Query in your MySql console to allow the maximum size of communication buffer:
   
   ``` 
    SET GLOBAL max_allowed_packet=268435456; 
   ```
 **Valid Profile Accounts:**
   
    Root Admin : email -> root_admin@gmail.com ; password -> 1234   
    Admin : email -> radorusevcrypto@gmail.com ; password -> 1234  
    Teacher : email -> mitkorusev@gmail.com ; password -> 1234 
    Student : email -> mruseva72@abv.bg ; password -> 1234 

### Routes

URLs | Description
---------|---------
 */* | Index page - page where guest user can see all courses and teachers in DevUni.
 */home* | Home page - page where authorized user can see all courses and teachers in DevUni.
 */users/register* | Register page - page where user can register.
 */users/login* | Login page - page where user can login.
 */about* | About page - page where user read more about DevUni.
 */courses/allCourses* | All Courses page - page where user can see all courses.
 */courses/courseDetails/{id}* | Course Details page - page where user can read more about each course.
 */courses/allCoursesInTopic/{id}* | All Courses In Topic page - page where user can see all courses filtered by topic name.
 */courses/enrolledCourses* | All Enrolled Courses From Students/Admins page - page where student/admin can see his all enrolled courses.
 */courses/completedCourses* | All Completed Courses From Students/Admins page - page where student/admin can see his all completed courses.
 */courses/create* | Create Course page - page where teacher can create course.
 */courses/teacher-courses* | All Courses Created From Teacher page - page where teacher can see all courses which he created.
 */courses/teacher-check-courses* | All Teacher Courses Table - page where teacher can see his all created courses.
 */courses/teacher-check-lecture* | All Lectures In One Course Table - page where teacher can see his all created lectures in single course.
 */assignments/check-assignments* | All Teacher Submitted Assignments In One Lectures  - page where teacher can see all submitted assignments.
 */users/user-details* | User Details page - page where user can see user profile details.
 */users/user-details* | About Me page - page where teacher can create About Me file.
 */users/change-email* | Change Email page - page where user can change his email.
 */users/change-password* | Change Password page - page where user can change his password.
 */users/userCheckedAssignments* | Checked Assignments page - page where student/admin can see all checked assignments from teacher. 
 */admins/admins/home-page* | Admin Home Page - page where root admin/admin can see all admins in DevUni.
 */admins/all-courses-details* | All Courses Details Page - page where root admin/admin approve course requests.
 */admins/create-admin* | Create Admin  Page - page where root admin can create new admin.
 */admins/all-admins* | All Admins  Page - page where root admin can demote admin to teacher or student and enable or disable user account.
 */admins/all-teachers* | All Teachers Page - page where root admin/admin can demote teacher to student  or promote teacher to admin and enable or disable user account.
 */admins/all-students* | All Teachers Page - page where root admin/admin can promote student to teacher or admin and enable or disable user account.
 */admins/all-teacher-requests* | All Teacher Requests Page - page where root admin/admin can approve all teacher requests.

 
 Database Diagram Screenshot
 ---
 
 ![database](/src/main/resources/static/screenshots/db_diagram.png)
 
 
 Website Screenshots
 ---
 
 - Index Page 
 ![indexpage](/src/main/resources/static/screenshots/index.jpeg) 
 
 - Register Page 
 ![registerpage](/src/main/resources/static/screenshots/register.jpg) 
 
 - Login Page 
  ![login](/src/main/resources/static/screenshots/login.jpg) 
 
 - Home Page 
 ![homepage](/src/main/resources/static/screenshots/home.jpg)
 
 - About Page 
 ![aboutpage](/src/main/resources/static/screenshots/about_us.jpg)
 
  
 - All Courses Page 
 ![allcoursespage](/src/main/resources/static/screenshots/all_courses_in_devuni.jpg)
 
  
 - Course Details Page 
 ![coursedetailspage](/src/main/resources/static/screenshots/course_details.jpg)
 
  
 - All Courses In Topic page 
 ![coursesintopicpage](/src/main/resources/static/screenshots/all_courses_in_topic.jpg)
 
  
 - Home Page 
 ![homepage](/src/main/resources/static/screenshots/home.jpg)
 
 - Student Profile Page 
  ![studentprofilepage](/src/main/resources/static/screenshots/student_profile.png) 
  
 - Enrolled Courses Page 
  ![enrolledcoursespage](/src/main/resources/static/screenshots/enrolled_courses.jpg)  
  
  
 - Lecture Page 
  ![courselecturespage](/src/main/resources/static/screenshots/course_single.jpg) 
 
 - Video Lecture Page 
  ![videolecturespage](/src/main/resources/static/screenshots/lecture.png)
 
 - Completed Courses Page 
  ![completedcoursespage](/src/main/resources/static/screenshots/completed_courses.jpg) 
  
 - Checked Assignments page
  ![checkedassignmentspage](/src/main/resources/static/screenshots/all_checked_assignments.jpg)
 
  - Teacher Profile Page 
   ![teacherprofilepage](/src/main/resources/static/screenshots/teacher_profile.png)
      
  - Create About Me Page 
   ![createaboutmepage](/src/main/resources/static/screenshots/abour_me.jpg) 
     
  - Teacher Courses Page
   ![teachercoursespage](/src/main/resources/static/screenshots/teacher_courses.jpg)  
      
  - Create Courses Page
   ![createcoursespage](/src/main/resources/static/screenshots/create_course.jpg) 
   
  - Teacher Courses Table Page
   ![teachercoursestablepage](/src/main/resources/static/screenshots/checked_assignments_courses.jpg)    
   
  - Teacher Lectures In Single Course Table Page
   ![teacherlecturesinsinglecoursetablepage](/src/main/resources/static/screenshots/checked_assignments_lecture.jpg)
      
  - Check Assignments Page
   ![checkassignmentspage](/src/main/resources/static/screenshots/check_assignments.jpg)  
    
  - Admin Home Page
   ![adminhomepage](/src/main/resources/static/screenshots/admin_home_page.jpg) 

  - Admin All Courses Page
   ![adminallcoursespage](/src/main/resources/static/screenshots/admin_all_courses.jpg)    
   
  - Root Admin All Admins Page
   ![adminalladminspage](/src/main/resources/static/screenshots/root_admin_all_admins.jpg) 
   
  - Root Admin Create Admin Page
   ![rootadmincreateadminpage](/src/main/resources/static/screenshots/create_admin.jpg) 

  - Admin All Teachers Page
   ![adminallteacherspage](/src/main/resources/static/screenshots/admin_all_teachers.jpg) 
   
  - Admin All Teacher Requests aPage
   ![adminallteacherrequestsspage](/src/main/resources/static/screenshots/admin_all_teacher_requests.jpg) 
  
  - Admin All Students Page
   ![adminallstudentspage](/src/main/resources/static/screenshots/admin_all_students.jpg) 
