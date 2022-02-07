# university-system

Simple programm of university system  where using Postman we can: 

1. Create lector(Post) : http://localhost:8080/api/university/lector

{
"name": "Anatoliy Anatolich",
"degree": "Associate Professor",
"salary": "100"
}

2. Create department(Post) : http://localhost:8080/api/university/department

{
    "name": "Algebra and Geometry"
}

3. Add lector to department(Post) : http://localhost:8080/api/university/addLector/id/id

4. Find out who Head of department(Get) : http://localhost:8080/api/university/department/headOfDepartment?departmentName=Algebra and Geometry

5. Get average salary for department(Get) : http://localhost:8080/api/university/department/avgSalary?departmentName=Algebra and Geometry

6. Count numbers of employees in department(Get) : http://localhost:8080/api/university/department/showCountOfEmployee?departmentName=Algebra and Geometry

7. Find any information according to the entered word(Get) : http://localhost:8080/api/university/globalSearch?template="enter value"

8. Show statistic by department(Get) : http://localhost:8080/api/university/showStatistics?departmentName=Algebra and Geometry

And other CRUD methods...
