package com.ssp.table.questions;

/**
 * Средний результат по отделу
 * Среднее значение берется по каждому вопросу
 */
@Deprecated
public class AVGinfo {

    private Long idQuestion;
    private Double avg;

    /**
     *
     * @param idQuestion идентификатор вопроса
     * @param avg среднее значение по данному вопросу
     */
    public AVGinfo( Long idQuestion,Double avg) {
        super();
        this.idQuestion = idQuestion;
        this.avg = avg;
    }


    public Long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }
}
