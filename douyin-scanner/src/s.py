import json

# 这个地方必须这么写 函数名：response
import sys

sys.path.append('/usr/local/lib/python3.8/site-packages')
sys.path.append('/usr/local/lib/python3.7/site-packages')

import pymysql as pymysql
db = pymysql.connect("localhost", "root", "1qaz2wsx", "douyin_crawler")


def response(flow):

    # 通过抓包软包软件获取请求的接口
    if '/aweme/favorite' in flow.request.url or '/aweme/post' in flow.request.url:
        # print("-------"+flow.response.text)
        for aweme in json.loads(flow.response.text)['aweme_list']:
            aweme_map = {}
            aweme_map['aweme_id'] = aweme['aweme_id']
            aweme_map['aweme_desc'] = aweme['desc']
            aweme_map['aweme_create_time'] = aweme['create_time']
            aweme_map['author_uid'] = aweme['author']['uid']
            aweme_map['author_short_id'] = aweme['author']['short_id']
            aweme_map['author_nickname'] = aweme['author']['nickname']
            aweme_map['author_signature'] = aweme['author']['signature']
            aweme_map['avatar_larger_url'] = aweme['author']['avatar_larger']['url_list'][0]
            aweme_map['author_share_info_qrcode'] = aweme['author']['share_info']['share_qrcode_url']['url_list'][0]
            aweme_map['video_cover'] = aweme['video']['cover']['url_list'][0]
            aweme_map['video_dynamic_cover'] = aweme['video']['dynamic_cover']['url_list'][0]
            aweme_map['video_download_addr'] = aweme['video']['download_addr']['url_list'][0]
            aweme_map['video_share_url'] = aweme['share_info']['share_url']
            if len(aweme['text_extra']) > 0:
                aweme_map['video_tag'] = aweme['text_extra']
            aweme_map['video_duration'] = aweme['duration']

            sql = """INSERT INTO douyin_crawler_log(aweme_id,aweme_desc,aweme_create_time,author_uid,author_short_id,author_nickname,author_signature,avatar_larger_url,
            author_share_info_qrcode_url,video_cover_url,video_dynamic_cover_url,video_download_addr_url ,video_share_url ,
            video_tag ,video_duration)
            VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)
            on duplicate key update video_download_addr_url = %s"""
            cursor = db.cursor()

            if 'video_tag' in aweme_map:
                t_tag = json.dumps(aweme_map['video_tag'], ensure_ascii=False)
            else:
                t_tag = "{}"

            values = (aweme_map['aweme_id'], aweme_map['aweme_desc'], aweme_map['aweme_create_time'],
                      aweme_map['author_uid'], aweme_map['author_short_id'],
                      aweme_map['author_nickname'], aweme_map['author_signature'],
                      aweme_map['avatar_larger_url'], aweme_map['author_share_info_qrcode'],
                      aweme_map['video_cover'], aweme_map['video_dynamic_cover'],
                      aweme_map['video_download_addr'], aweme_map['video_share_url'],
                      t_tag,
                      aweme_map['video_duration'], aweme_map['video_download_addr'])

            try:
                # 执行sql语句
                cursor.execute(sql, values)
                # 提交到数据库执行
                db.commit()
                print("here is succ")

            except:
                # 如果发生错误则回滚
                db.rollback()
                print("here is err:", sys.exc_info())

                # 关闭数据库连接
            # print("crawler res: ", aweme_map)
    # db.close()
