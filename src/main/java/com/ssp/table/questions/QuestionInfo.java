package com.ssp.table.questions;

/**
 * Класс отвечает за работу с вопросами
 * а именно за получение доступа к ним и их изменение
 */
public class QuestionInfo {

    private String question;
    private Long id;
    private String average;
    private Long meaning;

    /**
     *
     * @param id идентификатор вопроса
     * @param question содержание вопроса
     * @param average среднее значение по каждому вопросу(результат)
     * @param meaning смысл вопроса если 0, то он не изменен, если 1 смысл изменен и необходим реверс результатов
     */
    public QuestionInfo(Long id, String question,String average,Long meaning) {
        super();
        this.id = id;
        this.question = question;
        this.average = average;
        this.meaning = meaning;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public Long getMeaning() {
        return meaning;
    }

    public void setMeaning(Long meaning) {
        this.meaning = meaning;
    }
}

