# 抖音爬虫
----
用于爬取指定用户的'作品', 以及指定用户的'喜欢'


---
本项目使用语言及版本：
* jdk: 13-pre
* python: 3.8

----
本项目使用技术：
* mitmdump + python做代理拦截
* vertx作为整个项目的主要框架
* sqlbuilder帮助拼接sql语句
* 自己用反射实现了一个对象关系应该工具类com/aries/crawler/tools/Orm.java , 弥补了vertx没有orm的不便利之处。

----
本项目仅供学习研究, 不提供任何反爬虫等功能, 请不要恶意爬取, 恶意使用本代码者后果自负。