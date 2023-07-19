CREATE VIEW attendance_taken AS 
SELECT D.id, D.class_id, D.dateTaken, A.stud_id, A.attendance 
FROM attendance_details D, attendance A 
WHERE D.id=A.attendance_id;