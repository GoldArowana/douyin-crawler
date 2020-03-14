package com.aries.crawler.tools;

import com.aries.crawler.annotation.MysqlField;
import com.aries.crawler.model.DataModelable;
import com.aries.crawler.verticles.WideDataPickUpVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.sqlclient.Row;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * vert.x没有orm, 使用mysql获取到的数据很难转化为对象。
 * 平时只能这样使用：
 * <code>
 * DouYinCrawlerLogModel model = new DouYinCrawlerLogModel();
 * model.setFirstName(row.getString("first_name"));
 * model.setMale(row.getBoolean("male"));
 * model.setAge(row.getInteger("age"));
 * ...
 * ...
 * <code/>
 * 可见, 想要把数据库对象row转化为model需要花费数行代码, 该表中的字段越多, 代码函数耗费的越多。
 * 所以, 我实现了本方法, 只需一行代码即可将数据库对象Row转化为Model对象, 弥补了vert.x没有orm的不便之处:
 * <code>
 * DouYinCrawlerLogModel model = MySQLHelper.getModel(row, DouYinCrawlerLogModel.class);
 * </code>
 *
 * @author arowana
 */
public class Orm {

    private static final Logger logger = LoggerFactory.getLogger(WideDataPickUpVerticle.class);

    /**
     * 防止实例化
     */
    private Orm() {

    }

    /**
     * @param row   从vert.x-mysql获取到的数据数据行对象
     * @param clazz 要转化的model类
     * @param <T>   继承DataModel的某个类
     * @return 返回clazz类型的一个实例
     */
    public static <T extends DataModelable> T getModel(Row row, Class<T> clazz) {
        try {
            // 反射获取clazz的构造器
            Constructor<? extends T> constructor = clazz.getConstructor();
            // 利用反射的构造器实例化
            T dataModel = constructor.newInstance();
            // 获取所有字段
            Field[] dataModelFields = clazz.getDeclaredFields();
            // 如果存在字段
            if (dataModelFields.length != 0) {
                for (Field dataModelField : dataModelFields) {
                    dataModelField.setAccessible(true);
                    MysqlField annotation = dataModelField.getAnnotation(MysqlField.class);
                    if (annotation != null) {
                        // 根据表中字段名获取所在第几列
                        int columnIndex = row.getColumnIndex(annotation.alias());
                        if (columnIndex < 0) {
                            logger.error("index less than 0, ", annotation.alias());
                            continue;
                        }
                        // 根据类型和列, 获取值
                        Object columnValue = row.get(annotation.type(), columnIndex);
                        // 将值反射到dataModel里
                        dataModelField.set(dataModel, columnValue);
                    }
                }
            }
            return dataModel;
        } catch (Exception e) {
            logger.error("exception in MysqlHelper.getModel(Row, Class): ", e);
        }
        return null;
    }
}
