package com.ssp.table.questions;

import org.springframework.jdbc.core.*;

import java.sql.*;

/**
 * Маппер для результатов тестирования(общая суммма баллов от 15 до 75 пир 15 вопросах)
 */
public class QuestionResultMapper implements RowMapper<QuestionResultInfo> {
        @Override
        public QuestionResultInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long summary = rs.getLong("Summary");
            return new QuestionResultInfo(summary);
        }

    }


