package com.ssp.table.questions;

import java.util.*;

/**
 * Класс необходим для генерации ссылок каждому отделу
 */
public class ResultToCount {

    private String finalURL;

    /**
     *
     * @param department идентификатор отдела для которого генерится ссылка
     * @return сгенерир. ссылку на отдел
     */
    public String RandomGen(String department){
        Random random = new Random();
        int num = 1000000000 + random.nextInt(2000000000 - 1000000000);
        finalURL = Integer.toString(num) + "dep" + department;
        return finalURL;
    }

    public String getFinalURL() {
        return finalURL;
    }

    public void setFinalURL(String finalURL) {
        this.finalURL = finalURL;
    }
}
