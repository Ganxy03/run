package cn.ganxy03.run.server;

import cn.ganxy03.run.entity.ManagerInfo;

import java.util.List;

public interface AccountServer {
//    创建账户
    boolean CreateAccount(String account);
//    删除账户
    void DelAccount(String account);
//    修改密码
    boolean RePwd(String account, String old, String pwd);
//    重置密码(默认密码: '@inc20240501.')
    void ResetPwd(String account);
//    修改资料
    void EditInfo(String account, String depart, String name, String mail);

//    登录
    String Login(String account, String pwd);
//    登出
    void Logout(String account);
//    验证身份
    boolean Verify(String account, String token);


//    获取账号信息
    ManagerInfo GetInfo(String account);

//    获取全部账号
    List<ManagerInfo> GetAll();

}
