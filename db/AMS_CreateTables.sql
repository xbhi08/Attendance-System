CREATE TABLE ADMIN(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    contact VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL
);
CREATE TABLE STUDENT(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    contact VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL
);
CREATE TABLE FACULTY(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    contact VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL
);
CREATE TABLE CLASS_DETAILS(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    faculty_id INT NOT NULL,
    FOREIGN KEY(faculty_id) REFERENCES FACULTY(id)
);
CREATE TABLE CLASS(
    class_id INT NOT NULL,
    stud_id INT NOT NULL,
    FOREIGN KEY(class_id) REFERENCES CLASS_DETAILS(id),
    FOREIGN KEY(stud_id) REFERENCES STUDENT(id),
    PRIMARY KEY(class_id, stud_id)
);
CREATE TABLE ATTENDANCE_DETAILS(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    class_id INT NOT NULL,
    dateTaken DATE DEFAULT current_timestamp(),
    FOREIGN KEY(class_id) REFERENCES CLASS_DETAILS(id)
);
CREATE TABLE ATTENDANCE(
    attendance_id INT NOT NULL,
    stud_id INT NOT NULL,
    attendance ENUM('present', 'absent'),
    FOREIGN KEY(attendance_id) REFERENCES ATTENDANCE_DETAILS(id),
    FOREIGN KEY(stud_id) REFERENCES STUDENT(id),
    PRIMARY KEY(attendance_id, stud_id)
);
    