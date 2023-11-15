Castle知识库管理系统，提供各类文档的预览和全文检索功能，拥有视频知识库，拥有强大的查重系统，可以针对全系统或单一文档的查重并生成查重报告。

本系统已经商用给部分客户，如果企业业务是需要对内部的大量文件进行管理，分权限查看查询及下载，有文件需要进行查重，那就完全符合本系统的使用场景。

演示环境：暂不提供   
客户端地址：https://gitee.com/hcwdc/knowledgebase-member   
文档地址：http://doc.icuapi.com/#/knowledgebase/%E6%A6%82%E8%BF%B0   

具体功能如下：

1. 各类文件预览
2. 全文检索
3. 知识库权限分级（仅查看、可下载、可编辑、可管理）
4. 视频知识库
5. 文档内容查重 生成报告
6. 文档历史版本管理
7. 标签体系
8. 词云图
9. 自定义知识模型
10. 预览水印
11. 下载文件时支持转为PDF格式

# 技术说明
本系统使用的技术栈如下：

| **名称** | **版本** | **说明** |
| --- | --- | --- |
| jdk | 1.8 |  |
| Maven | 官网最新版 |  |
| MySql | 5.7 |  |
| LibreOffice | 官网最新版 | office转pdf |
| Elasticsearch | 7.17.3 | 全文检索引擎 |
| Elasticsearch-analysis-ik | 7.17.3 | ik分词器 |
| wkhtmltopdf |  | html转pdf |



# 查重说明
查重支持两种模式，单文件查重和本地库查重。
> 查重暂不支持文件内表格内容   
> 查重暂不支持图片文件

## 单文件查重说明
用户自主上传2个文件进行对比查重，系统进行对比查重。
## 本地库查重
用户上传某个文件，与知识库内的所有文件进行对比。

# 演示效果

<video src="https://oss.icuapi.com/docs/video/knowledgebase/Castleknowledgebase-1080p.mp4" autoplay="false" controls="controls" width="80%">
</video>