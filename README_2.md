# 二、接口及数据格式
> 如果README.md与README_2.md内容有出入，以README_2.md为准。
## 账户CRUD
/addAccount:验收通过
> I:{"accName":"xxx","userPswd":"xxx","email":"xxx"}
>
> O:{"success":"0 or 1"}

/deleteAccount
> I:{"accName":"xxx","userPswd":"xxx","email":"xxx"}
> 
> O:{"success":"0 or 1"}
>
> 必须三项都对，而且通过邮箱验证才能删。（防止邮箱验证的实现出现问题，这样黑客就能删掉全部账户）

/findAccount
> I:{"email":"xxx"}
>
> O:{"success":"0 or 1","account":{"aid":"xxx","accName":"xxx","userPswd":"xxx","email":"xxx"}}
>
> 根据email查账户是否存在，提供注册功能时有用。

/editAccount/editUserName
> I:{"aid":"xxx","ssid":"xxx","accName":"xxx"}
>
> O:{"success":"0 or 1","errCode":"xxx"}
>
> errCode仅当success=0时有效，可能取值为：["Login","Timeout","Ssid wrong","Exception"]
>
> Login:数据库中没有此人的ssid，说明此人当前未登录，应跳转至登录页面。
>
> Timeout:ssid有效时间是5小时，出现该错误说明超时了，要重新登录。
>
> Ssid wrong:提供的ssid与数据库中的对不上。
>
> Exception:出现异常，需要联系管理员。

/editAccount/editUserPswd
> I:{"aid":"xxx","ssid":"xxx","oldPassword":"xxx","newPassword":"xxx"}
>
> O:{"success":"0 or 1","errCode":"xxx"}
>
> 修改密码的时候要提供旧密码，因为用户离开电脑的时候，可能会有人直接以用户的身份修改密码。
>
> errCode仅当success=0时有效，可能取值为：["Login","Timeout","Ssid wrong","Password wrong","Exception"]
>
> Password wrong:密码错误。


`/editAccount/editUserEmail`:计划取消，可以用先注销后注册来替代
> {"aid":"xxx","ssid":"xxx","old_email":"xxx"}
> {"aid":"xxx","ssid_2":"xxx","new_email":"xxx"}
>
> 修改邮箱必须先点击旧邮箱的链接进入邮箱修改页，然后输入新邮箱并点击新邮箱的链接。
> 这里的安全性需要仔细考虑，但是因为功能相对独立，可以等会儿再实现。

/login:验收通过
> I:{"email":"xxx","password":"xxx"}
>
> 用MD5将"email+password"加密加密至32位，作为password传过来
>
> O:{"success":"0 or 1","aid":"xxx","ssid":"xxx","errCode":"xxx"}
>
> 因为允许重名，所以不能使用user_name登录。
> 登录后将返回aid、ssid。
>
> errCode仅当success=0时有效，可能取值为：["Password wrong","Exception"]

/logout
> 前端自己就能解决，cookies一删，页面一跳就行。

    Account[aid,accName,userPswd,email]

## 个人信息CRUD
/editProfilePic
> 暂时不知道怎么实现，应该有一个专门的文件提交方法。

/findProfilePic
> 获取头像

**要实现这个，还要修改数据库**

/editUserInfo:验收通过
> I:{"aid":"xxx","ssid":"xxx","userInfo":"打包成JSON格式的字符串"}
>
> O:{"success":"0 or 1"}
>
> aid的保存需要依赖于cookie，但是只有aid就能改的话那数据包很容易被伪造。
> 一般解决办法是对一个连接分配一个短时间有效的随机数，随机数对上了才能提供服务。
> 这个随机数就叫ssid吧。

/findUserInfo:验收通过
> I:{"aid":"xxx","ssid":"xxx"}
>
> O:{"success":"0 or 1","userInfo":"打包成JSON的字符串"}

    UserInfo[aid,userInfo]
    AidSsid[aid,ssid,time]

## 用户标签CRUD
/addUserTag:验收通过
> I:{"aid":"xxx","ssid":"xxx","tagName":"xxx","tagValue":"xxx"}
>
> O:{"success":"0 or 1"}

/deleteUserTag:验收通过
> I:{"aid":"xxx","ssid":"xxx","tagName":"xxx"}
>
> O:{"success":"0 or 1"}

/findUserTag:验收通过
> I:{"aid":"xxx","ssid":"xxx"}
>
> O:{"success":"0 or 1","userTag":"JSON"}
>
> 查询这个人的所有tag和值

/findAccountTag:验收通过
> I:{"aid":"xxx","ssid":"xxx","tagName":"xxx"}
>
> O:{"success":"0 or 1","accTags":["TagA","TagB"]}
>
> 用于查询是否存在以请求的tag开头的tag。
> 前端需要用户敲一个字就查一下或隔3s查一下。

    UserTag[aid,userTag]
    AccountTag[atid,aTagName,aTagInfo]

## 说说（广播）CRUD
/addBroadcast:验收通过
> I:{"aid":"xxx","ssid":"xxx","fid":"xxx","bcScript":"xxx",
> "bcTag":"xxx","timestp":"xxx","limits":"xxx"}
>
> O:{"success":"0 or 1"}
>
> 因为回复和发说说共用了这个接口，所以需要提供fid。

/deleteBroadcast:验收通过
> I:{"aid":"xxx","ssid":"xxx","bid":"xxx"}
>
> O:{"success":"0 or 1"}

/editBroadcast:验收通过
> I:{"aid":"xxx","ssid":"xxx","bid":"xxx",~~"bcScript":"xxx",
> "bcTag":"xxx","time":"xxx",~~"limits":"xxx"}
>
> O:{"success":"0 or 1"}

/findBroadcast:验收通过
> I:{~~"bid":"xxx",~~"bid_op":"任何允许的符号，如>","bid_val":"xxx",
> ~~"fid":"xxx",~~"fid_op":"任何允许的符号，如>","fid_val":"xxx",
> aid/broadcastScript/likeIt/timestp同上,
> "bcTag":["tag_1","tag_2"],
> "numPerPage":xxx,"page":xxx
> }
>
> O:{"success":"0 or 1","broadCasts":[
> {"bid":"xxx","fid":"xxx","broadcastScript":"xxx",
> "broadcastTag":"xxx","timestp":"xxx","limits":"xxx"},{同上一个}
> ]}
>
> 该接口将复用于显示说说和按条件查询说说。
>
> bid取值为-1时，表示按后面的条件查询；bid取值有意义时，后面的条件无意义。
> fid/aid/broadcastScript/likeIt/time同理。
>
> limit应永远生效，因为如果有人发布了一个说说，但不希望均分小于65的人看到，
> 那么均分小于65的人不光在自己的说说页看不到这条说说，在搜索页也不应该看到。
>
> broadcastTag用于指定包含的标签，由于标签中不允许出现~之类的特殊符号，
> 所以如果某个tag以~开头，表示“不包含这个标签”。

    UserBroadcast[bid,fid,aid,broadcastScript,likeIt,review,broadcastTag,timestp,limits]
    BroadcastTag[btid,bTagName,bTagInfo]

## 关注CRUD
/addFollower
> I:{"aid":"xxx","ssid":"xxx","follow_aid","xxx"}
>
> O:{"success":"0 or 1"}

/deleteFollower
> I:{"aid":"xxx","ssid":"xxx","follow_aid","xxx"}
>
> O:{"success":"0 or 1"}

/findFollower
> I:{"aid":"xxx","ssid":"xxx","numPerPage":xxx,"page":xxx}
>
> O:{"success":"0 or 1","followers":[Account_1,Account_2]}

/findBeFollowed
> I:{"aid":"xxx","ssid":"xxx","numPerPage":xxx,"page":xxx}
>
> O:{"success":"0 or 1","beFollowed":[Account_1,Account_2]}

    Follow[aid,followInfo]
    BeFollowed[aid,beFollowedInfo]
    
## 搜索模块
### 查说说
/findBroadcast
> 见上面，说说相关的万能查询接口

### 查用户
/findUser
> I:{"aid":"xxx",
> "userInfo":{
> "prop_1":"xxx","prop_2":"xxx"（只能精确匹配，不能比大小）
> },
> "userTag":"[
>   {"tag":"xxx","tagOp":"允许的操作符","tagVal":"xxx"},
>   {"tag":"xxx","tagOp":"允许的操作符","tagVal":"xxx"},...
>   ]",
> "numPerPage":xxx,"page":xxx
> }
>
> O:{"success":"0 or 1","users":[UserInfo_1,UserInfo_2]}
>
> aid/user_name（姓名缩写）/user_school_id（学号）/email只支持精确匹配，
> 这是为了有人捡到校园卡之后能直接联系到失主。
>
> user_tag语法与/findBroadcast一样。
> 
> 换言之，用户搜索只能根据aid、姓名缩写、学号、email、自己添加的tag来查询。