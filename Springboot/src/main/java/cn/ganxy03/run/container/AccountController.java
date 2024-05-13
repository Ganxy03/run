package cn.ganxy03.run.container;

import cn.ganxy03.run.server.AccountServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {
    private final AccountServer accountServer;

    @Autowired
    public AccountController(AccountServer accountServer) {
        this.accountServer = accountServer;
    }

//    创建账户
//    @Operation(summary = "创建账户")
    @ApiOperation(value = "创建账户", notes = "这是一个获取用户列表的API")
    @PostMapping("create")
    public ResponseEntity<String> CreateAccount(@RequestParam String account) {
        if(accountServer.CreateAccount(account)) {
            return new ResponseEntity<>("Create success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Account have exited", HttpStatus.OK);
    }

//    删除账户
    @ApiOperation(value = "删除账户", notes = "这是一个获取用户列表的API")
    @PostMapping("delete")
    public ResponseEntity<String> DeleteAccount(@RequestParam String account) {
        accountServer.DelAccount(account);
        return new ResponseEntity<>("Delete success", HttpStatus.OK);
    }

//    修改密码
    @ApiOperation(value = "修改密码", notes = "这是一个获取用户列表的API")
    @PostMapping("rePwd")
    public ResponseEntity<String> RePwd(@RequestParam String account, @RequestParam String old, @RequestParam String pwd) {
        if(accountServer.RePwd(account, old, pwd)) {
            return new ResponseEntity<>("Change success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Old is wrong", HttpStatus.OK);
    }

//    重置密码
    @ApiOperation(value = "重置密码", notes = "这是一个获取用户列表的API")
    @PostMapping("resetPwd")
    public ResponseEntity<String> ResetPwd(@RequestParam String account) {
        accountServer.ResetPwd(account);
        return new ResponseEntity<>("Reset success", HttpStatus.OK);
    }

//    修改资料
    @ApiOperation(value = "修改资料", notes = "这是一个获取用户列表的API")
    @PostMapping("editInfo")
    public ResponseEntity<String> EditInfo(@RequestParam String account, @RequestParam String depart, @RequestParam String name, @RequestParam String mail) {
        accountServer.EditInfo(account, depart, name, mail);
        return new ResponseEntity<>("Edit success", HttpStatus.OK);
    }

//    登录
    @ApiOperation(value = "登录", notes = "这是一个获取用户列表的API")
    @PostMapping("login")
    public ResponseEntity<String> Login(@RequestParam String account, @RequestParam String pwd) {
        String result = accountServer.Login(account, pwd);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
//    登出
    @ApiOperation(value = "登出", notes = "这是一个获取用户列表的API")
    @PostMapping("logout")
    public ResponseEntity<String> Logout(@RequestParam String account) {
        accountServer.Logout(account);
        return new ResponseEntity<>("Logout success", HttpStatus.OK);
    }
//    验证身份
    @ApiOperation(value = "验证身份", notes = "这是一个获取用户列表的API")
    @PostMapping("verify")
    public ResponseEntity<String> Verify(@RequestParam String account, @RequestParam String token) {
        if(accountServer.Verify(account, token)) {
            return new ResponseEntity<>("Valid identity", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid identity", HttpStatus.OK);
        }
    }

//    获取账号信息
    @ApiOperation(value = "获取账号信息", notes = "这是一个获取用户列表的API")
    @PostMapping("getInfo")
    public ResponseEntity<?> GetInfo(@RequestParam String account) {
        return new ResponseEntity<>(accountServer.GetInfo(account), HttpStatus.OK);
    }


//    获取所有账号
    @ApiOperation(value = "获取所有账号", notes = "这是一个获取用户列表的API")
    @PostMapping("getAll")
    public ResponseEntity<List> GetAll() {
        return new ResponseEntity<>(accountServer.GetAll(), HttpStatus.OK);
    }
}


