package com.ssp.table.questions;

import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.support.*;
import org.springframework.stereotype.*;

import javax.sql.*;
import java.text.*;
import java.util.*;
import java.util.stream.*;

@Repository
public class DataBaseDAO extends JdbcDaoSupport {

    DataSource dataSource;

    @Autowired
    public DataBaseDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
        this.dataSource = dataSource;
    }

    /**
     * Получение всех вопрос из бд
     * @return список
     * See {@link QuestionInfo}
     */
    //Получение вопросов
    public List<QuestionInfo> getQuestions() {
        // Select ba.Id, ba.Question, ba.Meaning From Questions ba Order By ba.Id
        return this.getJdbcTemplate().query(QuestionMapper.BASE_SQL, new Object[] {}, new QuestionMapper());
    }

    /**
     * Получение средних баллов по каждому вопросу
     * @param selectDepartment выбранный отдел
     * @param dateFrom Дата начала выборки результатов тестирования
     * @param dateTo Дата окончания выборки результатов тестирования
     * @return Список
     */
    public Map<Long, Double> getAVGResult(String selectDepartment , String dateFrom , String dateTo) {

        String sql = "Select rt.ID_QUESTION,AVG(rt.ANSWER) From RESULT_TEST rt,Interview inter WHERE inter.ID_INTERVIEW=rt.ID and " +
                " inter.Id_Department = " + Long.parseLong(selectDepartment) + " and inter.Date_Interview Between To_Date('" + dateFrom + "','yyyy-mm-dd')"+
                " and To_Date('" + dateTo + "','yyyy-mm-dd')  GROUP BY rt.ID_QUESTION ORDER BY rt.ID_QUESTION" ;

        Map<Long, Double> avgInfos = this.getJdbcTemplate().query(sql, new Object[]{}, new avgMapper())
            .stream().collect(Collectors.toMap(it -> it.getKey(), it -> it.getValue()) );


        return avgInfos;
    }

    /**
     * Получение суммы баллов каждого участника по выбранным параметрам
     * @param selectDepartment выбранный отдел
     * @param dateFrom Дата начала выборки результатов тестирования
     * @param dateTo Дата окончания выборки результатов тестирования
     * @return список
     * See {@link QuestionResultInfo}
     */
    public List<QuestionResultInfo> getQuestionResult(String selectDepartment , String dateFrom , String dateTo) {

        String sql = "Select inter.Summary From Interview inter Where  inter.Id_Department = " + Long.parseLong(selectDepartment) +
                " and inter.Date_Interview Between To_Date('" + dateFrom + "','yyyy-mm-dd')" + " and To_Date('" + dateTo + "','yyyy-mm-dd')" ;

        return this.getJdbcTemplate().query(sql, new Object[] {}, new QuestionResultMapper());
    }

    /**
     * Добавление вопроса в бд Questions
     * @param id идентификатор вопроса
     * @param query содержание вопроса
     * @param meaning смысл вопроса 0 нормальный, 1 реверс
     */
    public void WriteDBQuestion(Long id, String query,Long meaning){
        String saveSql = "insert into Questions(Id,Question,Meaning)" +" values (?,?,?)";
        this.getJdbcTemplate().update(saveSql,id,query,meaning);
    }

    /**
     * Изменение вопроса в бд Questions
     * @param id идентификатор вопроса
     * @param query содержание вопроса
     * @param meaning смысл вопроса 0 нормальный, 1 реверс
     */
    public void ChangeDBQuestion(Long id, String query,Long meaning){
        String Sql = "update Questions set Question=?,Meaning=? where id=?";
        this.getJdbcTemplate().update(Sql,query,meaning,id);
    }

    /**
     * Удаление вопроса с бд Questions
     * @param id идентификатор вопроса
     */
    public void DeleteDBQuestion(Long id){
        String Sql = "delete from Questions where id=?";
        this.getJdbcTemplate().update(Sql,id);
    }

    /**
     * Запись результатов в две табл. Interview and Result_Test после прохождения тестирования
     * @param allResult результаты тестирования в формате [x1,x2...]
     * @param idDepartment Отдел в котором проходилось тестирование
     * @param questionInfoList список вопросов, чтобы проверять смысл вопроса реверс он или нет
     */
    public void WriteResultTest(String allResult, Long idDepartment,List<QuestionInfo> questionInfoList) throws ParseException {
        String saveSql = "insert into Result_Test(Id,Id_Question,Answer)" + " values (?,?,?)";
        String saveSqlSecond = "insert into Interview(Id_Interview,Date_Interview,Id_Department,Link,Summary)" + " values (?,?,?,?,?)";
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();


        String dateTest = String.format("%tF", new Date());

        String[] subStr = allResult.split(",");
        Long[] question_result = new Long[subStr.length];

        for (int i = 0 ; i < subStr.length ; i++){
            question_result[i] = Long.parseLong(subStr[i]);

        }
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        Date myDate = format.parse(dateTest);
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());

        Long sumResult = 0L;
        int count = 0;
        for (QuestionInfo questionInfo : questionInfoList) {
            if(questionInfo.getMeaning() == 1){
                sumResult += 6 - question_result[count];
            }
            else{
                sumResult += question_result[count];
            }
            count++;
        }

        this.getJdbcTemplate().update(saveSqlSecond,randomUUIDString,sqlDate,idDepartment,13,sumResult);
        for(int i = 0;i < question_result.length;i++){
            this.getJdbcTemplate().update(saveSql,randomUUIDString,i + 1,question_result[i]);
        }
    }

}