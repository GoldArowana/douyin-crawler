# 抖音爬虫
----
<p>用于爬取指定用户的'作品', 以及指定用户的'喜欢'。
<p>用手机挂代理刷抖音, 当你访问其他用户的个人空间时, 就自动把这些用户信息、头像、视频封面、视频mp4、mp3等都爬取下来了。


---
本项目使用语言及版本：
* jdk: 13-pre
* python: 3.8


----
模块介绍：
* douyin-scanner \[python\] 本模块用于将抖音信息以mitmdump代理形式拦截, 然后以宽表形式写入到数据库中, 方便douyin-downloader模块做后续的处理。
* douyin-downloader \[java\] 使用vertx框架。本模块用于将爬取下来的信息做后续的分析、重组、下载。

----
本项目使用技术：
* mitmdump + python做代理拦截
* vertx作为整个项目的主要框架
* vertx拼接sql不方便, 自己实现了一个sqlBuilder, 方便拼接sql
* 自己用反射实现了一个对象关系应该工具类com/aries/crawler/tools/Orm.java , 弥补了vertx没有orm的不便利之处。美其名曰：几十行代码实现了一个orm。

----
本项目仅供学习研究, 不提供任何反爬虫等功能, 请不要恶意爬取, 恶意使用本代码者后果自负。
