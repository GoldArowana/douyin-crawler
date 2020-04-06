import pymysql
import sys


def main():
    print("start-----")
    db = pymysql.connect("localhost", "root", "1qaz2wsx", "douyin_crawler")
    sql = "INSERT INTO douyin_crawler_log(aweme_id) values(123)"
    cursor = db.cursor()
    try:
        cursor.execute(sql)
        db.commit()
        print("commit-----")
    except:
        # 如果发生错误则回滚
        db.rollback()
        print("rollback-----")
        print(sys.exc_info())

    # 关闭数据库连接
    db.close()


if __name__ == '__main__':
    main()
