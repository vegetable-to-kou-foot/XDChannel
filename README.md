# 一、需求分析及实现方式整理
## 登录/注册页
### 注册
L1：输入用户名、密码、确认密码，完成注册。
> 账户的CRUD：
> {/addAccount,/deleteAccount,/findAccount}
>
> 底层是一张Account表，记录与账户紧密相关的功能。不提供修改功能，因为每个属性的修改都涉及到校验，应当独立出来。
> 属性：[aid,accName,userPswd,email]

L2：在L1基础上增加验证码，并对提交次数过多的用户进行冻结操作。
全程加密。

L3：在注册时就要求绑定邮箱，并完成验证，以后可以用邮箱登录。

L4：保存cookies。防止各种攻击。

L5：补充一个登出和注销功能。

### 登录
L1：输入用户名密码，发送到后台，登录后跳转。
> {/login,/logout}

L2：在L1基础上增加登录次数过多弹出验证码，再连续登录失败就要冻结操作的功能。
全程加密。

L3：用邮箱也可以登录。同时用邮箱做密码找回。

L4：保存cookies。防止各种攻击。

## 个人主页
### 普通个人信息
L1：点修改，打开一个toast，修改完点确定，提交一张表到后台。提交头像。
> 用户信息的CRUD：
> {/editUserInfo,/findUserInfo}
> 
> 底层是一张UserInfo表，与账户同时创建同时销毁，因此这些操作是透明的，只有改查被允许调用。
> 属性：[aid,userInfo]
>
> userInfo列是JSON格式的，有一些默认属性，如头像位置、学号、姓名（建议拼音首字母）、学院、生日、爱好等属性。
> 创建时各栏为空。如果前端允许，可以进行任意的属性扩展。个人信息直接以JSON格式存入取出。

L2：对头像和个人信息做限制，防止某些攻击。

### 与登陆相关的个人信息
L1：点修改，打开一个toast，修改完点确定，提交一张表到后台。
> 作为“账户的CRUD”中查询的补充，应当分别提供对用户名、密码、邮箱的修改方法。
> {/editAccount/editUserName,/editAccount/editUserPswd,/editAccount/editUserEmail}
>
> 底层就是修改Account表。

L2：关于邮箱的修改，需要在两个邮箱里做验证。修改密码要重新登录。可以注销账号。

### 个人标签
L1：弹出规范，让用户自己编辑，说清楚与大家不统一的话会影响正常使用。
> 用户标签的CRUD：
> {/addUserTag,/deleteUserTag,/findUserTag}
> 
> 底层是一张UserTag表，不提供修改功能，要修改就删除再添加。
> 属性：[aid,userTag]。
> 
> userTag也是JSON格式的，允许无限扩展。
> findUserTag后期可以用知识图谱或一些更简单的算法实现模糊匹配，早期用精确匹配就行，
> 早期仅用于指示是否有人写过这个标签，没写过的话提示用户换一个。
> 早期的根据tag快速查人应该借助一张AccountTag表，[atid,tagInfo]。
>
> tagInfo是JSON格式的，形如{"aid_1":"value_1","aid_2":"value_2"}
> 
> userTag的具体实现：每个tag有名称有值，如“均分：80”，打包成JSON传给后台即可。
> 值主要在搜索时起作用，比如允许搜索均分>80且会打羽毛球的人。

L2：边写边匹配，可以直接选别人填过的。

L3：所有允许用户输入的地方都要做反黑！

### 发过的说说
L1：显示所有说说，包含点赞、评论、发送者等信息。
> 说说的CRUD：
> {/addBroadcast,/deleteBroadcast,/deleteBroadcast,/findBroadcast}
>
> 底层是UserBroadcast表，查询是根据aid查询该用户发过的说说，如果给出bid（说说id，broadcastid的缩写），
> 还能直接查到特定的说说，稍加扩展就能实现按点赞数查询、排序。返回时需要提供用户头像、账户名、点赞数等信息。
> 属性：[bid,fid,aid,broadcastScript,likeIt,review,broadcastTag,time,limit]
> 
> 其中review是JSON格式的，形如{"1":"52","2":"67"}表示一楼评论要在表里查bid=52的说说，二楼评论的bid为67。
> 评论功能正是复用说说功能实现的，一条评论可以视为一个说说。
>
> 这样就能理解fid，它表示这条说说是回复谁的。如果fid=0则是主动发送的说说；如果fid=30，则表示查询bid=30的说说就能看到这条说说是回复哪条说说的。
>
> limit表示限制，JSON格式，仅用于被查询后做过滤，不会成为被查询的条件。
>
> broadcastTag表示该说说具有的tag，JSON格式，应该与BroadcastTag表外联。

L2：完善功能，谁能看到这条消息，有什么TAG。
> broadcastTag就是以JSON的格式，表示这个说说有什么tag。
>
> 为实现根据tag的快速查询，底层需要维护另一张broadcastTag表，用于管理所有的说说tag。
> 属性：[btid,tagInfo]
>
> tagInfo是set格式的，形如{"btid_1","btid_2"}，没有取值，与用户tag不同。
> 
> 为了保证一致性，要用事务提交，并定期做一致性检查。

L3：反黑！注意代码注入、长度限制。

### 关注的人与被谁关注
L1：显示关注者与被关注者。
> 关注功能的CRUD：
> {/addFollower,/deleteFollower,/findFollower,/findBeFollowed}
>
> 底层是靠两张表，Follow，BeFollowed。Follow实现我关注了谁，BeFollowed实现谁关注了我。
> 
> Follow属性：[aid,followInfo]，followInfo格式为{"aid_1":"time_1","aid_2":"time_2"}。
>
> BeFollowed属性：[aid,beFollowedInfo]，beFollowedInfo格式同followInfo。注意维护一致性，并提供检验代码。

## 说说页
### 自己和关注者的说说
L1：显示出来，带上点赞评论头像昵称。
> 使用/findBroadcast，带上aid参数即可实现条件查询。

### 随机说说
L1：显示出来，带上点赞评论头像昵称。
> 随机生成bid，还是用/findBroadcast。

### 符合条件的说说
L1：显示出来，带上点赞评论头像昵称。
> 用/findBroadcast，带上限制即可。

## 消息页
### 系统通知
L1：显示标题和正文即可。
> 复用broadcast，借助BroadcastTag可以轻松实现，
> 只要给每个人分配一个带有特殊字符的btid，然后查就可以了。

### 被关注时、被@时通知
L1：显示标题和正文即可。
> 同理于系统通知。

## 关注页
### 我关注的
L1：带上头像和标签，显示出来就行。要有关注和取关功能。
> /findFollower

### 关注我的
L1：带上头像和标签，显示出来就行。
> /findBeFollowed

## 搜索模块
### 只有高级搜索
L1：搜人，要提供人的筛选要求；搜说说，要提供TAG或标题+正文内容的片段，应当支持常用的搜索符号，如空格表示与操作。
> 查人和查说说是两回事，需要分开实现。
