package com.mindguard.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "chatquestions")
public class QuestionChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;
    private String option1;
    private String option2;
    private String option3;

    public QuestionChat() {}

    public List<String> getOptions() {
        List<String> opts = new java.util.ArrayList<>();
        if(option1 != null) opts.add(option1);
        if(option2 != null) opts.add(option2);
        if(option3 != null) opts.add(option3);
        return opts;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

    // getters and setters
}


