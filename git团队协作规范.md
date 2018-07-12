# git 团队协作规范

零、前期准备

一、创建开发分支

[二、Fork项目到个人的仓库](#二fork项目到个人的仓库)

[三、Clone项目到本地](#三clone项目到本地)

[四、和团队项目保持同步](#四和团队项目保持同步)

[五、push修改到自己的项目上](#五push修改到自己的项目上)

[六、请求合并到团队项目上](#六请求合并到团队项目上)

七、团队项目负责人审核及同意合并请求

## 零、前期准备

## 一、创建开发分支

## 二、Fork项目到个人的仓库

点击右上角的Fork，并选择你的账号（一般在第一个）。就可以Fork团队项目到个人仓库啦。

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7ksx049tj20u70afgmc.jpg)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7ktfkmyej20ef07ut9e.jpg)

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7ku5k9v3j20se06vaae.jpg)

## 三、Clone项目到本地

- 克隆仓库的命令格式是 git clone [url]

```
use ssh
git clone git@github.com:ronething/notes.git
# use https
git clone https://github.com/ronething/notes.git
```

- 如果你想在克隆远程仓库的时候，自定义本地仓库的名字，你可以使用如下命令：

```
use ssh
git clone git@github.com:ronething/notes.git myproject
use https
git clone https://github.com/ronething/notes.git myproject
```

具体参考:[Git基础-获取Git仓库](https://notes.ronething.cn/git-ban-ben-kong-zhi-xi-tong/git-ji-chu#21-git-ji-chu-huo-qu-git-cang-ku)

克隆完成后发现本地只有`master`分支，用`git branch`可以查看。

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7l2mjgbej20cd01ca9t.jpg)

此时只有`master`一个分支。

用`git branch -a`可以查看所有分支，包括远程分支。

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7l419guhj20c802m742.jpg)

发现有`origin/dev`分支。

根据远程分支，我们可以创建一个新的本地分支dev，并把该项目的dev分支的内容放到本地dev分支

使用命令`git checkout -b dev origin/dev`。意思是创建一个dev分支（-b），并把远程dev分支（origin/dev）的内容放在该分支内。接着切换到该分支（checkout）

再次使用`git branch`

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7l6myqh5j20ff03gdfn.jpg)

如果要切换回`master`分支，使用`git checkout master`

## 四、和团队项目保持同步

首先查看有没有设置  `upstream`，使用`git remote -v`命令来查看。

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7l8pfxgoj20d1021t8i.jpg)

发现并没有。

如果没有显示`upstream`，则使用 `git remote add upstream 团队项目地址`添加

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7lag23cjj20hx047a9x.jpg)

开始同步。首先执行 `git fetch upstream` 获取团队项目最新版本

此时并没有把最新版本合并到你本地的分支上，因此还需要一步。当前分支是dev分支，执行 `git merge upstream/dev` 命令后，会将源分支（upstream/dev）合并到当前分支（dev）。

- [合并时冲突的解决](http://www.cnblogs.com/schaepher/p/4970291.html#conflict)

## 五、push修改到自己的项目上

解决冲突后，就可以使用 `git push` 命令将本地的修改同步到自己的GitHub仓库上了。

注意，在当前所在分支使用push，会push到与这个分支相关联的远程仓库分支。这里dev分支与origin/dev关联，因此push到GitHub上的dev分支。

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7liqvsrej20it0lmt9r.jpg)

## 六、请求合并到团队项目上

首先到你的GitHub上，进入你Fork的仓库里。点击箭头处的Pull request

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7ljvnsq7j20td0dfmy8.jpg)

下图左边，表示要合并到ssm-scauGroup/git-learn项目的dev分支。
下图右边，表示要从自己仓库的dev分支发起合并请求。

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7lm3k32zj20u70hv400.jpg)

点击`Create pull request`就可以发送合并请求了。

然后就会显示如下界面。接着就等着负责人`merge pull request` 了。

![](https://ws1.sinaimg.cn/large/ecb0a9c3gy1ft7lrgvuk7j20rz0ck75l.jpg)

## 七、团队项目负责人审核及同意合并请求