# 二、接口及数据格式
> 如果README.md与README_2.md内容有出入，以README_2.md为准。
## 账户CRUD
/addAccount
> {"user_name":"xxx","password":"xxx","email","xxx"}

/deleteAccount
> {"user_name":"xxx","password":"xxx","email","xxx"}
> 
> 必须三项都对，而且通过邮箱验证才能删。（防止邮箱验证的实现出现问题，这样黑客就能删掉全部账户）

/findAccount
> {"email","xxx"}
>
> 根据email查账户是否存在，提供注册功能时有用。

/editAccount/editUserName
> {"aid":"xxx","ssid":"xxx","user_name":"xxx"}

/editAccount/editUserPswd
> {"aid":"xxx","ssid":"xxx","old_password":"xxx","new_password":"xxx"}
>
> 修改密码的时候要提供旧密码，因为用户离开电脑的时候，可能会有人直接以用户的身份修改密码。

/editAccount/editUserEmail
> {"aid":"xxx","ssid":"xxx","old_email":"xxx"}
> {"aid":"xxx","ssid_2":"xxx","new_email":"xxx"}
>
> 修改邮箱必须先点击旧邮箱的链接进入邮箱修改页，然后输入新邮箱并点击新邮箱的链接。
> 这里的安全性需要仔细考虑，但是因为功能相对独立，可以等会儿再实现。

    Account[aid,accName,userPswd,email]

## 个人信息CRUD
/editProfilePic
> 暂时不知道怎么实现，应该有一个专门的文件提交方法。

/editUserInfo
> {"aid":"xxx","ssid":"xxx","user_info":"打包成JSON格式的字符串"}
>
> aid的保存需要依赖于cookie，但是只有aid就能改的话那数据包很容易被伪造。
> 一般解决办法是对一个连接分配一个短时间有效的随机数，随机数对上了才能提供服务。
> 这个随机数就叫ssid吧。

/findUserInfo
> {"aid":"xxx","ssid":"xxx"}

    UserInfo[aid,userInfo]
    UserSsid[aid,ssid]

## 用户标签CRUD
/addUserTag
> {"aid":"xxx","ssid":"xxx","tag_name":"xxx","tag_value":"xxx"}

/deleteUserTag
> {"aid":"xxx","ssid":"xxx","tag_name":"xxx"}

/findUserTag
> {"aid":"xxx","ssid":"xxx","tag_name":"xxx"}
>
> 前端需要用户敲一个字就查一下或隔3s查一下

    UserTag[aid,userTag]
    AccountTag[atid,aTagName,aTagInfo]

## 说说（广播）CRUD
/addBroadcast
> {"aid":"xxx","ssid":"xxx","fid":"xxx","broadcastScript":"xxx",
> "broadcastTag":"xxx","time":"xxx","limit":"xxx"}
>
> 因为回复和发说说共用了这个接口，所以需要提供fid。

/deleteBroadcast
> {"aid":"xxx","ssid":"xxx","bid":"xxx"}

/editBroadcast
> {"aid":"xxx","ssid":"xxx","bid":"xxx","broadcastScript":"xxx",
> "broadcastTag":"xxx","time":"xxx","limit":"xxx"}

/findBroadcast
> {"bid":"xxx","bid_op":"任何允许的符号，如>","bid_val":"xxx",
> "fid":"xxx","fid_op":"任何允许的符号，如>","fid_val":"xxx",
> aid/broadcastScript/likeIt/time同上,
> "broadcastTag":"{"tag_1","tag_2"}"}
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

    UserBroadcast[bid,fid,aid,broadcastScript,likeIt,review,broadcastTag,time,limit]
    BroadcastTag[btid,bTagName,bTagInfo]

## 关注CRUD
/addFollower
> {"aid":"xxx","ssid":"xxx","follow_aid","xxx"}

/deleteFollower
> {"aid":"xxx","ssid":"xxx","follow_aid","xxx"}

/findFollower
> {"aid":"xxx","ssid":"xxx"}

/findBeFollowed
> {"aid":"xxx","ssid":"xxx"}

    Follow[aid,followInfo]
    BeFollowed[aid,beFollowedInfo]
    
## 搜索模块
### 查说说
/findBroadcast
> 见上面，说说相关的万能查询接口

### 查用户
/findUser
> {"aid":"xxx","user_name":"xxx","user_school_id":"xxx","email":"xxx",
> "user_tag":"{
>   "tag_1":"xxx","tag_1_op":"允许的操作符","tag_1_val":"xxx",
>   "tag_2":"xxx","tag_2_op":"允许的操作符","tag_2_val":"xxx",...
>   }"}
>
> aid/user_name（姓名缩写）/user_school_id（学号）/email只支持精确匹配，
> 这是为了有人捡到校园卡之后能直接联系到失主。
>
> user_tag语法与/findBroadcast一样。
> 
> 换言之，用户搜索只能根据aid、姓名缩写、学号、email、自己添加的tag来查询。