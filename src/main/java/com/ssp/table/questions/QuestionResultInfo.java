package com.ssp.table.questions;

/**
 * Класс отвечает за работу с результатами тестирования
 * а именно за получение доступа к ним
 * todo Пока пусть будет так, но на будущее, создавать класс ради одного поля ({@link sumResult}) не стоит
 */
public class QuestionResultInfo {
    private Long sumResult;

    /**
     *
     * @param sumResult сумма по каждому участнику
     */
    public QuestionResultInfo( Long sumResult) {
        super();
        this.sumResult = sumResult;
    }

    public Long getSumResult() {
        return sumResult;
    }

    public void setSumResult(Long sumResult) {
        this.sumResult = sumResult;
    }
}

