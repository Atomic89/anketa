package com.ssp.table.questions;

import javafx.util.*;
import org.springframework.jdbc.core.*;

import java.sql.*;

/**
 *  Маппер для среднего результата по каждому вопросу
 *  чтобы вытащить с БД
 *
 *
 */

public class avgMapper implements RowMapper<Pair<Long, Double>> {
    @Override
    public Pair<Long, Double> mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long idQuestion = rs.getLong("Id_Question");
        Double avg = rs.getDouble("AVG(rt.ANSWER)");
        return new Pair<>(idQuestion, avg);
    }

}
