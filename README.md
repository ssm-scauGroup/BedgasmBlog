# Bedgasm Blog 

---

[![GitHub issues](https://img.shields.io/github/issues/ssm-scauGroup/BedgasmBlog.svg?style=flat-square)](https://github.com/ssm-scauGroup/BedgasmBlog/issues)
[![GitHub forks](https://img.shields.io/github/forks/ssm-scauGroup/BedgasmBlog.svg?style=flat-square)](https://github.com/ssm-scauGroup/BedgasmBlog/network)
[![GitHub stars](https://img.shields.io/github/stars/ssm-scauGroup/BedgasmBlog.svg?style=flat-square)](https://github.com/ssm-scauGroup/BedgasmBlog/stargazers)

[演示网址](http://bedgasmblog.cn) 

## 系统设计

- 功能模块

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jd568mnj20w30hrdmj.jpg)

- 开发环境：

    操作系统：windows 10

    API测试：postman

    前端编辑器：Sublime text3

    IDE：myeclipse 2017

    数据库：mysql 5.7

- 技术框架：

    前端：bootstrap+jquery+ajax

    后台：spring+springMVC+mybatis

- 数据库设计

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3ji222g9j20mv0hs0ww.jpg)

- `postman`生成`api`接口文档

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jj4l8fhj20z20hadit.jpg)

---

## 实现效果

### 前台显示

- 首页

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jk9det3j20yt0h4k6h.jpg)

- 登录/注册

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jm4ehguj20a50b8mxo.jpg)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jlit5tyj20b60duaa5.jpg)

- 个人首页

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jmh4h6hj20yu0hc77c.jpg)

- 文章详情/评论

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jnpykhkj20yr0h543a.jpg)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jn9si06j20yj0gbgn4.jpg)

### 后台管理

> 注: 功能后面加`(管理员)`表示是管理员才拥有的功能。

- 仪表盘(用户)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jomw5afj20xg0ffta9.jpg)

- 文章管理(管理员)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jp06o0uj20y90dyjuf.jpg)

- 文章栏目管理(管理员)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jrkvij2j20z90e53zn.jpg)

- 我的文章(用户)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jpdjh9rj20xa0czgms.jpg)

- 撰写文章(用户)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jptahbnj20vv0ep7cl.jpg)

- 友情链接管理(管理员)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jwtgw75j20xb0fpq4j.jpg)

- 评论管理(用户)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jsdkn7cj20w90fjq5z.jpg)

- 媒体库(用户)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jqbrl9gj20uq0f57cm.jpg)

- 订阅管理

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jvf140ij20vc0elq3o.jpg)

- 个人资料(用户)/修改头像/修改密码

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jxww22tj20y70g4abg.jpg)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jybm9tdj20x60ghgqr.jpg)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3jzw3to4j20xq0h0dgu.jpg)

- 忘记密码

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3k1wn039j21fm0rf0tm.jpg)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1fu3k3us8ajj211i0gu75l.jpg)

---

> 网站尚且存在诸多问题。例如媒体库只能上传图片无法删除图片。例如写文章不能插入emoji表情，数据库还需要优化，黑名单功能还没有实现等等……

## TODO

- [ ] 黑名单功能
- [ ] 媒体库
    - [x] 上传
    - [x] 预览
    - [ ] 删除
    - [ ] 展示图片链接
- [ ] 评论
    - [ ] 分页
    - [ ] 审核
- [ ] 文章
    - [ ] 全文检索
    - [ ] 发布状态（草稿,已发布,待审核）
- [ ] 用户
    - [ ] 感觉可以再搞一个用户级别，介于管理员和普通用户之间,用于审核编辑用户的文章