package com.mindguard.backend.services;

import org.springframework.stereotype.Service;

@Service
public class MoodDetectionService {

    public String detectMood(String[] answers) {
        int happy = 0, stressed = 0, sad = 0, angry = 0, emotional = 0, motivated = 0, depressed = 0;

        for (String ans : answers) {
            ans = ans.toLowerCase();

            // Match based on meaning from your quiz questions
            if (ans.contains("never") && (ans.contains("stressed") || ans.contains("anxious"))) happy++;
            else if (ans.contains("very good") || ans.contains("good") || ans.contains("excited") || ans.contains("always")) happy++;
            else if (ans.contains("often") || ans.contains("always") || ans.contains("worried") || ans.contains("overwhelmed")) stressed++;
            else if (ans.contains("poor") || ans.contains("very poor") || ans.contains("hopeless")) depressed++;
            else if (ans.contains("low") || ans.contains("tired") || ans.contains("lonely")) sad++;
            else if (ans.contains("moderate") || ans.contains("daily") || ans.contains("high")) motivated++;
            else if (ans.contains("angry") || ans.contains("irritated")) angry++;
            else if (ans.contains("emotional")) emotional++;
        }

        int max = Math.max(Math.max(Math.max(Math.max(happy, stressed), Math.max(sad, angry)), Math.max(emotional, motivated)), depressed);

        if (max == happy) return "😀 Happy";
        if (max == stressed) return "😓 Stressed";
        if (max == depressed) return "😞 Depressed";
        if (max == motivated) return "💪 Motivated";
        if (max == sad) return "😢 Sad";
        if (max == angry) return "😡 Angry";
        if (max == emotional) return "🥺 Emotional";
        return "😐 Neutral";
    }

    public String generateMessage(String mood) {
        switch (mood) {
            case "😀 Happy": return "You seem to be in a great mood today! Keep it up!";
            case "😓 Stressed": return "Try to take a break — relaxation and deep breathing help.";
            case "😞 Depressed": return "You seem low. Remember, it's okay to seek help and talk.";
            case "💪 Motivated": return "Great energy! Keep using your motivation positively!";
            case "😢 Sad": return "A bit low today? Try doing something that cheers you up.";
            case "😡 Angry": return "You might be feeling irritated. Take some time to cool off.";
            case "🥺 Emotional": return "Feeling emotional is natural. Take care of yourself.";
            default: return "Stay balanced and take care of your well-being.";
        }
    }
}
