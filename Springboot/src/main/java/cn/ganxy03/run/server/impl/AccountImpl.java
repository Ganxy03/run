package cn.ganxy03.run.server.impl;

import cn.ganxy03.run.config.RedisUtil;
import cn.ganxy03.run.entity.ManagerAccount;
import cn.ganxy03.run.entity.ManagerInfo;
import cn.ganxy03.run.repository.ManagerAccountRepository;
import cn.ganxy03.run.repository.ManagerInfoRepository;
import cn.ganxy03.run.repository.ManagerSecureRepository;
import cn.ganxy03.run.server.AccountServer;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
public class AccountImpl implements AccountServer {


    Integer sum = 0;


    private final ManagerAccountRepository managerAccountRepository;
    private final ManagerInfoRepository managerInfoRepository;
    private final ManagerSecureRepository managerSecureRepository;
    private final RedisUtil redisUtil;

    public AccountImpl(ManagerAccountRepository managerAccountRepository, ManagerInfoRepository managerInfoRepository, ManagerSecureRepository managerSecureRepository, RedisUtil redisUtil) {
        this.managerAccountRepository = managerAccountRepository;
        this.managerInfoRepository = managerInfoRepository;
        this.managerSecureRepository = managerSecureRepository;
        this.redisUtil = redisUtil;
    }

    //    创建账户
    public boolean CreateAccount(String account) {
        if(managerAccountRepository.findByAccount(account) == null){
            ManagerAccount managerAccount = new ManagerAccount();
            managerAccount.setAccount(account);
            managerAccount.setPwd(sha3224("52654"));
            managerAccountRepository.save(managerAccount);
            ManagerInfo managerInfo = new ManagerInfo();
            managerInfo.setAccount(account);
            managerInfo.setRole(0);
            return true;
        }
        return false;
    }
    //    删除账户
    public void DelAccount(String account) {
        managerAccountRepository.delete(managerAccountRepository.findByAccount(account));
    }
    //    修改密码
    public boolean RePwd(String account, String old, String pwd) {
        System.out.println(pwd);
        ManagerAccount target = managerAccountRepository.findByAccount(account);
        if(target.getPwd().equals(sha3224(old))) {
            target.setPwd(sha3224(pwd));
            managerAccountRepository.save(target);
            return true;
        }
        return false;
    }
    //    重置密码(默认密码: "@inc20240501.")
    public void ResetPwd(String account) {
        ManagerAccount target = managerAccountRepository.findByAccount(account);
        target.setPwd(sha3224("@inc20240501."));
        managerAccountRepository.save(target);
    }
    //    修改资料
    public void EditInfo(String account, String depart, String name, String mail) {
        ManagerInfo target = managerInfoRepository.findByAccount(account);
        if(target != null) {
            target.setDepart(depart);
            target.setName(name);
            target.setMail(mail);
            managerInfoRepository.save(target);
        } else {
            ManagerInfo managerInfo = new ManagerInfo();
            managerInfo.setAccount(account);
            managerInfo.setDepart(depart);
            managerInfo.setName(name);
            managerInfo.setMail(mail);
            managerInfo.setRole(0);
            managerInfoRepository.save(managerInfo);
        }
    }

    //    登录
    public String Login(String account, String pwd) {
        sum = sum + 1;
        System.out.println(pwd);
        System.out.println(sum);
        if(managerAccountRepository.findByAccount(account).getPwd().equals(sha3224(pwd))) {
            String token = UUID.randomUUID().toString();
            redisUtil.selectDatabase(7);
            redisUtil.set(account, token, 1800);
            return token;
        }
        return "Account not exited or pwd is wrong";
    }
    //    登出
    public void Logout(String account) {
        redisUtil.selectDatabase(7);
        redisUtil.delete(redisUtil.get(account));
    }
    //    验证身份
    public boolean Verify(String account, String token) {
        redisUtil.selectDatabase(7);
        String verify = redisUtil.get(account);
        if(verify == null) {
            return false;
        } else if(verify.equals(token)){
            System.out.println(token);
            System.out.println(verify);
            return true;
        }
        return false;
    }

    public ManagerInfo GetInfo(String account) {
        return managerInfoRepository.findByAccount(account);
    }

    public List<ManagerInfo> GetAll() {
        return managerInfoRepository.findAll();
    }


    //密码SHA-3加密
    public static String sha3224(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA3-224");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

