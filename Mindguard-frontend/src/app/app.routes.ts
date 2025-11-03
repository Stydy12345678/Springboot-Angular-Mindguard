import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Welcome } from './welcome/welcome';
import { Assesment } from './assesment/assesment';
import { Login } from './login/login';
import { Register } from './register/register';
import { About } from './about/about';
import { Contact } from './contact/contact';
import { AudioAssessment } from './audio-assessment/audio-assessment';
import { ResetPassword } from './reset-password/reset-password';
import { authGuard } from './auth-guard';
import { QuizAssessment } from './quiz-assessment/quiz-assessment';
import { Chatbot } from './chatbot/chatbot';
import { AdminDashboard } from './admin-dashboard/admin-dashboard';
import { Moodtracker } from './moodtracker/moodtracker';
import { AudioTherapy } from './audio-therapy/audio-therapy';
import { Adminaudio } from './adminaudio/adminaudio';
import { Analytics } from './analytics/analytics';

export const routes: Routes = [
     { path: 'home', component: Home,
      children: [
        { path: '', redirectTo: 'welcome', pathMatch: 'full' }, // default child
      { path: 'welcome', component: Welcome }, // home content
      { path: 'assessment', component: Assesment },
      //{ path: 'alert', component: alert },
      { path: 'chatboat', component: Chatbot }
    ] 
     },
  { path: 'login', component: Login },
  { path: 'register', component: Register},
  { path: 'about', component: About },
  { path: 'contact', component: Contact },
  { path: 'assessment/audio', component: AudioAssessment },  
 {path: 'assessment/quiz',component:QuizAssessment,canActivate: [authGuard]},
 
 { path: 'admin-dashboard', component: AdminDashboard },

  {path: 'moodtracker', component:Moodtracker},
  { path: '', redirectTo: 'home', pathMatch: 'full' },
         
   { path: 'reset-password', component: ResetPassword },
    { path: 'audio-therapy', component: AudioTherapy },
 { path: 'admin-dashboard/adminaudio', component: Adminaudio },
{ path: 'admin-dashboard/analytics', component: Analytics },

];


