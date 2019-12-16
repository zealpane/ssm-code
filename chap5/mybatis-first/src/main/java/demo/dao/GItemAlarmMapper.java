package demo.dao;

import demo.model.GItemAlarm;
import demo.model.GItemAlarmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GItemAlarmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table g_item_alarm
     *
     * @mbg.generated Sat Sep 07 15:55:48 CST 2019
     */
    long countByExample(GItemAlarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table g_item_alarm
     *
     * @mbg.generated Sat Sep 07 15:55:48 CST 2019
     */
    int deleteByExample(GItemAlarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table g_item_alarm
     *
     * @mbg.generated Sat Sep 07 15:55:48 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table g_item_alarm
     *
     * @mbg.generated Sat Sep 07 15:55:48 CST 2019
     */
    int insert(GItemAlarm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table g_item_alarm
     *
     * @mbg.generated Sat Sep 07 15:55:48 CST 2019
     */
    int insertSelective(GItemAlarm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table g_item_alarm
     *
     * @mbg.generated Sat Sep 07 15:55:48 CST 2019
     */
    List<GItemAlarm> selectByExample(GItemAlarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table g_item_alarm
     *
     * @mbg.generated Sat Sep 07 15:55:48 CST 2019
     */
    GItemAlarm selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table g_item_alarm
     *
     * @mbg.generated Sat Sep 07 15:55:48 CST 2019
     */
    int updateByExampleSelective(@Param("record") GItemAlarm record, @Param("example") GItemAlarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table g_item_alarm
     *
     * @mbg.generated Sat Sep 07 15:55:48 CST 2019
     */
    int updateByExample(@Param("record") GItemAlarm record, @Param("example") GItemAlarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table g_item_alarm
     *
     * @mbg.generated Sat Sep 07 15:55:48 CST 2019
     */
    int updateByPrimaryKeySelective(GItemAlarm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table g_item_alarm
     *
     * @mbg.generated Sat Sep 07 15:55:48 CST 2019
     */
    int updateByPrimaryKey(GItemAlarm record);
}