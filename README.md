This project involves creating a Spring Boot application with User and Role entities, implementing role-based access control. 
Users can have multiple roles (user, admin), and the application restricts access to CRUD operations and pages based on roles.

## Key Features:
- Implementation of Role and User models using GrantedAuthority and UserDetails interfaces
- Security configuration using userDetailService (instead of in-memory auth)
- Role-based access control: CRUD operations and pages under /admin/ accessible only to users with admin role/user page accessible only to users with user or admin roles, displaying user's data
- Custom LoginSuccessHandler directing admins to /admin page and users to their /user page upon authentication
- All pages implemented using Spring Boot and JavaScript for dynamic interactions
- Logout functionality configured on all pages using Thymeleaf
