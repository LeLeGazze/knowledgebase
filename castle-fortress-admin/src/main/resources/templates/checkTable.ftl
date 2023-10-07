<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Document</title>
</head>
<body>
<div style="font-size: 16px;padding: 0 5vw">
    <div style="text-align: center; font-size: 28px;margin-bottom: 20px">知识库个人查重服务报告单(全文对照)</div>
    <div style="width: 100%; height: 2px; background: #bad5eb"></div>
    <div
            style="
					font-size: 20px;
					background-color: #dfe9f2;
					padding: 10px 2px;
          height: 20px;
					border-top: 2px solid #086cbc;
					margin-top: 2px;
				"
    >
        <div style="width: 50%;float:left">
            <font color="#8d1152"
            >报告编号:${restData.id}</font
            >
        </div>
        <div style="width: 50%;float:left;text-align: end;">检测时间:${restData.detectionTime}</div>
    </div>
    <div style="width: 100%;height: 150px;">
        <div style=" padding: 5px ">
            <div style="width: 10vw; text-align: end;float: left;">
                <font color="#2372c6">篇名：</font>
            </div>
            <span style="font-weight: 700">&nbsp;${restData.famous}</span>
        </div>
        <div style=" padding: 5px;">
            <div style="width: 10vw; text-align: end;float: left;">
                <font color="#2372c6">作者：</font>
            </div>
            <span style="color: #6a6568">&nbsp;${restData.authorName}</span>
        </div>
        <div style=" padding: 5px;">
            <div style="width: 10vw; text-align: end;float: left;">
                <font color="#2372c6">检测类型：</font>
            </div>
            <span style="color: #6a6568">&nbsp;${restData.detectionType}</span>
        </div>
        <div style=" padding: 5px;">
            <div style="width: 10vw; text-align: end;float: left;">
                <font color="#2372c6">截止时间：</font>
            </div>
            <span style="color: #6a6568">&nbsp;${restData.deadline}</span>
        </div>
    </div>
    <div
            style="
					font-size: 20px;
					background-color: #dfe9f2;
					padding: 10px 2px;
					border-top: 2px solid #086cbc;
					margin-top: 2px;
				"
    >
        <h4 style="font-weight: 400;padding: 0;margin: 0">检测结果</h4>
    </div>
    <div>
        <div style="overflow: hidden">
            <#--            <div style="width: 33.33%; padding: 10px 0;float:left">-->
            <#--                <div style="float: left;"><font color="#2372c6">去除本人文献复制比：</font></div>-->
            <#--                <span><img src="https://img-blog.csdnimg.cn/f526fb171198470a8f393aba4b2c4d4b.png" alt=""-->
            <#--                           style="width: 40px;height: 15px;"> 20.6%</span>-->
            <#--            </div>-->
            <#--            <div style="width: 33.33%; padding: 10px 0;float:left">-->
            <#--                <div style="float: left;"><font color="#2372c6">去除引用文献复制比：</font></div>-->
            <#--                <span><img src="https://img-blog.csdnimg.cn/f526fb171198470a8f393aba4b2c4d4b.png" alt=""-->
            <#--                           style="width: 40px;height: 15px;"> 20.6%</span>-->
            <#--            </div>-->
            <div style="width: 25%; padding: 10px 0;float:left">
                <div style="float: left;"><font color="#2372c6">总文字复制比：</font></div>
                <span><img
                             <#if restData.replicationRatio &gt; 50 >
                                 src="http://zsk.chinahcses.top/upload/hongse.png" alt=""
                             <#else >
                                 src="http://zsk.chinahcses.top/upload/huangse.png" alt=""
                             </#if>
                           style="width: 40px;height: 15px;"> ${restData.replicationRatio}%</span>
            </div>
<#--            <div style="width: 33.33%; padding: 10px 0;float:left">-->
<#--                <div style="float: left;"><font color="#2372c6">单篇最大文字复制比：</font></div>-->
<#--                <span><img-->
<#--                            <#if restData.maxReplicationRatio &gt; 50 >-->
<#--                                src="http://zsk.chinahcses.top/upload/hongse.png" alt=""-->
<#--                            <#else >-->
<#--                                src="http://zsk.chinahcses.top/upload/huangse.png" alt=""-->
<#--                            </#if>-->
<#--                           style="width: 40px;height: 15px;"> ${restData.maxReplicationRatio}%</span>-->
<#--            </div>-->
            <div style="width: 25%; padding: 10px 0;float:left"><font color="#2372c6">重复字符数：</font
                ><span>[${restData.numberOfRepeated}]</span></div>
            <div style="width: 25%; padding: 10px 0;float:left">
                <font color="#2372c6">单篇最大重复字符数：</font
                ><span>[${restData.maxNumber}]</span>
            </div>
            <div style="width: 25%; padding: 10px 0;float:left"><font color="#2372c6">总字符数：</font
                ><span>[${restData.totalNumber}]</span>
            </div>
        </div>
        <div>
            <#--            单篇最大文字复制比：<span>${restData.maxReplicationRatio}%&ensp;&ensp;</span-->
            <#--            >-->
            <#--            &lt;#&ndash;            <span style="border-bottom: 2px solid #8f407e"&ndash;&gt;-->
            <#--            &lt;#&ndash;            ><font color="#8f407e"&ndash;&gt;-->
            <#--            &lt;#&ndash;                >(医院住院管理信息系统设计与实现)</font&ndash;&gt;-->
            <#--            &lt;#&ndash;                ></span&ndash;&gt;-->
            <#--            &lt;#&ndash;            >&ndash;&gt;-->
            <#--        </div>-->
        </div>
<#--        <div-->
<#--                style="-->
<#--          width: 100%;-->
<#--          height: 1px;-->
<#--          background-color: #eee;-->
<#--          margin: 15px 0;-->
<#--        "-->
<#--        ></div>-->
<#--        <div style="padding-bottom: 10px;">-->

<#--        </div>-->

        <!-- 新增---开始 -->
        <div style="width: 100%; color: #555;">
            <ul style="border: 1px solid #eee; padding-left: 20px">
                <#list restData.resultsList as item>
                    <li style="list-style:none; padding: 10px;">
                        <div style="width: 200px;float: left">
                            <img
                                    <#if item.replicationRatio &gt; 50 >
                                        src="http://zsk.chinahcses.top/upload/hongse.png" alt=""
                                    <#else >
                                        src="http://zsk.chinahcses.top/upload/huangse.png" alt=""
                                    </#if>
                                    style="width: 40px;height: 15px;"> ${item.replicationRatio}%&nbsp;( ${item.count})
                        </div>
                        <div style="margin: 0 20px;width: 180px;float: left"><img
                                    src="http://zsk.chinahcses.top/upload/ben.png" alt=""

                                    style="width: 20px; height: 20px;margin-right: 10px;">${item.replicationRatio}%
                            &nbsp;(${item.count})
                        </div>
                        <span>
							<a style="color: #555"
                               href="#${item.code}">${item.title}</a><span> &nbsp;(${item.length})</span>
						</span>
                    </li>
                </#list>
            </ul>
        </div>
        <!-- 新增---结束 -->
        <#list restData.restDataLists as restlist>
            <div
                    style="
					font-size: 20px;
					background-color: #dfe9f2;
					padding: 10px 2px;
					height: 20px;
					border-top: 2px solid #086cbc;
					margin-top: 2px;
				"
            >
                <h4 style="width: 50%;float: left;font-weight: 400;padding: 0;margin: 0"><a
                            name="${restlist.code}">${restlist_index+1}.${restlist.metaData[0].paragraphName}</a></h4>
                <div style="width: 50%;float: left;text-align: end;">总字符：${restlist.length}</div>
            </div>
            <div
                    style="
					font-size: 16px;
					background-color: #fcfaf1;
					padding: 10px 2px;
					border-top: 1px solid #999;
				"
            >
                <div>
                    <font color="#2f6dbc">相似文献列表</font>
                </div>
            </div>
            <div
                    style="
				font-size: 16px;
				background-color: #fcfaf1;
				padding-bottom: 10px;
				height: 30px;
				border-top: 1px solid #999;
				border-bottom: 2px solid #999;
			"
            >
                <div>
                    <#--                <div style="width: 33.33%; padding: 10px 0;float:left"><font color="#a4435d">重复字符数：</font-->
                    <#--                    ><span>80.2% (2540)</span></div>-->
                    <#--                <div style="width: 33.33%; padding: 10px 0;float:left">-->
                    <#--                    <font color="#a4435d">单篇最大重复字符数：</font-->
                    <#--                    ><span>80.2% (2540)</span>-->
                    <#--                </div>-->
                    <div style="width: 33.33%; padding: 10px 0;float:left"><font color="#a4435d">文字复制比：</font
                        ><span> ${restlist.textCopyRatio}% (${restlist.count})</span></div>
                </div>
            </div>
            <#list restlist.metaData as metaD>
                <div style="border-bottom: 1px solid #999;overflow: hidden">
                    <div style="width: 79%;float: left;border-right: 1px solid #999;">
                        <div style="height: 40px;">
                            <div style="width: 30px;; background-color: #fcfaf1;float: left;text-align: center;margin-right: 10px;border: 1px solid #eee;">
                                ${metaD_index+1}
                            </div>
                            <div style="float: left;">
                                ${metaD.fileName}
                            </div>
                        </div>
                        <div style="padding-left: 30px;">
                            <span>-</span>
                            <span>《${metaD.title}》</span>
                            <span>-</span>
                            <span>&nbsp;${metaD.date?substring(0,4)}</span>
                        </div>
                    </div>
                    <div style="width: 20.5%;float: left;">
                        <div style="border-bottom: 1px solid #999;padding-bottom: 5px;text-align: center;">
                            <font color="red">${metaD.repeatRatio}% (${metaD.repeatCount})</font>
                        </div>
                        <div style="text-align: center;padding-top: 5px;">
                            是否引证：否
                        </div>
                    </div>
                </div>
            </#list>


            <div style="margin-top: 10px; ">
                <table border="1" style="width: 100%;table-layout: auto; word-break: break-all; border-collapse: collapse;"
                       cellspacing="0">
                    <tr style="width: 100%">
                        <td colspan="2" style="width: 50%; text-align: center">
                            原文内容
                        </td>
                        <td style="text-align: center">相似内容来源</td>
                        <#--            </tr>-->

                        <#list  restlist.original as item >
                        <div>
                            <tr>
                                <td rowspan="${item.parseData[0].size}" style="width: 5%; text-align: center">
                                    ${item_index+1}
                                </td>
                                <td rowspan="${item.parseData[0].size}">
                                    <div style="padding: 15px;">
                                        <div style="text-align: center;padding-bottom: 20px;"><font
                                                    color="#9650aa">此处共有 ${item.parseData[0].textSize}
                                                字相似</font></div>
                                        <#--										<div>	1.…………………26摘要随着网络技术的发展和普及-->
                                        <#--											，计算机管理信息系统在企业管理中扮演着重要的角色-->
                                        <#--											。高效率、无差错的-->
                                        <#list item.parseData as par>
                                            ${par.beforeText}
                                            <#if par.text?length &gt;10>
                                                <span style="border-bottom: 1px solid red;">
										${par.text}
									</span>
                                            <#else  >
                                                ${par.text}
                                            </#if>

                                            ${par.afterTest}
                                        </#list>

                                    </div>

                                </td>
                        </div>
                        <td>
                            <div style="background-color: #fdf4f2; padding: 10px">
<#--                                ${item.parseData[0].similarList[0].title}-->
                                ${item.parseData[0].similarList[0].fileName} ${item.parseData[0].similarList[0].date}
                            </div>
                        </td>
                    </tr>
                    <#assign  index = 0 >
                    <#list item.parseData[0].list  as t >
                        <tr>
                            <td>
                                <div>
                                    <#assign index = index+1>
                                    <#list t as tt>
                                        ${tt.beforeText} ${tt.text} ${tt.afterTest}
                                    </#list>
                                </div>
                            </td>
                        </tr>
                        <#if index  != item.parseData[0].size/2>
                            <tr>
                                <td>
                                    <div style="background-color: #fdf4f2; padding: 10px">
<#--                                        ${item.parseData[0].similarList[t_index+1].title} -->
                                        ${item.parseData[0].similarList[t_index+1].fileName} ${item.parseData[0].similarList[t_index+1].date}
                                    </div>
                                </td>
                            </tr>
                        </#if>
                    </#list>

                    </#list>
                </table>
            </div>
        </#list>
    </div>
</body>
</html>
