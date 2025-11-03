package com.mindguard.backend.services;

import com.mindguard.backend.dto.quiz.SubmitAnswerDto;
import com.mindguard.backend.model.Response;
import com.mindguard.backend.repository.ResponseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final ResponseRepository responseRepository;

    public QuizService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    public void submitAnswers(List<SubmitAnswerDto> answers) {
        if (answers == null || answers.isEmpty()) {
            throw new IllegalArgumentException("No answers provided");
        }

        List<Response> responses = answers.stream().map(dto -> {
            Response r = new Response();
            r.setUserIdentifier(dto.getUserIdentifier());
            r.setQuizId(dto.getQuizId());
            r.setQuestionId(dto.getQuestionId());
            r.setSelectedOption(dto.getSelectedOption());  // ✅ store text
            return r;
        }).collect(Collectors.toList());

        responseRepository.saveAll(responses);
    }

}
