# Springboot-Angular-Mindguard

Mental health monitoring and support system

\# 🌿 MindGuard: Mental Health Monitoring \& Support System



> A full-stack web application designed to monitor and support mental health through assessments, alerts, and progress tracking.  

> Built using \*\*Angular\*\* for the frontend and \*\*Spring Boot\*\* for the backend.



---



\## 📘 Overview



MindGuard helps users evaluate and monitor their mental well-being.  

It provides mental health assessments, alert notifications, mood tracking, and progress reports — all managed through a secure login system and admin dashboard.



---



\## ⚙️ Features



\### 👤  Features

1\. Login / Register 

•	Secure user authentication with role-based access (User, Parent, Therapist, Admin). 

•	Email/phone verification and password encryption. 

2\.	Home 

•	Central dashboard showing quick links to all modules. 

•	Displays motivational quotes, mood status, and recent activity. 

3\.	Assessment – Quiz 

•	Multiple-choice mental health questionnaires. 

4\.	Assessment – Audio 

•	Users record short Audio about their mood or experiences. 

5\.	Alert  

•	Instant notification to user, parent when mental risk is high. 

6\.	Admin Dashboard (User \& Logs Management) 

•	Manage all registered users and their roles. 

•	View system usage logs and activity reports. 

7\.	About 

•	Provides project details, mission, and objectives. 

•	Includes development credits and contact information. 

8\.	Contact 

•	Allows users to send inquiries or feedback. 

•	Includes therapist support and helpdesk links. 

9\.	Mood Tracker (Daily Check-in) 

•	Users record daily mood with emojis, notes, and optional images. 

•	Data is stored for long-term trend analysis. 

10\.	Progress Report Generator (PDF) 

•	Generates downloadable PDF reports of mood, assessments, and progress. 

•	Shareable with admin or parents. 

11\.	Calendar with Mental Health Reminders 

•	Schedules therapy sessions, daily mood check-ins, and mindfulness activities. 

•	Sends push notifications and reminders. 

12\.	Audio Therapy / Meditation Module 

•	Provides guided meditation, breathing exercises, and calming audio. 

•	Tracks usage for progress analysis. 

13\.	Data Export / Integration 

•	Securely exports user progress data for research or medical consultation. 

•	Compatible with selected external systems (e.g., Google Calendar). 

14\.	Chatbot Wellness Chat 

•	chatbot for emotional support and wellness tips. 

•	Provides instant coping strategies and encouragement. 



\## 🧩 Project Structure



MindGuard/

│

├── MentalHealth\_Frontend/ # Angular Frontend

│ ├── src/app/

│ │ ├── components/ # UI components

│ │ ├── services/ # HTTP \& API connections

│ │ ├── pages/ # Modules (login, home, assessment, etc.)

│ │ └── assets/ # Images, icons, etc.

│ └── angular.json

│

└── MentalHealth\_Backend/ # Spring Boot Backend

├── src/main/java/com/mindguard/backend/

│ ├── controller/ # REST Controllers

│ ├── model/ # Entity Classes

│ ├── repository/ # Database Repositories

│ ├── service/ # Service Layer

│ └── config/ # Security \& CORS Configuration

└── pom.xml

\## 🖥️ Technologies Used



\### Frontend

\- Angular  

\- TypeScript, HTML, CSS  

\- Bootstrap or Tailwind CSS  



\### Backend

\- Spring Boot  

\- Java  

\- Spring Security  

\- Spring Data JPA  

\- MySQL Database  





